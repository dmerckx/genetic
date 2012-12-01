package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import main.Problem;
import main.crossover.EdgeRecombination;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import params.Params;
import representations.path.Path;
import util.ProblemGenerator;
import factory.PathFactory;

public class EdgeRecombinationTest {

	private static EdgeRecombination edgeRecombination;
	
	private static Problem problem;
	
	private static Params params;
	
	private static Path parent1;
	private static Path parent2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		problem = ProblemGenerator.generate("../genetic/datafiles/rondrit008.tsp");
		
		params = new Params();
		params.rand = new Random(33);
		
		List<Integer> path1 = new ArrayList<Integer>();
		path1.add(0);
		path1.add(1);
		path1.add(2);
		path1.add(3);
		path1.add(4);
		path1.add(5);
		path1.add(6);
		path1.add(7);
		path1.add(8);	
		parent1 = new Path(problem, path1);
		
		List<Integer> path2 = new ArrayList<Integer>();
		path2.add(3);
		path2.add(0);
		path2.add(1);
		path2.add(7);
		path2.add(6);
		path2.add(5);
		path2.add(8);
		path2.add(2);
		path2.add(4);
		parent2 = new Path(problem, path2);
		
		edgeRecombination = new EdgeRecombination(new PathFactory(), problem, params);
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void constructEdgeMapTest() {
		List<Set<Integer>> edgeMap = edgeRecombination.constructEdgeMap(parent1, parent2);
		//0
		assertTrue(edgeMap.get(0).contains(8));
		assertTrue(edgeMap.get(0).contains(1));
		assertTrue(edgeMap.get(0).contains(3));
		
//		1
		assertTrue(edgeMap.get(1).contains(0));
		assertTrue(edgeMap.get(1).contains(2));
		assertTrue(edgeMap.get(1).contains(7));
	
//		2
		assertTrue(edgeMap.get(2).contains(1));
		assertTrue(edgeMap.get(2).contains(3));
		assertTrue(edgeMap.get(2).contains(8));
		assertTrue(edgeMap.get(2).contains(4));
		
//		3
		assertTrue(edgeMap.get(3).contains(4));
		assertTrue(edgeMap.get(3).contains(0));
		assertTrue(edgeMap.get(3).contains(2));
		
//		4
		assertTrue(edgeMap.get(4).contains(3));
		assertTrue(edgeMap.get(4).contains(5));
		assertTrue(edgeMap.get(4).contains(2));
		
//		5
		assertTrue(edgeMap.get(5).contains(4));
		assertTrue(edgeMap.get(5).contains(6));
		assertTrue(edgeMap.get(5).contains(8));
		
//		6
		assertTrue(edgeMap.get(6).contains(7));
		assertTrue(edgeMap.get(6).contains(5));
		
//		7
		assertTrue(edgeMap.get(7).contains(1));
		assertTrue(edgeMap.get(7).contains(6));
		assertTrue(edgeMap.get(7).contains(8));
		
//		8
		assertTrue(edgeMap.get(8).contains(5));
		assertTrue(edgeMap.get(8).contains(2));
		assertTrue(edgeMap.get(8).contains(7));
		assertTrue(edgeMap.get(8).contains(0));
		
	}
	
	@Test
	public void breedTest() {
		List<Integer> result = edgeRecombination.breed(parent1, parent2);
		
		assertEquals((Integer)0, result.get(0));
		assertEquals((Integer)3, result.get(1));
		assertEquals((Integer)4, result.get(2));
		assertEquals((Integer)5, result.get(3));
		assertEquals((Integer)6, result.get(4));
		assertEquals((Integer)7, result.get(5));
		assertEquals((Integer)8, result.get(6));
		assertEquals((Integer)2, result.get(7));
		assertEquals((Integer)1, result.get(8));
		
		
	}
	
}
