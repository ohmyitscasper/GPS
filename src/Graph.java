import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
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
	private final int REALLYHIGHDISTANCE = 10000000;
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

	public GraphNode getVertex(int idx) {
		return vertices.get(idx);
	}
	
	public ArrayList<GraphNode> getVertices() {
		return vertices;
	}
	
	public Edge getEdge(int idx) {
		return edges.get(idx);
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
				while(v.outgoingConnections().contains(end)) {
					//The edge will be a duplicate
					city = r.nextInt(totalNodes);	//Generate a new random city number
					end = vertices.get(city);	
				}
				
				Edge e = new Edge(v, end, weight);	//Create a new edge
				addEdge(e);							//Add it to the list of edges
				end.addIncomingAdjacency(v);		//Add the current vertex to the incoming connections of end
				v.addAdjacency(end, e);				//Add the end vertex to the outgoing connections
			}
		}
		
		/* 
		 * If a city doesn't have any incoming edges, add one incoming edge from a random node in the graph.
		 * 
		 */
		for(GraphNode v:vertices) {
			//If there is an incoming edge, meaning the size of the incoming adj list is not 0
			if(v.incomingConnections().size()>0) {
				continue;
			}
			city = r.nextInt(totalNodes);	//Generate a random city number
			weight = r.nextInt(RANDWEIGHT) + 100; 	//Generate the random weight
			
			GraphNode begin = vertices.get(city);
			Edge e = new Edge(begin, v, weight);
			addEdge(e);
			begin.addAdjacency(v, e);
			v.addIncomingAdjacency(begin);
		}
	}
	
	/**
	 * This is just a method that I put in to make sure that my graph is properly created 
	 * and that every single vertex has incoming and outgoing connections
	 */
	public void checkAllEdgesExist() {
		for(GraphNode v:vertices) {
			if(v.incomingConnections().size()==0 || v.outgoingConnections().size()==0) {
				System.out.println(v.getID());
				
			}
		}
	}
	
	/**
	 * Does the computation for d. 
	 * Searches sequentially through the ArrayList of vertices to find out which cities
	 * have the matching state
	 * @param state the state to be matched
	 * @return an ArrayList of cities that are in the state
	 */
	public ArrayList<GraphNode> searchByState(String state) {
		ArrayList<GraphNode> cities = new ArrayList<GraphNode>();
		for(GraphNode v:vertices) {
			if(v.getState().equals(state)) {
				cities.add(v);
			}
		}
		return cities;
		
	}
	
	public ArrayList<GraphNode> searchCity(String city) {
		ArrayList<GraphNode> cities = new ArrayList<GraphNode>();
		for(GraphNode v:vertices) {
			if(v.getCity().equals(city)) {
				cities.add(v);
			}
		}
		return cities;
	}
	
	/**
	 * Finds the n closest cities to the current city by using a priority queue.
	 * First finds the distances between all of the cities and the current city and places
	 * them in a priority queue. Then one by one peeks the priority queue n times to build
	 * the arraylist that contains the n closest.
	 * Basically uses a heap sort
	 * @param currentCity
	 * @param n
	 * @return
	 */
	public ArrayList<GraphNode> nClosest(GraphNode currentCity, int n) {
		ArrayList<GraphNode> nClosest = new ArrayList<GraphNode>();
		double tempDistance;
		GraphNode temp;
		
		//Creating a comparator that can be used to compare distances from the current city
		//to either arg0 or arg1
		Comparator<GraphNode> c = new Comparator<GraphNode>() {
			public int compare(GraphNode arg0, GraphNode arg1) {
				// TODO Auto-generated method stub
				return (int)(arg0.getDistance() - arg1.getDistance());
			}	
		};
		PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>(totalNodes, c);
		
		//Go through each of the vertices and set a distance to the current city.
		for(GraphNode v:vertices) {
			//If we reach our currentCity, then set an arbitrary distance to itself
			if(v==currentCity) {
				currentCity.setDistance(REALLYHIGHDISTANCE);
				continue;
			}
			
			//Otherwise we calculate the distance between the current city and v
			tempDistance = distance(currentCity, v);
			v.setDistance(tempDistance);	//Set the distance in the GraphNode object
			queue.add(v);					//Add this object to the queue
		}
		
		for(int a = 0; a<n; a++) {
			temp = queue.poll();
			if(temp==null)
				break;
			nClosest.add(temp);
		}
		return nClosest;
	}
	
	public GraphNode setRandomCurrentCity() {
		Random r = new Random();
		int rand = r.nextInt(totalNodes);
		return vertices.get(rand);
	}
	
	public ArrayList<GraphNode> yClosest(GraphNode currentCity, double y) {
		ArrayList<GraphNode> yClosest = new ArrayList<GraphNode>();
		double tempDistance;
		
		//Figure out all of the distances from the current city to v
		for(GraphNode v:vertices) {
			//If we reach our currentCity, then set an arbitrary distance to itself
			if(v==currentCity) {
				currentCity.setDistance(REALLYHIGHDISTANCE);
				continue;
			}
			
			//Otherwise we calculate the distance between the current city and v
			tempDistance = distance(currentCity, v);
			v.setDistance(tempDistance);	//Set the distance in the GraphNode object
		}
		
		//Traverse through the list of vertices again and if
		//the distance is less than y, put it in the array list.
		for(GraphNode v:vertices) {
			if(v.getDistance()<=y) {
				yClosest.add(v);
			}
		}
		return yClosest;
	}
	
	public void dijkstra(GraphNode currentCity) {
		for(GraphNode v:vertices) {
			v.setDistance(REALLYHIGHDISTANCE);
			v.setKnown(false);
		}
		currentCity.setDistance(0);
		currentCity.setKnown(true);
		ArrayList<GraphNode> adjList = currentCity.outgoingConnections();
		for(GraphNode temp: adjList) {
			if(currentCity.getDistance()+temp.getDistance())
			temp.setDistance(distance)
		}
	}
	
	public double distance(GraphNode a, GraphNode b) {
		double distance;
		double aLat = a.getLat();
		double aLong = a.getLong();
		double bLat = b.getLat();
		double bLong = b.getLong();
		double x = 69.1 * (bLat-aLat);
		double y = 69.1 * (bLong-aLong) * Math.cos(aLat/57.3);
		distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		return distance;
	}
	
}
