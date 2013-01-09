package plots.populationsize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class PlotPopulationSizeLimited {

	private static final String problemFilePath = "../genetic/datafiles/xqf131.tsp"; //optimal length 564

	private static final Problem problem = ProblemGenerator.generate(problemFilePath);

	public static void main(String[] args) {
		makePlot();
	}

	public static void makePlot() {

		long before = System.currentTimeMillis();
		System.out.println("before: " + before);

		int nbTimes = 5;

		List<Double> simTime = Arrays.asList(0.01,0.05d,0.1d,0.5d,1d,2d);
		
		Map<Double,ArrayList<String>> bestAdj = new HashMap<Double,ArrayList<String>>();
		Map<Double,ArrayList<String>> bestPath = new HashMap<Double,ArrayList<String>>();

		int randomSeed = (new Random()).nextInt();
		System.out.println("seed: " + randomSeed);
		for(Double simulationTime: simTime) {
			ArrayList<String> listAdj = new ArrayList<String>();
			bestAdj.put(simulationTime, listAdj);
			ArrayList<String> listPath = new ArrayList<String>();
			bestPath.put(simulationTime, listPath);
			System.out.println("currentSimulationTime: " + simulationTime);
			for (int popSize = 25; popSize <= 500; popSize = popSize + 25) {
				long time = System.currentTimeMillis();
				History history1 = new History("");
				createGAAdj(problem, getParamsAdj(randomSeed, popSize, simulationTime)).run(problem, history1, nbTimes);
				System.out.println("Adjacency with population: " + popSize);
				history1.printShort();
				listAdj.add(popSize + " " + getLastNonZeroElement(history1.bestList)+ "\r\n");
				double diff = (System.currentTimeMillis()-time);
				diff = diff/1000;
				System.out.println("executed ADJ in: " + diff);
				
				time = System.currentTimeMillis();
				History history2 = new History("");
				createGAPath(problem, getParamsPath(randomSeed, popSize, simulationTime)).run(problem, history2, nbTimes);
				System.out.println("Path with population: " + popSize);
				history2.printShort();
				listPath.add(popSize + " " + getLastNonZeroElement(history2.bestList) + "\r\n");
				System.out.println(popSize + " " + getLastNonZeroElement(history2.bestList) + "\r\n");
				diff = (System.currentTimeMillis()-time);
				diff = diff/1000;
				System.out.println("executed PATH in: " + diff);
			}
		}

		int counter = 0;
		for (Double key : bestAdj.keySet()) {
			System.out.println("counter: " + counter + " key: " + key );
			PlotWriter.writeList("../genetic/result/result-adj-pop-" + counter + ".txt", bestAdj.get(key));
			counter++;
			
		}
		counter = 0;
		for (Double key : bestPath.keySet()) {
			System.out.println("counter: " + counter + " key: " + key );
			PlotWriter.writeList("../genetic/result/result-path-pop-" + counter + ".txt", bestPath.get(key));
			counter++;
		}
		
		System.out.println("after: " + System.currentTimeMillis());
		double time = System.currentTimeMillis()-before;
		double hours = time/(3600000);
		double mins = time/(60000);
		System.out.println("execution time in hours: " + hours);
		System.out.println("execution time in minutes: " + mins);
	}

	private static double getLastNonZeroElement(List<Double> list) {
		if(list.size() != 1500)
			System.out.println("illegal size: " + list.size() + "----------------------------");
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i) <= 0+1e-15) {
				if(i != 0)
					return list.get(i-1);
			}
		}
		return list.get(list.size()-1);
		
	}
	
	private static Params getParamsAdj(int seed, int popSize, double simulationTime) {
		Params params = getParams(seed, popSize, simulationTime);
		params.elitists = 0.10d;
		params.crossover = 0.20d;
		params.mutation = 0.35d;
		return params;
	}

	private static Params getParamsPath(int seed, int popSize , double simulationTime) {
		Params params = getParams(seed, popSize, simulationTime);
		params.elitists = 0.10d;
		params.crossover = 0.90d;
		params.mutation = 0.30d;
		return params;
	}

	private static Params getParams(int seed, int popSize, double simulationTime) {
		Params params = new Params();
		params.rand = new Random(seed);
		params.popSize = popSize;
		params.maxGenerations = 1500;
		params.detectLoops = false;
		params.correlativeTournament = false;
		params.similarSubsetSize = 0.0;
		params.stop = 0.95d;
		params.simulationTime = simulationTime;
		return params;
	}

	public static GA<Adjacency> createGAAdj(Problem problem, Params params){
		return new GA<Adjacency>(params, new AdjacencyFactory(), new SUS<Adjacency>(params), new AlternatingEdge(params, problem), new FBI<Adjacency>(params),  new SimpleInversionMutator<Adjacency>(params), new LineairRanker<Adjacency>(), new LoopDetection<Adjacency>());
	}

	public static GA<Path> createGAPath(Problem problem, Params params){
		return new GA<Path>(params, new PathFactory(), new SUS<Path>(params), new EdgeRecombination(problem, params), new FBI<Path>(params),  new SimpleInversionMutator<Path>(params), new LineairRanker<Path>(), new LoopDetection<Path>());
	}
}
