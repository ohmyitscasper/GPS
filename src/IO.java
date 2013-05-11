import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is mainly used for IO to and from files
 * as well as to and from the graph
 * I decided to separate the functionality so that I can have an easier time with making the GUI
 * @author Vanshil Shah vs2409
 *
 */
public class IO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Write to a serialized file
	 * @param g	the graph to write
	 * @param fileName	the name of the file to be opened.
	 */
	public static boolean write(MyGraph g, String fileName) {
		boolean write = false;
		try
		{
			FileOutputStream fileOut = new FileOutputStream(fileName);
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(g);
	        out.close();
	        fileOut.close();
	        write = true;
	     }catch(IOException i)
	     {
	    	 System.out.println("Write error.");
	    	 i.printStackTrace();
	     }
		return write;
	}
	
	/**
	 * Read from a serialized file.
	 * @param fileName
	 * @return
	 */
	public static MyGraph read(String fileName) {
		try {
			MyGraph graph;
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			graph = (MyGraph) in.readObject();
			in.close();
			fileIn.close();
			return graph;
		} catch(IOException i) {
			System.out.println("Read error.");
		} catch(ClassNotFoundException c) {
			System.out.println("Class not found");
		}
		return null;
	}

	/**
	 * Loads a file into the graph.
	 * @param g
	 * @param n
	 * @param fileName
	 * @return
	 */
	public static MyGraph loadFile(MyGraph g, int o, String fileName) {
		Scanner fileReader;
		File input;
		int totalCities, readInCities =0;
		String firstLine, cityState, lat, lon, city, state;
		double latitude, longitude;
		GraphNode n;
		
		if(o==0) 
			g.deleteAllData();
		else
			readInCities=g.getTotalNodes();

		input = new File(fileName);
		
		//This try catch block exists to prevent against failure from reading in a bad file name
		try {
			fileReader = new Scanner(input);
			firstLine = fileReader.nextLine();
			totalCities = Integer.parseInt(firstLine);
			g.updateTotalNodes(totalCities);
			
			//This for loop is where the magic happens. The file gets read and each city is put into the Graph
			for(int a = 0; a<totalCities; a++)  {
				cityState = fileReader.nextLine();
				lat = fileReader.nextLine();
				lon = fileReader.nextLine();
				latitude = Double.parseDouble(lat);
				longitude = Double.parseDouble(lon);

				//If the city name contains a comma, then there is a state
				if(cityState.contains(",")) {
					String[] separate = cityState.split(",");
					city = separate[0];
					state = separate[1];
					state = state.substring(1);
				}
				//If not, then there is no state so the city name should be repeated for the state
				else {
					city = cityState;
					state = cityState;
				}

				//Create a new GraphNode object with the attributes.
				n = new GraphNode(city, state, latitude, longitude, a+readInCities);
				
				//Add the node to the graph
				g.addNode(n);
			}
			g.clearEdges();
			g.addRandomEdges();
			return g;
		}
		catch(FileNotFoundException e) {
			//If the file name is bad, return the original object.
			return null;
		}

	}
	
	/**
	 * Does the IO for searching the state
	 * @param g the graph object to be searched.
	 */
	public static ArrayList<GraphNode> stateSearch(MyGraph g, String state) {
		return g.searchByState(state);	//Get the arraylist of states from graph
	}
	
	/**
	 * Does the IO for searching for the city
	 * @param g the graph to be searched.
	 * @return 
	 */
	public static ArrayList<GraphNode> citySearch(MyGraph g, String city) {
		return g.searchCity(city);
	}
	
	/**
	 * Sets the current city in the graph
	 * @param g the graph
	 * @return the node that contains the current city.
	 */
	public static GraphNode setCurrentCity(MyGraph g, int n) {
		return g.getVertex(n);
	}
	
	/**
	 * Prints the n closest cities
	 * @param g
	 * @param currentCity
	 * @return 
	 */
	public static ArrayList<GraphNode> nClosest(MyGraph g, GraphNode currentCity, int n) {
		return g.nClosest(currentCity, n);
	}
	
	/**
	 * Prints the y closest cities by breadth.
	 * @param g
	 * @param currentCity
	 * @return 
	 */
	public static ArrayList<GraphNode> yClosest(MyGraph g, GraphNode currentCity, int y) {
		return g.yClosest(currentCity, y);
	}
	
	
	/**
	 * Finds the shortest path by calling the dijkstra function in graph
	 * @param g
	 * @param currentCity
	 */
	public static ArrayList<GraphNode> shortestPath(MyGraph g, GraphNode currentCity, String cityInput) {
		ArrayList<GraphNode> cities = g.searchCity(cityInput);
		if(cities.size()!=0) {
			g.dijkstra(currentCity);
		}
		return cities;
	}
	
	/**
	 * A function that just prints out the information of the cities
	 * @param v
	 */
	public static void printCityInfo(GraphNode v) {
		System.out.println("City " + v.getCity() + " is in state " + v.getState() + " has ID " + v.getID());
		System.out.println("Latitude: " + v.getLat() + " Longitude: " + v.getLong());
		System.out.println("Num of Incoming edges: " + v.incomingConnections().size() + 
						   " Num of Outgoing edges: " + v.outgoingConnections().size() + "\n");
	}
}
