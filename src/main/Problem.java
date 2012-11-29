package main;

import java.util.List;

import util.Point;


public class Problem {

	private List<Point> cities;
	
	public Problem(List<Point> cities) {
		this.cities = cities;
	}
	
	public int size(){
		return cities.size();
	}
	
	public double distance(Point c1, Point c2){
		return c1.distance(c2);
	}
	
	public double distance(int c1, int c2) {
		return cities.get(c1).distance(cities.get(c2));
	}
}
