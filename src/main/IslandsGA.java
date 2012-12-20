package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import representations.Chromosome;

public class IslandsGA {
	
    private GA<?>[] gas; 
	private List<History> histories = new ArrayList<History>();
	private CommunicationModel model;
	
	public IslandsGA(Problem problem, GA<?>... gas) {
		this.gas = gas;
		this.model = new CommunicationModel(problem, gas.length);
	}
	
	public void run(final Problem problem, History history){
		final CountDownLatch latch = new CountDownLatch(gas.length);
		
		for(final GA<?> ga:gas){
			Thread task = new Thread() {
                @Override
                public void run() {
                	System.out.println("running !!");
        			runGa(ga, problem);
        			latch.countDown();
                }
            };
            task.start();
		}
        
        try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        //At this point all gas are done running
        
        System.out.println("nr of histories: " + histories.size());
        
        int size = histories.get(0).size();
        for(int i = 0; i < size; i++){
        	double worst = histories.get(0).worstList.get(i);
        	double best = histories.get(0).bestList.get(i);
        	double mean = histories.get(0).meanList.get(i);
        	
        	for(History h:histories.subList(1, histories.size())){
        		worst = Math.max(worst, h.worstList.get(i));
        		best = Math.min(best, h.bestList.get(i));
        		mean += h.meanList.get(i);
        	}
        	mean = mean / histories.size();
        	
        	history.write(best, mean, worst);
        }
        
        histories.get(0).printShort();
        histories.get(1).printShort();
        
        System.out.println("done!");
	}
	
	public <R extends Chromosome> void runGa(GA<R> ga, Problem problem){
		Communicator<R> comm = model.forge(ga.factory);
		History history = new History();
		addHistory(history);
		ga.run(problem, history, comm);
		System.out.println("done running");
	}
	
	public synchronized void addHistory(History history){
		histories.add(history);
	}

}