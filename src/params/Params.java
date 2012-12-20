package params;
import java.util.Random;


public class Params {
	public int popSize;
	public int maxGenerations;
	public double crossover;
	public double mutation;
	public double stop;
	public double elitists;
	public double mach = 1e-15d;
	public Random rand = new Random();
	
	public double renegades = 0.0d;
	public int renegadeFreq = 999999;
	
	public boolean detectLoops;
}
