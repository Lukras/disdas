package de.dis2011estateManagement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) {
		Runnable guiCreator = new Runnable() {
			public void run() {
				JFrame fenster = new JFrame("Estate Management");
				fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JPanel mainPanel = new MainPanel();
				fenster.add(mainPanel);
				fenster.setSize(300, 200);
				fenster.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(guiCreator);
	}
}
