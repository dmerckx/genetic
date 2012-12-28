package plots;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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
import representations.Adjacency;
import representations.path.Path;
import util.ProblemGenerator;
import factory.AdjacencyFactory;
import factory.PathFactory;

public class Crossover {

	public static Params params;
	public static Problem problem;
	
	public static void main(String[] args) {
		problem = ProblemGenerator.generate("../genetic/datafiles/rondrit127.tsp");
		
		double[] mutations = new double[]
				{0, 0.05, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5,
				 0.55, 0.6, 0.65, 0.7, 0.75, 0.8, 0.85, 0.9, 0.95, 1.0};
		double[] crossovers = new double[]
				{0, 0.05, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5,
				 0.55, 0.6, 0.65, 0.7, 0.75, 0.8, 0.85, 0.9, 0.95, 1.0};
		double[][] bestResultsAdj = new double[mutations.length][crossovers.length];
		double[][] bestResultsPath = new double[mutations.length][crossovers.length];
		
		for(int m = 0; m < mutations.length; m++){
			for(int c = 0; c < crossovers.length; c++){
				setParams(mutations[m], crossovers[c]);
				
				GA<?> ga1 = createAdjGA();
				History history1 = new History();
				ga1.run(problem, history1, 7);
				bestResultsAdj[m][c] = history1.getLastBest();
				

				GA<?> ga2 = createPathGA();
				History history2 = new History();
				ga2.run(problem, history2, 7);
				bestResultsPath[m][c] = history2.getLastBest();
				
				history1.printShort();
				history2.printShort();
			}
		}

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
	
	public static void setParams(double mutation, double crossover){
		params = new Params();
		params.popSize = 100;
		params.maxGenerations = 300;
		params.mutation = mutation;
		params.crossover = crossover;
		params.elitists = 0.20;
		params.rand = new Random(13);
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