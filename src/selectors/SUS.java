package selectors;

import java.util.ArrayList;
import java.util.List;

import params.Params;
import representations.Representation;

public class SUS<R extends Representation> implements SelectionStrategy<R>{

	private Params params;
	
	public SUS(Params params) {
		this.params = params;
	}

	@Override
	public List<R> doSelection(List<R> pop) {
		double total = 0;
		for(R chrom: pop){
			total += chrom.getFitness();
		}
		List<R> result = new ArrayList<R>();
		
		
		int nrToSelect = (int) Math.max(Math.floor((1 - params.elitists) * pop.size()), 2);
		
		double start = params.rand.nextDouble() * (total/nrToSelect);
		List<Double> ptrs = new ArrayList<Double>(nrToSelect);
		
		for(int i=0; i < nrToSelect; i++){
			ptrs.add(start + i * total / nrToSelect);
		}
		
		for(int i =0; i < nrToSelect; i++){
			result.add( RWS.select(pop, ptrs.get(i)));
		}
		return result;
	}
}
