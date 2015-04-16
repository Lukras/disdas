package de.dis2011estateManagement;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -7091238205226400082L;
	
	private static final String[] ITEMS = {
		"Estate Agent Management",
		"Estate Management",
		"Contract Management" };
	
	private JButton[] buttons;
	
	private MainFrame mainFrame;
	
	public MainPanel(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// create menu items
		buttons = new JButton[ITEMS.length];
		for (int i = 0; i < ITEMS.length; i++) {
			JButton btn = new JButton(ITEMS[i]);
			btn.setAlignmentX(Component.CENTER_ALIGNMENT);
			btn.addActionListener(this);
			this.add(btn);
			buttons[i] = btn;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0]) {
			mainFrame.goToPanel(new EstateAgentLoginPanel(mainFrame));
		} else if (e.getSource() == buttons[1]) {
			mainFrame.goToPanel(new EstateLoginPanel(mainFrame));
		}
	}
}
