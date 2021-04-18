package interfaceGui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class BoardSquare {
	
	/* Attributes */
	int w = Layout.w, h = Layout.h;
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
		this.setListOfPawnOn(new ArrayList<Pawn>(2));
		this.nbPawnOn = 0;
	}
	
	public void drawBoardSquare(Graphics g) {
		
		int player = this.iD/100;
		
		if(player == 1 || this.iD == 2) {
			g.setColor(Color.BLUE);
		}else if(player == 2 || this.iD == 15) {
			g.setColor(Color.YELLOW);
		}else if(player == 3 || this.iD == 28) {
			g.setColor(Color.RED);
		}else if(player == 4 || this.iD == 41) { 
			g.setColor(Color.GREEN);
		}else {
			g.setColor(Color.WHITE);
		}
		
		if(this.nbPawnOn == 2) {
			g.setColor(Color.MAGENTA);
		}
		
		if(this.nbPawnOn > 2) {
			g.setColor(Color.PINK);
		}
		
		if(this.nbPawnOn < 0) {
			g.setColor(Color.CYAN);
		}
		
		if(this.nbPawnOn == 1) {
			g.setColor(Color.ORANGE);
		}
		
		g.fillRect(this.xOnBoard, this.yOnBoard, w, h);
		g.setColor(Color.BLACK);
		g.drawRect(this.xOnBoard, this.yOnBoard, w, h);
	}
	
	
	
	
	
	/* Methods */
	
		
	public void AddPawn(Pawn pawn) {
		this.listOfPawnOn.add(pawn);
		this.nbPawnOn++;
		this.setIdOfPlayerOn(pawn.getPlayerID()); 
	}
	
	public void removePawn() {
		
		this.listOfPawnOn.remove(this.listOfPawnOn.size()-1);
		
		this.setNbPawnOn(this.HowManyPawn()-1);
		
		if (this.HowManyPawn() == 0) {
			this.setIdOfPlayerOn(0);
		}
		
	}
	
	public int HowManyPawn() {
		return this.nbPawnOn;
	}
	
	public void setNbPawnOn(int nb) {
		this.nbPawnOn = nb;
	}
	
	
	public String ToString() {
		return "iD = "+this.getiD()+" nbPawnOn = "+this.HowManyPawn()+" IdOfPlayerOn = "+this.getIdOfPlayerOn()+"\n"
				+" sizeOfList = "+this.getListOfPawnOn().size();
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
	
	public List<Pawn> getListOfPawnOn() {
		return listOfPawnOn;
	}

	public void setListOfPawnOn(List<Pawn> listOfPawnOn) {
		this.listOfPawnOn = listOfPawnOn;
	}
}
