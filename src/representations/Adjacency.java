package representations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import main.Problem;

public class Adjacency extends Representation {

	private List<Integer> path;
	private boolean isCached = false;
	private double fitness;
	private Problem problem;
	
	public Adjacency(Problem problem){
		this(problem, new ArrayList<Integer>());
	}
	
	public Adjacency(Problem problem, List<Integer> path ) {
		if(path == null ) throw new IllegalArgumentException();
		
		this.problem = problem;
		this.path = path;
	}

	public void setRandom(Random rand){
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
	public double getFitness() {
		if(!isCached)
			calculateFitness();
		
		return fitness;
	}
	
	private void calculateFitness(){
		recursiveCalculation(0);
		isCached = true;
	}
	
	private void recursiveCalculation(int nextCity) {
		int c1 = nextCity;
		int c2 = path.get(nextCity);
		fitness += problem.distance(c1, c2);
		if(c2 == 0)
			return;
		recursiveCalculation(c2);
	}

	@Override
	public void mutate() {
		// TODO Auto-generated method stub
	}
	
	public List<Integer> getPath() {
		return path;
	}
	
	public int size() {
		return path.size();
	}
}
