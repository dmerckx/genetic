package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Point;


public class Problem {

	public final List<Point> cities;
	
	public final Map<Point, Integer> index = new HashMap<Point, Integer>();
	
	public Problem(List<Point> cities) {
		this.cities = cities;
		int index = 0;
		for (Point point : cities) {
			this.index.put(point, new Integer(index));
			index++;
		}
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
