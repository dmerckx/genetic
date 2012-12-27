package main.crossover;

import java.util.ArrayList;
import java.util.List;

import main.Problem;
import params.Params;
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

	private static int counterSame = 0;
	private static int counterDiff = 0;
	
	
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
			R secondParent;
				
			if(haveToBreed()) {
				if(params.correlativeTournament) 
					secondParent = selectSimilarParent(selection, firstParent);
				else
					secondParent = selectParent(selection);
				ParentChromosome<R> par1 = new ParentChromosome<R>(firstParent, createInverse(firstParent));
				ParentChromosome<R> par2 = new ParentChromosome<R>(secondParent, createInverse(secondParent));
				List<Integer> path1 = breed(par1, par2);
				List<Integer> path2 = breed(par1, par2);
				children.add(factory.create(problem, path1));
				children.add(factory.create(problem, path2));
			}
			else {
				children.add((R)firstParent.clone());
				children.add((R)selectParent(selection).clone());
			}
		}
		return children;

	}

	//TODO de meest gelijkaardige selecteren in heel de selectie of in een subpopulatie?
	private R selectSimilarParent(List<R> selection, R firstParent) {
		int maxSimilarity = 0;
		R mostSimilarParent = selection.get(0);
		int range = ((int) (selection.size() - (params.similarSubsetSize*selection.size())));
		int begin = range == 0 ? 0: params.rand.nextInt(range);
		List<R> subset = selection.subList(begin, (int) (begin+params.similarSubsetSize*selection.size()));
		for (R r : subset) {
			int similarity = r.getCorrelationValue(firstParent);
			if(similarity > maxSimilarity) {
				maxSimilarity = similarity;
				mostSimilarParent = r;
			}
		}
		if(maxSimilarity == 70)
			counterSame++;
		else
			counterDiff++;
		selection.remove(mostSimilarParent);
		return mostSimilarParent;
	}

	private R createInverse(R chrom) {
		return factory.create(problem, chrom.getReversePath());
	}
	
	private boolean haveToBreed() {
		return params.rand.nextFloat()<params.crossover;
	}

	public final R selectParent(List<R> selection) {
		return selection.remove(params.rand.nextInt(selection.size()));
	}

	protected abstract List<Integer> breed(ParentChromosome<R> p1, ParentChromosome<R> p2);

}
