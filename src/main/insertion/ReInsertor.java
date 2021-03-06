package main.insertion;

import java.util.Collections;
import java.util.List;

import main.RankedChrom;
import params.Params;
import representations.Chromosome;

public abstract class ReInsertor<R extends Chromosome> {
	
	protected Params params;
	
	public ReInsertor(Params params) {
		this.params = params;
	}
	
	/**
	 * Children will always be reinserted, there are 2 scenarios:
	 * 
	 * 	More children then popSize -> murder the weakest children
	 *  Less children then popSize -> select some parents to be reused
	 */
	public List<R> reinsert(List<RankedChrom<R>> oldPop, List<R> children){
		int nrSurvivors = params.popSize - children.size();
		
		if(nrSurvivors <= 0){
			//There are more children then survivors
			Collections.sort(children); //Sort children to remove to weakest
			while(children.size() > params.popSize){
				//Murder weakest child
				children.remove(children.size()-1);
			}
		}
		else{
			//Less children then survivors, reuse some parents
			
			if(oldPop.size() < nrSurvivors) throw new IllegalStateException();
			children.addAll(selectParentSurvivors(oldPop, nrSurvivors));
		}
		
		if(children.size() != params.popSize) throw new IllegalStateException();
		return children;
	}
	
	public abstract List<R> selectParentSurvivors (List<RankedChrom<R>> oldPop, int nrSurvivors);
}
