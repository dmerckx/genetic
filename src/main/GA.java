package main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.crossover.CrossOver;
import main.insertion.Insertor;
import main.mutation.Mutator;
import main.selectors.Selector;
import params.Params;
import representations.Representation;
import factory.RepresentationFactory;


public class GA<R extends Representation> {

	protected Params params;
	private RepresentationFactory<R> factory;
	
	private final Selector<R> selector;
	private final CrossOver<R> crossover;
	private final Insertor<R> insertor;
	private final Mutator<R> mutator;
	
	public GA(Params params, RepresentationFactory<R> factory, Selector<R> selector,
			CrossOver<R> crossover, Insertor<R> insertor, Mutator<R> mutator) {
		this.params = params;
		this.factory = factory;
		
		this.selector = selector;
		this.crossover = crossover;
		this.insertor = insertor;
		this.mutator = mutator;
	}
	
	public void run(Problem problem, History history){
		
		List<R> pop = initPopulation(problem);
		
		int i = 0;
		while(i < params.maxGenerations){
			//TODO: work with linkedlists instead of arraylists?
			Collections.sort(pop);
			
			double worst = pop.get(0).getFitness();
			double best = pop.get(pop.size()-1).getFitness();
			double mean = calculateMean(pop);
			history.write(best, mean, worst);
			
			//if(checkStop(best, pop)) break;
			
			List<R> selection = selector.doSelection(pop);
			

			System.out.println("selection: " + selection.size());
			
			List<R> children = crossover.doCrossOver(selection);
			
			System.out.println("children: " + children.size());
			
			mutate(children);
			
			System.out.println("pop: " + pop.size());
			
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
}
