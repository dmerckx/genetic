package main;

public class Timer {

	private long time = 0;
	
	private long rankingTime = 0;
	private long selectionTime = 0;
	private long crossTime = 0;
	private long mutationTime = 0;
	private long insertTime = 0;
	private long loopDet = 0;
	
	public void start(){
		time = System.currentTimeMillis();
	}
	
	public void addRankingTime(){
		rankingTime += System.currentTimeMillis() - time;
		time = System.currentTimeMillis();
	}
	
	public void addSelectionTime(){
		selectionTime += System.currentTimeMillis() - time;
		time = System.currentTimeMillis();
	}
	
	public void addCrossTime(){
		crossTime += System.currentTimeMillis() - time;
		time = System.currentTimeMillis();
	}
	
	public void addMutationTime(){
		mutationTime += System.currentTimeMillis() - time;
		time = System.currentTimeMillis();
	}
	
	public void addInsertTime(){
		insertTime += System.currentTimeMillis() - time;
		time = System.currentTimeMillis();
	}
	
	public void addLoopdetTime(){
		loopDet += System.currentTimeMillis() - time;
		time = System.currentTimeMillis();
	}
	
	public void print(){
		double total = rankingTime + selectionTime + crossTime + mutationTime + insertTime + loopDet;
		
		
		int rankPerc = (int)(rankingTime / total * 100 + 0.5);
		int selectionPerc = (int) (selectionTime / total* 100+ 0.5);
		int crossPerc = (int) (crossTime / total* 100+ 0.5);
		int mutationPerc = (int) (mutationTime / total* 100+ 0.5);
		int insertPerc = (int) (insertTime / total* 100+ 0.5);
		int loopdetPerc = (int) (loopDet / total* 100+ 0.5);
		
		System.out.println("--------------------");
		System.out.println("Total time: " + total);
		System.out.println("--------------------");
		
		System.out.println("Ranking: " + rankingTime + "   |   " + rankPerc);
		System.out.println("Selection: " + selectionTime + "   |   " + selectionPerc);
		System.out.println("Cross: " + crossTime + "   |   " + crossPerc);
		System.out.println("Mutation: " + mutationTime + "   |   " + mutationPerc);
		System.out.println("Insertion: " + insertTime + "   |   " + insertPerc);
		System.out.println("Loopdet: " + loopdetPerc + "   |   " + loopdetPerc);
		System.out.println("--------------------");
	}
	
}
