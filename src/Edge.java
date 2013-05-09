import java.io.Serializable;

public class Edge implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int weight;
	//GraphNode start, end;
	int start, end;

	public Edge(int start, int end, int weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
	}
	
	public int getStartNode() {
		return start;
	}
	
	public int getEndNode() {
		return end;
	}
	
	public int getWeight() {
		return weight;
	}
}