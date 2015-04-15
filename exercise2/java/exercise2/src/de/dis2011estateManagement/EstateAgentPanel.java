package de.dis2011estateManagement;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

public class EstateAgentPanel extends JPanel {

	private static final long serialVersionUID = 9036863228877036537L;

	private MainFrame mainFrame;

	public EstateAgentPanel(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;

		String[] columnNames = { "First Name", "Last Name", "Sport",
				"# of Years", "Vegetarian" };

		Object[][] data = {
				{ "Kathy", "Smith", "Snowboarding", new Integer(5),
						new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true) },
				{ "Sue", "Black", "Knitting", new Integer(2),
						new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20),
						new Boolean(true) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false) } };

		JTable table = new JTable(data, columnNames);
		this.add(table);
		
		JButton btnBack = new JButton("back");
		this.add(btnBack);
	}
}
