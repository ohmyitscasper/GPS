public class Edge {

	int weight;
	GraphNode start, end;

	public Edge(GraphNode start, GraphNode end, int weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
	}
	
	public GraphNode getStartNode() {
		return start;
	}
	
	public GraphNode getEndNode() {
		return end;
	}
	
	public int getWeight() {
		return weight;
	}
}