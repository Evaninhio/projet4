package interfaceGui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class BottomPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public JTextArea log;
	public JScrollPane scrollPane;
	
	public BottomPanel() {
		
		this.setPreferredSize(new Dimension(100,100));
		this.setBackground(Color.GRAY);
		this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		
		
		this.log = new JTextArea();
		this.log.setText("Game Logs");
		this.log.setLineWrap(true);
		this.log.setWrapStyleWord(true);
		this.log.setBorder(BorderFactory.createBevelBorder(1));
		this.log.setForeground(Color.BLACK);
		this.log.setEditable(false);
		
		
		
		
		this.scrollPane = new JScrollPane(this.log);
		this.scrollPane.setPreferredSize(new Dimension(600, 80));
		this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		this.add(this.scrollPane);
		
		
	}

}
