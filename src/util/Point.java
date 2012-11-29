package util;

public class Point {
	
	private double x;
	private double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double distance(Point other) {
		return Math.sqrt(
	            (getX() - other.getX()) *  (getX() - other.getX()) + 
	            (getY() - other.getY()) *  (getY() - other.getY())
	        );
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
}
