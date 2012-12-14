package test;


import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import main.Problem;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import representations.Adjacency;
import representations.path.Path;
import util.ProblemGenerator;

public class PathTest {

	private static Problem problem;	
	private static Path path;
	private static String filePath = "../genetic/datafiles/rondrit016.tsp";
	
	public static final double machine_precision = 1e-10;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		problem = ProblemGenerator.generate(filePath);
		List<Integer> path1 = new ArrayList<Integer>();
		path1.add(0);
		path1.add(3);
		path1.add(7);
		path1.add(2);
		path1.add(15);
		path1.add(13);
		path1.add(11);
		path1.add(4);
		path1.add(9);
		path1.add(6);
		path1.add(14);
		path1.add(12);
		path1.add(10);
		path1.add(8);
		path1.add(5);
		path1.add(1);
		
		path = new Path(problem, path1);
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void getFitnessTest() {
		double fitness = 1 / 3210.522106;
		assertTrue(Math.abs(path.getFitness() - fitness) <= machine_precision);
	}
	
	@Test
	public void testGetPathLength_partial() {
		assertTrue((Math.floor(path.getPathLength(8, 3)*1000)/1000)-592.237 <= 1e-15);
	}
	
	@Test
	public void testSwap() {
		path.swap(0, 15);
		assertEquals((int)0,(int)path.getPath().get(15));
		assertEquals((int)1,(int)path.getPath().get(0));
		path.swap(5, 2);
		assertEquals((int)13,(int)path.getPath().get(2));
		assertEquals((int)7,(int)path.getPath().get(5));
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
		
		Path ad1 = new Path(problem, result);
			
		List<Integer> reverse = ad1.getReversePath();
		assertEquals((int)reverse.get(0),5);
		assertEquals((int)reverse.get(1),4);
		assertEquals((int)reverse.get(2),0);
		assertEquals((int)reverse.get(3),7);
		assertEquals((int)reverse.get(4),3);
		assertEquals((int)reverse.get(5),6);
		assertEquals((int)reverse.get(6),8);
		assertEquals((int)reverse.get(7),2);
		assertEquals((int)reverse.get(8),1);
	}
	
}
