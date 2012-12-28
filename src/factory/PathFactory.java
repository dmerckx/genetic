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
		int[] p = new int[problem.size()];
		
		for(int i = 0; i < path.size(); i++){
			p[i] = path.get(i);
		}
		
		return new Path(problem, p);
	}

}
