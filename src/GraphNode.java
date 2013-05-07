/**
 * This is my graph node class
 * 
 * @author Vanshil Shah vs2409
 *
 */
public class GraphNode {
	private String city;
	private String state;
	private double latitude;
	private double longitude;
	private int ID;
	private AdjacencyList<GraphNode> adjList;

	public GraphNode(String city, String state, double latitude, double longitude, int ID) {
		this.city = city;
		this.state = state;
		this.latitude = latitude;
		this.longitude = longitude;
		this.ID = ID;
		adjList = new AdjacencyList<GraphNode>();
	}

	public void addAdjacency(GraphNode endPt, int weight) {
		adjList.insert(endPt);
	}
}
