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

		this.layout = new Layout(30, 30, 0);
		this.setPath(new Path());

		this.setPlayers(new ArrayList<Player>(4));
		
		for(int i=1; i<5; i++) {
			this.getPlayers().add(new Player(i));
			System.out.println(this.getPlayers().get(i-1).getPlayerID());
		}
		
		this.setPreferredSize(new Dimension(550, 100));
	}

	/* Methods */

	
	public String ranking() {
		
		String ranking = "";
		
		for(Player player: this.getPlayers()) {
			ranking = ranking + player.getPseudo()+" is "+player.getRank()+"ème\n";
		}
		return ranking;
	}
	
	
	public void paintComponent(Graphics g) {

		for(int i=0;i<52;i++) {
			this.path.getPathMap().get(i).drawBoardSquare(g);
		}
		
		for(Player player : this.getPlayers()) { 
			
			player.DrawPlayer(g);
			
			player.getStartSquare().drawBoardSquare(g);
			
			for(int i=0;i<4;i++) {
				player.getHomeSquare().get(i).drawBoardSquare(g);
			}
			
			for(int i=1;i<6;i++) {
				player.getFinalSquare().get(player.getPlayerID()*100+10+i).drawBoardSquare(g);
			}
			
		}
		
		
		for(Player player : this.getPlayers()) {
			for(Pawn pawn : player.getPawn()) {
				pawn.DrawPawn(g, player);
			}
		}
		this.layout.draw(g);
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
