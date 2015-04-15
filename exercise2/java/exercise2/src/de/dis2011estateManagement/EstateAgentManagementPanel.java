package de.dis2011estateManagement;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EstateAgentManagementPanel extends JPanel {
	private static final long serialVersionUID = -6990942376290020635L;
	
	public EstateAgentManagementPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel caption = new JLabel("Estate Agent Management");
		caption.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(caption);
	}
}
