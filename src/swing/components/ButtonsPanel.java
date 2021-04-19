package swing.components;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ButtonsPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	public JButton diceButton;
	public JButton passButton;
	public JLabel diceLabel;
	public JPanel dicePanel;
	
	public ButtonsPanel(){

		this.setBackground(Color.GRAY);
		this.setPreferredSize(new Dimension(200, 100));
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		
		this.dicePanel = new JPanel();
		this.dicePanel.setLayout(new GridLayout(3,1));
		this.dicePanel.setVisible(false);
		DiceButtonInitializer();
		DiceLabelInitilizer();
		PassButtonInitializer();
		this.dicePanel.add(this.diceButton);
		this.dicePanel.add(this.diceLabel);
		this.dicePanel.add(this.passButton);
		
		this.add(dicePanel);
	}
	
	private void DiceLabelInitilizer() {
		this.diceLabel = new JLabel();
		this.diceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.diceLabel.setVerticalAlignment(SwingConstants.CENTER);
		this.diceLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
	}
	
	private void DiceButtonInitializer() {
		
		this.diceButton = new JButton("roll dice");
		this.diceButton.setPreferredSize(new Dimension(100,50));
	}
	
	private void PassButtonInitializer() {
		this.passButton = new JButton("Pass");
		this.passButton.setPreferredSize(new Dimension(100,50));
		
	}
}
