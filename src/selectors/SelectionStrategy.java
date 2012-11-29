package selectors;

import java.util.List;

import representations.Representation;



public interface SelectionStrategy<R extends Representation> {
	
	/**
	 * The selection strategy assumes that the population is sorted!
	 */
	List<R> doSelection(List<R> pop);
}
