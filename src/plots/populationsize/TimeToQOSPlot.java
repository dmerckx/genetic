package plots.populationsize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import factory.AdjacencyFactory;
import factory.PathFactory;

import params.Params;
import plots.PlotWriter;
import representations.Adjacency;
import representations.path.Path;

import main.History;
import main.Problem;
import main.crossover.AlternatingEdge;
import main.crossover.EdgeRecombination;
import main.insertion.FBI;
import main.mutation.SimpleInversionMutator;
import main.rankers.LineairRanker;
import main.rankers.Ranker;
import main.selectors.SUS;
import util.ProblemGenerator;

public class TimeToQOSPlot {

	private static final String problemFilePath = "../genetic/datafiles/xqf131.tsp";
	private static final Problem problem = ProblemGenerator.generate(problemFilePath);
	
	public static void main(String[] args) {
		makePlot();
	}

	private static void makePlot() {
		
		double before = System.currentTimeMillis();
		
		List<Double> qos = new ArrayList<Double>();
		qos.add(620.4);
		
		int randomSeed = (new Random()).nextInt();
		int nbTimes = 5;
		
		System.out.println("random seed: " + randomSeed);
		
		List<String> adjResult = new ArrayList<String>();
		List<String> pathResult = new ArrayList<String>();
		
		for (int popSize = 25; popSize < 300; popSize+=50) {
			for (int i = 0; i < qos.size(); i++) {
			double beginIt = System.currentTimeMillis();
			System.out.println("current required QOS: " + qos.get(i));
			System.out.println("adjacency: ");
			History history1 = new History("");
			List<Integer> generations = createGAAdj(problem, getParamsAdj(randomSeed, popSize), qos.get(i)).runQOS(problem, history1, nbTimes);
			double timeIt = System.currentTimeMillis() - beginIt;
			timeIt = timeIt/60000;
			System.out.println("time for adj in minutes: " + timeIt);
			int nbGenerations = 0;
			for (int j = 0; j < generations.size(); j++) {
				nbGenerations += generations.get(j);
			}
			double result = (nbGenerations/nbTimes);
			adjResult.add(qos.get(i) + " " + timeIt + " " +  result + " \r\n");
			
			beginIt = System.currentTimeMillis();
			System.out.println("path: ");
			History history2 = new History("");
			generations = createGAPath(problem, getParamsPath(randomSeed, popSize), qos.get(i)).runQOS(problem, history2, nbTimes);
			timeIt = System.currentTimeMillis() - beginIt;
			timeIt = timeIt/60000;
			System.out.println("time for path in minutes: " + timeIt);
			nbGenerations = 0;
			for (int j = 0; j < generations.size(); j++) {
				nbGenerations += generations.get(j);
			}
			result = (nbGenerations/nbTimes);
			pathResult.add(qos.get(i) + " " + timeIt + " " + result + " \r\n");
		}
		}
		
		double after = System.currentTimeMillis()-before;
		after = after/60000;
		System.out.println("total execution time: " + after);
		
		PlotWriter.writeList("../genetic/result/result-adj-timeqos.txt", adjResult);
		PlotWriter.writeList("../genetic/result/result-path-timeqos.txt", pathResult);
	}
	
	private static Params getParams(int seed, int pop) {
		Params params = new Params();
		params.rand =  new Random(seed);
		params.popSize = pop;
		params.maxGenerations = Integer.MAX_VALUE;
		params.stop = 0.95d;
		params.detectLoops = false;
		params.stop = 0.99;
		return params;
	}
	
	private static Params getParamsPath(int seed, int pop) {
		Params params = getParams(seed,pop);
		params.elitists = 0.05d;
		params.crossover = 0.90d;
		params.mutation = 0.20d;
		return params;
	}
	
	private static Params getParamsAdj(int seed, int pop) {
		Params params = getParams(seed,pop);
		params.elitists = 0.2d;
		params.crossover = 0.25d;
		params.mutation = 0.35d;
		return params;
	}
	
	public static QOSGA<Adjacency> createGAAdj(Problem problem, Params params, double qos){
		return new QOSGA<Adjacency>(params, new AdjacencyFactory(), new SUS<Adjacency>(params), new AlternatingEdge(params, problem), new FBI<Adjacency>(params), new SimpleInversionMutator<Adjacency>(params), new LineairRanker<Adjacency>(), null, qos);
	}
	
	public static QOSGA<Path> createGAPath(Problem problem, Params params, double qos){
		return new QOSGA<Path>(params, new PathFactory(), new SUS<Path>(params), new EdgeRecombination(problem,params), new FBI<Path>(params), new SimpleInversionMutator<Path>(params), new LineairRanker<Path>(), null, qos);
	}
	
}
