package representations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import representations.path.Path;

/**
 * Can be compared on basis of path length, not fitness !
 *
 */
public abstract class Chromosome implements Comparable<Chromosome>{

	private boolean isCached = false;
	private double length;
	
	public Chromosome() {
		
	}
	
	public int compareTo(Chromosome r2) {
		if(getPathLength() < r2.getPathLength())
			return -1;
		if(getPathLength() > r2.getPathLength())
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
	
	public final double getPathLength() {
		if(!isCached) {
			length = calcPathLength();
			isCached = true;
		}
		return length;
	}
	
	public abstract double calcPathLength();
	
	
	public abstract Path toPath();
	public abstract void fromPath(Path path);
	
	public abstract Chromosome clone();
	
	public abstract double getPathLength(int from, int to);
	
	/**
	 * Swaps the specified indices(!), not cities.
	 * @param city1
	 * @param city2
	 */
	public abstract void swap(int city1, int city2);
	
	public abstract List<Integer> getReversePath();
	
	/**
	 * Returns the number of edges that this chromosome has in common with the other one.
	 * @param other
	 * @return
	 */
	public final int getCorrelationValue(Chromosome other) {
		int counter = 0;
		Set<Edge> edges = other.getEdges();
		for (Edge edge : getEdges()) {
			if(edges.contains(edge))
				counter++;
		}
		return counter;
	}
	
	public abstract Set<Edge> getEdges();
	
	protected void shuffle(int[] vals, Random rand){
		Integer[] transf = new Integer[vals.length];
		
		for(int i = 0; i < vals.length; i++){
			transf[i] = vals[i];
		}
		Collections.shuffle(Arrays.asList(transf));
		
		for(int i = 0; i < vals.length; i++){
			vals[i] = transf[i];
		}
	}
}