package main.insertion;

import java.util.List;

import main.RankedChrom;
import params.Params;
import representations.Chromosome;

//TODO: fix this implementation
public class Nico<R extends Chromosome> extends ReInsertor<R>{

	private Params params;
	private double del;
	private double keep;
	
	public Nico(Params params) {
		this(params, 0.1d, 0.70d);
	}
	
	public Nico(Params params, double del, double keep) {
		super(params);
		this.del = del;
		this.keep = keep;
	}
	
	/*
	@Override
	public List<R> reinsert(List<R> oldPop, List<R> children) {
		
		List<R> newPop = new ArrayList<R>();
		
		oldPop.addAll(children);
		Collections.sort(oldPop);
		
		int min = (int) (del * oldPop.size());
		int max = (int) (keep * oldPop.size());
		
		newPop.addAll(oldPop.subList(max, oldPop.size()));
		
		LinkedList<Integer> available = new LinkedList<Integer>();
		for(int i = min; i < max; i++){
			available.add(i);
		}
		
		if(newPop.size() > params.popSize) throw new IllegalStateException("too big");
		while(newPop.size() < params.popSize){
			int i = params.rand.nextInt(available.size());
			newPop.add(oldPop.get(available.get(i)));
			available.remove(i);
		}
		
		return newPop;
	}*/

	@Override
	public List<R> selectParentSurvivors(List<RankedChrom<R>> oldPop, int nrSurvivors) {
		throw new RuntimeException();	//Currently can not be used
	}
}
