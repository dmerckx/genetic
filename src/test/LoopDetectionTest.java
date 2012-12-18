package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.LoopDetection;
import main.Problem;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import representations.Adjacency;
import representations.path.Path;
import util.ProblemGenerator;

public class LoopDetectionTest {

	private static Problem problem;	
	private static Adjacency ad;
	private static String filePath = "../genetic/datafiles/rondritTest.tsp";
	public static final int rounding = 1000000;
	public static final double MACH = 1e-10;
	
	private static final LoopDetection<Adjacency> loopDetection = new LoopDetection<Adjacency>();
	private static final LoopDetection<Path> loopDetection1 = new LoopDetection<Path>();
	
	private static Path pa;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		problem = ProblemGenerator.generate(filePath);
		List<Integer> path = new ArrayList<Integer>();
		path.add(1);
		path.add(3);
		path.add(4);
		path.add(2);
		path.add(5);
		path.add(6);
		path.add(0);
		ad = new Adjacency(problem, path);
		List<Integer> path1 = new ArrayList<Integer>();
		path1.add(0);
		path1.add(1);
		path1.add(3);
		path1.add(2);
		path1.add(4);
		path1.add(5);
		path1.add(6);
		pa = new Path(problem, path1);
		
	}
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testCorrect_Adjacency_Simple() {
		int counter = 0;
		for (int i = 0; i < 100; i++) {
			ad.setRandom(new Random());
			double before = ad.getPathLength();
			loopDetection.correct(ad);
			double after = ad.getPathLength();
			if(after - before <= 0)
				counter++;
		}
		assertTrue(counter == 100);
	}
	
	@Test
	public void testCorrect_Path_Simple() {
		int counter = 0;
		for (int i = 0; i < 100; i++) {
			ad.setRandom(new Random());
			double before = ad.getPathLength();
			loopDetection1.correct(pa);
			double after = ad.getPathLength();
			if(after - before <= 0)
				counter++;
		}
		assertTrue(counter == 100);
	}
	
}
