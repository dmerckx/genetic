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
		this(problem, new ArrayList<Integer>());
	}
	
	public Adjacency(Problem problem, List<Integer> path ) {
		this.problem = problem;
		if(path.isEmpty())
			fillPath();
		else
			this.path = path;
	}
	
	private void fillPath() {
		this.path = new ArrayList<Integer>();
		for(int i = 0; i < problem.size(); i++){
			path.add(i);
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
	
	public Edge getRandomEdge(Random rand) {
		int begin = rand.nextInt(size());
		int end = path.get(begin);
		return new Edge(begin, end);
	}

	public Edge getNextEdge(Edge edge) {
		return new Edge(edge.getEnd(),path.get(edge.getEnd()));
	}
	
	public void printPath() {
		int counter = 0;
		for (int nb : path) {
			System.out.println(counter + " " + nb);
			counter++;
		}
	}
	
}
