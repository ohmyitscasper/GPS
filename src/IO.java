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
	public static int loadFile() {
		System.out.println("Please enter the file name.\n");
		Scanner fileReader, in;
		File input;
		int totalCities;
		String firstLine, cityState, lat, lon, city, state;
		double latitude, longitude;
		Graph g = new Graph();
		GraphNode n;
		
		in = new Scanner(System.in);
		input = new File(in.nextLine());
		
		try {
			fileReader = new Scanner(input);
			firstLine = fileReader.nextLine();
			totalCities = Integer.parseInt(firstLine);
			for(int a = 0; a<totalCities; a++)
			{
				cityState = fileReader.nextLine();
				lat = fileReader.nextLine();
				lon = fileReader.nextLine();
				latitude = Double.parseDouble(lat);
				longitude = Double.parseDouble(lon);
				if(cityState.contains(",")) {
					String[] separate = cityState.split(",");
					city = separate[0];
					state = separate[1];
				}
				else {
					city = cityState;
					state = cityState;
				}
				n = new GraphNode(city, state, latitude, longitude, a);
				
			}	
			
			System.out.println("Cities Loaded");
			return 1;
		}
		catch(FileNotFoundException e) {
			return -1;
		}

	}
}
