package factory;

import java.util.List;

import main.Problem;
import representations.Representation;

public interface RepresentationFactory<E extends Representation> {

	E create(Problem problem);

	E create(Problem problem, List<Integer> path);
	
}
