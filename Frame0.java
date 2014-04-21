package assignment2;

import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class Frame0 extends JFrame{

	private static final long serialVersionUID = 1L;
	JTextField txtfield;
	JButton btn1;
	String lbltext;
	JLabel label;
	String Type[] = { "Clothing", "Beauty", "Home & Kitchen", "Electronics" };
	JComboBox combo;

	Frame0(){		
		this.setSize(300, 80);
		this.setResizable(false);
		setLayout(new FlowLayout());
		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		setLocation((width-this.getWidth())/2,(height-this.getHeight())/2);
        combo= new JComboBox(Type);
        btn1 = new JButton("OK");
        add(combo);
        add(btn1);
	}
}
