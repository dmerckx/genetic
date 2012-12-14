package main.crossover;

import java.util.ArrayList;
import java.util.List;

import main.Main;
import main.Problem;
import params.Params;
import representations.Adjacency;
import representations.Chromosome;
import factory.RepresentationFactory;


public abstract class CrossOver<R extends Chromosome> {

	protected Params params;
	protected Problem problem;

	private RepresentationFactory<R> factory;

	protected CrossOver(RepresentationFactory<R> factory, Problem problem, Params params) {
		this.factory = factory;
		this.problem = problem;
		this.params = params;
	}

	/**
	 * @pre The given list is supposed to have an even number of elements.
	 * @param selection
	 * @return
	 */
	public final List<R> doCrossOver(List<R> selection) {

		List<R> children = new ArrayList<R>();

		int size =selection.size()/2;
		for (int i = 0; i < size; i++) {
			R firstParent = selectParent(selection);
			R secondParent = selectParent(selection);
			if(haveToBreed()) {
				R first = determineDirection(firstParent);
				R second = determineDirection(secondParent);
				List<Integer> path1 = breed(first, second);
				List<Integer> path2 = breed(second, first);
				children.add(factory.create(problem, path1));
				children.add(factory.create(problem, path2));
			}
			else {
				children.add((R)firstParent.clone());
				children.add((R)secondParent.clone());
			}
		}
		return children;

	}

	private R determineDirection(R parent) {
		return getDirection() == 1 ? parent : factory.create(problem, parent.getReversePath());
	}
	
	private int getDirection() {
		return params.rand.nextFloat() > 0.5 ? 1: 0;
	}
	
	private boolean haveToBreed() {
		return params.rand.nextFloat()<params.crossover;
	}

	public final R selectParent(List<R> selection) {
		return selection.remove(params.rand.nextInt(selection.size()));
	}

	protected abstract List<Integer> breed(R p1, R p2);

}
