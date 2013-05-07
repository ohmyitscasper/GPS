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
	private AdjacencyList<GraphNode> adjList;	//The list of outgoing connections
	private AdjacencyList<GraphNode> incomingList;	//The list of incoming connections

	public GraphNode(String city, String state, double latitude, double longitude, int ID) {
		this.city = city;
		this.state = state;
		this.latitude = latitude;
		this.longitude = longitude;
		this.ID = ID;
		adjList = new AdjacencyList<GraphNode>();
		incomingList = new AdjacencyList<GraphNode>();
	}

	public void addAdjacency(GraphNode endPt) {
		adjList.insert(endPt);
	}
	
	public void addIncomingAdjacency(GraphNode beginPt) {
		incomingList.insert(beginPt);
	}
	
	public String getCity() {
		return city;
	}
	
	public String getState() {
		return state;
	}
	
	public double getLat() {
		return latitude;
	}
	
	public double getLong() {
		return longitude;
	}
	
	public int getID() {
		return ID;
	}
	
	public AdjacencyList<GraphNode> incomingConnections() {
		return incomingList;
	}
	
	public AdjacencyList<GraphNode> outgoingConnections() {
		return adjList;
	}

}
