package main.rankers;

import java.util.List;

import main.RankedChrom;
import representations.Chromosome;

public interface Ranker<R extends Chromosome>{

	List<RankedChrom<R>> rank(List<R> unrankedPop);
}
