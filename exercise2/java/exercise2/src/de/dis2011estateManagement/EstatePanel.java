package de.dis2011estateManagement;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import de.dis2011.data.Estate;

public class EstatePanel extends JPanel implements ActionListener, ListSelectionListener {

	private static final long serialVersionUID = 4997143805524000653L;

	private static final String[] COLUMNS = {
		"id",
		"city",
	    "postalCode",
	    "street",
	    "streetNumber",
	    "squareArea",
	    "estateAgentId" };
	
	private static final String[] ITEMS = {
		"create",
		"update",
		"delete",
		"back"};
	
	private JTable table;
	private JButton[] buttons;
	
	private MainFrame mainFrame;

	public EstatePanel(MainFrame mainFrame) throws HeadlessException {
		super();
		this.mainFrame = mainFrame;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// create table panel and init table
		initTable();

		// create button panel
		JPanel pnlButtons = new JPanel();
		pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.X_AXIS));
		buttons = new JButton[ITEMS.length];
		for (int i = 0; i < ITEMS.length; i++) {
			JButton button = new JButton(ITEMS[i]);
			button.addActionListener(this);
			pnlButtons.add(button);
			buttons[i] = button;
		}
		this.add(pnlButtons);
	}
	
	private void initTable() {
		JPanel pnlTable = new JPanel();
		pnlTable.setLayout(new BoxLayout(pnlTable, BoxLayout.X_AXIS));
		table = new JTable();
		JScrollPane scroll = new JScrollPane(table);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pnlTable.add(scroll);
		this.add(pnlTable);
		
		table.getSelectionModel().addListSelectionListener(this);
		refreshTable();
	}

	private void refreshTable() {
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.setColumnIdentifiers(COLUMNS);
		table.setModel(dtm);
		List<Estate> estates = Estate.loadAll();
		for (Estate estate : estates) {
			dtm.addRow(new Object[] {
					estate.getId(),
					estate.getCity(),
					estate.getPostalCode(),
					estate.getStreet(),
					estate.getStreetNumber(),
					estate.getSquareArea(),
					estate.getEstateAgentId() });
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[3]) {
			mainFrame.goToPanel(new MainPanel(mainFrame));
		}
	}
}
