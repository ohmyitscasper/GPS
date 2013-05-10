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
	public static void write(Graph g, String fileName) {
		try
		{
			FileOutputStream fileOut = new FileOutputStream(fileName);
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(g);
	        out.close();
	        fileOut.close();
	     }catch(IOException i)
	     {
	    	 System.out.println("Write error.");
	    	 i.printStackTrace();
	     }
	}
	
	/**
	 * Read from a serialized file.
	 * @param fileName
	 * @return
	 */
	public static Graph read(String fileName) {
		try {
			Graph graph;
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			graph = (Graph) in.readObject();
			System.out.println("Graph g contains " + graph.getTotalNodes() + " total nodes");
			for(GraphNode v: graph.getVertices()) {
				System.out.println("City: " + v.getCity());
			}
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
	 * Loading a text file
	 * @param g
	 * @return
	 */
	public static Graph loadFile(Graph g) {
		Scanner fileReader, in;
		File input;
		int totalCities, readInCities =0;
		String firstLine, cityState, lat, lon, city, state;
		double latitude, longitude;
		GraphNode n;
		in = new Scanner(System.in);

		System.out.println("Would you rather:\na. Load a file and erase all of the data in the graph currently" +
				"\nb. Load a file and append all of the data in the file to the existing graph" +
				"\n Please enter either a or b.");
		String option = in.nextLine();

		//If the user wants to delete everything from the graph, go ahead and do it.
		if(option.equals("a")) {
			g.deleteAllData();
		}
		else 
			readInCities=g.getTotalNodes();

		System.out.println("Please enter the file name.");
		input = new File(in.nextLine());
		
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
			System.out.println("Cities Loaded");
			System.out.println("Adding random edges\n\n");
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
	public static void stateSearch(Graph g) {
		String state;
		Scanner in = new Scanner(System.in);
		ArrayList<GraphNode> cities;
		
		System.out.println("\nPlease enter the state that you would like to search for.");
		state = in.nextLine();
		
		/*Sanitize the state*/
		
		cities = g.searchByState(state);	//Get the arraylist of states from graph
		
		//Makes sure that the state actually exists in the arraylist.
		while(cities.isEmpty()) {
			System.out.println("\nThe state cannot be found. Please enter another state or simply type m to return to the menu.");
			state = in.nextLine();
			if(state.equals("m")) {
				return;
			}
			else
				cities = g.searchByState(state);
		}
		
		//Print out all of the relevant information
		for(GraphNode v:cities) {
			printCityInfo(v);
		}
	}
	
	/**
	 * Does the IO for searching for the city
	 * @param g the graph to be searched.
	 */
	public static void citySearch(Graph g) {
		String cityInput;
		Scanner in = new Scanner(System.in);
		ArrayList<GraphNode> cities; 
		
		System.out.println("\nPlease enter the city that you would like to search for.");
		cityInput = in.nextLine();
		
		cities = g.searchCity(cityInput);
		if(cities.size()==0) {
			System.out.println("\nNo cities found with name " + cityInput + "\n");
			return;
		}
		for(GraphNode v:cities) {
			printCityInfo(v);
		}
	}
	
	/**
	 * Sets the current city in the graph
	 * @param g the graph
	 * @return the node that contains the current city.
	 */
	public static GraphNode setCurrentCity(Graph g) {
		int ID=0;
		String input;
		Scanner in  = new Scanner(System.in);
		GraphNode currentCity;
		boolean numb = true;
		
		
		//Makes sure that the user inputs something that makes sense
		do {
			System.out.println("\nPlease enter the ID (between 0 and " + g.getTotalNodes() + ") of the city you want to set to the current city." +
					"\nOr simply type m if you want to return to the menu");
			input = in.nextLine();
			if(input.equals("m"))
				return null;
			try {
				ID=Integer.parseInt(input);
				if(ID<0 || ID>g.getTotalNodes()) {
					numb = false;
					continue;
				}
				numb = true;
			}
			catch (NumberFormatException e) {
				numb = false;
			}
		} while(!numb);
		
		currentCity = g.getVertex(ID);
		
		return currentCity;
	}
	
	/**
	 * Prints the n closest cities
	 * @param g
	 * @param currentCity
	 */
	public static void nClosest(Graph g, GraphNode currentCity) {
		
		int n=0;
		String input;
		Scanner in  = new Scanner(System.in);
		boolean numb = true;

		//Makes sure that the user inputs something that makes sense
		do {
			System.out.println("\nPlease enter a number n to find n closest cities to the current city" +
					"\nOr simply type m if you want to return to the menu");
			input = in.nextLine();
			if(input.equals("m"))
				return;
			try {
				n=Integer.parseInt(input);
				numb = true;
			}
			catch (NumberFormatException e) {
				numb = false;
			}
		} while(!numb);
		
		//Calls the method in graph that returns the arraylist of n closest.
		ArrayList<GraphNode> closest = g.nClosest(currentCity, n);
		for(GraphNode v:closest) {
			printCityInfo(v);
		}
	}
	
	/**
	 * Prints the y closest cities by breadth.
	 * @param g
	 * @param currentCity
	 */
	public static void yClosest(Graph g, GraphNode currentCity) {
		int y = 0;
		String input; 
		Scanner in = new Scanner(System.in);
		boolean numb = true;
		
		do {
			System.out.println("\nPlease enter a weight y to find all cities within weight y of the closest city" +
					"\nOr simply type m if you want to return to the menu");
			input = in.nextLine();
			if(input.equals("m"))
				return;
			try {
				y=Integer.parseInt(input);
				numb = true;
			}
			catch (NumberFormatException e) {
				numb = false;
			}
		} while(!numb);
		
		ArrayList<GraphNode> yClosest = g.yClosest(currentCity, y);
		if(yClosest.isEmpty()) 
			System.out.println("None within.");
		for(GraphNode v:yClosest) 
			printCityInfo(v);
	}
	
	
	/**
	 * Finds the shortest path by calling the dijkstra function in graph
	 * @param g
	 * @param currentCity
	 */
	public static void shortestPath(Graph g, GraphNode currentCity) {
		String cityInput;
		Scanner in = new Scanner(System.in);
		ArrayList<GraphNode> cities; 
		
		System.out.println("\nPlease enter the city that you would like to search for.");
		cityInput = in.nextLine();
		cities = g.searchCity(cityInput);
		if(cities.size()==0) {
			System.out.println("\nNo cities found with name " + cityInput + "\n");
			return;
		}
		g.dijkstra(currentCity);
		printPath(cities.get(0));	//Just print the path to the first city with this name
	}
	
	/**
	 * A helper function that recursively prints the path
	 * @param city
	 */
	public static void printPath(GraphNode city) {
		if(city.path!=null) {
			printPath(city.path);
			System.out.println(" to ");
		}
		System.out.println(city.getCity());
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
