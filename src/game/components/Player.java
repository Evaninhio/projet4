package game.components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class Player{
	
	/*Attributes*/
	int w = Layout.w, h = Layout.h, offset = Layout.offset;
	private int playerID;		//The player ID (1,2,3,4)
	private List<Pawn> pawn;		//The list of 4 pawns of the player
	private List<BoardSquare> homeSquare;		//The 4 squares in home
	private HashMap<Integer,BoardSquare> finalSquare;		//The final line of the player
	private BoardSquare startSquare;	//The square where the pawn of the player go on when they leave home
	private int firstRoll;		//The first dice number that the player roll to decide who will start the game
	private int rank;		//The rank of the player at the end of the game
	private String pseudo;
	private int typeOfPlayer;  //1==AI && 2==Player
	
	
	
	
	/*Constructors*/
	
	public Player(int newPlayerID) {
		
		this.setPlayerID(newPlayerID);
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
		
		playerInitializer();
	}
	
	public String ToString() {
		return "playerID = "+this.getPlayerID()+" pseudo =  "+this.getPseudo()+" typeOfPlayer = "+this.typeOfPlayer;
	}
	
	public void playerInitializer() {
		
		for(Pawn pawn : this.getPawn()) {
			pawn.setCurrentSquare(pawn.getHomeSquare());
			pawn.setHitBox();
		}

		this.setRank(0);
		this.setFirstRoll(0);
		this.setPseudo(null);
	}
		
	
	public void DrawPlayer(Graphics g) {
		switch(this.playerID) {
		case 1:
			g.setColor(Color.BLUE);
			g.fillRect(offset, offset, 6*w, 6*h);
			g.clearRect(offset+w, offset+h, 4*w, 4*h);
			
			int[] xBluePolygon = {offset+6*w, offset+6*w, offset+(15*w)/2};
			int[] yBluePolygon = {offset+6*h, offset+9*h, offset+(15*h)/2};
			g.fillPolygon(xBluePolygon,yBluePolygon,3);
			break;
		case 2:
			g.setColor(Color.YELLOW);
			g.fillRect(offset+9*w+1, offset, 6*w, 6*h);
			g.clearRect(offset+10*w, offset+h, 4*w, 4*h);

			int[] xYellowPolygon = {offset+6*w, offset+9*w, offset+(15*w)/2};
			int[] yYellowPolygon = {offset+6*h, offset+6*h, offset+(15*h)/2};
			g.fillPolygon(xYellowPolygon,yYellowPolygon,3);
			break;
		case 3:
			g.setColor(Color.RED);
			g.fillRect(offset+9*w+1, offset+9*h+1, 6*w, 6*h);
			g.clearRect(offset+10*w, offset+10*h, 4*w, 4*h);
			
			int[] xRedPolygon = {offset+9*w, offset+9*w, offset+(15*w)/2};
			int[] yRedPolygon = {offset+6*h, offset+9*h, offset+(15*h)/2};
			g.fillPolygon(xRedPolygon,yRedPolygon,3);
			break;
		case 4:
			g.setColor(Color.GREEN);
			g.fillRect(offset, offset+9*h+1, 6*w, 6*h);
			g.clearRect(offset+w, offset+10*h, 4*w, 4*h);
			
			int[] xGreenPolygon = {offset+9*w, offset+6*w, offset+(15*w)/2};
			int[] yGreenPolygon = {offset+9*h, offset+9*h, offset+(15*h)/2};
			g.fillPolygon(xGreenPolygon,yGreenPolygon,3);
			g.setColor(Color.GREEN);
			break;
		}
	}

	public boolean hasFinishedGame(){
		
		return this.getFinalSquare().get((this.getPlayerID()*100)+16).HowManyPawn()== 4;
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
	
	public int getFirstRoll() {
		return firstRoll;
	}

	public void setFirstRoll(int firstRoll) {
		this.firstRoll = firstRoll;
	}

	public List<BoardSquare> getHomeSquare() {
		return homeSquare;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}


	public String getPseudo() {
		return pseudo;
	}


	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}


	public int getTypeOfPlayer() {
		return typeOfPlayer;
	}


	public void setTypeOfPlayer(String typeOfPlayer) {
		
		if(typeOfPlayer == "AI") {
			this.typeOfPlayer = 1;
		}else {
			this.typeOfPlayer = 2;
		}
	}
}
