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
		double fitness = 317.254916;
		assertTrue(Math.floor(ad.getFitness()*1000000)/1000000 - fitness <= 1e-15);
	}
	
	@Test
	public void testGetFitness_complex() {
		ad.setRandom(new Random(1));
		ad.printPath();
		double fitness = 2444.588783;
		assertTrue(Math.floor(ad.getFitness()*1000000)/1000000 - fitness <= 1e-15);
	}
	
	@Test
	public void testGetRandomEdge() {
		Edge edge = ad.getRandomEdge(new Random(65));
		assertEquals(11,edge.getBegin());
		assertEquals(0,edge.getEnd());
	}
	
	@Test
	public void testGetNextEdge() {
		Edge edge = ad.getNextEdge(new Edge(0,14));
		assertEquals(14,edge.getBegin());
		assertEquals(4,edge.getEnd());
		Edge edge1 = ad.getNextEdge(new Edge(10,11));
		assertEquals(11,edge1.getBegin());
		assertEquals(0,edge1.getEnd());
	}
	
}
