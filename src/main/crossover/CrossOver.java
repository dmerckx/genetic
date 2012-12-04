package main.crossover;

import java.util.ArrayList;
import java.util.List;

import main.Problem;
import params.Params;
import representations.Representation;
import factory.RepresentationFactory;


public abstract class CrossOver<R extends Representation> {

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

		for (int i = 0; i < selection.size()/2; i++) {
			R firstParent = selectParent(selection);
			R secondParent = selectParent(selection);
			if(haveToBreed()) {
				List<Integer> path1 = breed(firstParent, secondParent);
				List<Integer> path2 = breed(secondParent, firstParent);

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

	private boolean haveToBreed() {
		return params.rand.nextFloat()<params.crossover;
	}

	public final R selectParent(List<R> selection) {
		return selection.remove(params.rand.nextInt(selection.size()));
	}

	protected abstract List<Integer> breed(R p1, R p2);

}
