package test.selectors;


import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import main.Problem;
import main.RankedChrom;
import main.selectors.RWS;
import main.selectors.Tournament;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import params.Params;
import params.TestParams;
import representations.Chromosome;
import representations.path.Path;
import util.ProblemGenerator;

public class TournamentTest {
	
	private static Problem problem;
	private static String filePath = "../genetic/datafiles/rondrit016.tsp";
	
	private Params params;
	private Tournament tour;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		problem = ProblemGenerator.generate(filePath);		
	}
	
	@Before
	public void setUp() throws Exception {
		params = new TestParams();
		tour = new Tournament<DummyRep>(params);
	}
	
	@Test
	public void testSelect(){
		Tournament tour = new Tournament<DummyRep>(params, 1, 2);
		DummyRChrom r1 = new DummyRChrom(1);
		DummyRChrom r2 = new DummyRChrom(2);
		DummyRChrom r3 = new DummyRChrom(3);
		DummyRChrom r4 = new DummyRChrom(4);
		
		List<RankedChrom<DummyRep>> result = new ArrayList<RankedChrom<DummyRep>>();
		
		result.add(r2);
		result.add(r3);
		result.add(r4);
		result.add(r1);
		
		params.elitists = 0;
		int counter = 0;
		for (int i = 0; i < 100; i++) {
			if(tour.selectOneChrom(result).fitness - r1.fitness <= 1e-15)
				counter++;
		}
		assertTrue(counter > 35);
		
	}
	
	
class DummyRChrom extends RankedChrom<DummyRep>{

	public DummyRChrom(double fitness) {
		super(fitness, new DummyRep());
	}
	
	public String toString() {
		return "fitness: " + fitness;
	}
}

class DummyRep extends Chromosome{
	
	public DummyRep() {
		super();
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
		return new DummyRep();
	}

	@Override
	public double getPathLength(int from, int to) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void swap(int city1, int city2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Integer> getReversePath() {
		// TODO Auto-generated method stub
		return null;
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
}