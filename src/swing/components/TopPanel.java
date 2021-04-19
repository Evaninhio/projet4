package swing.components;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TopPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	public JButton startGame;
	public JLabel notification;
	
	public TopPanel() {
		
		this.setPreferredSize(new Dimension(100,100));
		this.setBackground(Color.GRAY);
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		
		this.startGame = new JButton("Start Game");
		this.startGame.setVisible(true);
		this.startGame.setPreferredSize(new Dimension(100, 50));
		this.add(this.startGame);
		
			
		this.notification = new JLabel();
		this.notification.setPreferredSize(new Dimension(500, 50));
		this.notification.setForeground(Color.WHITE);
		this.notification.setHorizontalAlignment(SwingConstants.CENTER);
		this.notification.setVerticalAlignment(SwingConstants.CENTER);
		this.notification.setFont(new Font("Verdana", Font.PLAIN, 18));
		this.notification.setVisible(false);
		this.add(this.notification);		
		
	}
}
