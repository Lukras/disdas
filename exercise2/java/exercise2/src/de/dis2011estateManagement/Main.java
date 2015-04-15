package de.dis2011estateManagement;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) {
		Runnable guiCreator = new Runnable() {
			public void run() {
				JFrame fenster = new JFrame("Hallo Welt mit Swing");
				fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

				JLabel label = new JLabel("Hauptmenü");
				label.setAlignmentX(Component.CENTER_ALIGNMENT);
				panel.add(label);

				JButton btnEstateAgentMgmt = new JButton(
						"Estate Agent Management");
				btnEstateAgentMgmt.setAlignmentX(Component.CENTER_ALIGNMENT);
				btnEstateAgentMgmt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						// ... called when button clicked
					}
				});
				panel.add(btnEstateAgentMgmt);

				fenster.add(panel);

				fenster.setSize(300, 200);
				fenster.setVisible(true);
			}
		};

		SwingUtilities.invokeLater(guiCreator);
	}
}
