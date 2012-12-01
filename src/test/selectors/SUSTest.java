package test.selectors;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.Problem;
import main.selectors.RWS;
import main.selectors.SUS;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import params.Params;
import params.TestParams;
import util.ProblemGenerator;

public class SUSTest {
	
	private static Problem problem;
	private static String filePath = "../genetic/datafiles/rondrit016.tsp";
	
	private Params params;
	private SUS<DummyRep> sus;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		problem = ProblemGenerator.generate(filePath);		
	}
	
	@Before
	public void setUp() throws Exception {
		params = new TestParams();
		sus = new SUS<DummyRep>(params);
	}
	
	@Test
	public void testDoSelection(){
		List<DummyRep> pop = new ArrayList<DummyRep>(); 
		params.elitists = 0.0;
		params.rand = new DummyRand(0.1d);
		
		DummyRep r1 = new DummyRep(1);
		DummyRep r2 = new DummyRep(2);
		DummyRep r3 = new DummyRep(3);
		DummyRep r4 = new DummyRep(4);
		
		pop.add(r2);
		pop.add(r4);
		pop.add(r3);
		pop.add(r1);
		
		Collections.sort(pop);

		List<DummyRep> newPop = sus.doSelection(pop);
		
		assertEquals(newPop.get(0), RWS.select(pop, 10, 0.1/4));
		assertEquals(newPop.get(1), RWS.select(pop, 10, 0.1/4 + 1d/4));
		assertEquals(newPop.get(2), RWS.select(pop, 10, 0.1/4 + 2d/4));
		assertEquals(newPop.get(3), RWS.select(pop, 10, 0.1/4 + 3d/4));
	}
	
	@Test
	public void testDoSelectionWithElitists1(){
		List<DummyRep> pop = new ArrayList<DummyRep>(); 
		params.elitists = 0.25;
		params.rand = new DummyRand(0d);
		
		DummyRep r1 = new DummyRep(1);
		DummyRep r2 = new DummyRep(2);
		DummyRep r3 = new DummyRep(3);
		DummyRep r4 = new DummyRep(4);
		
		pop.add(r2);
		pop.add(r4);
		pop.add(r3);
		pop.add(r1);
		
		Collections.sort(pop);
		
		List<DummyRep> newPop = sus.doSelection(pop);
		
		assertEquals(newPop.get(0), RWS.select(pop, 10, 0d/3));
		assertEquals(newPop.get(1), RWS.select(pop, 10, 1d/3));
		assertEquals(newPop.get(2), RWS.select(pop, 10, 2d/3));
	}
	
	@Test
	public void testDoSelectionWithElitists2(){
		List<DummyRep> pop = new ArrayList<DummyRep>(); 
		params.elitists = 0.25;
		params.rand = new DummyRand(0.5d);
		
		DummyRep r1 = new DummyRep(1);
		DummyRep r2 = new DummyRep(2);
		DummyRep r3 = new DummyRep(3);
		DummyRep r4 = new DummyRep(4);
		
		pop.add(r2);
		pop.add(r4);
		pop.add(r3);
		pop.add(r1);
		
		Collections.sort(pop);

		List<DummyRep> newPop = sus.doSelection(pop);
		
		assertEquals(newPop.get(0), RWS.select(pop, 10, 1d/6));
		assertEquals(newPop.get(1), RWS.select(pop, 10, 3d/6));
		assertEquals(newPop.get(2), RWS.select(pop, 10, 5d/6));
	}
	
	@Test
	public void testDoSelectionWithElitists3(){
		List<DummyRep> pop = new ArrayList<DummyRep>(); 
		params.elitists = 0.25;
		params.rand = new DummyRand(1.0d);
		
		DummyRep r1 = new DummyRep(1);
		DummyRep r2 = new DummyRep(2);
		DummyRep r3 = new DummyRep(3);
		DummyRep r4 = new DummyRep(4);
		
		pop.add(r2);
		pop.add(r4);
		pop.add(r3);
		pop.add(r1);
		
		Collections.sort(pop);

		List<DummyRep> newPop = sus.doSelection(pop);
		
		assertEquals(newPop.get(0), RWS.select(pop, 10, 1d/3));
		assertEquals(newPop.get(1), RWS.select(pop, 10, 2d/3));
		assertEquals(newPop.get(2), RWS.select(pop, 10, 3d/3));
	}

}