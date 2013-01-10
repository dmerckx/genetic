package main;

import java.util.Random;

import main.crossover.AlternatingEdge;
import main.crossover.CrossOver;
import main.crossover.EdgeRecombination;
import main.crossover.EdgeRecombinationOpt;
import main.crossover.EdgeRecombinationOpt2;
import main.insertion.FBI;
import main.insertion.ReInsertor;
import main.mutation.InversionMutator;
import main.mutation.Mutator;
import main.mutation.SimpleInversionMutator;
import main.rankers.LineairRanker;
import main.rankers.Ranker;
import main.selectors.SUS;
import main.selectors.Selector;
import params.Params;
import representations.Adjacency;
import representations.Chromosome;
import representations.path.Path;
import util.ProblemGenerator;
import factory.AdjacencyFactory;
import factory.PathFactory;


public class Main {

	public static void main(String[] args) {
		Problem problem = ProblemGenerator.generate("../genetic/datafiles/xqf131.tsp");
		
		int randomSeed = (new Random()).nextInt();
		int nbTimes = 20;
		
		long before = System.currentTimeMillis();

		History history = new History("../genetic/result/resultNewAdj.txt");
		GA<Path> ga = createGA(problem, randomSeed, new EdgeRecombination(problem, createParams(randomSeed)));
		ga.run(problem, history, nbTimes);
		history.printShort();
		((EdgeRecombination)ga.crossover).timer.print();
		double time = System.currentTimeMillis() - before;
		time = time/60000;
		System.out.println();
		System.out.println("time no opt: " + time);
		System.out.println();
		
		before = System.currentTimeMillis();
		History history2 = new History("../genetic/result/resultNewAdj.txt");
		GA<Path> ga2 = createGA(problem,randomSeed,new EdgeRecombinationOpt2(problem, createParams(randomSeed)));
		ga2.run(problem, history2, nbTimes);
		history2.printShort();
		((EdgeRecombinationOpt2)ga2.crossover).timer.print();
		time = System.currentTimeMillis() - before;
		time = time/60000;
		System.out.println("time with no opt: "+ time);
		
		before = System.currentTimeMillis();
		History history1 = new History("../genetic/result/resultNewAdj.txt");
		GA<Path> ga1 = createGA(problem,randomSeed, new EdgeRecombinationOpt(problem, createParams(randomSeed)));
		ga1.run(problem, history1, nbTimes);
		history1.printShort();
		((EdgeRecombinationOpt)ga1.crossover).timer.print();
		time = System.currentTimeMillis() - before;
		time = time/60000;
		System.out.println();
		System.out.println("time with hashindex opt: " + time);
		System.out.println();
		
//		History history2 = new History("../genetic/result/resultNewPath.txt");
//		createGA2(problem).run(problem, history2);
//		history2.writeFile();
//		
//		System.out.println("Path");
//		history2.printShort();
	}
	
	
	public static <R extends Chromosome>  void run(GA<R> ga){
	}
	
	public static GA<Adjacency> createGA1(Problem problem, Ranker<Adjacency> ranker, int randomSeed){
		Params params = createParams(randomSeed);
		AdjacencyFactory factory = new AdjacencyFactory();
		Selector<Adjacency> selector = new SUS<Adjacency>(params);
		CrossOver<Adjacency> crossover = new AlternatingEdge(params, problem);
		ReInsertor<Adjacency> insertor = new FBI<Adjacency>(params);
		Mutator<Adjacency> mutator = new InversionMutator<Adjacency>(params);
		LoopDetection<Adjacency> loopDetection = new LoopDetection<Adjacency>();
		return new GA<Adjacency>(params, factory, selector, crossover, insertor, mutator, ranker, loopDetection);
	}

	public static GA<Path> createGA(Problem problem, int randomSeed, CrossOver<Path> crossover){
		Params params = createParams(randomSeed);
		PathFactory factory = new PathFactory();
		Selector<Path> selector = new SUS<Path>(params);
		ReInsertor<Path> insertor = new FBI<Path>(params);
		Mutator<Path> mutator = new SimpleInversionMutator<Path>(params);
		Ranker<Path> ranker = new LineairRanker<Path>();
		LoopDetection<Path> loopDetection = new LoopDetection<Path>();
		return new GA<Path>(params, factory, selector, crossover, insertor, mutator, ranker, loopDetection);
	}
	
	
	private static Params createParams(int randomSeed) {
		Params params = new Params();
		params.crossover = 0.90;
		params.mutation = 0.20;
		params.elitists = 0.1;
		params.maxGenerations = 500;
		params.popSize = 150;
		params.stop = 0.95;
		params.rand = new Random(randomSeed);
		params.correlativeTournament = false;
		return params ;
	}
	
}
