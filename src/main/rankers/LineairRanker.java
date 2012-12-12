package main.rankers;

import java.util.ArrayList;
import java.util.List;

import main.RankedChrom;
import representations.Chromosome;

public class LineairRanker<R extends Chromosome> implements Ranker<R> {

	private int pressure;

	public LineairRanker() {
		this(2);
	}
	
	public LineairRanker(int selectivePressure) {
		this.pressure = selectivePressure;
	}
	
	
	@Override
	public List<RankedChrom<R>> rank(List<R> unrankedPop) {
		List<RankedChrom<R>> ranked = new ArrayList<RankedChrom<R>>();
		
		int size = unrankedPop.size();
		for(int i = 0; i < size; i++){
			//least fit individual has Pos=1
			//the fittest individual Pos=Nind
			//http://www.geatbx.com/docu/algindex-02.html#P240_15119
			int pos = size-i;
			int fit = 2 - pressure + 2 * (pressure-1) * (pos-1) / (size-1); 
			ranked.add(new RankedChrom<R>(fit, unrankedPop.get(i)));
		}
		
		return ranked;
	}
}
