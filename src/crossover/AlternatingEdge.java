package crossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.Problem;
import params.Params;
import representations.Adjacency;
import representations.Edge;

public class AlternatingEdge implements CrossOver<Adjacency> {

	private Params params;
	private Problem problem;

	public AlternatingEdge(Params params, Problem problem) {
		this.params = params;
		this.problem = problem;
	}

	@Override
	public List<Adjacency> doCrossOver(List<Adjacency> selection) {

		List<Adjacency> children = new ArrayList<Adjacency>();

		while (!selection.isEmpty()) {
			Adjacency firstParent = selectParent(selection);
			Adjacency secondParent = selectParent(selection);

			List<Integer> path = constructPath(firstParent, secondParent);

			children.add(new Adjacency(problem, path));
		}

		return children;
	}

	public List<Integer> constructPath(Adjacency firstParent,
			Adjacency secondParent) {
		Integer[] result = new Integer[firstParent.size()];
		List<String> options = initializeOptions(firstParent.size());
		Adjacency currentParent = secondParent;
		// Edge currentEdge = currentParent.getRandomEdge(params.rand);
		Edge currentEdge = new Edge(0, 1);
		options.remove(1 + "");
		int counter = 0;
		while (!options.isEmpty()) {
			counter++;
			result[currentEdge.getBegin()] = currentEdge.getEnd();
			currentEdge = determineEdge(result, options, currentParent,
					currentEdge, counter);
			currentParent = (currentParent == firstParent) ? secondParent
					: firstParent;
			options.remove(currentEdge.getEnd() + "");
			
		}
		result[currentEdge.getBegin()] = currentEdge.getEnd();
		return Arrays.asList(result);
	}

	private Edge determineEdge(Integer[] result, List<String> options,
			Adjacency currentParent, Edge currentEdge, int counter) {
		currentEdge = currentParent.getNextEdge(currentEdge);
		while (producesCycle(currentEdge, result, counter)) {
			currentEdge = chooseNewEdge(currentEdge, options);
		}
		return currentEdge;
	}

	private List<String> initializeOptions(int number) {
		List<String> options = new ArrayList<String>();
		for (int i = 0; i < number; i++) {
			options.add(i + "");
		}
		return options;
	}

	private Edge chooseNewEdge(Edge currentEdge, List<String> options) {
		System.out.println(options);
		int end = Integer.valueOf(options.get(params.rand.nextInt(options.size())));
		options.remove(end + "");
		return new Edge(currentEdge.getBegin(), end);
	}

	private boolean producesCycle(Edge currentEdge, Integer[] result, int counter) {
		return counter != problem.size()
				&& result[currentEdge.getEnd()] != null;
	}

	private Adjacency selectParent(List<Adjacency> selection) {
		return selection.remove(params.rand.nextInt(selection.size()));
	}

}
