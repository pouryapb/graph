package pouryapb;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {

	public Window(int w, int h, String title, Graph graph) {
		
		graph.setPreferredSize(new Dimension(w, h));
		graph.setMaximumSize(new Dimension(w, h));
		graph.setMinimumSize(new Dimension(w, h));
		
		JFrame frame =  new JFrame(title);
		frame.add(graph);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.requestFocus();
		graph.start();
	}
}
