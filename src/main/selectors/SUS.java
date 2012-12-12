package main.selectors;

import java.util.ArrayList;
import java.util.List;

import main.RankedChrom;

import params.Params;
import representations.Chromosome;

/**
 * 
 * @author dmerckx
 *
 * @param <R>
 */
public class SUS<R extends Chromosome> extends Selector<R>{

	public SUS(Params params) {
		super(params);
	}

	@Override
	public List<R> doSelection(List<RankedChrom<R>> pop, double total, int nrToSelect) {
		List<R> result = new ArrayList<R>();
		
		
		double start = params.rand.nextDouble() / (nrToSelect);
		List<Double> ptrs = new ArrayList<Double>(nrToSelect);
		
		for(int i=0; i < nrToSelect; i++){
			ptrs.add(start + 1.0d * i / nrToSelect);
		}
		
		for(int i =0; i < nrToSelect; i++){
			result.add((R) RWS.select(pop, total, ptrs.get(i)).chrom.clone());
		}
		return result;
	}
}
