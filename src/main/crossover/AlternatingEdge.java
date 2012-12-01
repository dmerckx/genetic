package main.crossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import factory.AdjacencyFactory;

import main.Problem;
import params.Params;
import representations.Adjacency;
import representations.Edge;

public class AlternatingEdge extends CrossOver<Adjacency> {

	//TODO lijst met string omzetten naar lijst met ints en werken met new Integer() voor het removen.

	public AlternatingEdge(AdjacencyFactory adjacencyFactory, Params params, Problem problem) {
		super(adjacencyFactory, problem, params);
	}

	//TODO misschien sneller met array list door de omzettingen die moeten gebeuren
	@Override
	protected List<Integer> breed(Adjacency firstParent,
			Adjacency secondParent) {
		Integer[] result = new Integer[firstParent.size()];
		List<String> options = initializeOptions(firstParent.size());
		Adjacency currentParent = firstParent;
		Edge currentEdge = currentParent.getRandomEdge(params.rand);
		options.remove(currentEdge.getEnd() + "");
		int counter = 1;
		while (Arrays.asList(result).contains(null)) {
			counter++;
			result[currentEdge.getBegin()] = currentEdge.getEnd();
			currentEdge = determineEdge(result, options, currentParent,
					currentEdge, isLastEdge(result, counter));
			currentParent = (currentParent == firstParent) ? secondParent
					: firstParent;
			options.remove(currentEdge.getEnd() + "");
			if(isLastEdge(result,counter))
				result[currentEdge.getBegin()] = currentEdge.getEnd();
		}

		return Arrays.asList(result);
	}

	private boolean isLastEdge(Integer[] result, int counter) {
		return (counter == result.length? true: false);
	}

	/**
	 * Determines which edge to add next. If adding the edge would result in forming
	 * a cycle then another (randomly chosen) one is picked instead. 
	 * @param result
	 * @param options
	 * @param currentParent
	 * @param currentEdge
	 * @param isLastEdge
	 * @return
	 */
	private Edge determineEdge(Integer[] result, List<String> options,
			Adjacency currentParent, Edge currentEdge, boolean isLastEdge) {
		if(isLastEdge) {
			int end;
			if(!options.isEmpty())
				end = Integer.valueOf(options.get(0));
			else {
				end = getRemainingCity(result);
			}
			currentEdge = new Edge(currentEdge.getEnd(), end);
		}
		else {	
			currentEdge = currentParent.getNextEdge(currentEdge);
			while (hasCycle(currentEdge, result)) {
				currentEdge = chooseNewEdge(currentEdge, options);
			}
		}
		return currentEdge;
	}

	private int getRemainingCity(Integer[] result) {
		List<String> options = initializeOptions(result.length);
		int index = 0;
		while(options.size() > 1) {
			if(result[index] != null && options.contains(result[index]+"")) {
				options.remove(result[index]+"");
			}
			index++;
		}
		return Integer.valueOf(options.get(0));
	}

	private List<String> initializeOptions(int number) {
		List<String> options = new ArrayList<String>();
		for (int i = 0; i < number; i++) {
			options.add(i + "");
		}
		return options;
	}

	private Edge chooseNewEdge(Edge currentEdge, List<String> options) {
		int end = Integer.valueOf(options.get(params.rand.nextInt(options
				.size())));
		options.remove(end + "");
		return new Edge(currentEdge.getBegin(), end);
	}

	private boolean hasCycle(Edge currentEdge, Integer[] result) {
		return result[currentEdge.getEnd()] != null;
	}

}
