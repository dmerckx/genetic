package main.selectors;

import java.util.ArrayList;
import java.util.List;

import params.Params;
import representations.Representation;

/**
 * 
 * @author dmerckx
 *
 * @param <R>
 */
public class SUS<R extends Representation> extends Selector<R>{

	public SUS(Params params) {
		super(params);
	}

	@Override
	public List<R> doSelection(List<R> pop, double total, int nrToSelect) {
		List<R> result = new ArrayList<R>();
		
		
		double start = params.rand.nextDouble() / (nrToSelect);
		List<Double> ptrs = new ArrayList<Double>(nrToSelect);
		
		for(int i=0; i < nrToSelect; i++){
			ptrs.add(start + 1.0d * i / nrToSelect);
		}

		System.out.println(ptrs);
		
		for(int i =0; i < nrToSelect; i++){
			result.add( RWS.select(pop, total, ptrs.get(i)));
			System.out.println(result.get(i).getFitness());
		}
		return result;
	}
}
