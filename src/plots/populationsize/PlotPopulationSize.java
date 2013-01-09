package plots.populationsize;

import java.util.ArrayList;
import java.util.Random;

import main.GA;
import main.History;
import main.LoopDetection;
import main.Problem;
import main.crossover.AlternatingEdge;
import main.crossover.EdgeRecombination;
import main.insertion.FBI;
import main.mutation.SimpleInversionMutator;
import main.rankers.LineairRanker;
import main.selectors.SUS;
import params.Params;
import plots.PlotWriter;
import representations.Adjacency;
import representations.path.Path;
import util.ProblemGenerator;
import factory.AdjacencyFactory;
import factory.PathFactory;

public class PlotPopulationSize {

	private static final String problemFilePath = "../genetic/datafiles/xqf131.tsp"; //optimal length 564
	
	private static final Problem problem = ProblemGenerator.generate(problemFilePath);
	
	public static void main(String[] args) {
		makePlot();
	}
	
	public static void makePlot() {
		
		long before = System.currentTimeMillis();
		System.out.println("before: " + before);
		
		int nbTimes = 10;
		
		ArrayList<String> bestAdj = new ArrayList<String>();
		ArrayList<String> bestPath = new ArrayList<String>();
		
		int randomSeed = (new Random()).nextInt();
//		int randomSeed = -2115352863;
		System.out.println("seed: " + randomSeed);
		for (int popSize = 20; popSize < 600; popSize = popSize + 20) {
			long time = System.currentTimeMillis();
			History history1 = new History("");
			createGAAdj(problem, getParamsAdj(randomSeed, popSize)).run(problem, history1, nbTimes);
			System.out.println("Adjacency with population: " + popSize);
			history1.printShort();
			bestAdj.add(popSize + " " + history1.bestList.get(history1.bestList.size()-1)+ "\r\n");
			
			History history2 = new History("");
			createGAPath(problem, getParamsPath(randomSeed, popSize)).run(problem, history2, nbTimes);
			System.out.println("Path with population: " + popSize);
			history2.printShort();
			bestPath.add(popSize + " " + history2.bestList.get(history2.bestList.size()-1)+ "\r\n");
			long diff = ((System.currentTimeMillis()-time)/1000);
			System.out.println("executed iteration in: " + diff);
		}
		
		PlotWriter.writeList("../genetic/result/result-adj-pop.txt", bestAdj);
		PlotWriter.writeList("../genetic/result/result-path-pop.txt", bestPath);
		System.out.println("after: " + System.currentTimeMillis());
		double time = System.currentTimeMillis()-before;
		double hours = time/(3600000);
		double mins = time/(60000);
		System.out.println("execution time in hours: " + hours);
		System.out.println("execution time in minutes: " + mins);
	}

	private static Params getParamsAdj(int seed, int popSize) {
		Params params = getParams(seed, popSize);
		params.elitists = 0.10d;
		params.crossover = 0.20d;
		params.mutation = 0.35d;
		return params;
	}
	
	private static Params getParamsPath(int seed, int popSize) {
		Params params = getParams(seed, popSize);
		params.elitists = 0.10d;
		params.crossover = 0.90d;
		params.mutation = 0.30d;
		return params;
	}
	
	private static Params getParams(int seed, int popSize) {
		Params params = new Params();
		params.rand = new Random(seed);
		params.popSize = popSize;
		params.maxGenerations = 400;
		params.detectLoops = false;
		params.correlativeTournament = false;
		params.similarSubsetSize = 0.0;
		params.stop = 0.95d;
		return params;
	}
	
	public static GA<Adjacency> createGAAdj(Problem problem, Params params){
		return new GA<Adjacency>(params, new AdjacencyFactory(), new SUS<Adjacency>(params), new AlternatingEdge(params, problem), new FBI<Adjacency>(params),  new SimpleInversionMutator<Adjacency>(params), new LineairRanker<Adjacency>(), new LoopDetection<Adjacency>());
	}
	
	public static GA<Path> createGAPath(Problem problem, Params params){
		return new GA<Path>(params, new PathFactory(), new SUS<Path>(params), new EdgeRecombination(problem, params), new FBI<Path>(params),  new SimpleInversionMutator<Path>(params), new LineairRanker<Path>(), new LoopDetection<Path>());
	}
}
