import java.util.ArrayList;
import java.util.Random;

/**
 * This is my graph class
 * This class will be the class that interfaces with the vertices and edges of the graph. 
 * It will be the workhorse of the program because this is where all of the algorithms will.
 * @author Vanshil Shah vs2409
 *
 */
public class Graph {
	private final int RANGE = (8-2)+1;
	private final int RANDWEIGHT = (2000-100)+1;
	private int totalNodes = 0;
	private ArrayList<Edge> edges;
	private ArrayList<GraphNode> vertices;
	
	public Graph() {
		edges = new ArrayList<Edge>();
		vertices = new ArrayList<GraphNode>();
	}
	public void addNode(GraphNode node) {
		vertices.add(node);
	}
	
	/**
	 * Might be redundant
	 * @param e
	 */
	public void addEdge(Edge e) {
		edges.add(e);
	}

	public void updateTotalNodes(int total) {
		totalNodes += total;
	}
	
	public int getTotalNodes() {
		return totalNodes;
	}
	
	public void deleteAllData() {
		vertices.clear();
		edges.clear();
		totalNodes = 0;
	}
	
	public void clearEdges() {
		edges.clear();
	}

	public ArrayList<GraphNode> getVertices() {
		return vertices;
	}
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	
	/**
	 * This method adds the random edges at the beginning of the program
	 * 
	 */
	public void addRandomEdges() {
		int numDirCon;	//Number of directed Connections
		int city;
		int weight;
		Random r = new Random();
		
		/*Goes through each of the vertices and adds edges to them. 
		 * When adding edges the following need to be done:
		 * 	- Calculate the number of directed connections needed
		 * 	- Calculate the random cities to connect to
		 * 	- Calculate the random weight for each edge
		 * 	- Make sure that the adjacency doesn't already exist
		 * 	- Update the Adjacency List of the starting vertex
		 * 	- Update the incoming list of the ending vertex
		 * 	
		 */
		for(GraphNode v:vertices) {
			numDirCon = r.nextInt(RANGE) + 2;	//Generating a random number between 2-8
			for(int a = 0; a<numDirCon; a++) {
				city = r.nextInt(totalNodes);	//Generating a random number for which cities to connect to.
				weight = r.nextInt(RANDWEIGHT) + 100;	//Generating the random weight
				
				GraphNode end = vertices.get(city);		
				
				//Checks if the connection to be created is already an adjacency
				while(v.outgoingConnections().find(end)!=-1) {
					//The edge will be a duplicate
					city = r.nextInt(totalNodes);	//Generate a new random city number
					end = vertices.get(city);	
				}
				
				Edge e = new Edge(v, end, weight);	//Create a new edge
				addEdge(e);							//Add it to the list of edges
				end.addIncomingAdjacency(v);		//Add the current vertex to the incoming connections of end
				v.addAdjacency(end);				//Add the end vertex to the outgoing connections
			}
		}
		
		/* 
		 * If a city doesn't have any incoming edges, add one incoming edge from a random node in the graph.
		 * 
		 */
		for(GraphNode v:vertices) {
			//If there is an incoming edge, meaning the size of the incoming adj list is not 0
			if(v.incomingConnections().getSize()>0) {
				continue;
			}
			city = r.nextInt(totalNodes);	//Generate a random city number
			weight = r.nextInt(RANDWEIGHT) + 100; 	//Generate the random weight
			
			GraphNode begin = vertices.get(city);
			Edge e = new Edge(begin, v, weight);
			addEdge(e);
			begin.addAdjacency(v);
			v.addIncomingAdjacency(begin);
		}
	}
	
	/**
	 * This is just a method that I put in to make sure that my graph is properly created 
	 * and that every single vertex has incoming and outgoing connections
	 */
	public void checkAllEdgesExist() {
		for(GraphNode v:vertices) {
			if(v.incomingConnections().getSize()==0 || v.outgoingConnections().getSize()==0) {
				System.out.println(v.getID());
				
			}
		}
	}
	
}
