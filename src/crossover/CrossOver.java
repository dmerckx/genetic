package crossover;

import java.util.List;

import representations.Representation;


public interface CrossOver<R extends Representation> {
	
	List<R> doCrossOver(List<R> selection);

}
