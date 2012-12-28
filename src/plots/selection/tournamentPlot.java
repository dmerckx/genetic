package plots.selection;

import java.util.ArrayList;
import java.util.List;

import main.GA;
import main.History;
import main.LoopDetection;
import main.Problem;
import main.crossover.AlternatingEdge;
import main.crossover.CrossOver;
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
import util.ProblemGenerator;
import factory.AdjacencyFactory;

public class tournamentPlot {

	private static Params params = getParams();
	
	private static Tournament<Adjacency> tournament = new Tournament<Adjacency>(params, 1.0,0.3);
	
	private static final AdjacencyFactory factory = new AdjacencyFactory();
	private static final ReInsertor<Adjacency> insertor = new FBI<Adjacency>(params);
	private static final Mutator<Adjacency> mutator = new SimpleInversionMutator<Adjacency>(params);
	private static final Ranker<Adjacency> ranker = new LineairRanker<Adjacency>();
	private static final LoopDetection<Adjacency> loopDetection = new LoopDetection<Adjacency>();
	
	private static final String problemFilePath = "../genetic/datafiles/rondrit070.tsp";
	private static final String outputFilePath = "../genetic/result/result-adj-tournament-params.txt";
	
	private static final Problem problem = ProblemGenerator.generate(problemFilePath);
	private static final CrossOver<Adjacency> crossover = new AlternatingEdge(params, problem);
	
	public static void main(String[] args) {
		makePlot();
	}
	
	private static void makePlot() {
		
		int nbTimes = 5;
		
		double[] kValues = initKValues();
		
		double[] pValues = initPValues();
		
		List<String> result = new ArrayList<String>();
		
		long before = System.currentTimeMillis();
		
		int runNb = 1;
		
		for (int i = 0; i < pValues.length; i++) {
			for (int j = 0; j < kValues.length; j++) {
				History history = new History();
				createGA1(problem, new Tournament<Adjacency>(params, pValues[i], kValues[j])).run(problem, history, nbTimes);
				System.out.println("completed run number " + runNb +" out of " + (pValues.length*kValues.length));
				result.add(pValues[i] + " " + kValues[j] + " " + history.bestList.get(history.bestList.size()-1) + "\r\n");
				runNb++;
			}
		}
		
		PlotWriter.writeList(outputFilePath, result);
		
		System.out.println("exec time: " + (System.currentTimeMillis()-before));
		
	}

	private static double[] initPValues() {
		double[] pValues = new double[7];
		pValues[0] = 0.01;
		pValues[1] = 0.05;
		pValues[2] = 0.1;
		pValues[3] = 0.2;
		pValues[4] = 0.5;
		pValues[5] = 0.75;
		pValues[6] = 1;
		return pValues;
	}

	private static double[] initKValues() {
		double[] kValues = new double[12];
		kValues[0] = 0.01;
		kValues[1] = 0.02;
		kValues[2] = 0.03;
		kValues[3] = 0.04;
		kValues[4] = 0.05;
		kValues[5] = 0.10;
		kValues[6] = 0.15;
		kValues[7] = 0.20;
		kValues[8] = 0.25;
		kValues[9] = 0.50;
		kValues[10] = 0.75;
		kValues[11] = 1.0;
		return kValues;
	}
	
	private static Params getParams() {
		Params params = new Params();
		params.popSize = 100;
		params.maxGenerations = 1500;
		params.elitists = 0.2d;
		params.crossover = 0.25d;
		params.mutation = 0.35d;
		params.stop = 0.95d;
		params.detectLoops = false;
		return params;
	}
	
	public static GA<Adjacency> createGA1(Problem problem, Tournament<Adjacency> tournament){
		return new GA<Adjacency>(params, factory, tournament, crossover, insertor, mutator, ranker, loopDetection);
	}
	
}
