package test;

import static org.junit.Assert.*;

import main.Problem;

import org.junit.BeforeClass;
import org.junit.Test;

import representations.Adjacency;
import util.ProblemGenerator;

public class RWSTest {
	
private static Problem problem;
	
	private static Adjacency ad;
	
	private static String filePath = "../genetic/datafiles/rondrit016.tsp";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		problem = ProblemGenerator.generate(filePath);
		ad = new Adjacency(problem);
	}

}
