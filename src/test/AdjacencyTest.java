package test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import main.Problem;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import representations.Adjacency;
import representations.Edge;
import util.ProblemGenerator;

public class AdjacencyTest {

	private static Problem problem;	
	private static Adjacency ad;
	private static String filePath = "../genetic/datafiles/rondrit016.tsp";
	public static final int rounding = 1000000;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		problem = ProblemGenerator.generate(filePath);
		ad = new Adjacency(problem);
	}
	
	@Before
	public void setUp() throws Exception {
		
		
	}

	@Test
	public void testGetFitness_simple() {
		ad.setRandom(new Random(5));
		double fitness = 2439.190713;
		assertTrue(Math.abs((Math.floor(ad.getFitness()*rounding)/rounding) - fitness) <= 1e-15);
	}
	
	@Test
	public void testGetFitness_complex() {
		ad.setRandom(new Random(1));
		double fitness = 3210.522106;
		assertTrue(Math.abs((Math.floor(ad.getFitness()*rounding)/rounding) - fitness) <= 1e-15);
	}
	
	@SuppressWarnings("serial")
	@Test
	public void testGetRandomEdge() {
		Random rand = new Random(65) {
			@Override
			public int nextInt(int n) {
				return 0;
			}
		};
		Edge edge = ad.getRandomEdge(rand);
		assertEquals(0,edge.getBegin());
		assertEquals(3,edge.getEnd());
	}
	
	@Test
	public void testGetNextEdge() {
		Edge edge = ad.getNextEdge(new Edge(0,14));
		assertEquals(14,edge.getBegin());
		assertEquals(12,edge.getEnd());
		Edge edge1 = ad.getNextEdge(new Edge(10,11));
		assertEquals(11,edge1.getBegin());
		assertEquals(4,edge1.getEnd());
	}
	
}
