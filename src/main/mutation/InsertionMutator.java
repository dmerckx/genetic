package main.mutation;

import params.Params;
import representations.Chromosome;
import representations.path.Path;

public class InsertionMutator<R extends Chromosome> extends Mutator<R>{

	public InsertionMutator(Params params) {
		super(params);
	}

	@Override
	public void mutate(R chrom) {
		Path p = chrom.toPath();
		
		int rnd1, rnd2;
		
		do{
			rnd1 = params.rand.nextInt(p.size());
			rnd2 = params.rand.nextInt(p.size());	
		}while(rnd1 == rnd2);
		
		int val1 = p.getPath().remove(rnd1);
		p.getPath().add(rnd2, val1);
		
		chrom.fromPath(p);
	}
}
