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
import javax.swing.JTextField;

import de.dis2011.data.EstateAgent;

public class EstateLoginPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 4924519762691179730L;
	
	private JTextField login;
	private JPasswordField password;
	private JButton btnBack;
	
	private MainFrame mainFrame;

	public EstateLoginPanel(MainFrame mainFrame) throws HeadlessException {
		super();
		this.mainFrame = mainFrame;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// login panel
		JPanel pnlLogin = new JPanel();
		pnlLogin.setLayout(new BoxLayout(pnlLogin, BoxLayout.X_AXIS));
		JLabel lblLogin = new JLabel("login");
		int labelWidth = 80;
		lblLogin.setMinimumSize(new Dimension(labelWidth,0));
		lblLogin.setPreferredSize(new Dimension(labelWidth,lblLogin.getPreferredSize().height));
		lblLogin.setMaximumSize(new Dimension(labelWidth, lblLogin.getPreferredSize().height));
		pnlLogin.add(lblLogin);
		login = new JTextField("login");
		login.setMaximumSize(new Dimension(Integer.MAX_VALUE, login.getPreferredSize().height));
		pnlLogin.add(login);
		this.add(pnlLogin);
		
		// password panel
		JPanel pnlPassword = new JPanel();
		pnlPassword.setLayout(new BoxLayout(pnlPassword, BoxLayout.X_AXIS));
		JLabel lblPassword = new JLabel("password");
		lblPassword.setMinimumSize(new Dimension(labelWidth,0));
		lblPassword.setPreferredSize(new Dimension(labelWidth,lblPassword.getPreferredSize().height));
		lblPassword.setMaximumSize(new Dimension(labelWidth, lblPassword.getPreferredSize().height));
		pnlPassword.add(lblPassword);
		password = new JPasswordField("password");
		password.setMaximumSize(new Dimension(Integer.MAX_VALUE, password.getPreferredSize().height) );
		password.addActionListener(this);
		pnlPassword.add(password);
		this.add(pnlPassword);
		
		// back button
		btnBack = new JButton("back");
		btnBack.addActionListener(this);
		this.add(btnBack);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == password) {
			String strLogin = login.getText();
			String strPassword = String.valueOf(password.getPassword());
			String dbPassword = EstateAgent.loadPasswordOfLogin(strLogin);
			if ((dbPassword != null) && (dbPassword.equals(strPassword))) {
				mainFrame.goToPanel(new EstateMenuPanel(mainFrame)); 
			}
		} else if (e.getSource() == btnBack) {
			mainFrame.goToPanel(new MainPanel(mainFrame));
		}
	}

}
