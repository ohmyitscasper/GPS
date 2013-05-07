public class Edge {

	int weight;
	GraphNode start, end;

	public Edge(GraphNode start, GraphNode end, int weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
	}
}