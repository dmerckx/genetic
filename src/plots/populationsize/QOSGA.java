package plots.populationsize;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.Communicator;
import main.GA;
import main.History;
import main.LoopDetection;
import main.Problem;
import main.RankedChrom;
import main.crossover.CrossOver;
import main.insertion.ReInsertor;
import main.mutation.Mutator;
import main.rankers.Ranker;
import main.selectors.Selector;
import params.Params;
import representations.Chromosome;
import factory.RepresentationFactory;


public class QOSGA<R extends Chromosome> extends GA<R>{

	private double qos;
	
	public QOSGA(Params params, RepresentationFactory<R> factory, Selector<R> selector,
			CrossOver<R> crossover, ReInsertor<R> insertor, Mutator<R> mutator, Ranker<R> ranker, LoopDetection<R> loopDetector, double qos) {
		super(params, factory, selector, crossover, insertor, mutator, ranker, loopDetector);
		this.qos = qos;
	}
	
	public int runQOS(Problem problem, History history){
		return runQOS(problem, history, null);
	}
	
	public List<Integer> runQOS(Problem problem, History history, int nrTimes){
		
		List<History> histories = new ArrayList<History>();
		List<Integer> result = new ArrayList<Integer>();
		for(int i=0; i < nrTimes; i++){
			History h = new History();
			histories.add(h);
			result.add(runQOS(problem, h, null));
			System.out.println("completed run: " + (i+1) + " out of " + nrTimes);
		}
		return result;
	}
	
	public int runQOS(Problem problem, History history, Communicator<R> comm){
		
		long startTime = System.nanoTime();
		
		List<R> pop = initPopulation(problem);
		int i = 0;
		while(pop.get(0).getPathLength() > qos){
			
			if(i >= 20000) {
				System.out.println("break!");
				break;
			}
			
			double elapsed = (System.nanoTime()-startTime);
			elapsed = elapsed/(60*Math.pow(10,9));
//			System.out.println("elapsed: " + elapsed + " generation nb: " + i);
			if(elapsed >= params.simulationTime)
				break;
			
			Collections.sort(pop);
			
			double best = pop.get(0).getPathLength();
			
			List<RankedChrom<R>> rankedPop = ranker.rank(pop);
			
			if(checkStop(best, pop)) {
				System.out.println("stop criterium triggerd!");
				break;
			}
			
			List<R> selection = selector.doSelection(rankedPop);
			
			List<R> children = crossover.doCrossOver(selection);
			
			mutate(children);
			
			pop = insertor.reinsert(rankedPop, children);
			
			if(params.detectLoops)
				doLoopDetection(pop);
			
			if(comm != null && (i+1) % params.migrationFreq == 0){
				Collections.sort(pop);
				sendMigrants(comm, pop);
				pop = addMigrants(pop, comm.getMessages());
			}
			
			i++;
		}
		System.out.println("number of generations needed to reach a qos of "  + qos + " is: " + i);
		return i;
	}
	
	private void doLoopDetection(List<R> pop) {
		for (R chrom: pop) {
			loopDetector.correct(chrom);
		}
	}

	private double calculateMean(List<R> pop){
		double total = 0;
		for(R chrom:pop){
			total += chrom.getPathLength();
		}
		
		return total / pop.size();
	}
	
	private boolean checkStop(double best, List<R> pop){
		int index = 0;
		for(R r: pop){
			if(Math.abs(r.getPathLength() - best) <= params.mach+5)
				index++;
		}
		return index > params.stop * pop.size();
	}
	
	private void mutate(List<R> selection){
		for(R chrom : selection){
			float next = params.rand.nextFloat();
			//System.out.println(next + " < " + params.mutation + " ? ");
			if(next < params.mutation )
				mutator.mutate(chrom);
		}
	}
	
	public List<R> initPopulation(Problem problem){
		List<R> result = new ArrayList<R>();
		
		for(int i=0; i < params.popSize; i++){
			R chrom = factory.create(problem);
			chrom.setRandom(params.rand);
			result.add(chrom);
		}
		
		return result;
		
	}
	
	public List<R> addMigrants(List<R> pop, List<R> migrants){
		if(migrants.size() > pop.size()){
			return migrants.subList(0, pop.size());
		}
		
		List<R> newPop = new ArrayList<R>();
		
		newPop.addAll(migrants);
		newPop.addAll(pop.subList(0, pop.size() - migrants.size()));
		
		if(newPop.size() != params.popSize)
			throw new IllegalStateException();
		
		return newPop;
	}
	
	public void sendMigrants(Communicator<R> comm, List<R> pop){
		int nrRens = (int) (params.migration * params.popSize + 0.5);
		
		//System.out.println("pop size:" + pop.size() + " nrRens: " + nrRens);
		for(int i = 0; i < nrRens; i++){
			comm.send(pop.get(i));
		}
	}
}
