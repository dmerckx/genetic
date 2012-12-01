package representations;

import java.util.Random;

import representations.path.Path;

public abstract class Representation implements Comparable<Representation>{

	private boolean isCached = false;
	
	private double fitness;
	
	public Representation() {
		
	}
	
	public Representation(double fitness){
		this.fitness = fitness;
		this.isCached = true;
	}
	
	public int compareTo(Representation r2) {
		if(getFitness() < r2.getFitness())
			return -1;
		if(getFitness() > r2.getFitness())
			return 1;
		return 0;
	}
	
	public final void setRandom(Random rand) {
		setRandomImpl(rand);
		
		isCached = false;
	}
	
	public abstract void setRandomImpl(Random rand);
	
	public final void isChanged(){
		isCached = false;
	}
	
	public final double getFitness() {
		if(!isCached) {
			fitness = 1 / getPathLength();
			isCached = true;
		}
		return fitness;
	}
	
	public abstract double getPathLength();
	
	
	public abstract Path toPath();
	public abstract void fromPath(Path path);
}