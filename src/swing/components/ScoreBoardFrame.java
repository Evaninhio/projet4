package swing.components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

public class ScoreBoardFrame extends JInternalFrame{

	private static final long serialVersionUID = 1L;
	
	public JLabel labelPlayer1;
	public JLabel labelPlayer2;
	public JLabel labelPlayer3;
	public JLabel labelPlayer4;
	
	
	public ScoreBoardFrame() {
		
		try {
			this.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setTitle("Score Board");
		this.setLayout(new FlowLayout());
		
		this.labelPlayer1 = new JLabel("Player 1 : -0-");
		this.labelPlayer1.setFont(new Font("Verdana", Font.PLAIN, 16));
		this.labelPlayer1.setPreferredSize(new Dimension(150, 50));
		this.add(this.labelPlayer1);
		
		this.labelPlayer2 = new JLabel("Player 2 : -0-");
		this.labelPlayer2.setFont(new Font("Verdana", Font.PLAIN, 16));
		this.labelPlayer2.setPreferredSize(new Dimension(150, 50));
		this.add(this.labelPlayer2);
		
		this.labelPlayer3 = new JLabel("Player 3 : -0-");
		this.labelPlayer3.setFont(new Font("Verdana", Font.PLAIN, 16));
		this.labelPlayer3.setPreferredSize(new Dimension(150, 50));
		this.add(this.labelPlayer3);
		
		this.labelPlayer4 = new JLabel("Player 4 : -0-");
		this.labelPlayer4.setFont(new Font("Verdana", Font.PLAIN, 16));
		this.labelPlayer4.setPreferredSize(new Dimension(150, 50));
		this.add(this.labelPlayer4);
		
		
		this.setVisible(false);
	}

}
