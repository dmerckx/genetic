package test;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.Problem;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import params.Params;
import representations.Adjacency;
import util.ProblemGenerator;
import crossover.AlternatingEdge;
import crossover.factory.AdjacencyFactory;

public class AlternatingEdgeTest {

	private static Adjacency firstParent;
	private static Adjacency secondParent;
	
	private static Problem problem = ProblemGenerator.generate("../genetic/datafiles/rondrit008.tsp");
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		List<Integer> path1 = new ArrayList<Integer>();
		List<Integer> path2 = new ArrayList<Integer>();
		path1.add(1);
		path1.add(2);
		path1.add(7);
		path1.add(6);
		path1.add(8);
		path1.add(0);
		path1.add(3);
		path1.add(4);
		path1.add(5);
		
		path2.add(6);
		path2.add(4);
		path2.add(0);
		path2.add(5);
		path2.add(8);
		path2.add(1);
		path2.add(7);
		path2.add(3);
		path2.add(2);
		firstParent = new Adjacency(problem, path1);
		secondParent = new Adjacency(problem, path2);
	}

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testCrossOver() {
		Params params = new Params();
		params.rand = new Random(1);
		List<Adjacency> parents = new ArrayList<Adjacency>();
		parents.add(firstParent);
		parents.add(secondParent);
		
		AlternatingEdge crossover = new AlternatingEdge(new AdjacencyFactory(),params, problem);
		System.out.println(crossover.doCrossOver(parents).get(0).getPath());
	}

}
