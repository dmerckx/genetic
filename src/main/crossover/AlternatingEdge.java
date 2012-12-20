package main.crossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.Problem;
import params.Params;
import representations.Adjacency;
import representations.Edge;
import factory.AdjacencyFactory;

public class AlternatingEdge extends CrossOver<Adjacency> {

	private Direction currentDirection = getRandomDirection();
	
	public AlternatingEdge(Params params, Problem problem) {
		super(new AdjacencyFactory(), problem, params);
	}

	// TODO misschien sneller met array list door de omzettingen die moeten
	// gebeuren
	@Override
	protected List<Integer> breed(ParentChromosome<Adjacency> first,
			ParentChromosome<Adjacency> second) {
		currentDirection = getRandomDirection();
		ParentChromosome<Adjacency> currentParent = first;
		Integer[] result = new Integer[problem.size()];
		List<Integer> options = initializeOptions(problem.size());
		Edge currentEdge = currentParent.getChromOfDirection(currentDirection)
				.getRandomEdge(params.rand);
		options.remove(new Integer(currentEdge.getEnd()));
		int counter = 1;
		while (Arrays.asList(result).contains(null)) {
			counter++;
			result[currentEdge.getBegin()] = currentEdge.getEnd();
			currentParent = (currentParent == first) ? second : first;
			currentEdge = determineEdge(result, options, currentParent,
					currentEdge, isLastEdge(result, counter));
			options.remove(new Integer(currentEdge.getEnd()));
			if (isLastEdge(result, counter))
				result[currentEdge.getBegin()] = currentEdge.getEnd();
		}

		return Arrays.asList(result);
	}

	private Direction getRandomDirection() {
		return params.rand.nextFloat() > 0.5 ? Direction.LEFT_TO_RIGHT
				: Direction.RIGHT_TO_LEFT;
	}

	private boolean isLastEdge(Integer[] result, int counter) {
		return (counter == result.length ? true : false);
	}

	/**
	 * Determines which edge to add next. If adding the edge would result in
	 * forming a cycle then another (randomly chosen) one is picked instead.
	 * 
	 * @param result
	 * @param options
	 * @param currentParent
	 * @param currentEdge
	 * @param isLastEdge
	 * @return
	 */
	private Edge determineEdge(Integer[] result, List<Integer> options,
			ParentChromosome<Adjacency> parent, Edge edge, boolean isLastEdge) {
		if (isLastEdge) {
			int end;
			if (!options.isEmpty())
				end = options.get(0);
			else
				end = getRemainingCity(result);
			edge = new Edge(edge.getEnd(), end);
		} else {
			edge = parent.getChromOfDirection(currentDirection).getNextEdge(edge);
			while (introducesCycle(edge, result)) {
				currentDirection = currentDirection.getOpposite();
				edge = parent.getChromOfDirection(currentDirection).getNextEdge(new Edge(-1,edge.getBegin()));
				if(!introducesCycle(edge, result)) 
					break;
				edge = chooseNewEdge(edge, options);
			}
		}
		return edge;
	}

	private int getRemainingCity(Integer[] result) {
		List<Integer> options = initializeOptions(result.length);
		int index = 0;
		while (options.size() > 1) {
			if (result[index] != null
					&& options.contains(new Integer(result[index]))) {
				options.remove(new Integer(result[index]));
			}
			index++;
		}
		return options.get(0);
	}

	/**
	 * Returns a list with all subsequent numbers from 0 to the given number -1.
	 * 
	 * @param number
	 * @return
	 */
	private List<Integer> initializeOptions(int number) {
		List<Integer> options = new ArrayList<Integer>();
		for (int i = 0; i < number; i++) {
			options.add(i);
		}
		return options;
	}

	private Edge chooseNewEdge(Edge edge, List<Integer> options) {
		int end = options.get(params.rand.nextInt(options.size()));
		options.remove(new Integer(end));
		return new Edge(edge.getBegin(), end);
	}

	private boolean introducesCycle(Edge edge, Integer[] result) {
		return result[edge.getEnd()] != null;
	}

}
