package plots.reps;

import main.GA;
import main.History;
import main.LoopDetection;
import main.Problem;
import main.crossover.AlternatingEdge;
import main.crossover.CrossOver;
import main.insertion.FBI;
import main.insertion.ReInsertor;
import main.mutation.Mutator;
import main.mutation.SimpleInversionMutator;
import main.rankers.LineairRanker;
import main.rankers.Ranker;
import main.selectors.RWS;
import main.selectors.SUS;
import main.selectors.Selector;
import main.selectors.Tournament;
import params.Params;
import params.TestParams;
import representations.Adjacency;
import util.ProblemGenerator;
import factory.AdjacencyFactory;

public class selectionPlot {

	private static final Params params = new TestParams();
	
	
	
	private static final RWS<Adjacency> rws = new RWS<Adjacency>(params);
	private static final SUS<Adjacency> sus = new SUS<Adjacency>(params);
	private static final Tournament<Adjacency> tournament = new Tournament<Adjacency>(params);
	
	private static final AdjacencyFactory factory = new AdjacencyFactory();
	private static final ReInsertor<Adjacency> insertor = new FBI<Adjacency>(params);
	private static final Mutator<Adjacency> mutator = new SimpleInversionMutator<Adjacency>(params);
	private static final Ranker<Adjacency> ranker = new LineairRanker<Adjacency>();
	private static final LoopDetection<Adjacency> loopDetection = new LoopDetection<Adjacency>();
	
	private static final String problemFilePath = "../genetic/datafiles/rondrit070.tsp";
	private static final String outputRWSFilePath = "../genetic/result/result-adj-rws.txt";
	private static final String outputSUSFilePath = "../genetic/result/result-adj-sus.txt";
	private static final String outputTournamentFilePath = "../genetic/result/result-adj-tournament.txt";
	
	private static final Problem problem = ProblemGenerator.generate(problemFilePath);
	private static final CrossOver<Adjacency> crossover = new AlternatingEdge(params, problem);
	
	public selectionPlot() {
	}
	
	public static void main(String[] args) {
		makePlot();
	}
	
	public static void makePlot() {
		
		History history1 = new History(outputRWSFilePath);
		for (int i = 0; i < 1; i++) {
			createGA1(problem, rws).run(problem, history1);
		}
		
		history1.printShort();
		
		History history2 = new History(outputSUSFilePath);
		for (int i = 0; i < 1; i++) {
			createGA1(problem, sus).run(problem, history2);
		}
		
		history2.printShort();
		
		History history3 = new History(outputTournamentFilePath);
		for (int i = 0; i < 1; i++) {
			createGA1(problem, tournament).run(problem, history3);
		}
		
		history3.printShort();
		
	}
	
	public static GA<Adjacency> createGA1(Problem problem, Selector selector){
		return new GA<Adjacency>(params, factory, selector, crossover, insertor, mutator, ranker, loopDetection);
	}
}
