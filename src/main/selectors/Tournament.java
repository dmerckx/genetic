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
		this(params, 1, 0.5);
	}

	/**
	 * 
	 * @param params
	 * @param p
	 *            -- the chance of selecting the best individual of the
	 *            tournament. the second best individual is selected with a
	 *            chance p*(1-p)^1, etc.
	 * @param percentage
	 *            -- the percentage of the total population that will form the
	 *            tournament subpopulation size.
	 */
	public Tournament(Params params, double p, double percentage) {
		super(params);
		this.p = p;
		this.k = Math.max((int) Math.floor(params.popSize * percentage), 1);
	}

	@Override
	protected List<R> doSelection(List<RankedChrom<R>> pop, double total,
			int nrToSelect) {
		List<R> result = new ArrayList<R>();

		for (int i = 0; i < nrToSelect; i++) {
			result.add((R) selectOneChrom(pop).chrom.clone());
		}

		return result;
	}

	public RankedChrom<R> selectOneChrom(List<RankedChrom<R>> pop) {
		return selectBest(createPool(pop), params.rand.nextDouble());
	}

	private RankedChrom<R> selectBest(List<RankedChrom<R>> pool, double roulette) {
		Collections.sort(pool);
		double traversed = 0;
		int counter = 0;
		for (int i = pool.size() - 1; i >= 0; i--) {
			traversed += p * Math.pow((1 - p), counter);
			counter++;
			if (traversed >= roulette)
				return pool.get(i);
		}
		return pool.get(pool.size() - 1);
	}

	private List<RankedChrom<R>> createPool(List<RankedChrom<R>> pop) {
		List<RankedChrom<R>> pool = new ArrayList<RankedChrom<R>>();
		for (int i = 0; i < k; i++) {
			pool.add(pop.get(params.rand.nextInt(pop.size())));
		}
		return pool;
	}

}
