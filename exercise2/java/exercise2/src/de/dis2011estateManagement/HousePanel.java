package de.dis2011estateManagement;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import de.dis2011.data.Estate;
import de.dis2011.data.House;

public class HousePanel extends JPanel implements ActionListener,
		ListSelectionListener {

	private static final long serialVersionUID = 3181591474929036807L;

	private static final String[] COLUMNS = { "id", "city", "postalCode",
			"street", "streetNumber", "squareArea", "estateAgentId", "floors",
			"price", "garden" };

	private static final String[] ITEMS = { "create", "update", "delete",
			"back" };

	private JTable table;
	private JTextField[] txtFldEdit;
	private JButton[] buttons;

	private MainFrame mainFrame;

	public HousePanel(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// create table panel and init table
		initTable();

		// create edit panels
		txtFldEdit = new JTextField[COLUMNS.length];
		for (int i = 0; i < COLUMNS.length; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			JLabel label = new JLabel(COLUMNS[i]);
			int labelWidth = 80;
			label.setMinimumSize(new Dimension(labelWidth, 0));
			label.setPreferredSize(new Dimension(labelWidth, label
					.getPreferredSize().height));
			label.setMaximumSize(new Dimension(labelWidth, label
					.getPreferredSize().height));
			panel.add(label);
			JTextField textfield = new JTextField();
			textfield.setMaximumSize(new Dimension(Integer.MAX_VALUE, textfield
					.getPreferredSize().height));
			txtFldEdit[i] = textfield;
			panel.add(textfield);
			this.add(panel);
		}
		txtFldEdit[0].setEnabled(false); // input field for ID is disabled

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
		List<Estate> houses = House.loadAll();
		for (Estate estate : houses) {
			House house = (House) estate;
			String strGarden;
			if (house.isGarden())
				strGarden = "X";
			else
				strGarden = "";

			dtm.addRow(new Object[] { house.getId(), house.getCity(),
					house.getPostalCode(), house.getStreet(),
					house.getStreetNumber(), house.getSquareArea(),
					house.getEstateAgentId(), house.getFloors(),
					house.getPrice(), strGarden });
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0]) {
			House house = getHouseOfInputFields();
			house.setId(-1); // set id to -1 to insert a new entry
			house.save();
			refreshTable();
		} else if (e.getSource() == buttons[1]) {
			getHouseOfInputFields().save();
			refreshTable();
		} else if (e.getSource() == buttons[2]) {
			getHouseOfInputFields().delete();
			refreshTable();
		} else if (e.getSource() == buttons[3]) {
			mainFrame.goToPanel(new EstateMenuPanel(mainFrame));
		}
	}

	private House getHouseOfInputFields() {
		House house = new House();
		house.setId(Integer.parseInt(txtFldEdit[0].getText()));
		house.setCity(txtFldEdit[1].getText());
		house.setPostalCode(txtFldEdit[2].getText());
		house.setStreet(txtFldEdit[3].getText());
		house.setStreetNumber(Integer.parseInt(txtFldEdit[4].getText()));
		house.setSquareArea(txtFldEdit[5].getText());
		house.setEstateAgentId(Integer.parseInt(txtFldEdit[6].getText()));
		house.setFloors(Integer.parseInt(txtFldEdit[7].getText()));
		house.setPrice(BigDecimal.valueOf(Double.parseDouble(txtFldEdit[8]
				.getText())));
		house.setGarden(txtFldEdit[9].getText().equals("X"));
		return house;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (table.getSelectedRow() > -1) {
			for (int i = 0; i < COLUMNS.length; i++) {
				txtFldEdit[i].setText(table.getValueAt(table.getSelectedRow(),
						i).toString());
			}
		}
	}

}
