package factory;

import java.util.List;

import main.Problem;
import representations.path.Path;

public class PathFactory implements RepresentationFactory<Path> {

	@Override
	public Path create(Problem problem) {
		return new Path(problem);
	}

	@Override
	public Path create(Problem problem, List<Integer> path) {
		return new Path(problem, path);
	}

}
