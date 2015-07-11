package de.dis2011estateManagement;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class EstateAgentLoginPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -6990942376290020635L;

	private JPasswordField password;
	private JButton btnBack;

	private MainFrame mainFrame;

	public EstateAgentLoginPanel(MainFrame mainFrame) throws HeadlessException {
		super();
		this.mainFrame = mainFrame;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel pnlPassword = new JPanel();
		pnlPassword.setLayout(new BoxLayout(pnlPassword, BoxLayout.X_AXIS));
		pnlPassword.add(new JLabel("Enter password: "));
		password = new JPasswordField("password");
		password.setMaximumSize(new Dimension(Integer.MAX_VALUE, password.getPreferredSize().height) );
		password.addActionListener(this);
		pnlPassword.add(password);
		this.add(pnlPassword);
		
		btnBack = new JButton("back");
		btnBack.addActionListener(this);
		this.add(btnBack);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == password) {
			if (String.valueOf(password.getPassword()).equals("password")) {
				mainFrame.goToPanel(new EstateAgentPanel(mainFrame));
			}
		} else if (e.getSource() == btnBack) {
			mainFrame.goToPanel(new MainPanel(mainFrame));
		}
	}
}
