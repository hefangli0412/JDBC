package assignment2;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class ResultPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea resultArea = null;
	JScrollPane scrollPane = null;
	ResultPanel(){
		setResultArea();
	}

	public void setResultArea(){
		resultArea = new JTextArea(10,30);
		resultArea.setLineWrap(true);
		resultArea.setBounds(10, 140,750, 250);

		scrollPane = new JScrollPane(resultArea);
		add(scrollPane);

	}
}