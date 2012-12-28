package main.mutation;

import java.util.ArrayList;
import java.util.List;

import params.Params;
import representations.Chromosome;
import representations.path.Path;

public class InversionMutator <R extends Chromosome> extends Mutator<R> {

	public InversionMutator(Params params) {
		super(params);
	}

	@Override
	public void mutate(R chrom) {
		Path p = chrom.toPath();
		List<Integer> pathList = p.pathAsList();

		//System.out.println(p.getPath());
		
		int rnd1, rnd2, rnd3;

		do{
			rnd1 = params.rand.nextInt(p.size());
			rnd2 = params.rand.nextInt(p.size());	
		}while(rnd2-1 < rnd1);
		//result: rnd2 > rnd1
		
		int[] subPath = new int[rnd2 - rnd1];
		//System.out.println(rnd1 + " - " + rnd2);
		for(int i=0; i < rnd2-rnd1; i++){
			subPath[i] = pathList.remove(rnd1);
		}
		//System.out.println(p.getPath());
		
		rnd3 = params.rand.nextInt(p.size());
		
		//System.out.println(rnd3);
		
		for(int i: subPath){
			pathList.add(rnd3, i);
		}
		
		//System.out.println(p.getPath());
		p.setPathAsList(pathList);
		chrom.fromPath(p);
	}

}
