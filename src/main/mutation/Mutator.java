package main.mutation;

import params.Params;
import representations.Chromosome;

public abstract class Mutator<R extends Chromosome> {
	
	protected Params params;
	
	public Mutator(Params params) {
		this.params = params;
	}
	
	public abstract void mutate(R chrom);
	
}
