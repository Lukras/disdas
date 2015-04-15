package de.dis2011estateManagement;

import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {

	JButton btnEstateAgentMgmt;

	public MainFrame() throws HeadlessException {
		super("Estate Management");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(new MainPanel(this));

		this.setSize(SIZE_X, SIZE_Y);
		this.setLocationByPlatform(true);
		this.setVisible(true);
	}

	private static final long serialVersionUID = -7998357433606040836L;

	static final int SIZE_X = 640;
	static final int SIZE_Y = 480;

	public static void main(String[] args) {
		Runnable guiCreator = new Runnable() {
			public void run() {
				new MainFrame();
			}
		};
		SwingUtilities.invokeLater(guiCreator);
	}
	
	public void goToEstateAgentLoginPanel() {
		getContentPane().removeAll();
		getContentPane().add(new EstateAgentLoginPanel(this));
		repaint();
		printAll(getGraphics());
	}
	
	public void goToEstateAgentPanel() {
		getContentPane().removeAll();
		getContentPane().add(new EstateAgentPanel(this));
		repaint();
		printAll(getGraphics());
	}
}
