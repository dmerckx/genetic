package main;

import java.util.List;

import util.Point;


public class Problem {

	public final List<Point> cities;
	public final double[] distances; 
	
	public Problem(List<Point> cities) {
		
		this.cities = cities;
		this.distances = new double[size() * size()];
		
		for(int c1 = 0; c1 < size(); c1++){
			for(int c2 = 0; c2 < size(); c2++){
				distances[c1 * size() + c2] = cities.get(c1).distance(cities.get(c2));
			}
		}
	}
	
	public int size(){
		return cities.size();
	}
	
	/*public double distance(Point c1, Point c2){
		return c1.distance(c2);
	}*/
	
	public double distance(int c1, int c2) {
		return distances[c1 * size() + c2];
	}
	
	@Override
	public String toString() {
		return cities.toString();
	}
}
