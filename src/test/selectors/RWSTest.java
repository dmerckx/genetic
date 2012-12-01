package test.selectors;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import main.Problem;
import main.selectors.RWS;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import params.Params;
import params.TestParams;
import representations.Representation;
import representations.path.Path;
import util.ProblemGenerator;

public class RWSTest {
	
	private static Problem problem;
	private static String filePath = "../genetic/datafiles/rondrit016.tsp";
	
	private Params params;
	private RWS rws;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		problem = ProblemGenerator.generate(filePath);		
	}
	
	@Before
	public void setUp() throws Exception {
		params = new TestParams();
		rws = new RWS<DummyRep>(params);
	}
	
	@Test
	public void testSelect(){
		List<DummyRep> reps = new ArrayList<DummyRep>(); 

		DummyRep r1 = new DummyRep(1);
		DummyRep r2 = new DummyRep(2);
		DummyRep r3 = new DummyRep(3);
		DummyRep r4 = new DummyRep(4);
		
		reps.add(r2);
		reps.add(r4);
		reps.add(r3);
		reps.add(r1);
		
		Collections.sort(reps);

		assertEquals(r1,RWS.select(reps, 10, 0.0));
		assertEquals(r1,RWS.select(reps, 10, 0.05));
		assertEquals(r1,RWS.select(reps, 10, 0.1));
		assertEquals(r2,RWS.select(reps, 10, 0.10000001));
		assertEquals(r2,RWS.select(reps, 10, 0.3));
		assertEquals(r3,RWS.select(reps, 10, 0.30000001));
		assertEquals(r3,RWS.select(reps, 10, 0.6));
		assertEquals(r4,RWS.select(reps, 10, 0.60000001));
		assertEquals(r4,RWS.select(reps, 10, 1));
		assertEquals(r4,RWS.select(reps, 10, 1.001));
	}
	
	@Test
	public void testDoSelection(){
		List<DummyRep> pop = new ArrayList<DummyRep>(); 
		params.elitists = 0.0;
		params.rand = new DummyRand(0.05d, 0.6d, 0.6d, 1d);
		
		DummyRep r1 = new DummyRep(1);
		DummyRep r2 = new DummyRep(2);
		DummyRep r3 = new DummyRep(3);
		DummyRep r4 = new DummyRep(4);
		
		pop.add(r2);
		pop.add(r4);
		pop.add(r3);
		pop.add(r1);
		
		Collections.sort(pop);
		
		pop = rws.doSelection(pop);

		assertEquals(4, pop.size());
		assertEquals(r1,pop.get(0));
		assertEquals(r3,pop.get(1));
		assertEquals(r3,pop.get(2));
		assertEquals(r4,pop.get(3));
	}
	
	@Test
	public void testDoSelectionWithElitists(){
		List<DummyRep> pop = new ArrayList<DummyRep>(); 
		params.elitists = 0.25;
		params.rand = new DummyRand(0.05d, 0.6d, 0.6d, 1d);
		
		DummyRep r1 = new DummyRep(1);
		DummyRep r2 = new DummyRep(2);
		DummyRep r3 = new DummyRep(3);
		DummyRep r4 = new DummyRep(4);
		
		pop.add(r2);
		pop.add(r4);
		pop.add(r3);
		pop.add(r1);
		
		Collections.sort(pop);
		
		pop = rws.doSelection(pop);

		assertEquals(3, pop.size());
		assertEquals(r1,pop.get(0));
		assertEquals(r3,pop.get(1));
		assertEquals(r3,pop.get(2));
	}

}

class DummyRep extends Representation{
	
	public DummyRep(double fitness) {
		super(fitness);
	}
	
	@Override
	public void setRandomImpl(Random rand) {
		
	}

	@Override
	public double getPathLength() {
		throw new RuntimeException("mag niet worden aangeroepen");
	}

	@Override
	public Path toPath() {
		return null;
	}

	@Override
	public void fromPath(Path path) {
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
