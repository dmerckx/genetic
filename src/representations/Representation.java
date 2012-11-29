package representations;

import java.util.Random;

public abstract class Representation implements Comparable<Representation>{

	public int compareTo(Representation r2) {
		if(getFitness() < r2.getFitness())
			return -1;
		if(getFitness() > r2.getFitness())
			return 1;
		return 0;
	}
	
	public abstract void setRandom(Random rand);
	
	public abstract double getFitness();
	
	public abstract  void mutate();
}
