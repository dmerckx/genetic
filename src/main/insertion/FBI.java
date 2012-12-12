package main.insertion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import params.Params;
import representations.Representation;

/**
 * Fitness based insertion.
 */
public class FBI<R extends Representation> extends ReInsertor<R>{

	public FBI(Params params) {
		super(params);
	}
	
	@Override
	public List<R> selectParentSurvivors(List<R> oldPop, int nrSurvivors) {
		Collections.sort(oldPop); //TODO: check if redundant
		
		ArrayList<R> result = new ArrayList<R>();
		for(int i =0; i < nrSurvivors; i++){
			result.add(oldPop.get(oldPop.size()-(i+1)));
		}
		
		return result;
	}
}
