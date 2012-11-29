package test;


import static org.junit.Assert.assertTrue;

import java.util.Random;

import main.Problem;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import representations.Adjacency;
import util.Point;
import util.ProblemGenerator;

public class AdjacencyTest {

	private static Problem problem;
	
	private static Adjacency ad;
	
	private static String filePath = "C:/Users/Kristof/Documents/School/Semester 1/Genetic Algorithms and Evolutionary Computing/Project/ga/template/datasets/rondrit016.tsp";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Point c0 = new Point(273.46509, 204.95761);
//		Point c1 = new Point( 367.34973, 5.14920);
//		Point c2 = new Point( 378.83914,  86.38704);
//		Point c3 = new Point(225.10242, 170.73392);
//		Point c4 = new Point(179.65437, 211.91306);
//		Point c5 = new Point(272.98428,  97.19602);
//		Point c6 = new Point(20.70829, 227.85622);
//		Point c7 = new Point(31.18826,  25.77590);
//		Point c8 = new Point(441.09842, 162.63313);
//		Point c9 = new Point(215.28487,  30.03942);
//		Point c10 = new Point(220.84784, 112.75235);
		Point c11 = new Point(153.61868,  31.05856);
//		Point c12 = new Point(251.23114, 240.80257);
//		Point c13 = new Point(339.36380, 209.36363);
//		Point c14 = new Point(141.00000, 143.00000);
//		Point c15 = new Point(143.00000,  85.00000);
//		List<Point> cities = new ArrayList<Point>(16);
		System.out.println(distance(c0,c11));
		problem = ProblemGenerator.generate(filePath);
		ad = new Adjacency(problem);
		
		//subtour of only 2 cities
		
//		ad.printPath();
		
//0-14		146.23865940858522
//14-4 		79.01373398758282
//4-1 		279.25050038921904
//1-15		238.13641387178257
//15-6		188.05095566737356
//6-5		284.10431709940644
//5-8		180.40060818381872
//8-7		432.153029232121
//7-3		242.10644148974225
//3-10		58.13745703624558
//10-11		105.79997768841777
//11-0		211.1966893225142		
	}

	public static double distance(Point c1, Point c2) {
		return c1.distance(c2);
	}
	
	@Before
	public void setUp() throws Exception {
		
		
	}

	@Test
	public void testGetFitness_simple() {
		ad.setRandom(new Random(5));
		double fitness = 317.254916;
		assertTrue(Math.floor(ad.getFitness()*1000000)/1000000 - fitness <= 1e-15);
	}
	
	@Test
	public void testGetFitness_complex() {
		ad.setRandom(new Random(1));
		double fitness = 2444.588783;
		assertTrue(Math.floor(ad.getFitness()*1000000)/1000000 - fitness <= 1e-15);
	}
	
}
