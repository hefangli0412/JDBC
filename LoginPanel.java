package assignment2;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	JTextField username;
	JPasswordField password;
	JButton login;
	JButton signup;
	private String UserName;

	public void disablePanel() {
		if (this.getComponentCount() == 0)
			return;
		Component[] comps = new Component[this.getComponentCount()];
		comps = this.getComponents();
		for (int i = 0; i < comps.length; i++) {
			comps[i].disable();
		}
	}

	public void enablePanel() {
		if (this.getComponentCount() == 0)
			return;
		Component[] comps = new Component[this.getComponentCount()];
		comps = this.getComponents();
		for (int i = 0; i < comps.length; i++) {
			comps[i].enable();
		}
	}

	LoginPanel(){
		this.setBounds(810, 30, 250, 100);
		this.setLayout(new GridLayout(3,2));
		this.add(new Label("UserName: "));
		username = new JTextField();
        this.add(username);
		this.add(new Label("Password: "));
		password = new JPasswordField();
        this.add(password);
        login = new JButton("login");
        signup = new JButton("signup");
        this.add(login);
        this.add(signup);
	}
	public String getUserName(){
		return UserName;
	}
	public void setUserName(String user){
		UserName=user;
	}
}


