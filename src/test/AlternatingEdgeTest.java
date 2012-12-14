package test;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.Problem;
import main.crossover.AlternatingEdge;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import params.Params;
import params.TestParams;
import representations.Adjacency;
import util.ProblemGenerator;
import factory.AdjacencyFactory;

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
			Params params = new TestParams();
			params.crossover = 1;
			params.rand = new Random(1);
			List<Adjacency> parents = new ArrayList<Adjacency>();
			parents.add(firstParent);
			parents.add(secondParent);
			
			AlternatingEdge crossover = new AlternatingEdge(new AdjacencyFactory(),params, problem);
//		System.out.println(crossover.doCrossOver(parents).get(0).getPath());
//		System.out.println(crossover.doCrossOver(parents).get(1).getPath());
		}

	@Test
	public void testcrossOver_SameParent_DiffChild() {
		int different = 0;
		Params params = new TestParams();
		params.crossover = 1;
		params.rand = new Random();
		AlternatingEdge crossover = new AlternatingEdge(new AdjacencyFactory(),params, problem);
		for (int i = 0; i < 10000; i++) {
			firstParent.setRandom(params.rand);
			Adjacency clone = firstParent.clone();
			List<Adjacency> parents = new ArrayList<Adjacency>();
			parents.add(firstParent);
			parents.add(clone);
			List<Adjacency> children = crossover.doCrossOver(parents);
			if(!equals(children.get(0).getPath(), children.get(1).getPath()))
				different++;
		}
		assertTrue(different > 4500 && different < 5500);
	}

	private boolean equals(List<Integer> p1, List<Integer> p2) {
		int index = search(p1.get(0),p2);
		for (int i = 0; i < p1.size(); i++) {
			if(p1.get(i) != p2.get((index+i)%p1.size()))
				return false;
		}
		return true;
	}

	private int search(int city, List<Integer> p2) {
		for (int i = 0; i < p2.size(); i++) {
			if(p2.get(i) == city)
				return i;
		}
		return 0;
	}

}
