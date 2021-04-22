package swing.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyVetoException;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GameOverFrame extends JInternalFrame{

	private static final long serialVersionUID = 1L;
	
	public JPanel panel;
	public JTextArea rankTextArea; 
	public JButton newGame;
	public JButton quit;
	
	public GameOverFrame() {
		
		try {
			this.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setTitle("Game Over");
		this.panel = new JPanel();
		this.panel.setPreferredSize(new Dimension(150, 350));
		this.panel.setLayout(new FlowLayout());
		
		
		this.rankTextArea = new JTextArea();
		this.rankTextArea.setFont(new Font("Verdana", Font.PLAIN, 16));
		this.rankTextArea.setOpaque(true);
		this.rankTextArea.setBackground(Color.PINK);
		this.rankTextArea.setPreferredSize(new Dimension(150, 250));
		this.panel.add(this.rankTextArea);
		
		this.newGame = new JButton("New Game");
		this.newGame.setPreferredSize(new Dimension(100, 50));
		this.panel.add(this.newGame);
		
		this.quit = new JButton("Quit");
		this.quit.setPreferredSize(new Dimension(100, 50));
		this.panel.add(this.quit);
		
		this.add(panel);
		
		this.setVisible(false);
	}

}
