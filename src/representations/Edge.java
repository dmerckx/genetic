package representations;

public class Edge implements Comparable<Edge>{

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

	@Override
	public int compareTo(Edge edge) {
		return (edge.getBegin()==getBegin() && edge.getEnd() == getEnd()) ? 0 : -1;
	}
	
	@Override
	public int hashCode() {
		int hash = 23;
		hash = hash * 31 + begin;
		hash = hash * 31 + end;
		return hash;
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof Edge ? compareTo((Edge) other) == 0 : false;
	}
	
}
