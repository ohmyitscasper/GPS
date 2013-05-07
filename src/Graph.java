import java.util.ArrayList;

/**
 * This is my graph class
 * This class will be the class that interfaces with the vertices and edges of the graph. 
 * It will be the workhorse of the program because this is where all of the algorithms will.
 * @author Vanshil Shah vs2409
 *
 */
public class Graph {
	
	private int totalNodes;
	private ArrayList<Edge> edges;
	private ArrayList<GraphNode> vertices;
	
	public Graph() {
		edges = new ArrayList<Edge>();
		vertices = new ArrayList<GraphNode>();
	}
	public void addNode(GraphNode node) {
		vertices.add(node);
	}
	
	
}
