package mutation;

import java.util.List;

import representations.Representation;

public interface Mutation<R extends Representation> {
	
	public List<Integer> mutate(R chrom);
	
}
