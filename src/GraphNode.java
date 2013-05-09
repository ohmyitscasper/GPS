import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is my graph node class
 * 
 * @author Vanshil Shah vs2409
 *
 */
public class GraphNode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String city;
	private String state;
	private double latitude;
	private double longitude;
	private int ID;
	private transient ArrayList<GraphNode> adjList;	//The list of outgoing connections
	private transient ArrayList<GraphNode> incomingList;	//The list of incoming connections
	private transient ArrayList<Edge> outgoingEdgeList;
	private double distance;
	private int cost;
	private boolean known;
	public GraphNode path;

	public GraphNode(String city, String state, double latitude, double longitude, int ID) {
		this.city = city;
		this.state = state;
		this.latitude = latitude;
		this.longitude = longitude;
		this.ID = ID;
		adjList = new ArrayList<GraphNode>();
		incomingList = new ArrayList<GraphNode>();
		outgoingEdgeList = new ArrayList<Edge>();
	}

	public void addAdjacency(GraphNode endPt, Edge e) {
		adjList.add(endPt);
		outgoingEdgeList.add(e);
	}
	
	public void addIncomingAdjacency(GraphNode beginPt) {
		incomingList.add(beginPt);
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
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void setKnown(boolean known) {
		this.known = known;
	}
	
	public boolean getKnown() {
		return known;
	}
	
	public ArrayList<GraphNode> incomingConnections() {
		return incomingList;
	}
	
	public ArrayList<GraphNode> outgoingConnections() {
		return adjList;
	}
	
	public ArrayList<Edge> outgoingEdges() {
		return outgoingEdgeList;
	}	
 }
