package plots.reps.mutation;

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

	private static final Params params = new Params();

	private static final String problemFilePath = "../genetic/datafiles/xqf131.tsp"; //optimal length 564
	private static final String outputPath = "../genetic/result/result-path-mutation-";
	private static final String outputAdjacency = "../genetic/result/result-adj-mutation-";

	private static final Problem problem = ProblemGenerator.generate(problemFilePath);

	public static void main(String[] args) {
		makePlot();
	}

	public static void makePlot() {

		int nbTimes = 1;
		double step = 0.05;
		double max = 0.1;
		
		int randomSeed = (new Random()).nextInt();

		for (double mut = 0.05; mut < max; mut = mut + step) {
			runSingleMutatorAdj(nbTimes, randomSeed, new SimpleInversionMutator<Adjacency>(getParamsAdj(randomSeed, mut)), "simpleInversion",mut);
			runSingleMutatorAdj(nbTimes, randomSeed, new ExchangeMutator<Adjacency>(getParamsAdj(randomSeed, mut)), "exchange",mut);
			runSingleMutatorAdj(nbTimes, randomSeed, new InversionMutator<Adjacency>(getParamsAdj(randomSeed,mut)), "inversion",mut);
			runSingleMutatorAdj(nbTimes, randomSeed, new InsertionMutator<Adjacency>(getParamsAdj(randomSeed,mut)), "insertion",mut);

			runSingleMutatorPath(nbTimes, randomSeed, new SimpleInversionMutator<Path>(getParamsPath(randomSeed,mut)), "simpleInversion",mut);
			runSingleMutatorPath(nbTimes, randomSeed, new ExchangeMutator<Path>(getParamsPath(randomSeed,mut)), "exchange",mut);
			runSingleMutatorPath(nbTimes, randomSeed, new InversionMutator<Path>(getParamsPath(randomSeed,mut)), "inversion",mut);
			runSingleMutatorPath(nbTimes, randomSeed, new InsertionMutator<Path>(getParamsPath(randomSeed,mut)), "insertion",mut);
		}

	}

	private static void runSingleMutatorAdj(int nbTimes, int randomSeed, Mutator<Adjacency> mut, String name, double mutPerc) {
		History history = new History(outputAdjacency + name + ".txt");
		createGAAdj(problem, getParamsAdj(randomSeed, mutPerc), mut).run(problem, history, nbTimes);
		history.writeFile();
	}

	private static void runSingleMutatorPath(int nbTimes, int randomSeed, Mutator<Path> mut, String name, double mutPerc) {
		History history = new History(outputPath + name + ".txt");
		createGAPath(problem, getParamsPath(randomSeed, mutPerc), mut).run(problem, history, nbTimes);
		history.writeFile();
	}

	private static Params getParamsPath(int seed, double mut) {
		params.popSize = 150;
		params.maxGenerations = 100;
		params.elitists = 0.05d;
		params.crossover = 0.95d;
		params.mutation = mut;
		params.stop = 0.95d;
		params.detectLoops = false;
		params.rand = new Random(seed);
		params.correlativeTournament = false;
		params.similarSubsetSize = 1.0;
		return params;
	}

	private static Params getParamsAdj(int seed, double mut) {
		params.popSize = 150;
		params.maxGenerations = 100;
		params.elitists = 0.05d;
		params.crossover = 0.20d;
		params.mutation = mut;
		params.stop = 0.95d;
		params.detectLoops = false;
		params.rand = new Random(seed);
		params.correlativeTournament = false;
		params.similarSubsetSize = 1.0;
		return params;
	}

	public static GA<Adjacency> createGAAdj(Problem problem, Params params, Mutator<Adjacency> mut){
		return new GA<Adjacency>(params, new AdjacencyFactory(), new SUS<Adjacency>(params), new AlternatingEdge(params, problem), new FBI<Adjacency>(params), mut, new LineairRanker<Adjacency>(), new LoopDetection<Adjacency>());
	}

	public static GA<Path> createGAPath(Problem problem, Params params, Mutator<Path> mut){
		return new GA<Path>(params, new PathFactory(), new SUS<Path>(params), new EdgeRecombination(problem, params), new FBI<Path>(params), mut, new LineairRanker<Path>(), new LoopDetection<Path>());
	}
}
