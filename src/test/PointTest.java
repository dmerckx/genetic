package test;


import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import util.Point;

public class PointTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testDistance_simple() {
		Point point1 = new Point(10.56,58.95);
		Point point2 = new Point(10.56,58.95);
		assertTrue(point1.distance(point2) <= 1e-15);
	}
	
	@Test
	public void testDistance() {
		Point point1 = new Point(10.56,58.95);
		Point point2 = new Point(48,98.63);
		double distance = 54.5550731;
		assertTrue(Math.floor(point1.distance(point2)*1000000)/1000000 - distance <= 1e-15);
	}

}
