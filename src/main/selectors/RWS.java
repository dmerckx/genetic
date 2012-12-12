package main.selectors;

import java.util.ArrayList;
import java.util.List;

import main.RankedChrom;
import params.Params;
import representations.Chromosome;

public class RWS<R extends Chromosome> extends Selector<R>{

	public RWS(Params params) {
		super(params);
	}
	
	@Override
	public List<R> doSelection(List<RankedChrom<R>> pop, double total, int nrToSelect) {
		List<R> result = new ArrayList<R>();
		
		for(int i =0; i < nrToSelect; i++){
			result.add((R) RWS.select(pop, total, params.rand.nextDouble()).chrom.clone());
		}
		
		return result;
	}
	
	public static <R extends Chromosome> RankedChrom<R> select(List<RankedChrom<R>> pop, double totalFitness, double roulette){
		double traversed = 0;
		//TODO can be made O(log n) with binary search
		for(RankedChrom<R> rc: pop){
			traversed += rc.fitness / totalFitness;
			if( traversed >= roulette )
				return rc;
		}
		
		return pop.get(pop.size() -1);
	}
}
