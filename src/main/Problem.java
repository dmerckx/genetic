package main;
import java.awt.Point;
import java.util.List;


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
}
