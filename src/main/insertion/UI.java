package main.insertion;

import java.util.ArrayList;
import java.util.List;

import main.RankedChrom;
import params.Params;
import representations.Chromosome;

/**
 * Uniform insertion
 */
public class UI<R extends Chromosome> extends ReInsertor<R>{

	public UI(Params params) {
		super(params);
	}

	@Override
	public List<R> selectParentSurvivors(List<RankedChrom<R>> oldPop, int nrSurvivors) {
		List<R> result = new ArrayList<R>();
		
		while(result.size() < nrSurvivors){
			int index = params.rand.nextInt(oldPop.size());
			
			result.add(oldPop.get(index).chrom);
			oldPop.remove(index);
		}
		
		return result;
	}

}
