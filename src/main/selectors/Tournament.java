package main.selectors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.RankedChrom;

import params.Params;
import representations.Chromosome;

public class Tournament<R extends Chromosome> extends Selector<R> {

	private final double p;
	
	private final int k;
	
	public Tournament(Params params) {
		this(params, 1, 10);
	}
	
	public Tournament(Params params, double p, int k) {
		super(params);
		this.p = p;
		this.k = k;
	}

	@Override
	protected List<R> doSelection(List<RankedChrom<R>> pop, double total, int nrToSelect) {
		List<R> result = new ArrayList<R>();
		
		for (int i = 0; i < nrToSelect; i++) {
			result.add((R) selectOneChrom(pop).chrom.clone());
			System.out.println("selected chrom: " + result.get(i));
		}
		
		return result;
	}

	public RankedChrom<R> selectOneChrom(List<RankedChrom<R>> pop) {
		return selectBest(createPool(pop), params.rand.nextDouble());
	}
	
	private RankedChrom<R> selectBest(List<RankedChrom<R>> pool, double roulette) {
		Collections.sort(pool);
		System.out.println("sorted pool: " + pool);
		double traversed = 0;
		int counter = 0;
		for(RankedChrom<R> rc: pool){
			traversed += p * Math.pow((1-p), counter);
			counter++;
			if( traversed >= roulette )
				return rc;
		}
		return pool.get(pool.size() -1);
	}

	private List<RankedChrom<R>> createPool(List<RankedChrom<R>> pop) {
		List<RankedChrom<R>> pool = new ArrayList<RankedChrom<R>>();
		for (int i = 0; i < k; i++) {
			pool.add(pop.get(params.rand.nextInt(pop.size())));
		}
		System.out.println("pool: " + pool);
		return pool;
	}

}
