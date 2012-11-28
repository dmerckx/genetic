package selectors;

import java.util.SortedSet;
import java.util.TreeSet;

import params.Params;
import representations.Representation;

public class RWS<R extends Representation> implements SelectionStrategy<R>{

	private Params params;
	
	public RWS(Params params) {
		this.params = params;
	}
	
	@Override
	public SortedSet<R> doSelection(SortedSet<R> pop) {
		double total = 0;
		for(R chrom: pop){
			total += chrom.getFitness();
		}
		SortedSet<R> result = new TreeSet<R>();
		
		int nrToSelect = (int) Math.max(Math.floor((1 - params.elitists) * pop.size()), 2);
		for(int i =0; i < nrToSelect; i++){
			result.add( RWS.select(pop, params.rand.nextDouble() * total) );
		}
		return result;
	}
	
	public static <R extends Representation> R select(SortedSet<R> pop, double roulette){
		double traversed = 0;
		for(R chrom: pop){
			traversed += chrom.getFitness();
			if( traversed >= roulette )
				return chrom;
		}
		throw new IllegalStateException();
	}
}
