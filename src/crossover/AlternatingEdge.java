package crossover;

import java.util.ArrayList;
import java.util.List;

import main.Problem;
import params.Params;
import representations.Adjacency;

public class AlternatingEdge implements CrossOver<Adjacency>{

	private Params params;
	private Problem problem;

	public AlternatingEdge(Params params, Problem problem) {
		this.params = params;
		this.problem = problem;
	}
	
	@Override
	public List<Adjacency> doCrossOver(List<Adjacency> selection) {
		
		List<Adjacency> children = new ArrayList<Adjacency>();
		
		while(!selection.isEmpty()) {
			Adjacency firstParent = selectParent(selection);
			Adjacency secondParent = selectParent(selection);
			
			List<Integer> path = constructPath(firstParent, secondParent);
			
			Adjacency child = new Adjacency(problem, path);
			
			children.add(child);
		}
		
		return children;
	}

	private List<Integer> constructPath(Adjacency firstParent,
			Adjacency secondParent) {
		
		return null;
	}

	private Adjacency selectParent(List<Adjacency> selection) {
		return selection.remove(params.rand.nextInt(selection.size()));
	}

	private Edge getRandomEdge(Adjacency parent) {
		int begin = params.rand.nextInt(parent.size());
		int end = parent.getPath().get(begin);
		return new Edge(begin, end);
	}
	
}
