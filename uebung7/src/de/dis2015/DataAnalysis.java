package de.dis2015;

import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DataAnalysis extends JFrame {
	JButton btnEstateAgentMgmt;

	private static final long serialVersionUID = -7998357433606040836L;
	
	public DataAnalysis() throws HeadlessException {
		super("Data Analysis");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(new DataAnalysisPanel(this));

		this.setSize(640, 480);
		this.setMinimumSize(new Dimension(1280,960));
		this.setLocationByPlatform(true);
		this.setVisible(true);
	}
	
	public void goToPanel(JPanel panel) {
		getContentPane().removeAll();
		getContentPane().add(panel);
		repaint();
		printAll(getGraphics());
	}

	public static void main(String[] args) {
		Runnable guiCreator = new Runnable() {
			public void run() {
				new DataAnalysis();
			}
		};
		SwingUtilities.invokeLater(guiCreator);
	}
}
