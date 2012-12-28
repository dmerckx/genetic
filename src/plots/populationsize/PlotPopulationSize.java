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

	private static final Params params = new Params();
	
	private static final String problemFilePath = "../genetic/datafiles/xqf131.tsp"; //optimal length 564
	
	private static final Problem problem = ProblemGenerator.generate(problemFilePath);
	
	public static void main(String[] args) {
		makePlot();
	}
	
	public static void makePlot() {
		
		int nbTimes = 1;
		
		ArrayList<String> bestAdj = new ArrayList<String>();
		ArrayList<String> bestPath = new ArrayList<String>();
		
		int randomSeed = (new Random()).nextInt();
		for (int popSize = 25; popSize < 200; popSize = popSize + 50) {
			History history1 = new History("");
			createGAAdj(problem, getParamsAdj(randomSeed, popSize)).run(problem, history1, nbTimes);
			System.out.println("Adjacency ");
			history1.printShort();
			bestAdj.add(popSize + " " + history1.bestList.get(history1.bestList.size()-1)+ "\r\n");
			
			History history2 = new History("");
			createGAPath(problem, getParamsPath(randomSeed, popSize)).run(problem, history2, nbTimes);
			System.out.println("Path: ");
			history2.printShort();
			bestPath.add(popSize + " " + history2.bestList.get(history2.bestList.size()-1)+ "\r\n");
		}
		
		PlotWriter.writeList("../genetic/result/result-adj-pop.txt", bestAdj);
		PlotWriter.writeList("../genetic/result/result-path-pop.txt", bestPath);
	}

	private static Params getParamsAdj(int seed, int popSize) {
		params.popSize = popSize;
		params.maxGenerations = 1000;
		params.elitists = 0.05d;
		params.crossover = 0.20d;
		params.mutation = 0.35d;
		params.stop = 0.95d;
		params.detectLoops = false;
		params.rand = new Random(seed);
		params.correlativeTournament = false;
		params.similarSubsetSize = 1.0;
		return params;
	}
	
	private static Params getParamsPath(int seed, int popSize) {
		params.popSize = popSize;
		params.maxGenerations = 1000;
		params.elitists = 0.05d;
		params.crossover = 0.95d;
		params.mutation = 0.05d;
		params.stop = 0.95d;
		params.detectLoops = false;
		params.rand = new Random(seed);
		params.correlativeTournament = false;
		params.similarSubsetSize = 1.0;
		return params;
	}
	
	public static GA<Adjacency> createGAAdj(Problem problem, Params params){
		return new GA<Adjacency>(params, new AdjacencyFactory(), new SUS<Adjacency>(params), new AlternatingEdge(params, problem), new FBI<Adjacency>(params),  new SimpleInversionMutator<Adjacency>(params), new LineairRanker<Adjacency>(), new LoopDetection<Adjacency>());
	}
	
	public static GA<Path> createGAPath(Problem problem, Params params){
		return new GA<Path>(params, new PathFactory(), new SUS<Path>(params), new EdgeRecombination(problem, params), new FBI<Path>(params),  new SimpleInversionMutator<Path>(params), new LineairRanker<Path>(), new LoopDetection<Path>());
	}
}
