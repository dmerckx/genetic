package representations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import main.Problem;

public class Adjacency extends Representation {

	private List<Integer> path;
	private boolean isCached = false;
	private double fitness;
	private Problem problem;
	
	public Adjacency(Problem problem){
		this.path = new ArrayList<Integer>();
		for(int i = 0; i < problem.size(); i++){
			path.add(i);
		}
		this.problem = problem;
	}
	
	public void setRandom(Random rand){
		Collections.shuffle(path, rand);
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
	
}