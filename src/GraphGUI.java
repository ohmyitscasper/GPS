import java.awt.*;
import java.awt.event.*;

import javax.swing.*;  
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;


public class GraphGUI extends JFrame {
	private JPanel mainPane;
	private JPanel buttonPanel;
	private JPanel textPanel;
	private JTextArea mainTextArea;
	
	public GraphGUI() {
		setTitle("Grapher");
		setSize(1200, 800); 
		mainPane = new JPanel(new BorderLayout());
		mainTextArea = new JTextArea(5, 50);
		textPanel = getTextPanel(mainTextArea);
		buttonPanel = getButtonPanel();
		
		mainPane.add(textPanel, BorderLayout.SOUTH);
		mainPane.add(buttonPanel, BorderLayout.WEST);
		
		add(mainPane);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public JPanel getButtonPanel() {
		JPanel buttonPanel = new JPanel();
		JButton loadFile = loadFile();
		buttonPanel.setLayout(new GridLayout(11,1));
		
		buttonPanel.add(loadFile);
		return buttonPanel;
	}
	
	public JPanel getTextPanel(JTextArea textArea) {
		JPanel textPanel = new JPanel();
		textArea.setText("Updated with what's going on here.");
		textPanel.add(textArea);
		return textPanel;
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
				
				int n = JOptionPane.showOptionDialog(inputFrame,
				    "Would you rather:",
				    "Input",
				    JOptionPane.YES_NO_CANCEL_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,
				    options,
				    options[1]);
				
				if()
			}
		});
		return file;
	}
	
}


