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
import representations.path.Path;
import util.ProblemGenerator;

public class AdjacencyTest {

	private static Problem problem;	
	private static Adjacency ad;
	private static String filePath = "../genetic/datafiles/rondrit016.tsp";
	public static final int rounding = 1000000;
	public static final double MACH = 1e-10;
	
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
		double fitness = 1 / 2439.190713;
		assertTrue(ad.getFitness() - fitness <= 1e-15);
	}
	
	@Test
	public void testGetFitness_complex() {
		ad.setRandom(new Random(1));
		double fitness = 1 / 3210.522106;
		assertTrue(ad.getFitness() - fitness <= 1e-15);
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
		//TODO ik zie deze af en toe falen
		Edge edge = ad.getNextEdge(new Edge(0,14));
		assertEquals(14,edge.getBegin());
		assertEquals(12,edge.getEnd());
		Edge edge1 = ad.getNextEdge(new Edge(10,11));
		assertEquals(11,edge1.getBegin());
		assertEquals(4,edge1.getEnd());
	}
	
	@Test
	public void testToPath() {
		for(int i = 0; i < 200; i++){
			ad.setRandom(new Random(i));
			double fitness = ad.getFitness();
			
			Path p = ad.toPath();
			
			assertTrue(Math.abs(fitness - p.getFitness()) < MACH);
			
			ad.fromPath(p);
			
			assertTrue(Math.abs(fitness - ad.getFitness()) < MACH);
		}
	}
	
}
