package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.AdjacencyGA;
import main.Problem;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import params.Params;
import params.TestParams;
import representations.Adjacency;
import selectors.RWS;
import util.ProblemGenerator;

public class AdjacencyGATest {
	
	private static Problem problem;
	private static String filePath = "../genetic/datafiles/rondrit067.tsp";
	
	private Params params;
	private AdjacencyGA adj;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		problem = ProblemGenerator.generate(filePath);
	}
	
	@Before
	public void setUp() throws Exception {
		params = new TestParams();
		adj = new AdjacencyGA(params, null, null, null);
	}
	
	@Test
	public void checkInit(){
		List<Adjacency> result = adj.initPopulation(problem);
		for(Adjacency chrom:result){
			assertTrue(checkPath( chrom.getPath() ));
		}
	}
	
	@Test
	public void checkRWS(){
		RWS<Adjacency> rws = new RWS<Adjacency>(params);
		adj = new AdjacencyGA(params, rws, null, null);
		
		List<Adjacency> pop = adj.initPopulation(problem);
		Collections.sort(pop);
		
		double best = pop.get(0).getFitness();
		double worst = pop.get(pop.size()-1).getFitness();
		double total = 0;
		for(Adjacency chrom:pop){
			total += chrom.getFitness();
		}
		double mean = total / pop.size();
		
		System.out.println(best);
		System.out.println(mean);
		System.out.println(worst);
		
		List<Adjacency> selection = rws.doSelection(pop);
		
		System.out.println(selection.size());
		
		
		best = selection.get(0).getFitness();
		worst = selection.get(selection.size()-1).getFitness();
		total = 0;
		for(Adjacency chrom:selection){
			total += chrom.getFitness();
		}
		mean = total / selection.size();
		
		System.out.println(best);
		System.out.println(mean);
		System.out.println(worst);
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
