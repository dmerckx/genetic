package main;

import java.util.Random;

import main.crossover.AlternatingEdge;
import main.crossover.CrossOver;
import main.crossover.EdgeRecombination;
import main.insertion.FBI;
import main.insertion.ReInsertor;
import main.insertion.UI;
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


public class MainDavid {

	public static void main(String[] args) {
		
		Problem problem = ProblemGenerator.generate("../genetic/datafiles/rondrit016.tsp");
		
		GA<Adjacency> ga1 = createGA1(problem);
		History hist1 = new History();
		ga1.run(problem, hist1);
		
		hist1.printShort();
		
		GA<Path> ga2 = createGA2(problem);
		History hist2 = new History();
		ga2.run(problem, hist2);

		hist2.printShort();
		
		IslandsGA is = new IslandsGA(problem, createGA1(problem), createGA2(problem));
		History comboHistory = new History("../genetic/result/islands.txt");	
		is.run(problem, comboHistory);
		comboHistory.printShort();
	}
	
	
	public static <R extends Chromosome>  void run(GA<R> ga){
	}
	
	public static GA<Adjacency> createGA1(Problem problem){
		Params params = createParams();
		AdjacencyFactory factory = new AdjacencyFactory();
		Selector<Adjacency> selector = new SUS<Adjacency>(params);
		CrossOver<Adjacency> crossover = new AlternatingEdge(factory, params, problem);
		ReInsertor<Adjacency> insertor = new FBI<Adjacency>(params);
		Mutator<Adjacency> mutator = new SimpleInversionMutator<Adjacency>(params);
		Ranker<Adjacency> ranker = new LineairRanker<Adjacency>();
		return new GA<Adjacency>(params, factory, selector, crossover, insertor, mutator, ranker);
	}

	public static GA<Path> createGA2(Problem problem){
		Params params = createParams();
		PathFactory factory = new PathFactory();
		Selector<Path> selector = new SUS<Path>(params);
		CrossOver<Path> crossover = new EdgeRecombination(factory, problem, params);
		ReInsertor<Path> insertor = new UI<Path>(params);
		Mutator<Path> mutator = new SimpleInversionMutator<Path>(params);
		Ranker<Path> ranker = new LineairRanker<Path>();
		return new GA<Path>(params, factory, selector, crossover, insertor, mutator, ranker);
	}
	
	/**
	 * useTestParams can be set to true to use the predefined TestParams class which has default values.
	 * @param useTestParams
	 * @return
	 */
	private static Params createParams() {
		Params params = new Params();
		params.crossover = 0.90;
		params.elitists = 0.05;
		params.maxGenerations = 100;
		params.mutation = 0.10;
		params.popSize = 100;
		params.stop = 0.95;
		params.rand = new Random(18);
		params.renegadeFreq = 35;
		params.renegades = 0.05d;
		return params;
	}
	
}
