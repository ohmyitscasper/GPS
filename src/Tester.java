import java.util.Scanner;

/**
 * This class contains the main method of the program. 
 * 
 * @author Vanshil Shah vs2409
 *
 */
public class Tester {

	private static String userMenu = "a. Load a text file into the system.\n" +
			"b. Load a graph from a file\n" +
			"c. Save current graph to file\n" +
			"d. Search for state and list all cities of that state\n" +
			"e. Search for city and display some information about it\n" +
			"f. Set City X as current city (X is between O-N cities)\n" +
			"g. Print Current City\n" +
			"h. Find n closest cities closest to current city using GPS distances\n" +
			"i. Find all cities from current city with directed edge cost less than Y\n" +
			"j. Find shortest path between current city and some target city\n" +
			"k. Quit\n" +
			"Please Enter the letter of the option you would like to choose.";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		String menuChoice;
		char choice;
		GraphNode currentCity = null;
		Graph graph = new Graph();
		String fileName = "mygraph.bin";
		GraphGUI gui = new GraphGUI();
		
		for(;;) {
			System.out.println(userMenu);
			menuChoice = in.next();
			System.out.println(menuChoice);
			
			//Lets do some error checking on the choice that the user inputs. 
			if(menuChoice.length()>1) {
				System.out.println("Please try again with a valid input.\n");
				continue;
			}
			choice = menuChoice.charAt(0);
			
			switch(choice) {
			case 'a': {
				/*Loading a text file into the system*/
				
				//The following line, IO.loadFile(graph) sends a reference of the graph object 
				//to IO and that method fills the graph object in. If unsuccessful, it returns the original
				//graph object which can either be null or have some older data.
				if(IO.loadFile(graph)==null) {
					System.out.println("\nPlease try again\n");
					continue;
				}
			}
				break;
			case 'b':
				/* Loading a serialized file*/
				graph = IO.read(fileName);
				if(graph == null) {
					System.out.println("Read Failed");
					continue;
				}
				break;
			case 'c':
				/*Saving the current graph to a serialized file*/
				IO.write(graph, fileName);
				break;
			case 'd':
				/* Search for state and list all cities of that state*/
				IO.stateSearch(graph);
				break;
			case 'e':
				/* Search for city and display some information about it*/
				IO.citySearch(graph);
				break;
			case 'f':
				/*Set City X as current city (X is between O-N cities)*/
				//What happens right now is that when you go to set the current city but don't, even though one has already been set, 
				//it says it doesn't exist. Figure out how to keep the state.
				currentCity = IO.setCurrentCity(graph);
				break;
			case 'g':
				/*Print Current City*/
				if(currentCity==null) {
					System.out.println("\nCurrent City doesn't exist");
					continue;
				}
				IO.printCityInfo(currentCity);
				break;
			case 'h':
				/*Find n closest cities closest to current city using GPS distances*/
				if(currentCity==null) {
					System.out.println("\nCurrent city doesn't exist. Please choose one first");
					continue;
				}
				IO.nClosest(graph, currentCity);
				break;
			case 'i':
				/*Find all cities from current city with directed edge cost less than Y*/
				if(currentCity==null) {
					currentCity = graph.setRandomCurrentCity();
					System.out.println("\nNo current city set. Current city is now " + currentCity.getCity());
				}
				IO.yClosest(graph, currentCity);
				break;
			case 'j':
				/*Find shortest path between current city and some target city*/
				if(currentCity == null) {
					currentCity = graph.setRandomCurrentCity();
					System.out.println("\nNo current city set. Current city is now " + currentCity.getCity());
				}
				IO.shortestPath(graph, currentCity);
				break;
			case 'k': {
				System.out.println("Program Complete");
				return;
			}
			default:
				break;
			}
			
		}
	}

}
