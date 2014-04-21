package assignment2;

import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class Frame2 extends JFrame {

	private static final long serialVersionUID = 1L;
	JTextField productID;
	JTextField quantity;
	JTextField totalPrice;
	JButton btn1;
	JButton btn2;
	JLabel lbl0;
	JLabel lbl1;
	JLabel lbl2;

	Frame2() {

        this.setSize(300, 350);
        this.setResizable(false);
		setLayout(null);

		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		setLocation((width-this.getWidth())/2,(height-this.getHeight())/2);
		lbl0 = new JLabel( "Product ID : ");
		add(lbl0);
		
		
		lbl1 = new JLabel( "Quantity : ");
		add(lbl1);
		
		lbl2 = new JLabel( "Total Price : ");
		add(lbl2);
		
		productID = new JTextField(10);
		add(productID);
		
		quantity = new JTextField(10);
		add(quantity);

		totalPrice = new JTextField(10);
		add(totalPrice);
		
		btn1 = new JButton("Continue");
		btn2 = new JButton("Place Order");
		add(btn1);
		add(btn2);
		
		lbl0.setBounds(10, 10, 90, 30);
		productID.setBounds(105, 10, 100, 30);
		lbl1.setBounds(10, 60, 90, 30);
		quantity.setBounds(105, 60, 100, 30);

		btn1.setBounds(200,60,90, 30);
		
		lbl2.setBounds(10, 100, 90, 30);
		totalPrice.setBounds(105, 110, 100, 30);
		
		btn2.setBounds(90,160,150, 30);
	}
}
