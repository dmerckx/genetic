package plots.reps;

import java.util.Random;

import main.GA;
import main.History;
import main.Problem;
import main.crossover.AlternatingEdge;
import main.crossover.CrossOver;
import main.insertion.FBI;
import main.insertion.ReInsertor;
import main.mutation.ExchangeMutator;
import main.mutation.Mutator;
import main.rankers.LineairRanker;
import main.rankers.Ranker;
import main.selectors.SUS;
import main.selectors.Selector;
import params.Params;
import representations.Adjacency;
import util.ProblemGenerator;
import factory.AdjacencyFactory;

public class Crossover {

	public static Params params;
	public static Problem problem;
	
	public static void main(String[] args) {
		problem = ProblemGenerator.generate("../genetic/datafiles/rondrit070.tsp");
		
		double[] mutations = new double[]{0,0.15,0.30,0.50,0.80};
		
		for(double mut:mutations){
			double cross = 0;
			while(cross <= 1.00){
				History history =
						new History("../genetic/plots/ga/crossover_m" + mut + "c" + cross + ".txt");
				GA<Adjacency> ga = createGA(mut, cross);
				ga.run(problem, history);
				history.writeFile();
				System.out.println("Results for mut:" + mut + " cross:" + cross);
				history.printShort();
				
				cross += 0.25;
			}
		}
	}
	
	public static GA<Adjacency> createGA(double mutation, double crossover){
		params = new Params();
		params.popSize = 300;
		params.maxGenerations = 500;
		params.mutation = mutation;
		params.crossover = crossover;
		params.elitists = 0.20;
		params.rand = new Random(256);
		
		/*return new GA<Adjacency>(
			params,
			new AdjacencyFactory(),
			getSelector(),
			getCrossover(),
			getInsertor(),
			getMutator(),
			getRanker()
		);*/
	}
	
	public static Selector<Adjacency> getSelector(){
		return new SUS<Adjacency>(params);
	}
	
	public static CrossOver<Adjacency> getCrossover(){
		return new AlternatingEdge(params, problem);
	}
	
	public static ReInsertor<Adjacency> getInsertor(){
		return new FBI<Adjacency>(params);
	}
	
	public static Mutator<Adjacency> getMutator(){
		return new ExchangeMutator<Adjacency>(params);
	}
	
	public static Ranker<Adjacency> getRanker(){
		return new LineairRanker<Adjacency>();
	}
}
