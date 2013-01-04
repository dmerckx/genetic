package plots;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import main.GA;
import main.History;
import main.Problem;
import main.crossover.AlternatingEdge;
import main.crossover.EdgeRecombination;
import main.insertion.FBI;
import main.mutation.ExchangeMutator;
import main.rankers.LineairRanker;
import main.selectors.SUS;
import params.Params;
import plots.islands.MigrationRate;
import representations.Adjacency;
import representations.path.Path;
import util.ProblemGenerator;
import factory.AdjacencyFactory;
import factory.PathFactory;

public class Crossover {

	public static Problem problem;
	
	public static double[] mutations;
	public static double[] crossovers;
	public static double[][] bestResultsAdj;
	public static double[][] bestResultsPath;
	
	
	public static void main(String[] args) throws Exception {
		problem = ProblemGenerator.generate("../genetic/datafiles/xqf131.tsp");
		
		mutations = new double[]
				{0, 0.05, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5,
				 0.55, 0.6, 0.65, 0.7, 0.75, 0.8, 0.85, 0.9, 0.95, 1.0};
		crossovers = new double[]
				{0, 0.05, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5,
				 0.55, 0.6, 0.65, 0.7, 0.75, 0.8, 0.85, 0.9, 0.95, 1.0};
		bestResultsAdj = new double[mutations.length][crossovers.length];
		bestResultsPath = new double[mutations.length][crossovers.length];
		
		
		ExecutorService pool = Executors.newFixedThreadPool(4);
		for(int i = 0; i < 21; i++){
			final int j = i;
			pool.execute(new Runnable() {
				@Override
				public void run() {
					work(j, j+1);
				}
			});
		}
		pool.shutdown();
		pool.awaitTermination(10, TimeUnit.HOURS);
		
		try {
			FileWriter writerAdj = new FileWriter(new File("../genetic/plots/crossoverAdj"));
			FileWriter writerPath = new FileWriter(new File("../genetic/plots/crossoverPath"));
			for(int m = 0; m < mutations.length; m++){
				for(int c = 0; c < crossovers.length; c++){
					writerAdj.write(bestResultsAdj[m][c] + " ");
					writerPath.write(bestResultsPath[m][c] + " ");
				}
				writerAdj.write("\r\n");
				writerPath.write("\r\n");
			}
			writerAdj.close();
			writerPath.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void work(int from, int to){
		for(int m = from; m < to; m++){
			for(int c = 0; c < crossovers.length; c++){
				Params params = getParams(mutations[m], crossovers[c]);
				
				GA<?> ga1 = createAdjGA(params);
				History history1 = new History();
				ga1.run(problem, history1, 10);
				bestResultsAdj[m][c] = history1.getLastBest();
				

				GA<?> ga2 = createPathGA(params);
				History history2 = new History();
				ga2.run(problem, history2, 10);
				bestResultsPath[m][c] = history2.getLastBest();
				
				System.out.println("m" + mutations[m] + " c" + crossovers[c] + " done");
				System.out.println("res adj: " + history1.getLastBest() + " res path: " + history2.getLastBest());
			}
		}
	}
	
	public static Params getParams(double mutation, double crossover){
		Params params = new Params();
		params.popSize = 100;
		params.maxGenerations = 550;
		params.mutation = mutation;
		params.crossover = crossover;
		params.elitists = 0.20;
		params.rand = new Random(11);
		
		return params;
	}
	
	public static GA<Adjacency> createAdjGA(Params params){
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
	
	public static GA<Path> createPathGA(Params params){
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