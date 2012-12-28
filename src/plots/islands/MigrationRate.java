package plots.islands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import main.GA;
import main.History;
import main.IslandsGA;
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

public class MigrationRate {

	public static Params params;
	public static Problem problem;
	
	public static void main(String[] args) {
		problem = ProblemGenerator.generate("../genetic/datafiles/rondrit070.tsp");

		double[] migr = new double[]{0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
		double[] results4 = new double[migr.length];
		double[] results8 = new double[migr.length];
		
		for(int m = 0; m < migr.length; m++){
			
			GA<?>[] gas = new GA<?>[12];
			for(int i = 0; i < 12;i++){
				setGAParams(migr[m]);
				params.rand = new Random(13 * i * m);
				gas[i] = createAdjGA();
			}
			
			History history1 = new History();
			IslandsGA mainGA = new IslandsGA(problem, gas);
			mainGA.run(problem, history1, 15);
			
			System.out.println("result for migration: " + migr[m]);
			history1.printShort();
			System.out.println("----------------------------");
		}

		/*try {
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
		}*/
	}
	
	public static void setGAParams(double migration){
		params = new Params();
		params.popSize = 70;
		params.maxGenerations = 220;
		params.mutation = 0.45;
		params.crossover = 0.30;
		params.elitists = 0.20;
		params.migration = migration;
		params.migrationFreq = 50;
		params.rand = new Random(254);
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