package plots.islands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import main.CommunicationModel;
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
		generate("circ");
		
		CommunicationModel.RANDOM_TARGET = true;
		generate("rand");
	}
	
	public static void generate(String type){
		problem = ProblemGenerator.generate("../genetic/datafiles/xqf131.tsp");
		Random seedMaker = new Random(63);
		
		double[] migr = new double[]{0.0, 0.05, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5, 0.55, 0.6, 0.65, 0.7, 0.75, 0.8, 0.85, 0.9, 0.95, 1.0};
		int[] islands = new int[]{4, 8, 12};
		String results4Adj = "";
		String results8Adj = "";
		String results12Adj = "";
		
		String results4Path = "";
		String results8Path = "";
		String results12Path = "";
		
		for(int m = 0; m < migr.length; m++){
			for(int nrIslands: islands){
				GA<?>[] gas = new GA<?>[nrIslands];
				for(int i = 0; i < nrIslands; i++){
					Params params = getAdjParams(migr[m]);
					params.rand = new Random(seedMaker.nextLong()); //23 is een goeie
					gas[i] = createAdjGA(params);
				}
				
				History history = new History();
				IslandsGA mainGA = new IslandsGA(problem, gas);
				mainGA.run(problem, history,20);
				
				System.out.println("All " + nrIslands + " islands adj: " + migr[m]);
				history.printShort();
				System.out.println("----------------------------");
				
				double best = history.getLastBest();
				switch(nrIslands){
					case 4: results4Adj += best + " "; break;
					case 8: results8Adj += best + " "; break;
					case 12: results12Adj += best + " "; break;
					default: throw new IllegalStateException();
				}
			}
			for(int nrIslands: islands){
				GA<?>[] gas = new GA<?>[nrIslands];
				for(int i = 0; i < nrIslands; i++){
					Params params = getPathParams(migr[m]);
					params.rand = new Random(seedMaker.nextLong()); //23 is een goeie
					gas[i] = createPathGA(params);
				}
				
				History history = new History();
				IslandsGA mainGA = new IslandsGA(problem, gas);
				mainGA.run(problem, history, 10);
				
				System.out.println("All " + nrIslands + " islands path: " + migr[m]);
				history.printShort();
				System.out.println("----------------------------");
				
				double best = history.getLastBest();
				switch(nrIslands){
					case 4: results4Path += best + " "; break;
					case 8: results8Path += best + " "; break;
					case 12: results12Path += best + " "; break;
					default: throw new IllegalStateException();
				}
			}
		}

		try {
			FileWriter writerAdj = new FileWriter(new File("../genetic/plots/migrationAdj" + type));
			FileWriter writerPath = new FileWriter(new File("../genetic/plots/migrationPath" + type));
			
			writerAdj.write(results4Adj + "\r\n");
			writerAdj.write(results8Adj + "\r\n");
			writerAdj.write(results12Adj + "\r\n");
			
			writerPath.write(results4Path + "\r\n");
			writerPath.write(results8Path + "\r\n");
			writerPath.write(results12Path + "\r\n");
			
			writerAdj.close();
			writerPath.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static Params getAdjParams(double migration){
		Params params = getGeneralParams(migration);
		params.mutation = 0.5;
		params.crossover = 0.25;
		return params;
	}
	
	public static Params getPathParams(double migration){
		Params params = getGeneralParams(migration);
		params.mutation = 0.25;
		params.crossover = 0.75;
		return params;
	}
	
	public static Params getGeneralParams(double migration){
		Params params = new Params();
		params.popSize = 50;
		params.maxGenerations = 400;
		params.elitists = 0.20;
		params.migration = migration;
		params.migrationFreq = 40;
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