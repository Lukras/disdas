package de.dis2011estateManagement;

import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {

	JButton btnEstateAgentMgmt;

	private static final long serialVersionUID = -7998357433606040836L;
	
	public MainFrame() throws HeadlessException {
		super("Estate Management");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(new MainPanel(this));

		this.setSize(640, 480);
		this.setMinimumSize(new Dimension(640,480));
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
				new MainFrame();
			}
		};
		SwingUtilities.invokeLater(guiCreator);
	}
}
