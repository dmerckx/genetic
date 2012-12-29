package main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.crossover.CrossOver;
import main.insertion.ReInsertor;
import main.mutation.Mutator;
import main.rankers.Ranker;
import main.selectors.Selector;
import params.Params;
import representations.Chromosome;
import factory.RepresentationFactory;


public class GA<R extends Chromosome> {

	protected Params params;
	public final RepresentationFactory<R> factory;
	
	private final Selector<R> selector;
	private final CrossOver<R> crossover;
	private final ReInsertor<R> insertor;
	private final Mutator<R> mutator;
	private final Ranker<R> ranker;
	private final LoopDetection<R> loopDetector;
	
	//public Timer timer = new Timer();
	
	public GA(Params params, RepresentationFactory<R> factory, Selector<R> selector,
			CrossOver<R> crossover, ReInsertor<R> insertor, Mutator<R> mutator, Ranker<R> ranker, LoopDetection<R> loopDetector) {
		this.params = params;
		this.factory = factory;
		
		this.selector = selector;
		this.crossover = crossover;
		this.insertor = insertor;
		this.mutator = mutator;
		this.ranker = ranker;
		this.loopDetector = loopDetector;
	}
	
	public void run(Problem problem, History history){
		run(problem, history, null);
	}
	
	public void run(Problem problem, History history, int nrTimes){
		
		List<History> histories = new ArrayList<History>();
		
		for(int i=0; i < nrTimes; i++){
			History h = new History();
			histories.add(h);
			run(problem, h, null);
			
			System.out.println("completed run: " + (i+1));
		}
		
		for(int i=0; i < params.maxGenerations; i++){
			double best = 0;
			double worst = 0;
			double mean = 0;
			for(History h:histories){
				best += h.bestList.get(i);
				worst += h.worstList.get(i);
				mean += h.meanList.get(i);
			}
			history.write(best/nrTimes, mean/nrTimes, worst/nrTimes);
		}
	}
	
	public void run(Problem problem, History history, Communicator<R> comm){
		
		List<R> pop = initPopulation(problem);
		double best = 0;
		int i = 0;
		while(i < params.maxGenerations){
			//timer.start();
			
			Collections.sort(pop);
			
			best = pop.get(0).getPathLength();
			double worst = pop.get(pop.size()-1).getPathLength();
			double mean = calculateMean(pop);
			history.write(best, mean, worst);
			
			//if(checkStop(best, pop)) break;
			
			List<RankedChrom<R>> rankedPop = ranker.rank(pop);
			//timer.addRankingTime();
			
			List<R> selection = selector.doSelection(rankedPop);
			//timer.addSelectionTime();
			if(i == 50){
				Collections.sort(selection);
				System.out.println("selection: " + best + " " + selection.get(0).getPathLength());
			}
			
			List<R> children = crossover.doCrossOver(selection);
			//timer.addCrossTime();
			if(i == 50){
				Collections.sort(children);
				System.out.println("children: " + best + " " + children.get(0).getPathLength());
			}
			
			mutate(children);
			//timer.addMutationTime();
			
			pop = insertor.reinsert(rankedPop, children);
			//timer.addInsertTime();
			
			if(params.detectLoops)
				doLoopDetection(pop);
			//timer.addLoopdetTime();
			
			
			if(comm != null && (i+1) % params.migrationFreq == 0){
				//System.out.println(" do migration: " + i + this);
				double meanBefore = calculateMean(pop);
				
				Collections.sort(pop);
				sendMigrants(comm, pop);
				pop = addMigrants(pop, comm.getMessages());
				
				//System.out.println(" finished migration: " + i + this + "    " + meanBefore + " -> " + calculateMean(pop));
			}
			
			//Collections.shuffle(pop, params.rand);
			i++;
		}
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
	
	/*private boolean checkStop(double best, List<RankedChrom<R>> pop){
		int index = 0;
		for(RankedChrom<R> r: pop){
			if(r.fitness - best > params.mach)
				break;
			index++;
		}
		return index > params.stop * pop.size();
	}*/
	
	private void mutate(List<R> selection){
		
		for(R chrom : selection){
			if( params.rand.nextFloat() < params.mutation )
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
	
	public List<R> addMigrants(List<R> pop, List<R> renegades){
		//System.out.println(this + " receive: " + renegades.size());
		
		if(renegades.size() > pop.size()){
			System.out.println("TOO MANY MIGRANTS: " + renegades.size());
			return renegades.subList(0, pop.size());
		}
		
		List<R> newPop = new ArrayList<R>();
		
		for(R chrom:renegades){
			newPop.addAll(renegades);
		}
		
		newPop.addAll(pop.subList(0, pop.size() - renegades.size()));
		
		return newPop;
	}
	
	public void sendMigrants(Communicator<R> comm, List<R> pop){
		int nrRens = (int) (params.migration * params.popSize + 0.5);
		
		//System.out.println("pop size:" + pop.size() + " nrRens: " + nrRens);
		synchronized (comm) {
			for(int i = 0; i < nrRens; i++){
				comm.send(pop.get(i));
			}
		}
	}
}
