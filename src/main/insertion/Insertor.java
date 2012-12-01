package main.insertion;

import java.util.List;

import representations.Representation;

public interface Insertor<R extends Representation> {
	
	/**
	 * OldPop is guaranteed to be sorted, the result does not have to be sorted.
	 * Children is not guaranteed to be sorted.
	 */
	List<R> merge(List<R> oldPop, List<R> children);
}
