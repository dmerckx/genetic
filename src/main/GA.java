package main;
import insertion.Insertor;

import java.util.Collections;
import java.util.List;

import params.Params;
import representations.Representation;
import selectors.SelectionStrategy;
import crossover.CrossOver;


public abstract class GA<R extends Representation> {

	protected Params params;
	private SelectionStrategy<R> selector;
	private CrossOver<R> crossover;
	private Insertor<R> insertor;
	
	public GA(Params params, SelectionStrategy<R> selector, CrossOver<R> crossover, Insertor<R> insertor) {
		this.params = params;
		this.selector = selector;
		this.crossover = crossover;
		this.insertor = insertor;
	}
	
	public void run(Problem problem, History history){
		
		List<R> pop = initPopulation(problem);
		
		int i = 0;
		while(i < params.maxGenerations){
			//TODO: work with linkedlists instead of arraylists?
			Collections.sort(pop);
			
			double best = pop.get(0).getFitness();
			double worst = pop.get(pop.size()-1).getFitness();
			double mean = calculateMean(pop);
			history.write(best, mean, worst);
			
			if(checkStop(best, pop)) break;
			
			List<R> selection = selector.doSelection(pop);
			
			List<R> children = crossover.doCrossOver(selection);
			
			mutate(children);
			
			pop = insertor.merge(pop, children);
			
			i++;
		}
	}
	
	private double calculateMean(List<R> pop){
		double total = 0;
		for(R chrom:pop){
			total += chrom.getFitness();
		}
		
		return total / pop.size();
	}
	
	private boolean checkStop(double best, List<R> pop){
		int index = 0;
		for(R r: pop){
			if(r.getFitness() - best > params.mach)
				break;
			index++;
		}
		return index > params.stop * pop.size();
	}
	
	private void mutate(List<R> selection){
		for(R chrom : selection){ 
			if( params.rand.nextFloat() > params.mutation )
				chrom.mutate();
		}
	}
	
	abstract List<R> initPopulation(Problem problem);
}
