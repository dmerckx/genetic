package main;

import main.crossover.AlternatingEdge;
import main.crossover.CrossOver;
import main.insertion.FBI;
import main.insertion.Insertor;
import main.mutation.ExchangeMutator;
import main.mutation.Mutator;
import main.selectors.SUS;
import main.selectors.Selector;
import params.Params;
import params.TestParams;
import representations.Adjacency;
import representations.Representation;
import util.ProblemGenerator;
import factory.AdjacencyFactory;


public class Main {

	public static void main(String[] args) {
		Problem problem = ProblemGenerator.generate("../genetic/datafiles/rondrit016.tsp");
		History history = new History("../genetic/result/result.txt");
		createGA(problem).run(problem, history);
	}
	
	
	public static <R extends Representation>  void run(GA<R> ga){
	}
	
	public static GA<Adjacency> createGA(Problem problem){
		Params params = createParams(true);
		AdjacencyFactory factory = new AdjacencyFactory();
		Selector<Adjacency> selector = new SUS<Adjacency>(params);
		CrossOver<Adjacency> crossover = new AlternatingEdge(factory, params, problem);
		Insertor<Adjacency> insertor =new FBI<Adjacency>(params);
		Mutator<Adjacency> mutator = new ExchangeMutator<Adjacency>(params);
		return new GA<Adjacency>(params, factory, selector, crossover, insertor, mutator);
	}

	/**
	 * useTestParams can be set to true to use the predefined TestParams class which has default values.
	 * @param useTestParams
	 * @return
	 */
	private static Params createParams(boolean useTestParams) {
		Params params = new Params();
		params.crossover = 0.95;
		params.elitists = 0.05;
		params.maxGenerations = 100;
		params.mutation = 0.05;
		params.popSize = 100;
		params.stop = 0.95;
		return useTestParams ? new TestParams() : params ;
	}
	
}
