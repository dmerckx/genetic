package plots.mutation;

import java.util.Random;

import main.GA;
import main.History;
import main.LoopDetection;
import main.Problem;
import main.crossover.AlternatingEdge;
import main.crossover.EdgeRecombination;
import main.insertion.FBI;
import main.mutation.ExchangeMutator;
import main.mutation.InsertionMutator;
import main.mutation.InversionMutator;
import main.mutation.Mutator;
import main.mutation.SimpleInversionMutator;
import main.rankers.LineairRanker;
import main.selectors.SUS;
import params.Params;
import representations.Adjacency;
import representations.path.Path;
import util.ProblemGenerator;
import factory.AdjacencyFactory;
import factory.PathFactory;

public class PlotMutation {

	private static final String problemFilePath = "../genetic/datafiles/xqf131.tsp"; //optimal length 564
	private static final String outputPath = "../genetic/result/result-path-mutation-";
	private static final String outputAdjacency = "../genetic/result/result-adj-mutation-";

	private static final Problem problem = ProblemGenerator.generate(problemFilePath);

	public static void main(String[] args) {
		makePlot();
	}

	public static void makePlot() {

		long before = System.currentTimeMillis();
		
		int nbTimes = 8;
		double step = 0.02;
		double max = 0.6;
		
		int randomSeed = (new Random()).nextInt();

		System.out.println("seed: " + randomSeed);
		for (double mutPerc = 0.00; mutPerc < max; mutPerc = mutPerc + step) {
			long beforeIt = System.currentTimeMillis();
			System.out.print("1: ");
			runSingleMutatorAdj(nbTimes, randomSeed, new SimpleInversionMutator<Adjacency>(getParamsAdj(randomSeed, mutPerc)), "simpleInversion",mutPerc);
			System.out.print("2: ");
			runSingleMutatorAdj(nbTimes, randomSeed, new ExchangeMutator<Adjacency>(getParamsAdj(randomSeed, mutPerc)), "exchange",mutPerc);
			System.out.print("3: ");
			runSingleMutatorAdj(nbTimes, randomSeed, new InversionMutator<Adjacency>(getParamsAdj(randomSeed,mutPerc)), "inversion",mutPerc);
			System.out.print("4: ");
			runSingleMutatorAdj(nbTimes, randomSeed, new InsertionMutator<Adjacency>(getParamsAdj(randomSeed,mutPerc)), "insertion",mutPerc);
			System.out.print("5: ");
			runSingleMutatorPath(nbTimes, randomSeed, new SimpleInversionMutator<Path>(getParamsPath(randomSeed,mutPerc)), "simpleInversion",mutPerc);
			System.out.print("6: ");
			runSingleMutatorPath(nbTimes, randomSeed, new ExchangeMutator<Path>(getParamsPath(randomSeed,mutPerc)), "exchange",mutPerc);
			System.out.print("7: ");
			runSingleMutatorPath(nbTimes, randomSeed, new InversionMutator<Path>(getParamsPath(randomSeed,mutPerc)), "inversion",mutPerc);
			System.out.print("8: ");
			runSingleMutatorPath(nbTimes, randomSeed, new InsertionMutator<Path>(getParamsPath(randomSeed,mutPerc)), "insertion",mutPerc);
			double timeIt = System.currentTimeMillis() - beforeIt;
			double hoursIt = timeIt / (60000*60);
			double minsIt = timeIt / (60000);
			System.out.println("exec time of one iteration: " + hoursIt + " hours, " + minsIt + " mins");
		}

		double time = System.currentTimeMillis() - before;
		double hours = time / (60000*60);
		double mins = time / (60000);
		
		System.out.println("execution time in hours: " + hours);
		System.out.println("execution time in mins: " + mins);
	}

	private static void runSingleMutatorAdj(int nbTimes, int randomSeed, Mutator<Adjacency> mut, String name, double mutPerc) {
		History history = new History(outputAdjacency + name + mutPerc + ".txt");
		createGAAdj(problem, getParamsAdj(randomSeed, mutPerc), mut).run(problem, history, nbTimes);
		history.writeFile();
		history.printShort();
	}

	private static void runSingleMutatorPath(int nbTimes, int randomSeed, Mutator<Path> mut, String name, double mutPerc) {
		History history = new History(outputPath + name + mutPerc + ".txt");
		createGAPath(problem, getParamsPath(randomSeed, mutPerc), mut).run(problem, history, nbTimes);
		history.writeFile();
		history.printShort();
	}

	private static Params getParamsPath(int seed, double mut) {
		Params params = getParams(seed);
		params.elitists = 0.05d;
		params.crossover = 0.90d;
		params.mutation = mut;
		return params;
	}

	private static Params getParamsAdj(int seed, double mut) {
		Params params = getParams(seed);
		params.elitists = 0.05d;
		params.crossover = 0.20d;
		params.mutation = mut;
		return params;
	}
	
	private static Params getParams(int seed) {
		Params params = new Params();
		params.popSize = 100;
		params.maxGenerations = 200;
		params.detectLoops = false;
		params.rand = new Random(seed);
		params.correlativeTournament = false;
		params.similarSubsetSize = 1.0;
		params.stop = 0.95d;
		return params;
	}

	public static GA<Adjacency> createGAAdj(Problem problem, Params params, Mutator<Adjacency> mut){
		return new GA<Adjacency>(params, new AdjacencyFactory(), new SUS<Adjacency>(params), new AlternatingEdge(params, problem), new FBI<Adjacency>(params), mut, new LineairRanker<Adjacency>(), new LoopDetection<Adjacency>());
	}

	public static GA<Path> createGAPath(Problem problem, Params params, Mutator<Path> mut){
		return new GA<Path>(params, new PathFactory(), new SUS<Path>(params), new EdgeRecombination(problem, params), new FBI<Path>(params), mut, new LineairRanker<Path>(), new LoopDetection<Path>());
	}
}
