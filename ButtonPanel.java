package assignment2;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class ButtonPanel extends JPanel{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JTextArea textArea = null;
	JScrollPane scrollPane = null;
	JLabel showLabel;
	JButton []buttons;
    private static final int btnNo = 12;
	ButtonPanel(){
	  
	 	buttons = new JButton[btnNo];
		this.setLayout(new GridLayout(3,4));
        for(int i=0;i<btnNo;i++){
        	buttons[i]= new JButton();
        	this.add(buttons[i]);
        }
        buttons[0].setText("Your Account");
        buttons[1].setText("All Products");
        buttons[2].setText("Choose Category"); 
        buttons[3].setText("Set Price Range");
        
        buttons[4].setText("Order Products"); 
        buttons[5].setText("Your Orders");
        buttons[6].setText("Review Products");
        buttons[7].setText("List All Reviews");
        
        buttons[8].setText("Like Reviews");
        buttons[9].setText("List All Likes");
        buttons[10].setText("Nearest Sellers");
        buttons[11].setText("Range Seller Search");
       

	}

	public void disableButton(){
		for(int i=0;i<buttons.length;i++)
			buttons[i].setEnabled(false);
	}

	public void enableButton(){
		for(int i=0;i<buttons.length;i++)

			buttons[i].setEnabled(true);
	}

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
}
