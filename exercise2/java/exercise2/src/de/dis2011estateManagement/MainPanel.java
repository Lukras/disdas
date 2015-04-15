package de.dis2011estateManagement;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 6727808630919232365L;

	public MainPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel caption = new JLabel("Hauptmenü");
		caption.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(caption);

		JButton btnEstateAgentMgmt = new JButton(
				"Estate Agent Management");
		btnEstateAgentMgmt.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEstateAgentMgmt.addActionListener(this);
		this.add(btnEstateAgentMgmt);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		EstateAgentManagementPanel panel = new EstateAgentManagementPanel();
		this.add(panel);
	}
}
