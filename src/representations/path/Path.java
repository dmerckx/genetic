package representations.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import main.Problem;
import representations.Edge;
import representations.Representation;

public class Path extends Representation {

	private List<Integer> path;
	private Problem problem;
	
	public Path(Problem problem){
		this(problem, new ArrayList<Integer>());
	}
	
	public Path(Problem problem, List<Integer> path ) {
		if(path == null ) throw new IllegalArgumentException();
		
		this.problem = problem;
		this.path = path;
	}

	@Override
	public void setRandomImpl(Random rand){
		
		for (int i = 0; i < problem.size(); i++) {
			path.add(i);
		}
		Collections.shuffle(path, rand);
	}

	
	@Override
	public double getPathLength() {
		double pathLength = 0;
		for (int i = 0; i < path.size()-1; i++) {
			pathLength += problem.distance(path.get(i), path.get(i+1));
			if(i+1 == path.size()-1)
				pathLength += problem.distance(path.get(i+1), path.get(0));
		}
		return pathLength;
	}
	
	public List<Integer> getPath() {
		return path;
	}
	
	public int size() {
		return path.size();
	}
	public Edge getRandomEdge(Random rand) {
		int begin = rand.nextInt(size());
		int end = path.get(begin);
		return new Edge(begin, end);
	}

	public Edge getNextEdge(Edge edge) {
		return new Edge(edge.getEnd(),path.get(edge.getEnd()));
	}
	
	public int getRandomCity(Random rand) {
		return path.get(rand.nextInt(path.size()));
	}
	
	public String printPath() {
		String print ="";
		for (int nb : path) {
			print = print + nb + " ";
		}
		return print;
	}
	
	public String toString() {
		return printPath();
	}

	@Override
	public Path toPath() {
		return this;
	}

	@Override
	public void fromPath(Path path) {
		this.path = path.path;
		isChanged();
	}
	
}
