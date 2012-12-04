package main.selectors;

import java.util.ArrayList;
import java.util.List;

import params.Params;
import representations.Representation;

public class RWS<R extends Representation> extends Selector<R>{

	public RWS(Params params) {
		super(params);
	}
	
	@Override
	public List<R> doSelection(List<R> pop, double total, int nrToSelect) {
		List<R> result = new ArrayList<R>();
		
		for(int i =0; i < nrToSelect; i++){
			result.add( (R)RWS.select(pop, total, params.rand.nextDouble()).clone() );
		}
		
		return result;
	}
	
	public static <R extends Representation> R select(List<R> pop, double totalFitness, double roulette){
		double traversed = 0;
		//TODO can be made O(log n) with binary search
		for(R chrom: pop){
			traversed += chrom.getFitness() / totalFitness;
			if( traversed >= roulette )
				return chrom;
		}
		
		return pop.get(pop.size() -1);
	}
}
