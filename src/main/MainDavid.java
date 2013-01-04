package main;

import java.util.Random;

import main.crossover.AlternatingEdge;
import main.crossover.EdgeRecombination;
import main.insertion.FBI;
import main.mutation.ExchangeMutator;
import main.rankers.LineairRanker;
import main.selectors.SUS;
import params.Params;
import representations.Adjacency;
import representations.path.Path;
import util.ProblemGenerator;
import factory.AdjacencyFactory;
import factory.PathFactory;

public class MainDavid {

	public static Params params;
	public static Problem problem;
	
	public static void main(String[] args) {
		problem = ProblemGenerator.generate("../genetic/datafiles/rondrit127.tsp");
		
		setParams();
		
		GA<?> ga1 = createAdjGA();
		History history1 = new History();
		ga1.run(problem, history1, 1);
		
		history1.printShort();
		
		GA<?> ga2 = createPathGA();
		History history2 = new History();
		ga2.run(problem, history2, 1);

		history2.printShort();
	}
	
	public static void setParams(){
		params = new Params();
		params.popSize = 500;
		params.maxGenerations = 500;
		params.mutation = 0.5;
		params.crossover = 0.5;
		params.elitists = 0.15;
		params.rand = new Random(255);
	}
	
	public static GA<Adjacency> createAdjGA(){
		return new GA<Adjacency>(
			params,
			new AdjacencyFactory(),
			new SUS<Adjacency>(params),
			new AlternatingEdge(params, problem),
			new FBI<Adjacency>(params),
			new ExchangeMutator<Adjacency>(params),
			new LineairRanker<Adjacency>(),
			null	//no loop detection
		);
	}
	
	public static GA<Path> createPathGA(){
		return new GA<Path>(
			params,
			new PathFactory(),
			new SUS<Path>(params),
			new EdgeRecombination(problem, params),
			new FBI<Path>(params),
			new ExchangeMutator<Path>(params),
			new LineairRanker<Path>(),
			null	//no loop detection
		);
	}
	
}