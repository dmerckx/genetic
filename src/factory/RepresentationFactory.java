package factory;

import java.util.List;

import main.Problem;
import representations.Chromosome;

public interface RepresentationFactory<E extends Chromosome> {

	E create(Problem problem);

	E create(Problem problem, List<Integer> path);
	
}
