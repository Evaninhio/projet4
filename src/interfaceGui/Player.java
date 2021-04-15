package interfaceGui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class Player{
	
	/*Attributes*/
	int w = Layout.w, h = Layout.h, offset = Layout.offset;
	private int playerID;
	private List<Pawn> pawn;
	private List<BoardSquare> homeSquare;
	private HashMap<Integer,BoardSquare> finalSquare;
	private BoardSquare startSquare;
	private boolean win;
	private int firstRoll;
	
	/*Constructors*/
	public Player() {}
	
	public Player(int newPlayerID) {
		
		this.setPlayerID(newPlayerID);
		this.win=false;
		this.setPawn(new ArrayList<Pawn>(4));
		this.getPawn().add(new Pawn(this.getPlayerID(), 1));
		this.getPawn().add(new Pawn(this.getPlayerID(), 2));
		this.getPawn().add(new Pawn(this.getPlayerID(), 3));
		this.getPawn().add(new Pawn(this.getPlayerID(), 4));
		
		this.homeSquare = new ArrayList<BoardSquare>(4);
		this.setFinalSquare(new HashMap<Integer, BoardSquare>(4));
		
		switch(this.getPlayerID()) {
		case 1: 
			this.homeSquare.add(new BoardSquare(offset+(3*w)/2, offset+(3*h)/2, 101));
			this.homeSquare.add(new BoardSquare(offset+(7*w)/2, offset+(3*h)/2, 102));
			this.homeSquare.add(new BoardSquare(offset+(3*w)/2, offset+(7*h)/2, 103));
			this.homeSquare.add(new BoardSquare(offset+(7*h)/2, offset+(7*h)/2, 104));
			for(int i=1; i<7; i++) {
				this.finalSquare.put(110+i, new BoardSquare(offset+i*w, offset+7*h, 110+i));
			}
			this.setStartSquare(Path.path.get(2));
			break;
		case 2: 
			this.homeSquare.add(new BoardSquare(offset+(3*w)/2+9*w, offset+(3*h)/2, 201));
			this.homeSquare.add(new BoardSquare(offset+(7*w)/2+9*w, offset+(3*h)/2, 202));
			this.homeSquare.add(new BoardSquare(offset+(3*w)/2+9*w, offset+(7*h)/2, 203));
			this.homeSquare.add(new BoardSquare(offset+(7*w)/2+9*w, offset+(7*h)/2, 204));
			for(int i=1; i<7; i++) {
				this.finalSquare.put(210+i, new BoardSquare(offset+7*w, offset+i*h, 210+i));
			}
			this.setStartSquare(Path.path.get(15));
			break;
		case 3: 
			this.homeSquare.add(new BoardSquare(offset+(3*w)/2+9*w, offset+(3*h)/2+9*h, 301));
			this.homeSquare.add(new BoardSquare(offset+(7*w)/2+9*w, offset+(3*h)/2+9*h, 302));
			this.homeSquare.add(new BoardSquare(offset+(3*w)/2+9*w, offset+(7*h)/2+9*h, 303));
			this.homeSquare.add(new BoardSquare(offset+(7*w)/2+9*w, offset+(7*h)/2+9*h, 304));
			for(int i=1; i<7; i++) {
				this.finalSquare.put(310+i, new BoardSquare(offset+14*w-i*w, offset+7*h, 310+i));
			}
			this.setStartSquare(Path.path.get(28));
			break;
		case 4: 
			this.homeSquare.add(new BoardSquare(offset+(3*w)/2, offset+(3*h)/2+9*h, 401));
			this.homeSquare.add(new BoardSquare(offset+(7*w)/2, offset+(3*h)/2+9*h, 402));
			this.homeSquare.add(new BoardSquare(offset+(3*w)/2, offset+(7*h)/2+9*h, 403));
			this.homeSquare.add(new BoardSquare(offset+(7*h)/2, offset+(7*h)/2+9*h, 404));
			for(int i=1; i<7; i++) {
				this.finalSquare.put(410+i, new BoardSquare(offset+7*w, offset+14*h-i*h, 410+i));
			}
			this.setStartSquare(Path.path.get(41));
			break;
		}
		
		for(int i=0; i<4; i++) {
			this.getPawn().get(i).setHomeSquare(this.homeSquare.get(i));
		}
		for(Pawn pawn : this.getPawn()) {
			pawn.setCurrentSquare(pawn.getHomeSquare());
			pawn.setHitBox();
		}
	}
	
	public void DrawPlayer(Graphics g) {
		switch(this.playerID) {
		case 1:
			g.setColor(Color.BLUE);
			break;
		case 2:
			g.setColor(Color.YELLOW);
			break;
		case 3:
			g.setColor(Color.RED);
			break;
		case 4:
			g.setColor(Color.GREEN);
			break;
		}
		g.fillRect(this.homeSquare.get(0).getxOnBoard(), this.homeSquare.get(0).getyOnBoard(), w, h);
		g.fillRect(this.homeSquare.get(1).getxOnBoard(), this.homeSquare.get(1).getyOnBoard(), w, h);
		g.fillRect(this.homeSquare.get(2).getxOnBoard(), this.homeSquare.get(2).getyOnBoard(), w, h);
		g.fillRect(this.homeSquare.get(3).getxOnBoard(), this.homeSquare.get(3).getyOnBoard(), w, h);
		
		for(Pawn pawn : this.getPawn()) {
			pawn.DrawPawn(g, this);
		}
	}




	/*Getter and Setter*/
	
	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerNumber) {
		this.playerID = playerNumber;
	}

	public List<Pawn> getPawn() {
		return pawn;
	}

	public void setPawn(List<Pawn> pawn) {
		this.pawn = pawn;
	}

	public BoardSquare getStartSquare() {
		return startSquare;
	}

	public void setStartSquare(BoardSquare startSquare) {
		this.startSquare = startSquare;
	}

	public HashMap<Integer, BoardSquare> getFinalSquare() {
		return finalSquare;
	}

	public void setFinalSquare(HashMap<Integer, BoardSquare> finalSquare) {

		this.finalSquare = finalSquare;
	}
	public boolean getWin(){
		return this.win;
	}

	public int getFirstRoll() {
		return firstRoll;
	}

	public void setFirstRoll(int firstRoll) {
		this.firstRoll = firstRoll;
	}

	public List<BoardSquare> getHomeSquare() {
		return homeSquare;
	}
}
