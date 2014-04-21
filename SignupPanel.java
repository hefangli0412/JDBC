package assignment2;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class SignupPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	JButton login;
	JButton signup;
	JPasswordField password;
	JPasswordField password2;

	JTextField country;
	JTextField state;
	JTextField city;
	JTextField email;
	JTextField birthday;
	JTextField fname;
	JTextField lname;
	JTextField str_no;
	JTextField str_address;
	JTextField zip;

	public void disablePanel(){
		if(this.getComponentCount()==0)
			return ;
		Component[] comps= new Component[this.getComponentCount()];
		comps = this.getComponents();
		for(int i=0;i<comps.length;i++){
				comps[i].disable();
		}
		disableButton();
	}

	public void enablePanel(){
		if(this.getComponentCount()==0)
			return ;
		Component[] comps= new Component[this.getComponentCount()];
		comps = this.getComponents();
		for(int i=0;i<comps.length;i++){
				comps[i].enable();
		}
		enableButton();
	}

	SignupPanel(){
		this.setBounds(810, 150, 250, 300);
		this.setLayout(new GridLayout(12,2));
		
		email = new JTextField();
		password = new JPasswordField();
		password2 = new JPasswordField();
		fname = new JTextField();
		lname = new JTextField();
		city = new JTextField();
		birthday = new JTextField();
		str_no = new JTextField();
		str_address = new JTextField();
		zip = new JTextField();
		signup = new JButton("signup");
		state = new JTextField();
		
		this.add(new Label("Email: "));
		this.add(email);
		this.add(new Label("Password: "));
		
        this.add(password);
		this.add(new Label("ReEnter Password: "));
        this.add(password2);
        
		this.add(new Label("First Name: "));
        this.add(fname);
		this.add(new Label("Last Name: "));
        this.add(lname);
        
		this.add(new Label("Birthday : "));
        this.add(birthday);
        
		this.add(new Label("strNo : "));
        this.add(str_no);
        
		this.add(new Label("strAdress : "));
        this.add(str_address);

		this.add(new Label("City: "));
        this.add(city);
        
        this.add(new Label("State : "));
        this.add(state);
        
        this.add(new Label("Zip : "));
        this.add(zip);
       
        this.add(new Label(""));
        this.add(signup);
                        
        disablePanel();
	}

	public void disableButton(){
		signup.setEnabled(false);
	}
	public void enableButton(){
		signup.setEnabled(true);
	}
}