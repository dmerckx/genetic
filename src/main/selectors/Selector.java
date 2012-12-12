package main.selectors;

import java.util.List;

import main.RankedChrom;
import params.Params;
import representations.Chromosome;



public abstract class Selector<R extends Chromosome> {
	
	protected Params params;
	
	public Selector(Params params) {
		this.params = params;
	}
	
	/**
	 * The selection strategy assumes that the population is sorted!
	 */
	public List<R> doSelection(List<RankedChrom<R>> pop){
		double total = 0;
		for(RankedChrom<R> chrom: pop){
			total += chrom.fitness;
		}
		int nrToSelect = (int) Math.round(Math.max(Math.floor((1 - params.elitists) * pop.size()), 2));
		
		return doSelection(pop, total, nrToSelect);
	}
	
	
	protected abstract List<R> doSelection(List<RankedChrom<R>> pop, double total, int nrToSelect);
}
