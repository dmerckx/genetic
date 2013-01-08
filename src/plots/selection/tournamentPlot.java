package plots.selection;

import java.util.ArrayList;
import java.util.List;
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
import main.selectors.Tournament;
import params.Params;
import plots.PlotWriter;
import representations.Adjacency;
import representations.path.Path;
import util.ProblemGenerator;
import factory.AdjacencyFactory;
import factory.PathFactory;

public class tournamentPlot {
	
	private static final AdjacencyFactory adjFactory = new AdjacencyFactory();
	private static final PathFactory pathFactory = new PathFactory();
	
	private static final ReInsertor<Adjacency> adjInsertor = new FBI<Adjacency>(getAdjParams());
	private static final ReInsertor<Path> pathInsertor = new FBI<Path>(getPathParams());
	
	private static final Mutator<Adjacency> adjMutator = new SimpleInversionMutator<Adjacency>(getAdjParams());
	private static final Mutator<Path> pathMutator = new SimpleInversionMutator<Path>(getPathParams());
	
	private static final Ranker<Adjacency> adjRanker = new LineairRanker<Adjacency>();
	private static final Ranker<Path> pathRanker = new LineairRanker<Path>();
	
	private static final LoopDetection<Adjacency> adjLoopDetection = new LoopDetection<Adjacency>();
	private static final LoopDetection<Path> pathLoopDetection = new LoopDetection<Path>();
	
	private static final String problemFilePath = "../genetic/datafiles/xqf131.tsp";
	private static final String outputAdjFilePath = "../genetic/result/result-adj-tournament-params.txt";
	private static final String outputPathFilePath = "../genetic/result/result-path-tournament-params.txt";
	
	private static final Problem problem = ProblemGenerator.generate(problemFilePath);
	private static final CrossOver<Adjacency> adjCrossover = new AlternatingEdge(getAdjParams(), problem);
	private static final CrossOver<Path> pathCrossover = new EdgeRecombination(problem, getPathParams());
	
	public static void main(String[] args) {
		makePlot();
	}
	
	private static void makePlot() {
		
		int nbTimes = 5;
		
		double[] kValues = initKValues();
		double[] pValues = initPValues();
		
		List<String> resultAdj = new ArrayList<String>();
		List<String> resultPath = new ArrayList<String>();
		
		long before = System.currentTimeMillis();
		
		int iterationNb = 1;
		
		for (int i = 0; i < pValues.length; i++) {
			for (int j = 0; j < kValues.length; j++) {
				History adjHistory = new History();
				createGAAdj(problem, new Tournament<Adjacency>(getAdjParams(), pValues[i], kValues[j])).run(problem, adjHistory, nbTimes);
				History pathHistory = new History();
				createGAPath(problem, new Tournament<Path>(getPathParams(), pValues[i], kValues[j])).run(problem, pathHistory, nbTimes);
				System.out.println("completed run number: " + iterationNb +" out of " + (pValues.length*kValues.length));
				resultAdj.add(pValues[i] + " " + kValues[j] + " " + adjHistory.bestList.get(adjHistory.bestList.size()-1) + "\r\n");
				resultPath.add(pValues[i] + " " + kValues[j] + " " + pathHistory.bestList.get(pathHistory.bestList.size()-1) + "\r\n");
				iterationNb++;
			}
		}
		
		PlotWriter.writeList(outputAdjFilePath, resultAdj);
		PlotWriter.writeList(outputPathFilePath, resultPath);
		
		System.out.println("exec time: " + (System.currentTimeMillis()-before));
	}

	private static double[] initPValues() {
		double[] pValues = new double[7];
		pValues[0] = 0.01;
		pValues[1] = 0.05;
		pValues[2] = 0.1;
		pValues[3] = 0.15;
		pValues[4] = 0.20;
		pValues[5] = 0.25;
		pValues[6] = 1;
		return pValues;
	}

	private static double[] initKValues() {
		double[] kValues = new double[7];
		kValues[0] = 0.01;
		kValues[1] = 0.05;
		kValues[2] = 0.10;
		kValues[3] = 0.15;
		kValues[4] = 0.20;
		kValues[5] = 0.25;
		kValues[6] = 0.30;
		return kValues;
	}
	
	private static Params getPathParams() {
		Params params = getParams();
		params.elitists = 0.1d;
		params.crossover = 0.9d;
		params.mutation = 0.2d;
		return params;
	}
	
	private static Params getAdjParams() {
		Params params = getParams();
		params.elitists = 0.1d;
		params.crossover = 0.25d;
		params.mutation = 0.35d;
		return params;
	}
	
	private static Params getParams() {
		Params params = new Params();
		params.rand = new Random(5);
		params.popSize = 100;
		params.maxGenerations = 300;
		params.stop = 0.95d;
		params.detectLoops = false;
		return params;
	}
	
	public static GA<Adjacency> createGAAdj(Problem problem, Tournament<Adjacency> tournament){
		return new GA<Adjacency>(getAdjParams(), adjFactory, tournament, adjCrossover, adjInsertor, adjMutator, adjRanker, adjLoopDetection);
	}
	
	public static GA<Path> createGAPath(Problem problem, Tournament<Path> tournament){
		return new GA<Path>(getPathParams(), pathFactory, tournament, pathCrossover, pathInsertor, pathMutator, pathRanker, pathLoopDetection);
	}
	
}
