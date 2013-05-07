import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;
/**
 * This class is mainly used for IO to and from files
 * Functionality so far:
 * 	- Loading a text file into the system
 *	- Also does the creation and loading of the graphs
 *
 * @author Vanshil Shah vs2409
 *
 */
public class IO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
			System.out.println("Cities Loaded\n");
			System.out.println("Adding random edges");
			g.clearEdges();
			g.addRandomEdges();
			return g;
		}
		catch(FileNotFoundException e) {
			//If the file name is bad, return the original object.
			return g;
		}

	}
}
