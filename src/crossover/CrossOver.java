package crossover;

import java.util.SortedSet;

import representations.Representation;


public interface CrossOver<R extends Representation> {
	
	SortedSet<R> doCrossOver(SortedSet<R> selection);

}
