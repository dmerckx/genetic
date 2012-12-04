package main.insertion;

import java.util.ArrayList;
import java.util.List;

import params.Params;
import representations.Representation;

/**
 * Uniform insertion
 */
public class UI<R extends Representation> implements Insertor<R>{

	private Params params;
	
	public UI(Params params) {
		this.params = params;
	}
	
	@Override
	public List<R> merge(List<R> oldPop, List<R> children) {
		
		List<R> result = new ArrayList<R>();
		
		for(int i=0;i<params.popSize;i++){
			int index = params.rand.nextInt(oldPop.size()+children.size());
			R chrom;
			if(index >= oldPop.size()){
				chrom = children.get(index - oldPop.size());
				children.remove(index - oldPop.size());
			}
			else {
				chrom = oldPop.get(index);
				oldPop.remove(index);
			}
			
			result.add(chrom);
		}
		
		return result;
	}

}
