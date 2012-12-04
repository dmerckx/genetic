package main.insertion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import params.Params;
import representations.Representation;

/**
 * Fitness based insertion.
 */
public class FBI<R extends Representation> implements Insertor<R>{

	private Params params;
	
	public FBI(Params params) {
		this.params = params;
	}
	
	@Override
	public List<R> merge(List<R> oldPop, List<R> children) {
		
		oldPop.addAll(children);
		
		Collections.sort(oldPop);
		
		ArrayList<R> result = new ArrayList<R>();
		for(int i =0; i < params.popSize; i++){
			result.add(oldPop.get(oldPop.size()-(i+1)));
		}
		
		return result;
	}
}
