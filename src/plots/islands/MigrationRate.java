package plots.islands;

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

	public static Problem problem;
	
	public static void main(String[] args) {
		problem = ProblemGenerator.generate("../genetic/datafiles/rondrit070.tsp");
		Random seedMaker = new Random(71);
		
		double[] migr = new double[]{1.0};
		double[] results4 = new double[migr.length];
		double[] results8 = new double[migr.length];
		
		for(int m = 0; m < migr.length; m++){
			
			GA<?>[] gas = new GA<?>[2];
			for(int i = 0; i < 2; i++){
				Params params = setGAParams(migr[m]);
				params.rand = new Random(seedMaker.nextLong()); //23 is een goeie
				gas[i] = createPathGA(params);
			}
			
			History history1 = new History();
			IslandsGA mainGA = new IslandsGA(problem, gas);
			mainGA.run(problem, history1, 1);
			
			System.out.println("result for migration: " + migr[m]);
			for(int i = 0; i < 52; i++){
				System.out.println(i + ": " + history1.bestList.get(i));
			}
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
	
	public static Params setGAParams(double migration){
		Params params = new Params();
		params.popSize = 100;
		params.maxGenerations = 90;
		params.mutation = 0.45;
		params.crossover = 0.0;
		params.elitists = 0.20;
		params.migration = migration;
		params.migrationFreq = 50;
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