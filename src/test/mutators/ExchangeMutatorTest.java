package test.mutators;

import static org.junit.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import main.Problem;
import main.mutation.ExchangeMutator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import params.Params;
import params.TestParams;
import representations.Adjacency;
import representations.path.Path;
import util.ProblemGenerator;

public class ExchangeMutatorTest {

	private static Problem problem;	
	private static String filePath = "../genetic/datafiles/rondrit016.tsp";
	private static final double MACH = 1e-15;
	
	private Params params;
	private Adjacency ad;	
	private Path pa;
	
	private ExchangeMutator<Adjacency> adMut;
	private ExchangeMutator<Path> paMut;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		problem = ProblemGenerator.generate(filePath);
	}

	@Before
	public void setUp() throws Exception {
		params = new TestParams();
		ad = new Adjacency(problem);
		pa = new Path(problem);
		adMut = new ExchangeMutator<Adjacency>(params);
		paMut = new ExchangeMutator<Path>(params);
	}

	@Test
	public void test() {
		for( int i = 0; i < 200; i++){
			params.rand = new Random(i);
			
			ad.setRandom(params.rand);
			String pathStr = ""+ad.getPath();
			
			System.out.println(ad.getPath());
			
			adMut.mutate(ad);
			
			System.out.println(ad.getPath());
			assertNotSame(pathStr, ad.getPath());
			
			
			pa.setRandom(params.rand);
			pathStr = ""+pa.getPath();
			paMut.mutate(pa);
			assertNotSame(pathStr, pa.getPath());
		}
	}
	
	public boolean checkAdjPath(List<Integer> path){
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
	
	public boolean checkPath(List<Integer> path){
		List<Integer> seen = new ArrayList<Integer>();
		
		for(int index:path){
			if( seen.contains(index) ){
				return false;
			}
			seen.add(index);
			index = path.get(index);
		}
		return true;
	}
}

class DummyRand extends Random{
	
	private List<Double> values;
	
	public DummyRand(double... vals) {
		values = new LinkedList<Double>();
		for(Double v:vals){
			values.add(v);
		}
	}
	
	@Override
	public double nextDouble() {
		double val = values.get(0);
		values.remove(0);
		return val;
	}
	
}
