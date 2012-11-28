package selectors;

import java.util.SortedSet;

import representations.Representation;



public interface SelectionStrategy<R extends Representation> {
	
	SortedSet<R> doSelection(SortedSet<R> pop);
}
