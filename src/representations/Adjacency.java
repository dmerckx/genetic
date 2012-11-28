package representations;

import java.util.ArrayList;
import java.util.Collection;
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
		path = new ArrayList<Integer>(problem.size());
		for(int i = 0; i < problem.size(); i++){
			path.set(i, i);
		}
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
		//TODO
	}

	@Override
	public void mutate() {
		// TODO Auto-generated method stub
	}

}
