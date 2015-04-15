package de.dis2011estateManagement;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -7091238205226400082L;
	
	private JButton btnEstateAgentMgmt;
	
	private MainFrame mainFrame;
	
	public MainPanel(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		btnEstateAgentMgmt = new JButton("Estate Agent Management");
		btnEstateAgentMgmt.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEstateAgentMgmt.addActionListener(this);
		this.add(btnEstateAgentMgmt);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEstateAgentMgmt) {
			mainFrame.goToPanel(new EstateAgentLoginPanel(mainFrame));
		}
	}
}
