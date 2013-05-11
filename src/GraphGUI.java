import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;  
import javax.swing.border.TitledBorder;

import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;


/**
 * The gui backbone of my program
 * @author Vanshil Shah vs2409
 *
 */
public class GraphGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPane;
	private JPanel buttonPanel;
	private JPanel textPanel;
	private JPanel graphPanel;
	private JPanel outputPanel;
	private JTextArea mainTextArea;
	private JTextArea outputArea;
	private MyGraph graph;
	private GraphNode currentCity;
	private Graph<String,Integer> grapher;
	JButton loadFile;
	JButton loadSerialFile;
	JButton writeSerialFile;
	JButton stateSearch;
	JButton citySearch;
	JButton setCurrentCity;
	JButton printCurrent;
	JButton nClosest;
	JButton yCost;
	JButton shortestPath;
	JButton quitButton;
	
	public GraphGUI(MyGraph graph) {
		this.graph = graph;
		setTitle("Grapher");
		setSize(1200, 800); 
		mainPane = new JPanel(new BorderLayout());
		mainTextArea = new JTextArea(5, 50);
		outputArea = new JTextArea(5, 50);
		textPanel = getTextPanel();
		buttonPanel = getButtonPanel();
		outputPanel = getOutputPanel();
		graphPanel = getGraphPane();
		
		mainPane.add(textPanel, BorderLayout.SOUTH);
		mainPane.add(buttonPanel, BorderLayout.WEST);
		mainPane.add(outputPanel, BorderLayout.NORTH);
		mainPane.add(graphPanel, BorderLayout.CENTER);
		
		add(mainPane);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * Takes a very long time with huge lists of cities and is very laggy.
	 */
	public void drawGraph() {
		mainTextArea.append("\nLoading graph...");
		grapher = new DirectedSparseGraph<String, Integer>();
		int i = 0;
		ArrayList<GraphNode> vertices = graph.getVertices();
		for (GraphNode node : vertices) {
			grapher.addVertex(node.getCity());
			
			for (Edge edge : node.outgoingEdges())
				grapher.addEdge(i++, node.getCity(), vertices.get(edge.getEndNode()).getCity(), EdgeType.DIRECTED);
		}
		Layout<String, Integer> layout = new ISOMLayout<String, Integer>(grapher);
		layout.setSize(new Dimension(1000,400));//graph viewer
		final VisualizationViewer<String, Integer> viewer = new VisualizationViewer<String, Integer>(layout);
		viewer.setPreferredSize(layout.getSize());
		viewer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());
		
		//graph mouse
		final DefaultModalGraphMouse<Integer, String> mouse = new DefaultModalGraphMouse<Integer, String>();		
		viewer.setGraphMouse(mouse);
		graphPanel.add(viewer);
		mainTextArea.setText("Graph Loaded");
	}
	
	public JPanel getGraphPane() {
		graphPanel = new JPanel();
		TitledBorder graphBorder = new TitledBorder("Graph");
		graphPanel.setBorder(graphBorder);
		graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));
		return graphPanel;
	}
	
	public JPanel getOutputPanel() {
		JPanel outputTextPanel = new JPanel();
		Font font = new Font("Verdana", Font.BOLD, 12);
		
		outputArea.setFont(font);
		outputArea.setEditable(false);
		outputArea.setBackground(Color.BLUE);
		outputArea.setForeground(Color.WHITE);
		outputArea.setMargin(new Insets(5, 5, 5, 5));
		outputArea.setText("Your outputs will show up here.");
		
		JScrollPane scrollPane = new JScrollPane(outputArea);
		outputTextPanel.add(scrollPane);
		return outputTextPanel;
	}
	
	public JPanel getTextPanel() {
		JPanel textPanel = new JPanel();
		Font font = new Font("Verdana", Font.BOLD, 12);
		
		mainTextArea.setFont(font);
		mainTextArea.setEditable(false);
		mainTextArea.setBackground(Color.BLACK);
		mainTextArea.setForeground(Color.WHITE);
		mainTextArea.setMargin(new Insets(5, 5, 5, 5));
		mainTextArea.setText("You will be updated with what's going on here.");
		
		textPanel.add(mainTextArea);
		return textPanel;
	}
	
	public JPanel getButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(11,1));
		
		loadFile = loadFile();
		loadSerialFile = loadSerialFile();
		writeSerialFile = writeSerialFile();
		stateSearch = stateSearch();
		citySearch = citySearch();
		setCurrentCity = setCurrentCity();
		printCurrent = printCurrent();
		nClosest = nClosest();
		yCost = yCost();
		shortestPath = shortestPath();
		quitButton = quitButton();
		
		
		stateSearch.setEnabled(false);
		citySearch.setEnabled(false);
		setCurrentCity.setEnabled(false);
		printCurrent.setEnabled(false);
		nClosest.setEnabled(false);
		yCost.setEnabled(false);
		shortestPath.setEnabled(false);
		
		buttonPanel.add(loadFile);
		buttonPanel.add(loadSerialFile);
		buttonPanel.add(writeSerialFile);
		buttonPanel.add(stateSearch);
		buttonPanel.add(citySearch);
		buttonPanel.add(setCurrentCity);
		buttonPanel.add(printCurrent);
		buttonPanel.add(nClosest);
		buttonPanel.add(yCost);
		buttonPanel.add(shortestPath);
		buttonPanel.add(quitButton);
		return buttonPanel;
	}
	
	public JButton quitButton() {
		JButton button = new JButton("Quit");
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//quit program
				System.exit(0);
			}
		});
		return button;
	}
	
	private JButton shortestPath() {
		// TODO Auto-generated method stub
		JButton city = new JButton("Shortest Path");
		city.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFrame inputFrame = new JFrame("Input");
				String city = (String) JOptionPane.showInputDialog(inputFrame,
						"Please enter the name of the city you want the shortest path to",
						JOptionPane.PLAIN_MESSAGE
	                    );
				
				if(city==null || city.equals("")) 
					city = "Boston";	//arbitrarily set a city
				
				ArrayList<GraphNode> cities = IO.shortestPath(graph, currentCity, city);
				
				if(cities.isEmpty()) {
					mainTextArea.setText("The city you are searching for cannot be found.");
				}
				else {
					mainTextArea.setText("Output Area updated");
					outputArea.setText("Path:\n");
					printPath(cities.get(0));
				}
				
			}
		});
		return city;
	}
	
	/**
	 * A helper function that recursively prints the path
	 * @param city
	 */
	public void printPath(GraphNode city) {
		if(city.path!=null) {
			printPath(city.path);
			outputArea.append(" to ");
		}
		outputArea.append(city.getCity());
	}

	private JButton yCost() {
		// TODO Auto-generated method stub
		JButton b = new JButton("Y closest cities by Cost");
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFrame inputFrame = new JFrame("Input");
				String city = (String) JOptionPane.showInputDialog(inputFrame,
						"Please enter Y to find all of the cities within Y cost",
						JOptionPane.PLAIN_MESSAGE
	                    );
				
				int y = 0;
				
				try {
					y = Integer.parseInt(city);
				} catch (NumberFormatException e) {
					mainTextArea.setText("ERROR: ONLY INTEGERS ACCEPTED");
					y = 1;
				}
				
				ArrayList<GraphNode> yClosest = IO.yClosest(graph, currentCity, y);
				
				if(yClosest.isEmpty()) {
					mainTextArea.setText("None within Y Cost");
				}
				else {
					mainTextArea.setText("Output Area updated");
					outputArea.setText("Y Closest Cities:");
					for(GraphNode v: yClosest) {
						printCity(v);
					}
				}
			}
		});
		return b;
	}

	private JButton nClosest() {
		// TODO Auto-generated method stub
		JButton city = new JButton("N closest cities");
		city.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFrame inputFrame = new JFrame("Input");
				String city = (String) JOptionPane.showInputDialog(inputFrame,
						"Please enter n to find n cities closest to the current city",
						JOptionPane.PLAIN_MESSAGE
	                    );
				
				int n = 0;
				
				try {
					n = Integer.parseInt(city);
				} catch (NumberFormatException e) {
					mainTextArea.setText("ERROR: ONLY INTEGERS ACCEPTED");
					n = 1;
				}
				
				if(n>graph.getTotalNodes() || n<0) {
					n = 1;	//arbitrarily set a number
				}
				
				ArrayList<GraphNode> nClosest = IO.nClosest(graph, currentCity, n);
				
				if(nClosest.isEmpty()) {
					mainTextArea.setText("The city you are searching for cannot be found.");
				}
				else {
					mainTextArea.setText("Output Area updated");
					outputArea.setText("N Closest Cities:");
					for(GraphNode v: nClosest) {
						printCity(v);
					}
				}
			}
		});
		return city;
	}

	private JButton printCurrent() {
		// TODO Auto-generated method stub
		JButton city = new JButton("Display current city");
		city.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(currentCity==null)
					mainTextArea.setText("ERROR: PLEASE SET A CITY AS THE CURRENT CITY");
				else {
					outputArea.setText("Current city: ");
					printCity(currentCity);
				}
			}
		});
		return city;
	}

	private JButton setCurrentCity() {
		// TODO Auto-generated method stub
		JButton city = new JButton("Set current city");
		city.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFrame inputFrame = new JFrame("Input");
				String city = (String) JOptionPane.showInputDialog(inputFrame,
						"Please enter the number of the city you want to search for.",
						JOptionPane.PLAIN_MESSAGE
	                    );
				
				int n = 0;
				
				try {
					n = Integer.parseInt(city);
				} catch (NumberFormatException e) {
					mainTextArea.setText("ERROR: ONLY INTEGERS ACCEPTED\nCURRENT CITY SET RANDOMLY");
					n = 1;
				}
				
				if(n>graph.getTotalNodes() || n<0) {
					n = 1;	//arbitrarily set a city
				}
				
				currentCity = IO.setCurrentCity(graph, n);
				
				if(currentCity==null) 
					mainTextArea.setText("The city you are searching for cannot be found.");
				else {
					mainTextArea.setText("Current City Updated to " + currentCity.getCity());
					printCurrent.setEnabled(true);
					nClosest.setEnabled(true);
					yCost.setEnabled(true);
					shortestPath.setEnabled(true);
				}
			}
		});
		return city;
	}

	private JButton citySearch() {
		// TODO Auto-generated method stub
		JButton city = new JButton("Search for city");
		city.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFrame inputFrame = new JFrame("Input");
				String city = (String) JOptionPane.showInputDialog(inputFrame,
						"Please enter the name of the city you want to search for",
						JOptionPane.PLAIN_MESSAGE
	                    );
				
				if(city==null || city.equals("")) 
					city = "Boston";	//arbitrarily set a city
				
				ArrayList<GraphNode> cities = IO.citySearch(graph, city);
				
				if(cities.isEmpty()) {
					mainTextArea.setText("The city you are searching for cannot be found.");
				}
				else {
					mainTextArea.setText("Output Area updated");
					outputArea.setText("Cities:");
					for(GraphNode v: cities) {
						printCity(v);
					}
				}
				
			}
		});
		return city;
	}

	private JButton stateSearch() {
		// TODO Auto-generated method stub
		JButton state = new JButton("Search by state");
		state.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFrame inputFrame = new JFrame("Input");
				String state = (String) JOptionPane.showInputDialog(inputFrame,
						"Please enter the name of the state you are searching for.",
						JOptionPane.PLAIN_MESSAGE
	                    );
				
				if(state==null || state.equals("")) 
					state = "New Jersey";	//arbitrarily set a state
				ArrayList<GraphNode> cities = IO.stateSearch(graph, state);
				
				if(cities.isEmpty()) {
					mainTextArea.setText("The state you are searching for cannot be found.");
				}
				else {
					mainTextArea.setText("Output Area updated");
					outputArea.setText("Cities:");
					for(GraphNode v: cities) {
						printCity(v);
					}
				}
				
			}
		});
		return state;
	}

	private JButton writeSerialFile() {
		JButton write = new JButton("Write Serialized Graph");
		
		write.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!IO.write(graph,"mygraph.bin"))
					mainTextArea.setText("Serialized file write failed");
				else
					mainTextArea.setText("Serialized file mygraph.bin written");
			}
		});
		return write;
	}

	private JButton loadSerialFile() {
		JButton load = new JButton("Load Serialized Graph");
		
		load.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				graph=IO.read("mygraph.bin");
				if(graph == null) 
					mainTextArea.setText("Serialized file read failed");
				else {
					mainTextArea.setText("Serialized file read");
					drawGraph();
				}
			}
		});
		
		return load;
	}
	
	public JButton loadFile() {
		JButton file = new JButton("Load File");
		
		file.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFrame inputFrame = new JFrame("Input");
				
				Object[] options = {"Load a new graph",
	                    "Append to the graph"};
				
				mainTextArea.setText("Loading...");
				
				int n = JOptionPane.showOptionDialog(inputFrame,
				    "Would you rather:",
				    "Input",
				    JOptionPane.YES_NO_CANCEL_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,
				    options,
				    options[1]);
				
				//arbitrarily set n if the user doesn't pick anything.
				if(n==-1) 
					n = 0;
				
				String file = (String) JOptionPane.showInputDialog(inputFrame,
						"Please enter the name of the file you wish to open",
						JOptionPane.PLAIN_MESSAGE
	                    );
				
				if(file==null || file.equals("")) 
					file = "fict100.txt";	//arbitrarily set a file
					
				if(IO.loadFile(graph, n, file)==null) {
					mainTextArea.setText("File name not vaid, please load again.");
				}
				else {
					mainTextArea.setText("Cities loaded and random edges added from file: " + file + 
							"\n" + graph.getTotalNodes() + " cities added.");
					stateSearch.setEnabled(true);
					citySearch.setEnabled(true);
					setCurrentCity.setEnabled(true);
					drawGraph();
				}
				
			
				
			}
		});
		return file;
	}
	
	public void printCity(GraphNode v) {
		outputArea.append("\n\nCity: " + v.getCity());
		outputArea.append("\nState: " + v.getState());
		outputArea.append("\nID: " + v.getID());
		outputArea.append("\nLatitude: " + v.getLat() + " Longitude: " + v.getLong());
	}
	
}


