package representations;

import java.util.Random;

public abstract class Representation implements Comparable<Representation>{

	private boolean isCached = false;
	
	protected double fitness;
	
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
	
	public final double getFitness() {
		if(!isCached) {
			fitness = 0;
			getFitnessImpl();
			isCached = true;
		}
		return fitness;
	}
	
	public abstract void getFitnessImpl();

	public final void mutate() {
		mutateImpl();
		isCached = false;
	}
	
	public abstract void mutateImpl();
}
