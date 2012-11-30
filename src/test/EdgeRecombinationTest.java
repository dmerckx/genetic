package test;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.Problem;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import params.Params;
import representations.path.Path;
import util.ProblemGenerator;
import crossover.EdgeRecombination;
import crossover.factory.PathFactory;

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
		params.rand = new Random(1);
		
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
		int[][] edgeMap = edgeRecombination.constructEdgeMap(parent1, parent2);
		//0
		assertEquals(8,edgeMap[0][0]);
		assertEquals(1,edgeMap[0][1]);
		assertEquals(3,edgeMap[0][2]);
		assertEquals(1,edgeMap[0][3]);
		
//		1
		assertEquals(0,edgeMap[1][0]);
		assertEquals(2,edgeMap[1][1]);
		assertEquals(0,edgeMap[1][2]);
		assertEquals(7,edgeMap[1][3]);
	
//		2
		assertEquals(1,edgeMap[2][0]);
		assertEquals(3,edgeMap[2][1]);
		assertEquals(8,edgeMap[2][2]);
		assertEquals(4,edgeMap[2][3]);
		
//		3
		assertEquals(4,edgeMap[3][0]);
		assertEquals(0,edgeMap[3][1]);
		assertEquals(2,edgeMap[3][2]);
		assertEquals(4,edgeMap[3][3]);
		
//		4
		assertEquals(3,edgeMap[4][0]);
		assertEquals(5,edgeMap[4][1]);
		assertEquals(2,edgeMap[4][2]);
		assertEquals(3,edgeMap[4][3]);
		
//		5
		assertEquals(4,edgeMap[5][0]);
		assertEquals(6,edgeMap[5][1]);
		assertEquals(6,edgeMap[5][2]);
		assertEquals(8,edgeMap[5][3]);
		
//		6
		assertEquals(7,edgeMap[6][0]);
		assertEquals(5,edgeMap[6][1]);
		assertEquals(5,edgeMap[6][2]);
		assertEquals(7,edgeMap[6][3]);
		
//		7
		assertEquals(1,edgeMap[7][0]);
		assertEquals(6,edgeMap[7][1]);
		assertEquals(6,edgeMap[7][2]); 
		assertEquals(8,edgeMap[7][3]); 
		
//		8
		assertEquals(5,edgeMap[8][0]);
		assertEquals(2,edgeMap[8][1]);
		assertEquals(7,edgeMap[8][2]);
		assertEquals(0,edgeMap[8][3]);
		
	}
	
	@Test
	public void getFitnessTest() {
		//TODO
	}
	
}
