import java.util.ArrayList;

/**
 * This is my graph node class
 * 
 * @author Vanshil Shah vs2409
 *
 */
public class GraphNode { //implements Comparator<GraphNode>{
	private String city;
	private String state;
	private double latitude;
	private double longitude;
	private int ID;
	private ArrayList<GraphNode> adjList;	//The list of outgoing connections
	private ArrayList<GraphNode> incomingList;	//The list of incoming connections
	private ArrayList<Edge> outgoingEdgeList;
	private double distance;
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

//	@Override
//	public int compare(GraphNode arg0, GraphNode arg1) {
//		// TODO Auto-generated method stub
//		return (int)(arg0.distance - arg1.distance);
//	}	
 }
