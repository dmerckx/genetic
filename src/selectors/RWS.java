package selectors;

import java.util.ArrayList;
import java.util.List;

import params.Params;
import representations.Representation;

public class RWS<R extends Representation> implements SelectionStrategy<R>{

	private Params params;
	
	public RWS(Params params) {
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
		for(int i =0; i < nrToSelect; i++){
			result.add( RWS.select(pop, params.rand.nextDouble() * total) );
		}
		return result;
	}
	
	public static <R extends Representation> R select(List<R> pop, double roulette){
		double traversed = 0;
		//can be made O(1)
		for(R chrom: pop){
			traversed += chrom.getFitness();
			if( traversed >= roulette )
				return chrom;
		}
		throw new IllegalStateException();
	}
}
