package interfaceGui;


import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class BoardPanel extends JPanel{

	/* Attributes */
	private static final long serialVersionUID = 1L;

	private Layout layout;
	private Path path;
	private List<Player> players;

	/* Constructors */
	public BoardPanel() {

		this.layout = new Layout(30, 30, 50);
		this.setPath(new Path());

		this.setPlayers(new ArrayList<Player>(4));
		
		for(int i=1; i<5; i++) {
			this.getPlayers().add(new Player(i));
			System.out.println(this.getPlayers().get(i-1).getPlayerID());
		}
		
		this.setPreferredSize(new Dimension(550, 100));
	}

	/* Methods */

	public void paintComponent(Graphics g) {

		this.layout.draw(g); 
		
		for(Player player : getPlayers()) { 
			player.DrawPlayer(g); 
		}
	}

	

	/* Getters and Setters */
	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	
}
