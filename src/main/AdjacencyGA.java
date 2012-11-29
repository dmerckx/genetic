package main;

import insertion.Insertor;

import java.util.ArrayList;
import java.util.List;

import params.Params;
import representations.Adjacency;
import selectors.SelectionStrategy;
import crossover.CrossOver;

public class AdjacencyGA extends GA<Adjacency>{

	public AdjacencyGA(Params params, SelectionStrategy<Adjacency> selector,
			CrossOver<Adjacency> crossover, Insertor<Adjacency> insertor) {
		super(params, selector, crossover, insertor);
	}

	@Override
	public List<Adjacency> initPopulation(Problem problem) {
		List<Adjacency> result = new ArrayList<Adjacency>();
		
		for(int i=0; i < problem.size(); i++){
			Adjacency adj = new Adjacency(problem);
			adj.setRandom(params.rand);
			result.add(adj);
		}
		
		return result;
	}
}