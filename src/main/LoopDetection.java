package main;

import representations.Representation;
import representations.path.Path;

public class LoopDetection<R extends Representation> {

	public LoopDetection() {
	}
	
	public void correct(R representation) {
		Path path = representation.toPath();
		for (int i = 0; i < path.size(); i++) {
			int firstCity = path.getPath().get(i);
			int secondCity = path.getPath().get((i + 1) % path.size());
			int thirdCity = path.getPath().get((i + 2) % path.size());
			int fourthCity = path.getPath().get((i + 3) % path.size());
			if(!isAlreadyShortestPath(firstCity, secondCity, thirdCity, fourthCity, path))
				path.swap((i + 1) % path.size(), (i + 2) % path.size());
		}
		representation.fromPath(path);
	}

	private boolean isAlreadyShortestPath(int first, int second, int third, int last, Path path) {
		Path clone = path.clone();
		clone.swap(second, third);
		return path.getPathLength(first, last) < clone.getPathLength(first, last);
	}
	
}
