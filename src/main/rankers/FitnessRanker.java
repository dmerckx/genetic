package main.rankers;

import java.util.ArrayList;
import java.util.List;

import main.RankedChrom;
import representations.Chromosome;

public class FitnessRanker<R extends Chromosome> implements Ranker<R> {


	public FitnessRanker() {
		
	}
	
	
	@Override
	public List<RankedChrom<R>> rank(List<R> unrankedPop) {
		List<RankedChrom<R>> ranked = new ArrayList<RankedChrom<R>>();
		int size = unrankedPop.size();
		for(int i = 0; i < size; i++){
			ranked.add(new RankedChrom<R>(1/unrankedPop.get(i).getPathLength(), unrankedPop.get(i)));
		}
		
		return ranked;
	}
}
