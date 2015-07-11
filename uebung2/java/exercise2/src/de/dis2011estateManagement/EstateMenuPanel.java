package de.dis2011estateManagement;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class EstateMenuPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 5020833274356058884L;

	private static final String[] ITEMS = { "Apartment Management",
			"House Management", "back" };

	private JButton[] buttons;

	private MainFrame mainFrame;

	public EstateMenuPanel(MainFrame mainFrame) {
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
			mainFrame.goToPanel(new ApartmentPanel(mainFrame));
		} else if (e.getSource() == buttons[1]) {
			mainFrame.goToPanel(new HousePanel(mainFrame));
		} else if (e.getSource() == buttons[2]) {
			mainFrame.goToPanel(new MainPanel(mainFrame));
		}
	}

}
