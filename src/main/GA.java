package main;
import java.util.SortedSet;

import crossover.CrossOver;

import params.Params;
import representations.Representation;
import selectors.SelectionStrategy;


public abstract class GA<R extends Representation> {

	private Params params;
	private SelectionStrategy<R> selector;
	private CrossOver<R> crossover;
	
	public GA(Params params) {
		this.params = params;
		//TODO
	}
	
	public void run(Problem problem, History history){
		
		SortedSet<R> pop = initPopulation(problem);
		
		int i = 0;
		while(i < params.maxGenerations){
			double best = pop.first().getFitness();
			double worst = pop.last().getFitness();
			double mean = calculateMean(pop);
			history.write(best, mean, worst);
			
			if(checkStop(best, pop)) break;
			
			SortedSet<R> selection = selector.doSelection(pop);
			
			SortedSet<R> children = crossover.doCrossOver(selection);
			
			mutate(children);
			
			pop = merge(pop, selection, children);
			
			loopDetection(pop);
			
			i++;
		}
	}
	
	private double calculateMean(SortedSet<R> pop){
		double total = 0;
		for(R chrom:pop){
			total += chrom.getFitness();
		}
		
		return total / pop.size();
	}
	
	private boolean checkStop(double best, SortedSet<R> pop){
		int index = 0;
		for(R r: pop){
			if(r.getFitness() - best > params.mach)
				break;
			index++;
		}
		return index > params.stop * pop.size();
	}
	
	abstract SortedSet<R> initPopulation(Problem problem);
	
	private void mutate(SortedSet<R> selection){
		for(R chrom : selection){ 
			if( params.rand.nextFloat() > params.mutation )
				chrom.mutate();
		}
	}
	
	abstract SortedSet<R> merge(SortedSet<R> oldPop, SortedSet<R> selection, SortedSet<R> children);
	
	abstract void loopDetection(SortedSet<R> population);
}
