package main.insertion;

import java.util.ArrayList;
import java.util.List;

import params.Params;
import representations.Representation;

/**
 * Uniform insertion
 */
public class UI<R extends Representation> extends ReInsertor<R>{

	public UI(Params params) {
		super(params);
	}

	@Override
	public List<R> selectParentSurvivors(List<R> oldPop, int nrSurvivors) {
		List<R> result = new ArrayList<R>();
		
		while(result.size() < nrSurvivors){
			int index = params.rand.nextInt(oldPop.size());
			
			result.add(oldPop.get(index));
			oldPop.remove(index);
		}
		
		return result;
	}

}
