package de.dis2011estateManagement;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
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

import de.dis2011.data.TenancyContract;

public class TenancyContractPanel extends JPanel implements ActionListener,
		ListSelectionListener {

	private static final long serialVersionUID = 8974244798444379236L;

	private static final String[] COLUMNS = { "id", "contractNo", "date",
			"place", "startDate", "duration", "additionalCosts", "apartmentId",
			"personId" };

	private static final String[] ITEMS = { "create", "update", "delete",
			"back" };

	private JTable table;
	private JTextField[] txtFldEdit;
	private JButton[] buttons;

	private MainFrame mainFrame;

	public TenancyContractPanel(MainFrame mainFrame) {
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
		List<TenancyContract> contracts = TenancyContract.loadAll();
		for (TenancyContract contract : contracts) {

			dtm.addRow(new Object[] { contract.getId(),
					contract.getContractNo(), contract.getDate(),
					contract.getPlace(), contract.getStartDate(),
					contract.getDuration(), contract.getAdditionalCosts(),
					contract.getApartmentId(), contract.getPersonId() });
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0]) {
			TenancyContract contract = getTenancyContractOfInputFields();
			contract.setId(-1); // set id to -1 to insert a new entry
			contract.save();
			refreshTable();
		} else if (e.getSource() == buttons[1]) {
			getTenancyContractOfInputFields().save();
			refreshTable();
		} else if (e.getSource() == buttons[2]) {
			getTenancyContractOfInputFields().delete();
			refreshTable();
		} else if (e.getSource() == buttons[3]) {
			mainFrame.goToPanel(new MainPanel(mainFrame));
		}
	}

	private TenancyContract getTenancyContractOfInputFields() {
		TenancyContract contract = new TenancyContract();
		contract.setId(Integer.parseInt(txtFldEdit[0].getText()));
		contract.setContractNo(Integer.parseInt(txtFldEdit[1].getText()));
		contract.setDate(Date.valueOf(txtFldEdit[2].getText()));
		contract.setPlace(txtFldEdit[3].getText());
		contract.setStartDate(Date.valueOf(txtFldEdit[4].getText()));
		contract.setDuration(Integer.parseInt(txtFldEdit[5].getText()));
		contract.setAdditionalCosts(BigDecimal.valueOf(Double.parseDouble(txtFldEdit[6].getText())));
		contract.setApartmentId(Integer.parseInt(txtFldEdit[7].getText()));
		contract.setPersonId(Integer.parseInt(txtFldEdit[8].getText()));
		return contract;
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
