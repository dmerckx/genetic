package main.mutation;

import params.Params;
import representations.Representation;
import representations.path.Path;

public class SimpleInversionMutator<R extends Representation> extends Mutator<R> {

	public SimpleInversionMutator(Params params) {
		super(params);
	}

	@Override
	public void mutate(R chrom) {
		Path p = chrom.toPath();
		
		int rnd1, rnd2;
		
		do{
			rnd1 = params.rand.nextInt(p.size());
			rnd2 = params.rand.nextInt(p.size());	
		}while(rnd2-1 < rnd1);
		//result: rnd2-1 >= rnd1
		
		do{
			int val1 = p.getPath().get(rnd1);
			int val2 = p.getPath().get(rnd2);

			p.getPath().set(rnd1, val2);
			p.getPath().set(rnd2, val1);
			
			rnd1++;
			rnd2--;
		}while(rnd2-1 >= rnd1);
		
		
		chrom.fromPath(p);
		
		chrom.setRandom(params.rand);
	}

}
