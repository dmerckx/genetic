package representations;

public class Edge {

	private int begin;
	private int end;
	
	public Edge(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}
	
	public int getBegin() {
		return begin;
	}
	
	public int getEnd() {
		return end;
	}
	
	public String toString() {
		return "begin: " + begin + " end: " + end;
	}
	
}