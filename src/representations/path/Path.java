package representations.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.ejml.alg.dense.misc.PermuteArray;

import main.Problem;
import representations.Chromosome;
import representations.Edge;

public class Path extends Chromosome {

	private int[] path;
	private Problem problem;
	
	public Path(Problem problem){
		this(problem, new int[problem.size()]);
	}
	
	public Path(Problem problem, int[] p ) {
		if(p == null ) throw new IllegalArgumentException();
		
		this.problem = problem;
		this.path = p;
	}

	@Override
	public void setRandomImpl(Random rand){
		if(path.length == 0) {
			throw new IllegalArgumentException();
		}
		for(int i = 0; i < path.length; i++){
			path[i] = i;
		}
		shuffle(path, rand);
	}

	
	@Override
	public double calcPathLength() {
		double pathLength = 0;
		for (int i = 0; i < path.length-1; i++) {
			pathLength += problem.distance(path[i], path[i+1]);
			if(i+1 == path.length-1)
				pathLength += problem.distance(path[i+1], path[0]);
		}
		return pathLength;
	}
	
	public int[] getPath() {
		return path;
	}
	
	public List<Integer> pathAsList(){
		List<Integer> list = new ArrayList<Integer>();
		for(int p:path){
			list.add(p);
		}
		
		return list;
	}
	
	public void setPathAsList(List<Integer> list){
		for(int i = 0; i < path.length; i++){
			path[i] = list.get(i);
		}
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
	
	public int getRandomCity(Random rand) {
		return path[rand.nextInt(path.length)];
	}
	
	public String printPath() {
		String print ="";
		for (int nb : path) {
			print = print + nb + "\r\n";
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
	
	@Override
	public Path clone() {
		return new Path(problem, Arrays.copyOf(path, path.length));
	}
	
	@Override
	public void swap(int index1, int index2) {
		int temp = path[index1];
		path[index1] = path[index2];
		path[index2] = temp;
	}

	@Override
	public double getPathLength(int from, int to) {
		int index = 0;
		double distance = 0;
		for (int i = 0; i < path.length; i++) {
			if(path[i] == from)
				index = i;
		}
		for (int i = index; i < path.length+index; i++) {
			distance += problem.distance(path[i%path.length], path[(i+1)%path.length]);
			if(path[(i+1)%path.length] == to)
				break;
		}
		return distance;
	}
	
	@Override
	public List<Integer> getReversePath() {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = path.length-1; i >= 0; i--) {
			result.add(path[i]);
		}
		return result;
	}

	@Override
	public Set<Edge> getEdges() {
		Set<Edge> result = new HashSet<Edge>();
		for (int i = 0; i < path.length; i++) {
			if(i != path.length-1)
				result.add(new Edge(path[i],path[i+1]));
			else
				result.add(new Edge(path[i],path[0]));
		}
		return result;
	}
	
}
