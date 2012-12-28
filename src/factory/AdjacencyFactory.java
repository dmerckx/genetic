package factory;

import java.util.List;

import main.Problem;
import representations.Adjacency;

public class AdjacencyFactory implements RepresentationFactory<Adjacency>{

	@Override
	public Adjacency create(Problem problem) {
		return new Adjacency(problem);
	}

	@Override
	public Adjacency create(Problem problem, List<Integer> path) {
		return new Adjacency(problem,path);
	}

}
