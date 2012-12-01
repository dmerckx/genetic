package main.selectors;

import java.util.List;

import params.Params;
import representations.Representation;



public abstract class Selector<R extends Representation> {
	
	protected Params params;
	
	public Selector(Params params) {
		this.params = params;
	}
	
	/**
	 * The selection strategy assumes that the population is sorted!
	 */
	public List<R> doSelection(List<R> pop){
		double total = 0;
		for(R chrom: pop){
			total += chrom.getFitness();
		}
		int nrToSelect = (int) Math.round(Math.max(Math.floor((1 - params.elitists) * pop.size()), 2));
		
		return doSelection(pop, total, nrToSelect);
	}
	
	
	protected abstract List<R> doSelection(List<R> pop, double total, int nrToSelect);
}
