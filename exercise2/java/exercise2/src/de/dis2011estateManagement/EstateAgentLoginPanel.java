package de.dis2011estateManagement;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class EstateAgentLoginPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = -6990942376290020635L;
	
	private JPasswordField password;
	
	private MainFrame mainFrame;
	
	public EstateAgentLoginPanel(MainFrame mainFrame) throws HeadlessException {
		super();
		this.mainFrame = mainFrame;
		
		JPanel pnlPassword = new JPanel();
		pnlPassword.setLayout(new BoxLayout(pnlPassword, BoxLayout.X_AXIS));
		pnlPassword.add(new JLabel("Enter password: "));
		password = new JPasswordField("password");
		password.addActionListener(this);
		pnlPassword.add(password);
		this.add(pnlPassword);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == password) {
			if (String.valueOf(password.getPassword()).equals("password")) {
				mainFrame.goToEstateAgentPanel();
			}
		}
	}
}
