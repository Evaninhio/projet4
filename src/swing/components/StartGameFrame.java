package swing.components;

import java.awt.GridLayout;
import java.beans.PropertyVetoException;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;

public class StartGameFrame extends JInternalFrame{
	
	private static final long serialVersionUID = 1L;

	private String[] typePlayer = {"player","AI"};
	public JTextField pseudoPlayer1;
	public JTextField pseudoPlayer2;
	public JTextField pseudoPlayer3;
	public JTextField pseudoPlayer4;
	public JComboBox<String> choiceTypePlayer1;
	public JComboBox<String> choiceTypePlayer2;
	public JComboBox<String> choiceTypePlayer3;
	public JComboBox<String> choiceTypePlayer4;
	
	
	
	public StartGameFrame(){
		try {
			this.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setTitle("Start Game");
		this.setVisible(true);
		this.setLayout(new GridLayout(6,2));
		
		this.pseudoPlayer1 = new JTextField("Player 1");
		this.pseudoPlayer2 = new JTextField("Player 2");
		this.pseudoPlayer3 = new JTextField("Player 3");
		this.pseudoPlayer4 = new JTextField("Player 4");
		
		this.choiceTypePlayer1 = new JComboBox<String>(this.typePlayer);
		this.choiceTypePlayer2 = new JComboBox<String>(this.typePlayer);
		this.choiceTypePlayer3 = new JComboBox<String>(this.typePlayer);
		this.choiceTypePlayer4 = new JComboBox<String>(this.typePlayer);
		
		this.add(pseudoPlayer1);
		this.add(choiceTypePlayer1);
		this.add(pseudoPlayer2);
		this.add(choiceTypePlayer2);
		this.add(pseudoPlayer3);
		this.add(choiceTypePlayer3);
		this.add(pseudoPlayer4);
		this.add(choiceTypePlayer4);
		
		
	}
}
