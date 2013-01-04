package plots.islands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import main.CommunicationModel;
import main.GA;
import main.History;
import main.IslandsGA;
import main.Problem;
import main.crossover.EdgeRecombination;
import main.insertion.FBI;
import main.mutation.ExchangeMutator;
import main.rankers.LineairRanker;
import main.selectors.SUS;
import params.Params;
import representations.path.Path;
import util.ProblemGenerator;
import factory.PathFactory;

public class Islands {

	public static Problem problem;
	
	private static final int NR_GENS = 450;
	private static final int NR_TIMES = 2;
	
	public static void main(String[] args) {
		generate(0.0, "Ref");
		generate(0.4, "Circ");
		CommunicationModel.RANDOM_TARGET = true;
		generate(0.4, "Rand");
	}
	
	public static void generate(double migration, String type){
		problem = ProblemGenerator.generate("../genetic/datafiles/xqf131.tsp");
		Random seedMaker = new Random(63);
		
		List<History>[] islandHistories = new List[NR_TIMES];
		
		GA<?>[] gas = new GA<?>[12];
		for(int i = 0; i < 12; i++){
			Params params = getPathParams(migration);
			params.rand = new Random(seedMaker.nextLong()); 
			gas[i] = createPathGA(params);
		}
			
		History history = new History();
		IslandsGA mainGA = new IslandsGA(problem, gas);
		
		for(int t = 0; t < NR_TIMES; t++){
			islandHistories[t] = mainGA.run(problem, history);
			System.out.println(type + " run " + t + " completed");
		}
		
		System.out.println(type + " done");
		
		try {
			FileWriter writer = new FileWriter(new File("../genetic/plots/islands" + type));
			
			for(int i = 0; i < NR_GENS; i++){
				double bestTotal = 0;
				double bestMeanTotal = 0;
				double bestWorstTotal = 0;
				for(List<History> run: islandHistories){
					double best = Double.POSITIVE_INFINITY;
					double bestMean = 0;
					double bestWorst = 0;
					for(History island: run){
						double val = island.bestList.get(i);
						best = Math.min(best, val);
						bestMean += val;
						bestWorst = Math.max(bestWorst, val);	
					}
					bestMean /= run.size();
					
					bestTotal += best;
					bestMeanTotal += bestMean;
					bestWorstTotal += bestWorst;
				}
				bestTotal /= NR_TIMES;
				bestMeanTotal /= NR_TIMES;
				bestWorstTotal /= NR_TIMES;
				
				writer.write(bestTotal + " " + bestMeanTotal + " " + bestWorstTotal + "\r\n");
			}
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static Params getPathParams(double migr){
		Params params = getGeneralParams(migr);
		params.mutation = 0.25;
		params.crossover = 0.75;
		return params;
	}
	
	public static Params getGeneralParams(double migr){
		Params params = new Params();
		params.popSize = 100;
		params.maxGenerations = NR_GENS;
		params.elitists = 0.20;
		params.migration = migr;
		params.migrationFreq = 50;
		return params;
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