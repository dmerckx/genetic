package main.mutation;

import params.Params;
import representations.Representation;

public abstract class Mutator<R extends Representation> {
	
	protected Params params;
	
	public Mutator(Params params) {
		this.params = params;
	}
	
	public abstract void mutate(R chrom);
	
}
