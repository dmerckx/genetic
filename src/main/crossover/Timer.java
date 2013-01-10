package main.crossover;

public class Timer {

	public Timer() {
		
	}
	
	private long begin = 0;
	private long start;
	private long constructionTime = 0;
	private long removeOcc = 0;
	private long removeUnv = 0;
	private long addChoose = 0;
	private double nb = 1;
	private long total = 0;
	
	
	public void start() {
		start = System.currentTimeMillis();
		begin = System.currentTimeMillis();
//		nb++;
	}
	
	public void addConstructionMap() {
		constructionTime += System.currentTimeMillis() - (start);
		start = System.currentTimeMillis();
	}
	
	public void addRemoveOccurences() {
		removeOcc += System.currentTimeMillis() - (start);
		start = System.currentTimeMillis();
	}

	public void addRemoveUnvisited() {
		removeUnv += System.currentTimeMillis()-start;
		start = System.currentTimeMillis();
	}

	public void addChooseCity() {
		addChoose += System.currentTimeMillis() - start;
		start = System.currentTimeMillis();
	}
	
	public void addTotal() {
		total += System.currentTimeMillis()-begin;
	}
	
	public void print() {
		double con = constructionTime/(nb*1000);
		double remO = removeOcc/(nb*1000);
		double remU = removeUnv/(nb*1000);
		double add = addChoose/(nb*1000);
		double tot = (constructionTime+removeOcc+removeUnv+addChoose)/(nb*1000);
		double totalconv = total/(nb*1000);
		System.out.println("time constructing map: " + con);
		System.out.println("time removing occ: " + remO);
		System.out.println("time removing unvisited: " + remU);
		System.out.println("time choosing cities: " + add);
		System.out.println("total time: " + totalconv + " sum= " + tot);
	}
	
}
