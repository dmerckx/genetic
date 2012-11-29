package crossover;

import java.util.List;

import representations.Representation;


public interface CrossOver<R extends Representation> {
	
	/**
	 * @pre The given list is supposed to have an even number of elements.
	 * @param selection
	 * @return
	 */
	List<R> doCrossOver(List<R> selection);

}
