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
		int[] p = new int[problem.size()];
		
		for(int i = 0; i < path.size(); i++){
			p[i] = path.get(i);
		}
		
		return new Adjacency(problem,p);
	}

}
