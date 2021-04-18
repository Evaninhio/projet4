package interfaceGui;

import java.awt.Dimension;
import javax.swing.JDesktopPane;

public class DesktopFrame extends JDesktopPane{

	private static final long serialVersionUID = 1L;
	
	public GameOverFrame gameOverInternalFrame;
	public StartGameFrame startGameInternalFrame;
	
	
	
	public DesktopFrame() {
		
		this.gameOverInternalFrame = new GameOverFrame();
		this.startGameInternalFrame = new StartGameFrame();
		this.add(this.gameOverInternalFrame);
		this.add(this.startGameInternalFrame);
		this.setPreferredSize(new Dimension(200,100));
		this.setVisible(true);
	}
}
