package de.dis2011estateManagement;

import java.awt.Dimension;
import java.awt.HeadlessException;
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

import de.dis2011.data.Apartment;
import de.dis2011.data.Estate;

public class EstatePanel extends JPanel implements ActionListener,
		ListSelectionListener {

	private static final long serialVersionUID = 4997143805524000653L;

	private static final String[] COLUMNS = { "id", "city", "postalCode",
			"street", "streetNumber", "squareArea", "estateAgentId", "class" };
	
	private static final String[] APARTMENT_FLDS = {"floor", "rent", "rooms", "balcony", "builtInKitchen"};

	private static final String[] ITEMS = { "create", "update", "delete",
			"back" };

	private JTable table;
	private JTextField[] txtFldEdit;
	private JTextField[] txtFldEditApartment;
	private JButton[] buttons;
	
	private List<Estate> estates;

	private MainFrame mainFrame;

	public EstatePanel(MainFrame mainFrame) throws HeadlessException {
		super();
		this.mainFrame = mainFrame;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// create table panel and init table
		initTable();

		// create edit panels
		JPanel pnlEdit = new JPanel();
		pnlEdit.setLayout(new BoxLayout(pnlEdit, BoxLayout.X_AXIS));
		JPanel pnlEditLeft = new JPanel();
		pnlEditLeft.setLayout(new BoxLayout(pnlEditLeft, BoxLayout.Y_AXIS));
		txtFldEdit = new JTextField[COLUMNS.length];
		for (int i = 0; i < COLUMNS.length; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			JLabel label = new JLabel(COLUMNS[i]);
			int labelWidth = 120;
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
			pnlEditLeft.add(panel);
		}
		txtFldEdit[0].setEnabled(false); // input field for ID is disabled
		txtFldEdit[7].setEnabled(false); // synthetic field class is disabled
		pnlEdit.add(pnlEditLeft);
		JPanel pnlEditRight = new JPanel();
		pnlEditRight.setLayout(new BoxLayout(pnlEditRight, BoxLayout.Y_AXIS));
		txtFldEditApartment = new JTextField[APARTMENT_FLDS.length];
		for (int i = 0; i < APARTMENT_FLDS.length; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			JLabel label = new JLabel(APARTMENT_FLDS[i]);
			int labelWidth = 120;
			label.setMinimumSize(new Dimension(labelWidth, 0));
			label.setPreferredSize(new Dimension(labelWidth, label
					.getPreferredSize().height));
			label.setMaximumSize(new Dimension(labelWidth, label
					.getPreferredSize().height));
			panel.add(label);
			JTextField textfield = new JTextField();
			textfield.setMaximumSize(new Dimension(Integer.MAX_VALUE, textfield
					.getPreferredSize().height));
			txtFldEditApartment[i] = textfield;
			panel.add(textfield);
			pnlEditRight.add(panel);
		}
		pnlEdit.add(pnlEditRight);
		this.add(pnlEdit);
		fillInputFieldsWithEstate();

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
		refreshTable();
		table.getSelectionModel().addListSelectionListener(this);
	}

	private void refreshTable() {
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.setColumnIdentifiers(COLUMNS);
		table.setModel(dtm);
		estates = Estate.loadAll();
		for (Estate estate : estates) {

			String strClass;
			if (estate instanceof Apartment)
				strClass = "Apartment";
			else
				strClass = "House";

			dtm.addRow(new Object[] { estate.getId(), estate.getCity(),
					estate.getPostalCode(), estate.getStreet(),
					estate.getStreetNumber(), estate.getSquareArea(),
					estate.getEstateAgentId(), strClass });
		}
		table.setRowSelectionInterval(0, 0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0]) {
			Estate estate = getEstateOfInputFields();
			estate.setId(-1); // set id to -1 to insert a new entry
			estate.save();
			refreshTable();
		} else if (e.getSource() == buttons[1]) {
			getEstateOfInputFields().save();
			refreshTable();
		} else if (e.getSource() == buttons[2]) {
			getEstateOfInputFields().delete();
			refreshTable();
		} else if (e.getSource() == buttons[3]) {
			mainFrame.goToPanel(new MainPanel(mainFrame));
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (table.getSelectedRow() > -1) {
			fillInputFieldsWithEstate();
		}
	}
	
	private void fillInputFieldsWithEstate() {
		// get id and class of selected row
		int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
		String strClass = table.getValueAt(table.getSelectedRow(), 7).toString();
		
		// select estate from list
		Estate estate = null;
		for (Estate e : estates) {
			if (e.getId() == id) {
				estate = e;
				break;
			}
		}
		
		if (estate != null) {
			
			// fill estate fields
			txtFldEdit[0].setText(""+estate.getId());
			txtFldEdit[1].setText(estate.getCity());
			txtFldEdit[2].setText(estate.getPostalCode());
			txtFldEdit[3].setText(estate.getStreet());
			txtFldEdit[4].setText(""+estate.getStreetNumber());
			txtFldEdit[5].setText(estate.getSquareArea());
			txtFldEdit[6].setText(""+estate.getEstateAgentId());
			txtFldEdit[7].setText(strClass);
			
			if (strClass.equals("Apartment")) {
				if (estate instanceof Apartment) {
					Apartment apartment = (Apartment) estate;
					txtFldEditApartment[0].setText(""+apartment.getFloor());
					txtFldEditApartment[1].setText(""+apartment.getRent());
					txtFldEditApartment[2].setText(""+apartment.getRooms());
					
					if (apartment.isBalcony())
						txtFldEditApartment[3].setText("X");
					else
						txtFldEditApartment[3].setText(" ");
					
					if (apartment.isBuiltInKitchen())
						txtFldEditApartment[4].setText("X");
					else
						txtFldEditApartment[4].setText(" ");
				}
			}
		}
	}
	
	private Estate getEstateOfInputFields() {
		Estate estate;

		String strClass = table.getValueAt(table.getSelectedRow(), 7).toString();
		
		if (strClass.equals("Apartment")) {
			Apartment apartment = new Apartment();
			
			apartment.setFloor(Integer.parseInt(txtFldEditApartment[0].getText()));
			apartment.setRent(BigDecimal.valueOf(Double.parseDouble((txtFldEditApartment[1].getText()))));
			apartment.setRooms(Integer.parseInt(txtFldEditApartment[2].getText()));
			apartment.setBalcony(txtFldEditApartment[3].getText().equals("X"));
			apartment.setBuiltInKitchen(txtFldEditApartment[4].getText().equals("X"));
			
			estate = apartment;
		} else {
			// TODO estate = new House();
			estate = new Apartment();
		}
		
		estate.setId(Integer.parseInt(txtFldEdit[0].getText()));
		estate.setCity(txtFldEdit[1].getText());
		estate.setPostalCode(txtFldEdit[2].getText());
		estate.setStreet(txtFldEdit[3].getText());
		estate.setStreetNumber(Integer.parseInt(txtFldEdit[4].getText()));
		estate.setSquareArea(txtFldEdit[5].getText());
		estate.setEstateAgentId(Integer.parseInt(txtFldEdit[6].getText()));
		
		return estate;
	}
}
