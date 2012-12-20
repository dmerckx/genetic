package test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
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
		assertTrue(ad.getFitnes() - fitness <= 1e-15);
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
		Adjacency ad1 = new Adjacency(problem) {
			@Override
			public List<Integer> getPath() {
				List<Integer> result = new ArrayList<Integer>();
				result.add(5);
				result.add(0);
				result.add(1);
				result.add(2);
				result.add(3);
				result.add(4);
				return result;
			}
		};
		Edge edge = ad1.getNextEdge(new Edge(0,5));
		assertEquals(5,edge.getBegin());
		assertEquals(4,edge.getEnd());
		Edge edge1 = ad1.getNextEdge(new Edge(5,4));
		assertEquals(4,edge1.getBegin());
		assertEquals(3,edge1.getEnd());
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
	
	@Test
	public void testGetPathLength_partial() {
		ad.setRandom(new Random(5));
		ad.printPath();
		assertTrue((Math.floor(ad.getPathLength(0, 13)*1000)/1000)-409.664 <= 1e-15);
	}
	
	@Test
	public void getReseversePath() {
		List<Integer> result = new ArrayList<Integer>();
		result.add(1);
		result.add(2);
		result.add(8);
		result.add(6);
		result.add(3);
		result.add(7);
		result.add(0);
		result.add(4);
		result.add(5);
		
		Adjacency ad1 = new Adjacency(problem, result);
			
		List<Integer> reverse = ad1.getReversePath();
		assertEquals((int)reverse.get(0),6);
		assertEquals((int)reverse.get(1),0);
		assertEquals((int)reverse.get(2),1);
		assertEquals((int)reverse.get(3),4);
		assertEquals((int)reverse.get(4),7);
		assertEquals((int)reverse.get(5),8);
		assertEquals((int)reverse.get(6),3);
		assertEquals((int)reverse.get(7),5);
		assertEquals((int)reverse.get(8),2);
	}
	
}
