package interfaceGui;

import java.util.ArrayList;
import java.util.List;

public class BoardSquare {
	
	/* Attributes */
	private int iD;
	private int xOnBoard;
	private int yOnBoard;
	private int nbPawnOn;
	private int idOfPlayerOn;
	private List<Pawn> listOfPawnOn;
	
	
	/* Constructor */
	public BoardSquare(int NewXOnBoard, int NewYOnBoard, int iD) {
		this.setiD(iD);
		this.setxOnBoard(NewXOnBoard);
		this.setyOnBoard(NewYOnBoard);
		this.listOfPawnOn = new ArrayList<Pawn>(2);
		this.nbPawnOn = 0;
	}
	
	
	/* Methods */
	
	public void eatOppositPawn() {
		this.listOfPawnOn.get(0).setCurrentSquare(this.listOfPawnOn.get(0).getHomeSquare());
		this.listOfPawnOn.get(0).setNbSquareTraveled(0);
		removePawn();
	}
	
	public boolean isOppositePawnOnTargetSquare(int id) {
		return nbPawnOn > 0 && idOfPlayerOn != id;
	}
	
	
	public void AddPawn(Pawn pawn) {
		this.listOfPawnOn.add(pawn);
		this.nbPawnOn++;
		this.setIdOfPlayerOn(pawn.getPlayerID()); 
	}
	
	public void removePawn() {
		
		this.listOfPawnOn.remove(this.listOfPawnOn.size()-1);
		this.setNbPawnOn(this.HowManyPawn()-1);
		if (this.HowManyPawn() == 0) this.setIdOfPlayerOn(0);
		
	}
	
	public int HowManyPawn() {
		return this.nbPawnOn;
	}
	
	public void setNbPawnOn(int nb) {
		this.nbPawnOn = nb;
	}
	
	
	public String ToString() {
		return "iD = "+this.getiD()+" nbPawnOn = "+this.HowManyPawn()+" IdOfPlayerOn = "+this.getIdOfPlayerOn();
	}
	
	
	/*Getters and Setters*/
	public int getiD() {
		return iD;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}


	public int getxOnBoard() {
		return xOnBoard;
	}


	public void setxOnBoard(int xOnBoard) {
		this.xOnBoard = xOnBoard;
	}


	public int getyOnBoard() {
		return yOnBoard;
	}


	public void setyOnBoard(int yOnBoard) {
		this.yOnBoard = yOnBoard;
	}


	public int getIdOfPlayerOn() {
		if(nbPawnOn > 0) {
			return idOfPlayerOn;
		}else {
			return 0;
		}
	}


	public void setIdOfPlayerOn(int idOfPlayerOn) {
		this.idOfPlayerOn = idOfPlayerOn;
	}
}
