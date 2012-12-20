package main;

import java.util.Random;

import main.crossover.AlternatingEdge;
import main.crossover.CrossOver;
import main.crossover.EdgeRecombination;
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
import params.TestParams;
import representations.Adjacency;
import representations.Chromosome;
import representations.path.Path;
import util.ProblemGenerator;
import factory.AdjacencyFactory;
import factory.PathFactory;


public class Main {

	public static void main(String[] args) {
		Problem problem = ProblemGenerator.generate("../genetic/datafiles/rondrit127.tsp");
		
		History history1 = new History("../genetic/result/resultNewAdj.txt");
		createGA1(problem).run(problem, history1);
		
		history1.printShort();
		
		/*
		History history2 = new History("../genetic/result/resultNewPath.txt");
		createGA2(problem).run(problem, history2);
		history2.writeFile();
		
		System.out.println("Path");
		history2.printShort();*/
	}
	
	
	public static <R extends Chromosome>  void run(GA<R> ga){
	}
	
	public static GA<Adjacency> createGA1(Problem problem){
		Params params = createParams(false);
		AdjacencyFactory factory = new AdjacencyFactory();
		Selector<Adjacency> selector = new SUS<Adjacency>(params);
		CrossOver<Adjacency> crossover = new AlternatingEdge(params, problem);
		ReInsertor<Adjacency> insertor = new FBI<Adjacency>(params);
		Mutator<Adjacency> mutator = new InversionMutator<Adjacency>(params);
		Ranker<Adjacency> ranker = new LineairRanker<Adjacency>();
		LoopDetection<Adjacency> loopDetection = new LoopDetection<Adjacency>();
		return new GA<Adjacency>(params, factory, selector, crossover, insertor, mutator, ranker, loopDetection);
	}

	public static GA<Path> createGA2(Problem problem){
		Params params = createParams(false);
		PathFactory factory = new PathFactory();
		Selector<Path> selector = new SUS<Path>(params);
		CrossOver<Path> crossover = new EdgeRecombination(problem, params);
		ReInsertor<Path> insertor = new FBI<Path>(params);
		Mutator<Path> mutator = new SimpleInversionMutator<Path>(params);
		Ranker<Path> ranker = new LineairRanker<Path>();
		LoopDetection<Path> loopDetection = new LoopDetection<Path>();
		return new GA<Path>(params, factory, selector, crossover, insertor, mutator, ranker, loopDetection);
	}
	
	/**
	 * useTestParams can be set to true to use the predefined TestParams class which has default values.
	 * @param useTestParams
	 * @return
	 */
	private static Params createParams(boolean useTestParams) {
		Params params = new Params();
		params.crossover = 0.85;
		params.elitists = 0.15;
		params.maxGenerations = 1000;
		params.mutation = 0.08;
		params.popSize = 100;
		params.stop = 0.95;
		params.rand = new Random();
		return useTestParams ? new TestParams() : params ;
	}
	
}
