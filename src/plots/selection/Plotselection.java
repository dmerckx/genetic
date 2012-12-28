package plots.selection;

import java.util.Random;

import main.GA;
import main.History;
import main.LoopDetection;
import main.Problem;
import main.crossover.AlternatingEdge;
import main.crossover.CrossOver;
import main.crossover.EdgeRecombination;
import main.insertion.FBI;
import main.insertion.ReInsertor;
import main.mutation.Mutator;
import main.mutation.SimpleInversionMutator;
import main.rankers.LineairRanker;
import main.rankers.Ranker;
import main.selectors.RWS;
import main.selectors.SUS;
import main.selectors.Selector;
import main.selectors.Tournament;
import params.Params;
import representations.Adjacency;
import representations.path.Path;
import util.ProblemGenerator;
import factory.AdjacencyFactory;
import factory.PathFactory;

public class Plotselection {

	private static Params params = getParamsAdj(549);
	private static Params paramsPath = getParamsPath(549);
	
	private static final String problemFilePath = "../genetic/datafiles/rondrit070.tsp";
	private static final Problem problem = ProblemGenerator.generate(problemFilePath);
	
	private static final RWS<Adjacency> rws = new RWS<Adjacency>(params);
	private static final RWS<Path> rwsPath = new RWS<Path>(paramsPath);
	private static final SUS<Adjacency> sus = new SUS<Adjacency>(params);
	private static final SUS<Path> susPath = new SUS<Path>(paramsPath);
	private static final Tournament<Adjacency> tournament = new Tournament<Adjacency>(params, 1.0,1.0);
	private static final Tournament<Path> tournamentPath = new Tournament<Path>(paramsPath, 1.0,1.0);
	
	private static final AdjacencyFactory factory = new AdjacencyFactory();
	private static final PathFactory factoryPath = new PathFactory();
	
	private static final ReInsertor<Adjacency> insertor = new FBI<Adjacency>(params);
	private static final ReInsertor<Path> insertorPath = new FBI<Path>(paramsPath);
	
	private static final Mutator<Adjacency> mutator = new SimpleInversionMutator<Adjacency>(params);
	private static final Mutator<Path> mutatorPath = new SimpleInversionMutator<Path>(paramsPath);
	
	private static final CrossOver<Adjacency> crossover = new AlternatingEdge(params, problem);
	private static final CrossOver<Path> crossoverPath = new EdgeRecombination(problem,paramsPath);
	
	private static final Ranker<Adjacency> ranker = new LineairRanker<Adjacency>();
	private static final Ranker<Path> rankerPath = new LineairRanker<Path>();
	
	private static final LoopDetection<Adjacency> loopDetection = new LoopDetection<Adjacency>();
	private static final LoopDetection<Path> loopDetectionPath = new LoopDetection<Path>();
	
	private static final String outputAdjRWSFilePath = "../genetic/result/result-adj-rws.txt";
	private static final String outputAdjSUSFilePath = "../genetic/result/result-adj-sus.txt";
	private static final String outputAdjTournamentFilePath = "../genetic/result/result-adj-tournament.txt";
	
	private static final String outputPathRWSFilePath = "../genetic/result/result-path-rws.txt";
	private static final String outputPathSUSFilePath = "../genetic/result/result-path-sus.txt";
	private static final String outputPathTournamentFilePath = "../genetic/result/result-path-tournament.txt";
	
	public Plotselection() {
	}
	
	public static void main(String[] args) {
		makePlot();
	}
	
	public static void makePlot() {
		
		int nbTimes = 1;
		
		History history1 = new History(outputAdjRWSFilePath);
		createGA(problem, rws).run(problem, history1, nbTimes);
		history1.printShort();
		history1.writeFile();
		
		History history2 = new History(outputAdjSUSFilePath);
		createGA(problem, sus).run(problem, history2, nbTimes);
		history2.printShort();
		history2.writeFile();
		
		History history3 = new History(outputAdjTournamentFilePath);
		createGA(problem, tournament).run(problem, history3, nbTimes);
		history3.printShort();
		history3.writeFile();
		
		History history4 = new History(outputPathRWSFilePath);
		createGAPath(problem, rwsPath).run(problem, history4, nbTimes);
		history4.printShort();
		history4.writeFile();
		
		History history5 = new History(outputPathSUSFilePath);
		createGAPath(problem, susPath).run(problem, history5, nbTimes);
		history5.printShort();
		history5.writeFile();
		
		History history6 = new History(outputPathTournamentFilePath);
		createGAPath(problem, tournamentPath).run(problem, history6, nbTimes);
		history6.printShort();
		history6.writeFile();
		
	}

	private static Params getParams(int seed) {
		Params params = new Params();
		params.rand =  new Random(seed);
		params.popSize = 100;
		params.maxGenerations = 100;
		params.stop = 0.95d;
		params.detectLoops = false;
		return params;
	}
	
	private static Params getParamsPath(int seed) {
		Params params = getParams(seed);
		params.elitists = 0.05d;
		params.crossover = 0.95d;
		params.mutation = 0.05d;
		return params;
	}
	
	private static Params getParamsAdj(int seed) {
		Params params = getParams(seed);
		params.elitists = 0.2d;
		params.crossover = 0.25d;
		params.mutation = 0.35d;
		return params;
	}
	
	public static GA<Adjacency> createGA(Problem problem, Selector<Adjacency> selector){
		return new GA<Adjacency>(params, factory, selector, crossover, insertor, mutator, ranker, loopDetection);
	}
	
	public static GA<Path> createGAPath(Problem problem, Selector<Path> selector){
		return new GA<Path>(paramsPath, factoryPath, selector, crossoverPath, insertorPath, mutatorPath, rankerPath, loopDetectionPath);
	}
}
