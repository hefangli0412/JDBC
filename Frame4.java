package assignment2;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Frame4 extends JFrame{
	
	private static final long serialVersionUID = 1L;
	JTextField txtfield;
	JButton btn1;
	JLabel lbl;
	Frame4(String text0,String text1) {
        this.setSize(300, 180);
        this.setResizable(false);
		setLayout(null);

		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		setLocation((width-this.getWidth())/2,(height-this.getHeight())/2);
		lbl = new JLabel();
			
		lbl.setText(text0);
		
		txtfield = new JTextField(10);
		
		btn1 = new JButton(text1);
		
		lbl.setBounds(10, 10, 90, 30);
		txtfield.setBounds(105, 10, 100, 30);
		btn1.setBounds(30, 50, 160, 35);
		add(lbl);
		add(txtfield);
		add(btn1);
	}			
}
