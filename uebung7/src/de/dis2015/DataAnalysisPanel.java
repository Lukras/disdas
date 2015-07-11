package de.dis2015;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DataAnalysisPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -4351524344222249018L;

	private static final String[] ITEMS = {
		"Drill-Down(Product)",
		"Roll-Up(Product)",
		"Drill-Down(Time)",
		"Roll-Up(Time)",
		"Drill-Down(Branch)",
		"Roll-Up(Branch)"};
	
	private JTable table;
	private JButton[] buttons;
	
	private DataAnalysis mainFrame;
	
	private int dateDim = 0,
				articleDim = 0,
				shopDim = 0;
	
	public DataAnalysisPanel(DataAnalysis mainFrame) {
		super();
		this.mainFrame = mainFrame;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// create table panel and init table
		initTable();
		
		// create menu items
		JPanel pnlButtons = new JPanel();
		pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.X_AXIS));
		buttons = new JButton[ITEMS.length];
		for (int i = 0; i < ITEMS.length; i++) {
			JButton button = new JButton(ITEMS[i]);
			button.addActionListener(this);
			pnlButtons.add(button);
			buttons[i] = button;
		}
		checkButtons();
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
	}
	
	private void refreshTable() {
		DefaultTableModel dtm = new DefaultTableModel();
		String[] columns = DataAnalysisHelper.getColumnsForDimensions(dateDim, articleDim, shopDim);
		dtm.setColumnIdentifiers(columns);
		table.setModel(dtm);
		
		List<Map<String,String>> data = DataAnalysisHelper.getArticleData(dateDim, articleDim, shopDim);
		for(Map<String,String> row: data) {
			boolean containsNull = false;
			List<String> lstRow = new LinkedList<String>();
			for (int i = 0; i < columns.length; i++) {
				lstRow.add(row.get(columns[i]));
				if (row.get(columns[i]) == null) {
					containsNull = true;
				}
			}
			
			String[] strRow = new String[lstRow.size()];
			for (int i = 0; i < lstRow.size(); i++) {
				strRow[i] = lstRow.get(i);
			}
			//System.out.println(Arrays.toString(strRow));
			
			if (!containsNull) {
				dtm.addRow(strRow);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0]) {
			articleDim++;
		} else if (e.getSource() == buttons[1]) {
			articleDim--;
		} else if (e.getSource() == buttons[2]) {
			dateDim++;
		} else if (e.getSource() == buttons[3]) {
			dateDim--;
		} else if (e.getSource() == buttons[4]) {
			shopDim++;
		} else if (e.getSource() == buttons[5]) {
			shopDim--;
		}
		
		checkButtons();
		refreshTable();
	}
	
	public void checkButtons() {
		buttons[0].setEnabled(articleDim<DataAnalysisHelper.ARTICLE_COL.length-1);
		buttons[1].setEnabled(articleDim>0);
		buttons[2].setEnabled(dateDim<DataAnalysisHelper.DATE_COL.length-1);
		buttons[3].setEnabled(dateDim>0);
		buttons[4].setEnabled(shopDim<DataAnalysisHelper.SHOP_COL.length-1);
		buttons[5].setEnabled(shopDim>0);
	}
}
