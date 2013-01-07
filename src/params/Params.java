package params;

import java.util.Random;


public class Params {
	public double simulationTime = 0;
	public boolean correlativeTournament = false;
	public int popSize;
	public int maxGenerations;
	public double crossover;
	public double mutation;
	public double stop;
	public double elitists;
	public double mach = 1e-15d;
	
	public Random rand = null;
	
	public double migration = 0.0d;
	public int migrationFreq = 999999;
	
	public boolean detectLoops;
	public double similarSubsetSize = 0.5;
}
