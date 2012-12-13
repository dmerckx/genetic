package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import main.GA;
import main.Problem;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import factory.AdjacencyFactory;

import params.Params;
import params.TestParams;
import representations.Adjacency;
import util.ProblemGenerator;

public class AdjacencyGATest {
	
	private static Problem problem;
	private static String filePath = "../genetic/datafiles/rondrit067.tsp";
	
	private Params params;
	private GA<Adjacency> adj;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		problem = ProblemGenerator.generate(filePath);
	}
	
	@Before
	public void setUp() throws Exception {
		params = new TestParams();
		adj = new GA<Adjacency>(params, new AdjacencyFactory(), null, null, null, null, null);
	}
	
	@Test
	public void checkInit(){
		List<Adjacency> result = adj.initPopulation(problem);
		for(Adjacency chrom:result){
			assertTrue(checkPath( chrom.getPath() ));
		}
	}
	
	public boolean checkPath(List<Integer> path){
		List<Integer> seen = new ArrayList<Integer>();
		
		int index = 0;
		for(int i = 0; i < path.size(); i++){
			if( seen.contains(index) ){
				return false;
			}
			seen.add(index);
			index = path.get(index);
		}
		return true;
	}
}
