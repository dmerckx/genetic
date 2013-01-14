package plots.ranking;

import java.util.Random;

import main.GA;
import main.History;
import main.Problem;
import main.crossover.AlternatingEdge;
import main.crossover.EdgeRecombination;
import main.insertion.FBI;
import main.mutation.SimpleInversionMutator;
import main.rankers.FitnessRanker;
import main.rankers.LineairRanker;
import main.rankers.NonLinearRanker;
import main.rankers.Ranker;
import main.selectors.SUS;
import params.Params;
import representations.Adjacency;
import representations.path.Path;
import util.ProblemGenerator;
import factory.AdjacencyFactory;
import factory.PathFactory;

public class PlotRanking {

	private static final String problemFilePath = "../genetic/datafiles/xqf131.tsp";
	private static final Problem problem = ProblemGenerator.generate(problemFilePath);

	private static final String outputAdjLinearRankerFilePath = "../genetic/result/result-adj-lr";
	private static final String outputAdjNonLinearRankerFilePath = "../genetic/result/result-adj-nr";
	private static final String outputAdjFitnessRankerFilePath = "../genetic/result/result-adj-fr.txt";

	private static final String outputPathLinearRankerFilePath = "../genetic/result/result-path-lr";
	private static final String outputPathNonLinearRankerFilePath = "../genetic/result/result-path-nr";
	private static final String outputPathFitnessRankerFilePath = "../genetic/result/result-path-fr.txt";

	public static void main(String[] args) {
		makePlot();
	}

	public static void makePlot() {

		int nbTimes = 5;

		long before = System.currentTimeMillis();

		int randomSeed = (new Random()).nextInt();


		System.out.println("adjacency fitness ranker: ");
		System.out.println();
		
		History history1 = new History(outputAdjFitnessRankerFilePath);
		createGA(problem, new FitnessRanker<Adjacency>(), getParamsAdj(randomSeed)).run(problem, history1, nbTimes);
		history1.printShort();
		history1.writeFile();

		System.out.println();
		System.out.println("adjacency linear ranker");
		System.out.println();
		
		for (double pressure = 1.5; pressure <= 2; pressure = pressure + 0.5) {
			System.out.println("pressure: " + pressure);
			long s = Math.round(pressure*100);
			History history2 = new History(outputAdjLinearRankerFilePath + "-" + s +  ".txt");
			createGA(problem, new LineairRanker<Adjacency>(pressure), getParamsAdj(randomSeed)).run(problem, history2, nbTimes);
			history2.printShort();
			history2.writeFile();
			System.out.println();
		}

		System.out.println();
		System.out.println("adjacency nonlinear ranker");
		System.out.println();
		
		for (int pressure = 2; pressure <= 5; pressure = pressure + 3) {
			System.out.println("pressure: " + pressure);
			History history3 = new History(outputAdjNonLinearRankerFilePath + "-" + Math.round(pressure*100) +  ".txt");
			createGA(problem, new NonLinearRanker<Adjacency>(pressure, getParamsAdj(randomSeed)), getParamsAdj(randomSeed)).run(problem, history3, nbTimes);
			history3.printShort();
			history3.writeFile();
			System.out.println();
		}

		System.out.println();
		System.out.println("path fitness ranking");
		System.out.println();
		
		History history4 = new History(outputPathFitnessRankerFilePath);
		createGAPath(problem, new FitnessRanker<Path>(), getParamsPath(randomSeed)).run(problem, history4, nbTimes);
		history4.printShort();
		history4.writeFile();

		System.out.println();
		System.out.println("path linear ranking");
		System.out.println();
		
		for (double pressure = 1.5; pressure <= 2; pressure = pressure + 0.5) {
			System.out.println("pressure: " + pressure);
			History history5 = new History(outputPathLinearRankerFilePath + "-" + Math.round(pressure*100) +  ".txt");
			createGAPath(problem, new LineairRanker<Path>(pressure), getParamsPath(randomSeed)).run(problem, history5, nbTimes);
			history5.printShort();
			history5.writeFile();
			System.out.println();
		}

		System.out.println();
		System.out.println("path nonlinear ranking");
		System.out.println();
		
		for (int pressure = 2; pressure <= 5; pressure = pressure + 3) {
			System.out.println("pressure: " + pressure);
			History history6 = new History(outputPathNonLinearRankerFilePath + "-" + Math.round(pressure*100) +  ".txt");
			createGAPath(problem, new NonLinearRanker<Path>(pressure,getParamsPath(randomSeed)), getParamsPath(randomSeed)).run(problem, history6, nbTimes);
			history6.printShort();
			history6.writeFile();
			System.out.println();
		}

		double time = System.currentTimeMillis() - before;
		double mins = time / (60000);
		System.out.println("total time in mins: " + mins);
		
	}

	private static Params getParams(int seed) {
		Params params = new Params();
		params.rand =  new Random(seed);
		params.popSize = 50;
		params.maxGenerations = 10000;
		params.stop = 0.95d;
		params.detectLoops = false;
		return params;
	}

	private static Params getParamsPath(int seed) {
		Params params = getParams(seed);
		params.elitists = 0.2d;
		params.crossover = 0.75d;
		params.mutation = 0.40d;
		return params;
	}

	private static Params getParamsAdj(int seed) {
		Params params = getParams(seed);
		params.elitists = 0.2d;
		params.crossover = 0.25d;
		params.mutation = 0.40d;
		return params;
	}

	public static GA<Adjacency> createGA(Problem problem, Ranker<Adjacency> ranker, Params params){
		return new GA<Adjacency>(params, new AdjacencyFactory(), new SUS<Adjacency>(params), new AlternatingEdge(params, problem), new FBI<Adjacency>(params), new SimpleInversionMutator<Adjacency>(params), ranker, null);
	}

	public static GA<Path> createGAPath(Problem problem, Ranker<Path> ranker, Params params){
		return new GA<Path>(params, new PathFactory(), new SUS<Path>(params), new EdgeRecombination(problem,params), new FBI<Path>(params), new SimpleInversionMutator<Path>(params), ranker, null);
	}
}
