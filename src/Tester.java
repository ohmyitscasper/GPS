import java.util.Scanner;

/**
 * This class contains the main method of the program. 
 * 
 * Functionality being implemented:
 * 	Displaying the menu to the user
 *  Reading the data in.
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
		String inputFile;
		char choice;
		
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
				if(IO.loadFile() < 0) {
					System.out.println("\nPlease try again\n");
					continue;
				}
			}
				break;
			case 'b':
				
				break;
			case 'c':
				
				break;
			case 'd':
				
				break;
			case 'e':
				
				break;
			case 'f':
				
				break;
			case 'g':
				
				break;
			case 'h':
				
				break;
			case 'i':
				
				break;
			case 'j':
				
				break;
			case 'k':
				return;
			default:
				break;
			}
			
		}
	}

}
