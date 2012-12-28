package main.mutation;

import java.util.List;

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
		List<Integer> pathList = p.pathAsList();
		
		int rnd1, rnd2;
		
		do{
			rnd1 = params.rand.nextInt(p.size());
			rnd2 = params.rand.nextInt(p.size());	
		}while(rnd1 == rnd2);
		
		int val1 = pathList.remove(rnd1);
		pathList.add(rnd2, val1);

		p.setPathAsList(pathList);
		chrom.fromPath(p);
	}
}
