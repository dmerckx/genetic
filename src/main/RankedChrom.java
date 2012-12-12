package main;

import representations.Chromosome;

public class RankedChrom<R extends Chromosome>  implements Comparable<RankedChrom<?>> {

	public final double fitness;
	public final R chrom;
	
	public RankedChrom(double fitness, R chrom) {
		this.fitness = fitness;
		this.chrom = chrom;
	}

	@Override	
	public int compareTo(RankedChrom<?> r2) {
		if(fitness < r2.fitness)
			return -1;
		if(fitness > r2.fitness)
			return 1;
		return 0;
	}
	
	public RankedChrom<R> clone(){
		return new RankedChrom<R>(fitness, (R) chrom.clone());
	}
}
