//Antone J. Evans Jr.
//CSCI 3381 OO with Java 

package project2;

import javax.swing.JFrame;

public class MainFrame {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Antone Evans Project 2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MainPanel panel = new MainPanel();
		frame.getContentPane().add(panel);
		
		frame.pack();
		frame.setVisible(true);
	}
}

