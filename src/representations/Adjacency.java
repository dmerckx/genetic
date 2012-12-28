package representations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import main.Problem;
import representations.path.Path;

public class Adjacency extends Chromosome {

	private int[] path;
	
	private Problem problem;
	
	public Adjacency(Problem problem){
		this(problem, new int[problem.size()]);
	}
	
	public Adjacency(Problem problem, int[] p) {
		if(p == null ) throw new IllegalArgumentException();
		
		this.problem = problem;
		this.path = p;
	}

	@Override
	public void setRandomImpl(Random rand){
		List<Integer> pathRep = new ArrayList<Integer>();
		path = new int[path.length];
		
		for(int i = 1; i < problem.size(); i++){
			pathRep.add(i);
			path[i-1] = 0;
		}
		path[path.length-1] = 0;

		Collections.shuffle(pathRep, rand);
		
		int index = 0;
		for(int i = 0; i < problem.size()-1; i++){
			path[index] = pathRep.get(i);
			index = pathRep.get(i);
		}
		path[index] = 0;
	}
	
	@Override
	public double calcPathLength() {
		return recursiveCalculation(0);
	}
	
	private double recursiveCalculation(int nextCity) {
		int c1 = nextCity;
		int c2 = path[nextCity];
		if(c2 == 0)
			return problem.distance(c1, c2);
		return problem.distance(c1, c2) + recursiveCalculation(c2);
	}

	public int[] getPath() {
		return path;
	}
	
	public int size() {
		return path.length;
	}
	public Edge getRandomEdge(Random rand) {
		int begin = rand.nextInt(size());
		int end = path[begin];
		return new Edge(begin, end);
	}

	public Edge getNextEdge(Edge edge) {
		return new Edge(edge.getEnd(),path[edge.getEnd()]);
	}
	
	public void printPath() {
		for (int i =0; i < path.length; i++) {
			System.out.println(i + " " + path[i]);
		}
	}

	
	@Override
	public Path toPath() {
		int[] pathRep = new int[path.length];
		
		int index = 0;
		for(int i = 0; i < problem.size(); i++){
			pathRep[i] = path[index];
			index = path[index];
		}
		
		return new Path(problem, pathRep);
	}

	@Override
	public void fromPath(Path p) {
		int[] pathRep = p.getPath();
		
		if(path.length == 0){
			throw new IllegalStateException();
		}
		
		for(int i = 0; i < problem.size()-1; i++){
			path[pathRep[i]] = pathRep[i+1];
		}
		path[pathRep[problem.size()-1]] = pathRep[0];
		isChanged();
	}
	
	@Override
	public Adjacency clone() {
		return new Adjacency(problem, Arrays.copyOf(path, path.length));
	}

	@Override
	public double getPathLength(int from, int to) {
		return recursivePartialCalculation(from,to);
	}

	private double recursivePartialCalculation(int from, int to) {
		int c1 = from;
		int c2 = path[from];
		if(c2 == to)
			return problem.distance(c1, c2);
		return problem.distance(c1, c2) + recursivePartialCalculation(c2, to);
	}
	
	@Override
	public void swap(int index1, int index2) {
		int temp = path[index1];
		path[index1] = path[index2];
		path[index2] = temp;
	}
	
	@Override
	public List<Integer> getReversePath() {
		List<Integer> result = new ArrayList<Integer>();
		Map<Integer, Integer> indexMapping = new HashMap<Integer, Integer>();
		for (int i = 0; i < path.length; i++) {
			indexMapping.put(path[i],i);
			result.add(0);
		}
		for (int i = 0; i < path.length; i++) {
			result.set(i, indexMapping.get(i));
		}
		return result;
	}
	
	public String toString() {
		String print = "";
		for (int iterable_element : path) {
			print = print + " " + iterable_element;
		}
		return print;
	}
	
	@Override
	public Set<Edge> getEdges() {
		Set<Edge> result = new HashSet<Edge>();
		Edge begin = getRandomEdge(new Random());
		Edge currentEdge = getNextEdge(begin);
		result.add(currentEdge);
		while((!(currentEdge = getNextEdge(currentEdge)).equals( begin ))) {
			result.add(currentEdge);
		}
		result.add(begin);
		return result;
	}
	
}
