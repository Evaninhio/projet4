package interfaceGui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

public class Window extends JFrame implements ActionListener, MouseListener{

	private static final long serialVersionUID = 1L;
	private final BoardPanel boardPanel;
	private final ButtonsPanel buttonsPanel;
	private final TopPanel topPanel;
	private final BottomPanel bottomPanel;
	private int diceNumber;
	private int currentPlayer;
	private int manage;
	/**
	 * variable can manage the screen with different values
	 * values:
	 * 		
	 * 		 0 : we can start the game
	 * 		10 : we can do the first roll to decide which one is the first player
	 *		20 : we can throw the dice
	 *		30 : we can select a pawn
	 *		40 : we can pass 
	 *
	 */
	
	
	public Window(){
		this.setTitle("Ludo Game");
		this.setSize(800,800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container mainContainer = this.getContentPane();
		mainContainer.setLayout(new BorderLayout());
		this.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		
		this.boardPanel = new BoardPanel();
		mainContainer.add(boardPanel, BorderLayout.CENTER);
		
		this.buttonsPanel = new ButtonsPanel();		
		mainContainer.add(buttonsPanel, BorderLayout.EAST);
		
		this.topPanel = new TopPanel();
		mainContainer.add(topPanel, BorderLayout.NORTH);
		
		this.bottomPanel = new BottomPanel();
		mainContainer.add(bottomPanel, BorderLayout.SOUTH);
		
		this.topPanel.startGame.addActionListener(this);
		this.buttonsPanel.diceButton.addActionListener(this);
		this.buttonsPanel.passButton.addActionListener(this);
		this.boardPanel.addMouseListener(this);
		
		this.setVisible(true);
		this.manage=0;
	}

	/***
	 *
	 * @param e
	 * if it is not the start of the game we throw the dice
	 * else we set the dice at 0 and the player at player 2
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.buttonsPanel.diceButton) {		//Action performed when the dice button is clicked
			
			if(manage == 10) {

				if(this.currentPlayer < 4) {
					diceRoll();
					this.boardPanel.getPlayers().get(this.currentPlayer-1).setFirstRoll(this.diceNumber);
					addLogsAndNotification("Player "+this.currentPlayer+" roll a " + this.diceNumber);
					this.currentPlayer++;
					addLogsAndNotification("it's the turn of player " + this.currentPlayer);
				
				}else if(this.currentPlayer == 4) {
					
					diceRoll();
					this.boardPanel.getPlayers().get(this.currentPlayer-1).setFirstRoll(this.diceNumber);
					addLogsAndNotification("Player "+this.currentPlayer+" roll a " + this.diceNumber);
					
					this.currentPlayer = firstPlayer(this.boardPanel.getPlayers().get(0).getFirstRoll(), this.boardPanel.getPlayers().get(1).getFirstRoll(), this.boardPanel.getPlayers().get(2).getFirstRoll(), this.boardPanel.getPlayers().get(3).getFirstRoll());
					addLogsAndNotification("Player n°"+this.currentPlayer+" is the first to play");
					this.diceNumber = 0;
					this.buttonsPanel.diceLabel.setText("" + this.diceNumber);
					manage = 20;
				}
				
			}else if(manage==20) {					//we can use the button only if we have manage set to 2
				diceRoll();
				addLogsAndNotification("Player "+this.currentPlayer+" roll a " + this.diceNumber);
				if (canMovePawn(this.boardPanel.getPlayers().get(this.currentPlayer-1))){
					manage=30;
					if(this.currentPlayer!=0){
						IATurn();
						this.repaint();

						if (diceto6()) {
							this.manage = 20;
						} else {
							nextPlayer();
						}
					}
				}else{
					nextPlayer();
				}
			}
		}else if (e.getSource() == this.topPanel.startGame) {		//Action performed when the startGame button is clicked
			
			if(manage==0) {
				addLogsAndNotification("Game started");
				this.diceNumber = 0;
				this.buttonsPanel.diceLabel.setText("" + this.diceNumber);
				this.currentPlayer = 1;
				addLogsAndNotification("it's the turn of player " + this.currentPlayer);
				this.topPanel.startGame.setVisible(false);
				manage=10;
			}
		}else if(e.getSource() == this.buttonsPanel.passButton) {		//action performed when the pass button is clicked
			if(this.manage == 30) {
				nextPlayer();
				this.diceNumber = 0;
				this.buttonsPanel.diceLabel.setText("" + this.diceNumber);
			}
		}
	}
	
	/****
	 *
	 * set the attribute dicenumber of this class with a random number between 1 and 6
	 *
	 */

	public void diceRoll() {
		Random rand = new Random();
		this.diceNumber = rand.nextInt(6) + 1;
		this.buttonsPanel.diceLabel.setText("" + this.diceNumber);
	}
	
	
	
	/****
	 *
	 * @param rollPlayer1
	 * @param rollPlayer2
	 * @param rollPlayer3
	 * @param rollPlayer4
	 * @return firstPlayer
	 * 
	 * Return the player which will be the first to play the game
	 *
	 */
	
	
	public static int firstPlayer(int rollPlayer1, int rollPlayer2, int rollPlayer3, int rollPlayer4) {

	    int firstPlayer = 1;
	    int max = rollPlayer1;

	    if (rollPlayer2 > max)
	        firstPlayer = 2;
	    	max = rollPlayer2;
	    if (rollPlayer3 > max)
	        firstPlayer = 3;
	    	max = rollPlayer3;
	    if (rollPlayer4 > max)
	        firstPlayer = 4;
	    	max = rollPlayer4;

	     return firstPlayer;
	}

	public void addLogsAndNotification(String str) {
		this.bottomPanel.log.append("\n"+str);
		this.topPanel.notification.setText(str);
	}
	
	/***
	 *
	 * @param e: the mouse click
	 * 
	 * if we detect a click in a paw we move it but if we have a 6 we can out a paw of the home
	 * 
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(manage==30) {
			
			Point point = e.getPoint();
			Player currentPlayer = this.boardPanel.getPlayers().get(this.currentPlayer-1);
			Pawn currentPawn = null;
			Boolean pawnSelected = false;
			
			/* Find the pawn clicked */
			
			if(currentPlayer.getPawn().get(0).getHitBox().contains(point)) {
				currentPawn = currentPlayer.getPawn().get(0);
				pawnSelected = true;
				
			}else if(currentPlayer.getPawn().get(1).getHitBox().contains(point)) {
				currentPawn = currentPlayer.getPawn().get(1);
				pawnSelected = true;
				
			}else if(currentPlayer.getPawn().get(2).getHitBox().contains(point)) {
				currentPawn = currentPlayer.getPawn().get(2);
				pawnSelected = true;
				
			}else if(currentPlayer.getPawn().get(3).getHitBox().contains(point)) {
				currentPawn = currentPlayer.getPawn().get(3);
				pawnSelected = true;
			}
			
			
			
			if (pawnSelected) { //If a pawn is selected
				
				if(currentPawn.isAtHome()) { //if the pawn selected is at home
					
					if(diceto6()) { //If the player rolled a 6
						moveOutOfHomePawn(currentPawn, currentPlayer);

						
					}else {	//If the player rolled another number than 6
						addLogsAndNotification("Sorry you can't get out of home a pawn, you may roll a 6");
					}
					
				}else { //If the pawn selected is on the path	
					moveForwardPawn(currentPawn, currentPlayer);
					
				}
			}
			
			this.repaint();
		}
	}
	
	/****
	 *
	 * @param pawn
	 * @param player
	 * Move a pawn out of home, in the start square
	 *
	 */
	public void moveOutOfHomePawn(Pawn pawn, Player player) {

		if (player.getStartSquare().HowManyPawn() == 2) {
			System.out.println("There is a Block on your start square");
		} else if (player.getStartSquare().HowManyPawn() == 1 && player.getStartSquare().getIdOfPlayerOn() == player.getPlayerID()) {
			pawn.setCurrentSquare(player.getStartSquare());
			pawn.setHitBox();
			player.getStartSquare().setNbPawnOn(2);
			addLogsAndNotification("Player n°"+player.getPlayerID()+" ");
			this.manage = 20;

		} else if (player.getStartSquare().HowManyPawn() == 1 && player.getStartSquare().getIdOfPlayerOn() != player.getPlayerID()) {
			player.getStartSquare().eatOppositPawn();
			pawn.setCurrentSquare(player.getStartSquare());
			pawn.setHitBox();
			player.getStartSquare().setNbPawnOn(2);
			pawn.getCurrentSquare().setIdOfPlayerOn(player.getPlayerID());
			this.manage = 20;

		} else if (player.getStartSquare().HowManyPawn() == 0) {
			pawn.setCurrentSquare(player.getStartSquare());
			pawn.setHitBox();
			player.getStartSquare().setNbPawnOn(1);
			pawn.getCurrentSquare().setIdOfPlayerOn(player.getPlayerID());
			this.manage = 20;
		}
	}


	
	/***
	 *
	 * @param player
	 * @param targetSquare
	 * @return true if the pawn is a pawn of the player
	 * 
	 */
	public boolean isOppositePawnOnTargetSquare(Player player, BoardSquare targetSquare) {
		return targetSquare.HowManyPawn()>0 && targetSquare.getIdOfPlayerOn() != player.getPlayerID();
	}

	
	
	/****
	 *
	 * @param pawn
	 * 
	 * return true if there is a block between the pawn and his target square, else return false
	 *
	 */
	
	public boolean isBlockOnWay(Pawn pawn) {
		Path path = this.boardPanel.getPath();

		for(int i=1; i<this.diceNumber+1; i++) {
			System.out.println((pawn.getCurrentSquare().getiD()+i)%52);
			if(path.getPathMap().get((pawn.getCurrentSquare().getiD()+i)%52).HowManyPawn() == 2 && path.getPathMap().get((pawn.getCurrentSquare().getiD()+i)%52).getIdOfPlayerOn() != pawn.getPlayerID()) {
				return true;
			}
		}

		return false;
	}

	/****
	 *
	 * @param pawn
	 * @param player
	 * 
	 * Move the pawn forward
	 *
	 */
	
	public void moveForwardPawn(Pawn pawn, Player player) {

		if (isBlockOnWay(pawn)) {    //If the way is blocked then the pawn can't be move forward
			System.out.println("A Block blocked the path");
		} else {

				int indexTargetSquare = (this.diceNumber + pawn.getCurrentSquare().getiD()) % 52;
				BoardSquare targetSquare = this.boardPanel.getPath().getPathMap().get(indexTargetSquare);

				if (isOppositePawnOnTargetSquare(player, targetSquare)) {  //If there is an opposite pawn on the target square

					targetSquare.eatOppositPawn();
					pawn.getCurrentSquare().removePawn();
					pawn.setCurrentSquare(targetSquare);
					pawn.setHitBox();
					pawn.setNbSquareTraveled(pawn.getNbSquareTraveled() + this.diceNumber);

				} else if (pawn.hasAllPathTraveled(this.diceNumber)) {    //If the pawn has traveled the complete path

					if (pawn.getCurrentSquare().getiD() + this.diceNumber <= 100 * player.getPlayerID() + 16) {        //If the target square is not out of the limit of the final line of the player
						indexTargetSquare = (100 * player.getPlayerID()) + 10 + ((pawn.getNbSquareTraveled() + this.diceNumber) - 50);
						targetSquare = player.getFinalSquare().get(indexTargetSquare);
						pawn.getCurrentSquare().removePawn();
						pawn.setCurrentSquare(targetSquare);
						pawn.setHitBox();
						pawn.setNbSquareTraveled(pawn.getNbSquareTraveled() + this.diceNumber);
					}


				} else {        //If it is a simple forward movement without any other action on the way
					pawn.getCurrentSquare().removePawn();
					pawn.setCurrentSquare(targetSquare);
					pawn.setHitBox();
					pawn.setNbSquareTraveled(pawn.getNbSquareTraveled() + this.diceNumber);
				}

			if (diceto6()) {
				this.manage = 20;
			} else {
				nextPlayer();
			}
		}
	}
	
	/****
	 *
	 * Change the attribute currentPlayer of this class with the next player 
	 *
	 */
	
	public void nextPlayer() {
		if(this.currentPlayer < 4) {
			this.currentPlayer++;
		}else {
			this.currentPlayer = 1;
		}
		addLogsAndNotification("it's the turn of player " + this.currentPlayer);
		this.manage = 20;
	}
	
	
	/****
	 * 
	 * @param player
	 * 
	 * Check for each pawn of the player if it can move
	 *
	 */
	
	public boolean canMovePawn(Player player) {
		int compt=0;

		for ( Pawn pawn : player.getPawn()){
			if(pawnCanBePlay(pawn)){
				compt++;
			}
		}
		
		if (compt == 0) {
			addLogsAndNotification("Player n°"+player.getPlayerID()+" cannot play, must pass the turn");
		}
		
		return compt>0 ; 
	}
	
	
	/****
	 *
	 * @param pawn
	 * @return boolean
	 * 
	 * return true if the pawn is in the final line of his owner
	 *
	 */	
	
	public boolean IsInFinalsSquares(Pawn pawn){

		return  this.boardPanel.getPlayers().get(this.currentPlayer - 1).getFinalSquare().containsValue(pawn.getCurrentSquare());
	}

	/****
	 *
	 * @param pawn
	 * @param player
	 * @return boolean
	 * 
	 * Do the movement
	 *
	 */	
	
	public boolean IsInHome(Pawn pawn){
		return pawn.getCurrentSquare().getiD() >= this.boardPanel.getPlayers().get(this.currentPlayer - 1).getHomeSquare().get(0).getiD() && pawn.getCurrentSquare().getiD() <= this.boardPanel.getPlayers().get(this.currentPlayer - 1).getHomeSquare().get(3).getiD();
	}

	/****
	 *
	 * @param player
	 * @return pawn
	 * 
	 * return the first pawn of the list of pawn of the player which is at home. No pawn at home = return null
	 *
	 */
	
	public Pawn APawnIsInHome(Player player){
		for(Pawn pawn : player.getPawn()){
			if(IsInHome(pawn)){
				return pawn;
			}
		}
		return null;
	}
	
	/****
	 *
	 * @param pawn
	 * 
	 * Move the pawn in the final square 
	 *
	 */
	
	public void MoveInFinalSquare(Pawn pawn){
		move(pawn,this.boardPanel.getPlayers().get(this.currentPlayer-1).getFinalSquare().get(100 * this.currentPlayer + 16).getiD() );
		pawn.setNbSquareTraveled(pawn.getNbSquareTraveled()+1);
		addLogsAndNotification("Player n°"+pawn.getPlayerID()+" move in final square his pawn n°"+pawn.getPawnID());
	}

	/****
	 *
	 * @param pawn
	 * @param IDSquare
	 * 
	 * Move the pawn to the target square
	 *
	 */

	public void move(Pawn pawn, int IDSquare){
		
		BoardSquare targetSquare = null;
		
		if(IDSquare>52){
			 targetSquare = this.boardPanel.getPlayers().get(this.currentPlayer-1).getFinalSquare().get(IDSquare);
		}else {
			 targetSquare = this.boardPanel.getPath().getPathMap().get(IDSquare);
		}
		
		
		pawn.getCurrentSquare().removePawn();
		pawn.setCurrentSquare(targetSquare);
		pawn.setHitBox();
		
		
	}
	
	
	/****
	 * 
	 * Return a pawn of the current player which can go in final line, else return null
	 * @return pawn
	 *
	 */
	
	public Pawn canGoFinalSquares(){
		
			for(Pawn pawn : this.boardPanel.getPlayers().get(this.currentPlayer -1).getPawn()){
				if (pawn.hasAllPathTraveled(this.diceNumber) && pawnCanBePlay(pawn)) {        //If the target square is not out of the limit of the final line of the player
					return pawn;
				}
			}
			return null;
	}

	/****
	 *
	 * @param pawn
	 * 
	 * Move the pawn which is in the final line
	 *
	 */	
		
	public void moveFinalSquares(Pawn pawn){
	
		int indexTargetSquare = (100 *(this.currentPlayer) )+ 10 + ((pawn.getNbSquareTraveled() + this.diceNumber) - 50);
		
		move(pawn,indexTargetSquare);
		pawn.setNbSquareTraveled(pawn.getNbSquareTraveled() + this.diceNumber);
	}

	/****
	 *
	 * @param player
	 * @return pawn
	 * 
	 * Return a pawn of the current player that which can finish the final line, else return null
	 *
	 */
	
	public Pawn canFinish(Player player) {
		for (Pawn pawn : player.getPawn()) {
			
			if (player.getFinalSquare().get( (100 * player.getPlayerID()) + 16).getiD() == pawn.getCurrentSquare().getiD() + this.diceNumber) {
				return pawn;
			}
		}
		return null;
	}

	/****
	 *
	 * @param player
	 * @return pawn
	 * 
	 * Return a pawn of the player which can eat a pawn of another player, else return null
	 *
	 */
	
	public Pawn canEat(Player player) {
	
		for (Pawn pawn : player.getPawn()) {
			if (isOppositePawnOnTargetSquare(this.boardPanel.getPlayers().get(this.currentPlayer-1), this.boardPanel.getPath().getPathMap().get((pawn.getCurrentSquare().getiD()+this.diceNumber)%52)) && pawnCanBePlay(pawn) && !isBlockOnWay(pawn)) {  //If there is an opposite pawn on the target square
				return pawn;
			}
		}
		return null;
	}
	
	
	
	
	public void eat() {
		
	}
	
	public void moveAtHome() {
		
	}

	/****
	 *
	 * @param pawn
	 * 
	 * Move the pawn on the target square and eat the opposite pawn
	 *
	 */
	
	public void moveAndEat(Pawn pawn){
		
		BoardSquare targetSquare = this.boardPanel.getPath().getPathMap().get((pawn.getCurrentSquare().getiD()+this.diceNumber)%52);
		targetSquare.eatOppositPawn();
		move(pawn,(pawn.getCurrentSquare().getiD()+this.diceNumber)%52);
		pawn.setNbSquareTraveled(pawn.getNbSquareTraveled() + this.diceNumber);
		addLogsAndNotification("Player n°"+pawn.getPlayerID()+" move his pawn n°"+pawn.getPawnID());
	}

	/****
	 * 
	 * @return pawn
	 * Return a pawn of the player which can create a block, else return null
	 *
	 */
	
	public Pawn canDoaBloc(){
		
		for (Pawn pawn : this.boardPanel.getPlayers().get(this.currentPlayer - 1).getPawn()) {
			if (this.boardPanel.getPath().getPathMap().get((pawn.getCurrentSquare().getiD()+this.diceNumber)%52).getIdOfPlayerOn()==this.currentPlayer && this.boardPanel.getPath().getPathMap().get((pawn.getCurrentSquare().getiD()+this.diceNumber)%52).HowManyPawn() == 1 && pawnCanBePlay(pawn)) {// a vrifier si getidofplayer est ==
				return pawn;
			}
		}
		return null;
	}
	
	/****
	 *
	 * @param pawn
	 * 
	 * Move the pawn on the target square in order to create a block
	 *
	 */

	public void moveForaBloc(Pawn pawn){
		
		move(pawn,(pawn.getCurrentSquare().getiD()+this.diceNumber)%52);
		pawn.setNbSquareTraveled(pawn.getNbSquareTraveled() + this.diceNumber);
	}

	/**
	 * 
	 * play the pawn which is nearer than the final squares
 	 * 
	 */
	
	public void playTheDefaultPawn(){
		
		Pawn targetPawn = new Pawn();
		int howmanymove = 0;
		
		for(Pawn pawn : this.boardPanel.getPlayers().get(this.currentPlayer - 1).getPawn()){
			if(pawnCanBePlay(pawn) && pawn.getNbSquareTraveled() >= howmanymove){
				targetPawn = pawn;
				howmanymove = targetPawn.getNbSquareTraveled();
			}
		}
		
		move(targetPawn,(targetPawn.getCurrentSquare().getiD()+this.diceNumber)%52);
		targetPawn.setNbSquareTraveled(targetPawn.getNbSquareTraveled() + this.diceNumber);
	}

	/****
	 *
	 * @param pawn
	 * @return boolean
	 * 
	 * Return true if the pawn can be play, else return false
	 *
	 */
	
	public boolean pawnCanBePlay(Pawn pawn){

		if(IsInHome(pawn)){
			return diceto6();

		}else{

			if(isBlockOnWay(pawn)){
				return false;

			}else {
				return pawn.getCurrentSquare().getiD() + this.diceNumber < this.boardPanel.getPlayers().get(this.currentPlayer - 1).getFinalSquare().get((100 * (this.currentPlayer) + 16)).getiD();
			}
		}
	}
	
	
	/****
	 *
	 * @param pawn
	 * 
	 * Move the pawn on his start square
	 *
	 */
	
	public void moveOutHome(Pawn pawn){
			move(pawn, this.boardPanel.getPlayers().get(this.currentPlayer-1).getStartSquare().getiD());
			addLogsAndNotification("Player n°"+pawn.getPlayerID()+" move out of home his pawn n°"+pawn.getPawnID());
	}
	
	
	/****
	 * 
	 * IA : decide what the robot player should do according to priorities
	 * 
	 * Priorities:
	 *  1: Move out of home
	 *  2: Move in final square
	 *  3: Move and eat (move on a pawn of another player)
	 *  4: Move in final line
	 *  5: Move the pawn the closest to the final line
	 *
	 */

	public void IATurn(){

		Player currentPlayer = this.boardPanel.getPlayers().get(this.currentPlayer-1);
	
		if(canMovePawn(currentPlayer)){
			
			if (this.diceto6() && APawnIsInHome(currentPlayer)!=null ) {
				
				System.out.println("premier if");
				moveOutHome(APawnIsInHome(currentPlayer));
			}else{
				
				if(canFinish(currentPlayer)!=null){
					MoveInFinalSquare(canFinish(currentPlayer));
					System.out.println("deuxieme if");
	
				}else if(canEat(currentPlayer)!=null){
					moveAndEat(canEat(currentPlayer));
					System.out.println("troisieme if");
	
				}else if(canGoFinalSquares()!=null){
					moveFinalSquares(canGoFinalSquares());
					System.out.println("coooool");
	
				}else if(canDoaBloc()!=null){
					moveForaBloc(canDoaBloc());
					System.out.println("quatre if");
	
				}else{
					playTheDefaultPawn();
					System.out.println("cinq if");
				}
			}
		}
	}
	
	
	public boolean hasWinner(){
		int count=0;
		for (Player player : boardPanel.getPlayers()){
			if(!player.getWin()){
				count++;
			}
		}
		return count != 4;

	}

	/**
	 * change the current player
	 */
	public void changeCurrentPlayer(){
		System.out.println(this.currentPlayer);
		this.currentPlayer=boardPanel.getPlayers().get((this.currentPlayer)%4).getPlayerID();
		System.out.println(this.currentPlayer);
	}

	/**
	 * if the dice is a 6 the player can replay
	 */
	public boolean diceto6(){
		return this.diceNumber == 6;
	}


//	public boolean turn(){
//while(!hasWinner()) {
//	for (Player player : boardPanel.getPlayers()) {
//		manage = 1;
//		currentPlayer = player.getPlayerID();
//		while (manage != 4 || diceNumber == 6) {
//
//			if (manage == 4 && diceNumber == 6) {
//				manage = 2;
//
//
//			} else {
//				System.out.println(manage + " yo " + diceNumber + " " + currentPlayer + "" + player.getPlayerID());
//
//			}
//
//		}
//		System.out.println("next player");
//
//	}
//}
//return true;
//	}





	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
