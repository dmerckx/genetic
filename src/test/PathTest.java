package test;


import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import main.Problem;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
	
}
