package crossover;

import java.util.List;

import main.Problem;
import params.Params;
import crossover.factory.RepresentationFactory;

import representations.path.Path;

public class EdgeRecombination extends CrossOver<Path>{
	
	public EdgeRecombination(RepresentationFactory<Path> factory,
			Problem problem, Params params) {
		super(factory, problem, params);
	}

	@Override
	public List<Integer> breed(Path p1, Path p2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int[][] constructEdgeMap(Path parent1, Path parent2) {
		int[][] edgeMap = new int[parent1.size()][parent1.size()];
		for (int city = 0; city < parent1.size(); city++) {
			edgeMap[city] = getConnectedCities(city, parent1, parent2);
//			for (int i = 0; i < edgeMap[0].length; i++) {
//				System.out.println(edgeMap[city][i]);
//			}
		}
		return edgeMap;
	}

	private int[] getConnectedCities(int city, Path parent1, Path parent2) {
		int[] result = new int[parent1.size()];
		
		int currentIndex = 0;
		for (int index = 0; index < result.length; index++) {
			currentIndex = extractConnectedCities(city, parent1, result,
					currentIndex, index);
			System.out.println(parent1);
			currentIndex = extractConnectedCities(city, parent2, result,
					currentIndex, index);
			System.out.println(parent2);
		}
		
		return result;
	}

	private int extractConnectedCities(int city, Path parent, int[] result,
			int currentIndex, int j) {
		if(parent.getPath().get(j)==city) {
			System.out.println("city: " + (city+1));
			System.out.println("index: " + (j+1));
			result[currentIndex] = convertToValidLeftNeighbour(parent, j, result.length);
			System.out.println("left neighbour: " + (result[currentIndex]+1));
			currentIndex++;
			result[currentIndex] = convertToValidRightNeighbour(parent, j, result.length);
			System.out.println("right neighbour: " + (result[currentIndex]+1));
			currentIndex++;
			System.out.println("city: " + (city+1) + " with neighbours: " + (result[currentIndex-2]+1) +" and " + (result[currentIndex-1]+1));
		}
		return currentIndex;
	}

	private int convertToValidRightNeighbour(Path parent, int j, int length) {
		int index = (j+1 < length) ? j+1 : 0;
		return parent.getPath().get(index);
	}

	private int convertToValidLeftNeighbour(Path parent, int j, int length) {
		int index = (j-1 >= 0) ? j-1 : length-1;
		return parent.getPath().get(index);
		
	}

}
