package interfaceGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Pawn{
	
	/*Attributes*/

	private int pawnID;
	private int playerID;
	private int w = Layout.w;
	private int h = Layout.h;
	private BoardSquare homeSquare;
	private BoardSquare currentSquare;
	private Rectangle hitBox;
	private int nbSquareTraveled;
	
	/*Constructors*/
	public Pawn() {}
	
	public Pawn(int newPlayerID, int newPawnID) {
		this.setPlayerID(newPlayerID);
		this.setPawnID(newPawnID);
		this.setNbSquareTraveled(0);
		
	}

	/* Methods */
	
	public boolean hasAllPathTraveled(int diceValue) {
		return this.nbSquareTraveled+diceValue > 50;
	}
	
	public void setHitBox() {
		this.hitBox = new Rectangle(this.currentSquare.getxOnBoard(), this.currentSquare.getyOnBoard(),w, h);
	}
	
	public void moveHitBox() {
		this.hitBox.x = this.currentSquare.getxOnBoard();
		this.hitBox.y = this.currentSquare.getyOnBoard();
	}
	
	
	public void DrawPawn(Graphics g, Player player) {
		switch(player.getPlayerID()) {
		case 1:
			g.setColor(new Color(18, 147, 241));
			break;
		case 2:
			g.setColor(new Color(220, 220, 35));
			break;
		case 3:
			g.setColor(new Color(255, 70, 70));
			break;
		case 4:
			g.setColor(new Color(0, 175, 0));
			break;
		}
		
		g.fillOval(this.currentSquare.getxOnBoard()+2, this.currentSquare.getyOnBoard()+2, w-4, h-4);
		g.setColor(Color.BLACK);
		g.drawOval(this.currentSquare.getxOnBoard()+2, this.currentSquare.getyOnBoard()+2, w-4, h-4);
		g.setColor(Color.WHITE);
		g.fillOval(this.currentSquare.getxOnBoard()+10, this.currentSquare.getyOnBoard()+10, w-20, h-20);
	}
	
	public String ToString() {
		return "pawn n°"+this.getPawnID()+" of the player n°"+this.playerID+" on the point: x="+this.currentSquare.getxOnBoard()+" y="+this.currentSquare.getyOnBoard();
	}
	
	public boolean isAtHome() {
		if(this.currentSquare == this.homeSquare) {
			System.out.println("Pawn is at home");
			return true;
		}else {
			System.out.println("Pawn is on the path");
			return false;
		}
	}
	
	
	/*Getters and Setters*/
	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerLocation) {
		this.playerID = playerLocation;
	}
	
	public int getPawnID() {
		return pawnID;
	}

	public void setPawnID(int iD) {
		this.pawnID = iD;
	}

	public BoardSquare getHomeSquare() {
		return homeSquare;
	}

	public void setHomeSquare(BoardSquare homeSquare) {
		this.homeSquare = homeSquare;
	}

	public BoardSquare getCurrentSquare() {
		return currentSquare;
	}

	public void setCurrentSquare(BoardSquare currentSquare) {
		this.currentSquare = currentSquare;
		this.currentSquare.AddPawn(this);
		
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public int getNbSquareTraveled() {
		return nbSquareTraveled;
	}

	public void setNbSquareTraveled(int nbSquare) {
		this.nbSquareTraveled = nbSquare;
	}
}
