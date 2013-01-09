package plots.ranking;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.GA;
import main.History;
import main.Problem;
import main.crossover.AlternatingEdge;
import main.crossover.EdgeRecombination;
import main.insertion.FBI;
import main.mutation.SimpleInversionMutator;
import main.rankers.LineairRanker;
import main.rankers.Ranker;
import main.selectors.SUS;
import main.selectors.Tournament;
import params.Params;
import representations.Adjacency;
import representations.path.Path;
import util.ProblemGenerator;
import factory.AdjacencyFactory;
import factory.PathFactory;

public class PlotRanking {

	private static final String problemFilePath = "../genetic/datafiles/xqf131.tsp";
	private static final Problem problem = ProblemGenerator.generate(problemFilePath);

	private static final String outputAdjRWSFilePath = "../genetic/result/result-adj-rws.txt";
	private static final String outputAdjSUSFilePath = "../genetic/result/result-adj-sus.txt";
	private static final String outputAdjTournamentFilePath = "../genetic/result/result-adj-tournament.txt";
	
	private static final String outputPathRWSFilePath = "../genetic/result/result-path-rws.txt";
	private static final String outputPathSUSFilePath = "../genetic/result/result-path-sus.txt";
	private static final String outputPathTournamentFilePath = "../genetic/result/result-path-tournament.txt";
	
	public static void main(String[] args) {
		makePlot();
	}
	
	public static void makePlot() {
		
		int nbTimes = 10;
		
		long before = System.currentTimeMillis();
		
		List<Double> selectivePressures = new ArrayList<Double>();
		
		int randomSeed = (new Random()).nextInt();
		
		
		History history1 = new History(outputAdjRWSFilePath);
		createGA(problem, new LineairRanker<Adjacency>(), getParamsAdj(randomSeed)).run(problem, history1, nbTimes);
		history1.printShort();
		history1.writeFile();
		
		History history2 = new History(outputAdjSUSFilePath);
		createGAPath(problem, new LineairRanker<Path>(), getParamsPath(randomSeed)).run(problem, history2, nbTimes);
		history2.printShort();
		history2.writeFile();
		
	}

	private static Params getParams(int seed) {
		Params params = new Params();
		params.rand =  new Random(seed);
		params.popSize = 150;
		params.maxGenerations = 400;
		params.stop = 0.95d;
		params.detectLoops = false;
		return params;
	}
	
	private static Params getParamsPath(int seed) {
		Params params = getParams(seed);
		params.elitists = 0.05d;
		params.crossover = 0.90d;
		params.mutation = 0.20d;
		return params;
	}
	
	private static Params getParamsAdj(int seed) {
		Params params = getParams(seed);
		params.elitists = 0.2d;
		params.crossover = 0.25d;
		params.mutation = 0.35d;
		return params;
	}
	
	public static GA<Adjacency> createGA(Problem problem, Ranker<Adjacency> ranker, Params params){
		return new GA<Adjacency>(params, new AdjacencyFactory(), new SUS<Adjacency>(params), new AlternatingEdge(params, problem), new FBI<Adjacency>(params), new SimpleInversionMutator<Adjacency>(params), ranker, null);
	}
	
	public static GA<Path> createGAPath(Problem problem, Ranker<Path> ranker, Params params){
		return new GA<Path>(params, new PathFactory(), new SUS<Path>(params), new EdgeRecombination(problem,params), new FBI<Path>(params), new SimpleInversionMutator<Path>(params), ranker, null);
	}
}
