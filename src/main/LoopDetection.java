package main;

import representations.Chromosome;
import representations.path.Path;

public class LoopDetection<R extends Chromosome> {

	public LoopDetection() {
	}

	public void correct(R representation) {
		Path path = representation.toPath();
		for (int i = 0; i < path.size(); i++) {
			int firstCity = path.getPath().get(i);
			int indexSecondCity = (i + 1) % path.size();
			int indexThirdCity = (i + 2) % path.size();
			int fourthCity = path.getPath().get((i + 3) % path.size());
			if(!isAlreadyShortestPath(firstCity, indexSecondCity, indexThirdCity, fourthCity, path)) {
				path.swap(indexSecondCity, indexThirdCity);
			}
		}
		representation.fromPath(path);
	}

	private boolean isAlreadyShortestPath(int first, int second, int third, int last, Path path) {
		Path clone = path.clone();
		clone.swap(second, third);
		return path.getPathLength(first, last) < clone.getPathLength(first, last);
	}

}
