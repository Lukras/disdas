package de.dis2011estateManagement;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import de.dis2011.data.EstateAgent;

public class EstateAgentPanel extends JPanel implements ActionListener,
		ListSelectionListener {

	private static final long serialVersionUID = 9036863228877036537L;

	private static final String[] COLUMNS = { "id", "name", "address", "login",
			"password" };

	private JTable table;
	private JTextField[] txtFldEdit;
	private JButton btnCreate, btnUpdate, btnDelete, btnBack;

	private MainFrame mainFrame;

	public EstateAgentPanel(MainFrame mainFrame) {
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
			label.setMinimumSize(new Dimension(labelWidth,0));
			label.setPreferredSize(new Dimension(labelWidth,label.getPreferredSize().height));
			label.setMaximumSize(new Dimension(labelWidth, Integer.MAX_VALUE));
			panel.add(label);
			JTextField textfield = new JTextField();
			textfield.setMaximumSize(new Dimension(Integer.MAX_VALUE, textfield.getPreferredSize().height));
			txtFldEdit[i] = textfield;
			panel.add(textfield);
			this.add(panel);
		}
		txtFldEdit[0].setEnabled(false); // input field for ID is disabled

		// create button panel
		JPanel pnlButtons = new JPanel();
		pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.X_AXIS));
		btnCreate = new JButton("create");
		btnCreate.addActionListener(this);
		pnlButtons.add(btnCreate);
		btnUpdate = new JButton("update");
		btnUpdate.addActionListener(this);
		pnlButtons.add(btnUpdate);
		btnDelete = new JButton("delete");
		btnDelete.addActionListener(this);
		pnlButtons.add(btnDelete);
		btnBack = new JButton("back");
		btnBack.addActionListener(this);
		pnlButtons.add(btnBack);
		this.add(pnlButtons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCreate) {
			EstateAgent agent = getAgentOfInputFields();
			agent.setId(-1); // set id to -1 to insert a new entry
			agent.save();
			refreshTable();
		} else if (e.getSource() == btnUpdate) {
			getAgentOfInputFields().save();
			refreshTable();
		} else if (e.getSource() == btnDelete) {
			getAgentOfInputFields().delete();
			refreshTable();
		} else if (e.getSource() == btnBack) {
			mainFrame.goToPanel(new MainPanel(mainFrame));
		}
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
		List<EstateAgent> agents = EstateAgent.loadAll();
		for (EstateAgent agent : agents) {
			dtm.addRow(new Object[] { agent.getId(), agent.getName(),
					agent.getAddress(), agent.getLogin(), agent.getPassword() });
		}
	}
	
	private EstateAgent getAgentOfInputFields() {
		EstateAgent agent = new EstateAgent();
		agent.setId(Integer.parseInt(txtFldEdit[0].getText()));
		agent.setName(txtFldEdit[1].getText());
		agent.setAddress(txtFldEdit[2].getText());
		agent.setLogin(txtFldEdit[3].getText());
		agent.setPassword(txtFldEdit[4].getText());
		return agent;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (table.getSelectedRow() > -1) {
			// print first column value from selected row
			for (int i = 0; i < COLUMNS.length; i++) {
				txtFldEdit[i].setText(table.getValueAt(table.getSelectedRow(),
						i).toString());
			}
		}
	}
}
