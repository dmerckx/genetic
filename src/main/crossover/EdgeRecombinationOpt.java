package main.crossover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import main.Problem;
import params.Params;
import representations.path.Path;
import factory.PathFactory;

public class EdgeRecombinationOpt extends CrossOver<Path> {

//	public Timer timer = new Timer();
	
	public EdgeRecombinationOpt(Problem problem, Params params) {
		super(new PathFactory(), problem, params);
	}

	@Override
	public List<Integer> breed(ParentChromosome<Path> parent1, ParentChromosome<Path> parent2) {
		Path p1 = parent1.getChromOfDirection(Direction.LEFT_TO_RIGHT);
		Path p2 = parent2.getChromOfDirection(Direction.LEFT_TO_RIGHT);
		List<Integer> result = new ArrayList<Integer>();
		List<Integer> unvisitedCities = getCities();
//		timer.start();
		HashMap<Integer,Set<Integer>> edgeMap = constructEdgeMap(p1, p2);
		HashIndex index = new HashIndex();
		index.add(edgeMap);
//		timer.addConstructionMap();
		int currentCity = chooseCity(p1, p2);
		while (unvisitedCities.size() > 1) {
			removeOccurences(currentCity, edgeMap, index);
//			timer.addRemoveOccurences();
			result.add(currentCity);
			unvisitedCities.remove(new Integer(currentCity));
//			timer.addRemoveUnvisited();
			if (hasNeighbours(currentCity, edgeMap)) {
				currentCity = chooseCity(currentCity, edgeMap);
//				timer.addChooseCity();
			}
			else {
				currentCity = chooseCity(unvisitedCities);
//				timer.addChooseCity();
			}
		}
		result.add(unvisitedCities.get(0));
//		timer.addTotal();
		return result;
	}

	/**
	 * Choose a random city from the list of unvisited cities (corresponds to
	 * step 5 of the algorithm).
	 * 
	 * @param unvisitedCities
	 * @return
	 */
	private int chooseCity(List<Integer> unvisitedCities) {
		return unvisitedCities.get(params.rand.nextInt(unvisitedCities.size()));
	}

	/**
	 * Choose the city which has the fewest entities in its (own) edge list. In case of ties, the last city is chosen.
	 * 
	 * @param edgeMap
	 * @return
	 */
	private int chooseCity(int currentCity, HashMap<Integer,Set<Integer>> edgeMap) {
		int currentMinimum = -1;
		int minimumNbNeighbours = 4;
		for (int neighbour : edgeMap.get(currentCity)) {
			int nbNeighbours = edgeMap.get(neighbour).size();
			if(nbNeighbours <= minimumNbNeighbours) {
				currentMinimum = neighbour;
				minimumNbNeighbours = nbNeighbours;
			}
		}
		return currentMinimum;
	}

	/**
	 * Returns a list with all the integers between 0 (inc.) and the size of the
	 * problem (inc.).
	 * 
	 * @return
	 */
	private List<Integer> getCities() {
		List<Integer> cities = new ArrayList<Integer>();
		for (int i = 0; i < problem.size(); i++) {
			cities.add(i);
		}
		return cities;
	}

	/**
	 * Returns true if the given city still has entities in its edge list.
	 * @param city
	 * @param edgeMap
	 * @return
	 */
	private boolean hasNeighbours(int city, HashMap<Integer,Set<Integer>> edgeMap) {
		return !edgeMap.get(city).isEmpty();
	}

	/**
	 * Remove all occurrences of the given city from the right hand side of
	 * the given edgemap. (corresponds to step 2 of the algorithm).
	 * 
	 * @param city
	 * @param edgeMap
	 */
	private void removeOccurences(int city, HashMap<Integer,Set<Integer>> edgeMap, HashIndex index) {
		for(Integer i: index.index.get(city)) {
			edgeMap.get(i).remove(city);
		}
		index.index.remove(city);
	}

	/**
	 * Choose a city of one of the 2 parents at random.
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	private int chooseCity(Path p1, Path p2) {
		return params.rand.nextFloat() > 0.5 ? p1.getRandomCity(params.rand)
				: p2.getRandomCity(params.rand);
	}

	public HashMap<Integer,Set<Integer>> constructEdgeMap(Path parent1, Path parent2) {
		HashMap<Integer,Set<Integer>> edgeMap = new HashMap<Integer,Set<Integer>>();
		for (int city = 0; city < parent1.size(); city++) {
			edgeMap.put(city,getConnectedCities(city, parent1, parent2));
		}
		return edgeMap;
	}

	private Set<Integer> getConnectedCities(int city, Path parent1, Path parent2) {
		Set<Integer> result = new LinkedHashSet<Integer>();
		for (int index = 0; index < parent1.size(); index++) {
			extractNeighbours(city, parent1, result, index);
			extractNeighbours(city, parent2, result, index);
		}
		return result;
	}

	private void extractNeighbours(int city, Path parent, Set<Integer> neighbours, int j) {
		if (parent.getPath().get(j) == city) {
			neighbours.add(convertToValidLeftNeighbour(parent, j,
					parent.size()));
			neighbours.add(convertToValidRightNeighbour(parent, j,
					parent.size()));
		}
	}

	private int convertToValidRightNeighbour(Path parent, int j, int length) {
		int index = (j + 1 < length) ? j + 1 : 0;
		return parent.getPath().get(index);
	}

	private int convertToValidLeftNeighbour(Path parent, int j, int length) {
		int index = (j - 1 >= 0) ? j - 1 : length - 1;
		return parent.getPath().get(index);

	}

}
