package plots.parentSelection;

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
import main.rankers.Ranker;
import main.selectors.SUS;
import params.Params;
import representations.Adjacency;
import representations.path.Path;
import util.ProblemGenerator;
import factory.AdjacencyFactory;
import factory.PathFactory;

public class CorrelativeTournament {

	private static final Params params = new Params();
	
	private static final AdjacencyFactory factory = new AdjacencyFactory();
	private static final Ranker<Adjacency> ranker = new LineairRanker<Adjacency>();
	private static final LoopDetection<Adjacency> loopDetection = new LoopDetection<Adjacency>();
	
	private static final String problemFilePath = "../genetic/datafiles/rondrit070.tsp";
	private static final String outputRWSFilePath = "../genetic/result/result-adj-rws.txt";
	private static final String outputSUSFilePath = "../genetic/result/result-adj-sus.txt";
	private static final String outputTournamentFilePath = "../genetic/result/result-adj-tournament.txt";
	
	private static final Problem problem = ProblemGenerator.generate(problemFilePath);
	
	public static void main(String[] args) {
		makePlot();
	}
	
	public static void makePlot() {
		
		int nbTimes = 20;
		
		int randomSeed = (new Random(8956)).nextInt();
		
		History history1 = new History(outputRWSFilePath);
		
		createGAPath(problem, getParams(false, randomSeed)).run(problem, history1, nbTimes);
		
		System.out.println("without correlative: ");
		history1.printResults();
		history1.writeFile();
		
		History history2 = new History(outputSUSFilePath);
		createGAPath(problem, getParams(true, randomSeed)).run(problem, history2, nbTimes);
		
		System.out.println("with correlative: ");
		history2.printResults();
		history2.writeFile();
		
	}

	private static Params getParams(boolean setCorrelativeTournamentOn, int seed) {
		params.popSize = 150;
		params.maxGenerations = 1000;
		params.elitists = 0.05d;
		params.crossover = 0.95d;
		params.mutation = 0.60d;
		params.stop = 0.95d;
		params.detectLoops = false;
		params.rand = new Random(seed);
		params.correlativeTournament = setCorrelativeTournamentOn;
		params.similarSubsetSize = 1.0;
		return params;
	}
	
	public static GA<Adjacency> createGAAdj(Problem problem, Params params){
		return new GA<Adjacency>(params, factory, new SUS<Adjacency>(params), new AlternatingEdge(params, problem), new FBI<Adjacency>(params),  new SimpleInversionMutator<Adjacency>(params), ranker, loopDetection);
	}
	
	public static GA<Path> createGAPath(Problem problem, Params params){
		return new GA<Path>(params, new PathFactory(), new SUS<Path>(params), new EdgeRecombination(problem, params), new FBI<Path>(params),  new SimpleInversionMutator<Path>(params), new LineairRanker<Path>(), new LoopDetection<Path>());
	}
}
