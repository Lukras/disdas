package de.dis2011estateManagement;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import de.dis2011.data.EstateAgent;

public class EstateAgentPanel extends JPanel implements ActionListener, ListSelectionListener {

	private static final long serialVersionUID = 9036863228877036537L;
	
	private static final String[] COLUMNS = { "id", "name", "address", "login", "password" };

	private JTable table;
	private JTextField[] txtFldEdit;
	private JButton btnSave, btnBack;
	
	private MainFrame mainFrame;

	public EstateAgentPanel(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// create table panel
		JPanel pnlTable = new JPanel();
		pnlTable.setLayout(new BoxLayout(pnlTable, BoxLayout.X_AXIS));
		table = new JTable();
		pnlTable.add(table);
		this.add(pnlTable);
		
		// create edit panel
		JPanel pnlEdit = new JPanel();
		pnlEdit.setLayout(new BoxLayout(pnlEdit, BoxLayout.X_AXIS));
		JPanel pnlEditFields = new JPanel();
		pnlEditFields.setLayout(new BoxLayout(pnlEditFields, BoxLayout.Y_AXIS));
		txtFldEdit = new JTextField[COLUMNS.length];
		for (int i = 0; i < COLUMNS.length; i++) {
			pnlEditFields.add(new JLabel(COLUMNS[i]));
			JTextField txtFld = new JTextField();
			txtFld.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtFld.getPreferredSize().height) );
			pnlEditFields.add(txtFld);
			txtFldEdit[i] = txtFld;
		}
		pnlEdit.add(pnlEditFields);
		this.add(pnlEdit);
		
		// create button panel
		JPanel pnlButtons = new JPanel();
		pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.X_AXIS));
		btnSave = new JButton("save");
		btnSave.addActionListener(this);
		pnlButtons.add(btnSave);
		btnBack = new JButton("back");
		btnBack.addActionListener(this);
		pnlButtons.add(btnBack);
		this.add(pnlButtons);
		
		// fill table with estate agents
		initTable();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnBack) {
			mainFrame.goToPanel(new MainPanel(mainFrame));
		} else if (e.getSource() == btnSave) {
			EstateAgent agent = new EstateAgent();
			agent.setId(Integer.parseInt(txtFldEdit[0].getText()));
			agent.setName(txtFldEdit[1].getText());
			agent.setAddress(txtFldEdit[2].getText());
			agent.setLogin(txtFldEdit[3].getText());
			agent.setPassword(txtFldEdit[4].getText());
			agent.save();
			
			refreshTable();
		}
	}
	
	private void initTable() {
		table.getSelectionModel().addListSelectionListener(this);
		refreshTable();
	}
	
	private void refreshTable() {
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.setColumnIdentifiers(COLUMNS);
		table.setModel(dtm);
		List<EstateAgent> agents = EstateAgent.loadAll();
		for (EstateAgent agent : agents) {
			dtm.addRow(new Object[] {agent.getId(), agent.getName(), agent.getAddress(), agent.getLogin(), agent.getPassword()});
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (table.getSelectedRow() > -1) {
            // print first column value from selected row
			for (int i = 0; i < COLUMNS.length; i++) {
				txtFldEdit[i].setText(table.getValueAt(table.getSelectedRow(), i).toString());
			}
        }
	}
}
