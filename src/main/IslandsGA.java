package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import representations.Chromosome;

public class IslandsGA {
	
    private GA<?>[] gas; 
	private CommunicationModel model;
	
	public IslandsGA(Problem problem, GA<?>... gas) {
		this.gas = gas;
	}
	
	public void run(Problem problem, History history, int nrTimes){
		List<History> histories = new ArrayList<History>();
		
		for(int i=0; i < nrTimes; i++){
			History h = new History();
			histories.add(h);
			
			this.model = new CommunicationModel(problem, gas.length);
			
			run(problem, h);
			
			//System.out.println("result: " + h.getLastBest());
			
			//h.printShort();
			
			//System.out.println("completed run: " + (i+1));
		}
		
		for(int i=0; i < gas[0].params.maxGenerations; i++){
			double best = 0;
			double worst = 0;
			double mean = 0;
			for(History h:histories){
				if(i == 50){
				}
				
				best += h.bestList.get(i);
				worst += h.worstList.get(i);
				mean += h.meanList.get(i);
			}
			
			history.write(best/nrTimes, mean/nrTimes, worst/nrTimes);
		}
	}
	
	public void run(final Problem problem, History history){
		List<History> histories = new ArrayList<History>();
		
		final CountDownLatch latch = new CountDownLatch(gas.length);
		
		for(final GA<?> ga:gas){
			final History h = new History();
			histories.add(h);
			final Communicator<?> comm = model.forge(ga.factory);
			
			Thread task = new Thread() {
                @Override
                public void run() {
                	//System.out.println("running !!");
        			runGa(ga, problem, comm, h);
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
        
        //System.out.println("nr of histories: " + histories.size());
        
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
        
        //histories.get(0).printShort();
        //histories.get(1).printShort();
        
        //System.out.println("done!");
	}
	
	public <R extends Chromosome> void runGa(GA ga, Problem problem, Communicator comm, History history){
		ga.run(problem, history, comm);
		//System.out.println("done running");
	}
}