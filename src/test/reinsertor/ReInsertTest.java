package test.reinsertor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import main.Problem;
import main.insertion.FBI;
import main.insertion.UI;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import params.Params;
import params.TestParams;
import representations.Chromosome;
import representations.path.Path;
import util.ProblemGenerator;

public class ReInsertTest {
	
	private static Problem problem;
	private static String filePath = "../genetic/datafiles/rondrit016.tsp";
	
	private Params params;
	private FBI<DummyRep> fbi;
	private UI<DummyRep> ui;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		problem = ProblemGenerator.generate(filePath);		
	}
	
	@Before
	public void setUp() throws Exception {
		params = new TestParams();
		fbi = new FBI<DummyRep>(params);
		ui = new UI<DummyRep>(params);
	}
	
	@Test
	public void testEnoughChildren(){ 
		int popSize = 3;
		params.popSize = popSize;
		List<DummyRep> oldPop = new ArrayList<DummyRep>();
		
		DummyRep r1 = new DummyRep(1);
		DummyRep r2 = new DummyRep(2);
		DummyRep r3 = new DummyRep(3);
		
		oldPop.add(r2);
		oldPop.add(r3);
		oldPop.add(r1);
		
		List<DummyRep> children = new ArrayList<DummyRep>();
		
		DummyRep c1 = new DummyRep(1);
		DummyRep c2 = new DummyRep(2);
		DummyRep c3 = new DummyRep(3);
		
		children.add(c1);
		children.add(c3);
		children.add(c2);
		
		List<DummyRep> resFBI = fbi.reinsert(oldPop, children);
		List<DummyRep> resUI = ui.reinsert(oldPop, children);
		
		assertEquals(popSize, resFBI.size());
		assertEquals(popSize, resUI.size());
		
		for(int i = 0; i < children.size(); i++){
			assertEquals(resFBI.get(i), children.get(i));
			assertEquals(resUI.get(i), children.get(i));
		}
	}
	
	
	@Test
	public void testTooManyChildren(){ 
		int popSize = 3;
		params.popSize = popSize;
		List<DummyRep> oldPop = new ArrayList<DummyRep>();
		
		DummyRep r1 = new DummyRep(1);
		DummyRep r2 = new DummyRep(2);
		DummyRep r3 = new DummyRep(3);
		
		oldPop.add(r2);
		oldPop.add(r3);
		oldPop.add(r1);
		
		List<DummyRep> children = new ArrayList<DummyRep>();
		
		DummyRep c1 = new DummyRep(1);
		DummyRep c2 = new DummyRep(2);
		DummyRep c3 = new DummyRep(3);
		DummyRep c4 = new DummyRep(4);
		
		children.add(c1);
		children.add(c3);
		children.add(c2);
		children.add(c4);
		
		List<DummyRep> resFBI = fbi.reinsert(clone(oldPop), clone(children));
		List<DummyRep> resUI = ui.reinsert(clone(oldPop), clone(children));
		
		assertEquals(popSize, resFBI.size());
		assertEquals(popSize, resUI.size());
		
		Collections.sort(children);
		for(int i = children.size()-1; i >= children.size() - popSize; i--){
			assertTrue(resFBI.contains(children.get(i)));
			assertTrue(resUI.contains(children.get(i)));
		}
	}
	
	@Test
	public void testTooLittleChildren(){ 
		int popSize = 3;
		params.popSize = popSize;
		List<DummyRep> oldPop = new ArrayList<DummyRep>();
		
		DummyRep r1 = new DummyRep(1);
		DummyRep r2 = new DummyRep(2);
		DummyRep r3 = new DummyRep(3);
		DummyRep r4 = new DummyRep(4);
		DummyRep r5 = new DummyRep(5);
		
		oldPop.add(r4);
		oldPop.add(r3);
		oldPop.add(r1);
		oldPop.add(r5);
		oldPop.add(r2);
		
		List<DummyRep> children = new ArrayList<DummyRep>();
		
		DummyRep c1 = new DummyRep(1);
		
		children.add(c1);
		
		List<DummyRep> resFBI = fbi.reinsert(clone(oldPop), clone(children));
		List<DummyRep> resUI = ui.reinsert(clone(oldPop), clone(children));
		
		assertEquals(popSize, resFBI.size());
		assertEquals(popSize, resUI.size());
		
		assertTrue(resFBI.contains(c1));
		assertTrue(resFBI.contains(r4));
		assertTrue(resFBI.contains(r5));

		assertTrue(resUI.contains(c1));
		
		//ui was denk ik ook wel oke
	}
	
	public static <T> List<T> clone(List<T> list){
		List<T> result = new ArrayList<T>();
		
		for(T item:list){
			result.add(item);
		}
		
		return result;
	}
	
}

class DummyRep extends Chromosome{
	
	public DummyRep(double fitness) {
		super(fitness);
	}
	
	@Override
	public void setRandomImpl(Random rand) {
		
	}

	@Override
	public double calcPathLength() {
		throw new RuntimeException("mag niet worden aangeroepen");
	}

	@Override
	public Path toPath() {
		return null;
	}

	@Override
	public void fromPath(Path path) {
	}

	@Override
	public Chromosome clone() {
		return null;
	}
	
	@Override
	public String toString() {
		return super.toString()+" : " + getFitness();
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
