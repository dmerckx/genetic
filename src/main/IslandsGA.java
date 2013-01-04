package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class IslandsGA {
	
    private GA<?>[] gas; 
	private CommunicationModel model;
	
	public IslandsGA(Problem problem, GA<?>... gas) {
		this.gas = gas;
	}
	
	public void run(Problem problem, History history, int nrTimes){
		List<List<History>> fullHistories = new ArrayList<List<History>>();
		List<History> histories = new ArrayList<History>();
		
		for(int i=0; i < nrTimes; i++){
			History h = new History();
			histories.add(h);
			
			fullHistories.add( run(problem, h) );
		}
		history.mergeFrom(histories);
		
		
		/*List<History> fullHistory = new ArrayList<History>();
		for(int island = 0; island < gas.length; island++){
			History h = new History();
			
			List<History> historiesToMerge = new ArrayList<History>();
			
			//Merge the results for island X together from all different runs
			for(int t = 0; t < nrTimes; t++){
				historiesToMerge.add(fullHistories.get(t).get(island));
			}
			
			fullHistory.add(h);
		}
		return fullHistory;*/
	}
	
	public List<History> run(final Problem problem, History history){
		this.model = new CommunicationModel(problem, gas.length);
		List<History> histories = new ArrayList<History>();
		Map<GA<?>, Communicator<?>> comms = new HashMap<GA<?>, Communicator<?>>();
		
		final CountDownLatch latch = new CountDownLatch(gas.length);
		
		for(int i = 0; i < gas.length; i++){
			comms.put(gas[i], model.forge(gas[i].factory, i));
		}
		
		for(final GA<?> ga:gas){
			final History h = new History();
			histories.add(h);
			final Communicator comm = comms.get(ga);
			
			Thread task = new Thread() {
                @Override
                public void run() {
                	//System.out.println("running !!");
        			ga.run(problem, h, comm);
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
        return histories;
	}
}