package representations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import main.Problem;
import representations.path.Path;

public class Adjacency extends Representation {

	private List<Integer> path;
	
	private Problem problem;
	
	public Adjacency(Problem problem){
		this(problem, new ArrayList<Integer>());
	}
	
	public Adjacency(Problem problem, List<Integer> path ) {
		if(path == null ) throw new IllegalArgumentException();
		
		this.problem = problem;
		this.path = path;
	}

	@Override
	public void setRandomImpl(Random rand){
		List<Integer> pathRep = new ArrayList<Integer>();
		path = new ArrayList<Integer>();
		
		for(int i = 1; i < problem.size(); i++){
			pathRep.add(i);
			path.add(0);
		}
		path.add(0);

		Collections.shuffle(pathRep, rand);
		
		int index = 0;
		for(int i = 0; i < problem.size()-1; i++){
			path.set( index, pathRep.get(i));
			index = pathRep.get(i);
		}
		path.set(index, 0);
	}
	
	@Override
	public double getPathLength() {
		return recursiveCalculation(0);
	}
	
	private double recursiveCalculation(int nextCity) {
		int c1 = nextCity;
		int c2 = path.get(nextCity);
		if(c2 == 0)
			return problem.distance(c1, c2);
		return problem.distance(c1, c2) + recursiveCalculation(c2);
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
		return new Edge(edge.getEnd(),getPath().get(edge.getEnd()));
	}
	
	public void printPath() {
		for (int i =0; i < path.size(); i++) {
			System.out.println(i + " " + path.get(i));
		}
	}

	
	@Override
	public Path toPath() {
		List<Integer> pathRep = new ArrayList<Integer>();
		
		int index = 0;
		for(int i = 0; i < problem.size(); i++){
			pathRep.add(path.get(index));
			index = path.get(index);
		}
		
		return new Path(problem, pathRep);
	}

	@Override
	public void fromPath(Path p) {
		List<Integer> pathRep = p.getPath();
		
		for(int i = 0; i < problem.size()-1; i++){
			path.set(pathRep.get(i), pathRep.get(i+1));
		}
		path.set(pathRep.get(problem.size()-1), pathRep.get(0));
		isChanged();
	}
	
}
