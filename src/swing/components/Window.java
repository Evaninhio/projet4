package swing.components;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import game.components.BoardSquare;
import game.components.Pawn;
import game.components.Player;

public class Window extends JFrame implements ActionListener, MouseListener{

	private static final long serialVersionUID = 1L;
	private final BoardPanel boardPanel;
	private final ButtonsPanel buttonsPanel;
	private final TopPanel topPanel;
	private final BottomPanel bottomPanel;
	private final DesktopFrame desktopFrame;
	private int diceNumber;
	private int currentPlayer;
	private int manage;
	private int currentRank;
	/**
	 * variable can manage the screen with different values
	 * values:
	 * 		
	 * 		 0 : we can start the game
	 * 		10 : we can do the first roll turn to decide which one is the first player
	 * 		15 : last roll of the first roll turn
	 *		20 : we can throw the dice
	 *		30 : we can select a pawn
	 *		40 : we can pass
	 *		50 : game over 
	 *
	 */
	
	
	public Window(){
		
		this.setTitle("Ludo Game");
		this.setSize(870,700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container mainContainer = this.getContentPane();
		mainContainer.setLayout(new BorderLayout());
		this.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		
		
		this.desktopFrame = new DesktopFrame();
		mainContainer.add(this.desktopFrame, BorderLayout.WEST);
		
		this.boardPanel = new BoardPanel();
		mainContainer.add(this.boardPanel, BorderLayout.CENTER);
		
		this.buttonsPanel = new ButtonsPanel();		
		mainContainer.add(this.buttonsPanel, BorderLayout.EAST);
		
		this.topPanel = new TopPanel();
		mainContainer.add(this.topPanel, BorderLayout.NORTH);
		
		this.bottomPanel = new BottomPanel();
		mainContainer.add(this.bottomPanel, BorderLayout.SOUTH);
		
		this.topPanel.startGame.addActionListener(this);
		this.buttonsPanel.diceButton.addActionListener(this);
		this.buttonsPanel.passButton.addActionListener(this);
		this.desktopFrame.gameOverInternalFrame.quit.addActionListener(this);
		this.desktopFrame.gameOverInternalFrame.newGame.addActionListener(this);
		this.boardPanel.addMouseListener(this);
		
		this.setVisible(true);
		
		
		windowInitializer();
	}
	
		
	public void windowInitializer() {
		this.manage=0;
		this.currentRank = 0;
		this.currentPlayer = 0;
		this.diceNumber = 0;
	}

	
	public void firstTurn() {
		
		System.out.println("first turn");
		
		if(this.currentPlayer < 4) {
			
			if(this.boardPanel.getPlayers().get(this.currentPlayer-1).getTypeOfPlayer() == 1) {  //If the current player is an AI
				diceRoll();
				addLogsAndNotification("Player "+this.currentPlayer+" roll a " + this.diceNumber);
				this.boardPanel.getPlayers().get(this.currentPlayer-1).setFirstRoll(this.diceNumber);
				this.currentPlayer++;
				addLogsAndNotification("It's the turn of player " + this.currentPlayer);
				
				firstTurn();
				
			}else {
				
				this.manage = 10;
			}
		
		}else if(this.currentPlayer == 4) {
			
			if(this.boardPanel.getPlayers().get(this.currentPlayer-1).getTypeOfPlayer() == 1) {  //If the current player is an AI
				
				diceRoll();
				this.boardPanel.getPlayers().get(this.currentPlayer-1).setFirstRoll(this.diceNumber);
				addLogsAndNotification("Player "+this.currentPlayer+" roll a " + this.diceNumber);
				
				this.currentPlayer = firstPlayer(this.boardPanel.getPlayers().get(0).getFirstRoll(), this.boardPanel.getPlayers().get(1).getFirstRoll(), this.boardPanel.getPlayers().get(2).getFirstRoll(), this.boardPanel.getPlayers().get(3).getFirstRoll());
				addLogsAndNotification("Player n°"+this.currentPlayer+" is the first to play");
				this.diceNumber = 0;
				this.buttonsPanel.diceLabel.setText("" + this.diceNumber);
				
				turn();
				
			}else {
				
				this.manage = 15;
			}
		}
	}
	
	
	
	
	public void turn() {
		
		System.out.println("turn");
		
		if(this.boardPanel.getPlayers().get(this.currentPlayer-1).getTypeOfPlayer() == 1) {  //If the current player is an AI
			
			diceRoll();
			addLogsAndNotification("Player "+this.currentPlayer+" roll a " + this.diceNumber);
			IATurn();
			
		}else {   //If the player is an IRL player
			
			this.manage = 20;
		}
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

				diceRoll();
				this.boardPanel.getPlayers().get(this.currentPlayer-1).setFirstRoll(this.diceNumber);
				addLogsAndNotification("Player "+this.currentPlayer+" roll a " + this.diceNumber);
				this.currentPlayer++;
				addLogsAndNotification("It's the turn of player " + this.currentPlayer);
				
				firstTurn();
				
			}else if(manage==15) {	
				
				diceRoll();
				this.boardPanel.getPlayers().get(this.currentPlayer-1).setFirstRoll(this.diceNumber);
				addLogsAndNotification("Player "+this.currentPlayer+" roll a " + this.diceNumber);
				
				this.currentPlayer = firstPlayer(this.boardPanel.getPlayers().get(0).getFirstRoll(), this.boardPanel.getPlayers().get(1).getFirstRoll(), this.boardPanel.getPlayers().get(2).getFirstRoll(), this.boardPanel.getPlayers().get(3).getFirstRoll());
				addLogsAndNotification("Player n°"+this.currentPlayer+" is the first to play");
				this.diceNumber = 0;
				this.buttonsPanel.diceLabel.setText("" + this.diceNumber);
				
				turn();
				
			}else if(manage==20) {					
				
								
				diceRoll();
				addLogsAndNotification("Player "+this.currentPlayer+" roll a " + this.diceNumber);
				
				if(canMovePawn(this.boardPanel.getPlayers().get(this.currentPlayer-1))){
					
					this.manage = 30;
					
				}else{
					
					addLogsAndNotification("You cannot play, please pass");
					this.manage = 40;
				}
			}
			
		}else if (e.getSource() == this.topPanel.startGame) {		//Action performed when the startGame button is clicked
				
			if(this.manage==0) {
				
				pseudoAndTypePlayer();
				
				for(Player player : this.boardPanel.getPlayers()) {
					System.out.println(player.ToString());
				}
				
				
				addLogsAndNotification("Game started");
				this.diceNumber = 0;
				this.buttonsPanel.diceLabel.setText("" + this.diceNumber);
				this.currentPlayer = 1;
				addLogsAndNotification("It's the turn of " + this.boardPanel.getPlayers().get(this.currentPlayer-1).getPseudo());
				this.topPanel.startGame.setVisible(false);
				this.buttonsPanel.dicePanel.setVisible(true);
				this.topPanel.notification.setVisible(true);
				this.desktopFrame.startGameInternalFrame.setVisible(false);
				this.desktopFrame.scoreBoardInternalFrame.setVisible(true);


				firstTurn();
			}
			
			
			
		}else if(e.getSource() == this.buttonsPanel.passButton) {		//action performed when the pass button is clicked
			if(this.manage == 40) {
				nextPlayer();
			}
			
			
		}else if(e.getSource() == this.desktopFrame.gameOverInternalFrame.quit) {
			System.exit(0);
			
		}else if(e.getSource() == this.desktopFrame.gameOverInternalFrame.newGame) {
			
			gameInitializer();
			this.manage = 0;
		}
	}
	
	
	/****
	 *
	 * Reset to default all objects of the game
	 *
	 */
	
	public void gameInitializer() {
		
		windowInitializer();
		
		for(int i=0;i<52;i++) {
			this.boardPanel.getPath().getPathMap().get(i).boardSquareInitializer();
		}
		
		
		for(Player player: this.boardPanel.getPlayers()) {
			player.playerInitializer();
			
			for(Pawn pawn: player.getPawn()) {
				pawn.pawnInitializer();
			}
			
			for(BoardSquare homeSquare: player.getHomeSquare()) {
				homeSquare.homeSquareInitializer(player.getPlayerID());
			}
			
			for(int i=1; i<7; i++) {
				player.getFinalSquare().get((100*player.getPlayerID())+10+i).boardSquareInitializer();
			}
		}
		
		this.desktopFrame.gameOverInternalFrame.setVisible(false);
		this.desktopFrame.startGameInternalFrame.setVisible(true);
		
		
		this.topPanel.startGame.setVisible(true);
		this.buttonsPanel.diceLabel.setText("0");
		this.topPanel.notification.setText(null);
		this.bottomPanel.log.setText(null);
		
		this.repaint();
	}
	
	
	
	/****
	 *
	 * set the attribute dice number of this class with a random number between 1 and 6
	 *
	 */

	public void diceRoll() {
		Random rand = new Random();
		this.diceNumber = rand.nextInt(6) + 1;
		this.buttonsPanel.diceLabel.setText("" + this.diceNumber);
	}
	
	
	
	
	public void pseudoAndTypePlayer() {
		
		StartGameFrame start = this.desktopFrame.startGameInternalFrame;
		
		if(start.pseudoPlayer1 != null && start.pseudoPlayer2 != null && start.pseudoPlayer3 != null && start.pseudoPlayer4 != null) {
			this.boardPanel.getPlayers().get(0).setPseudo(start.pseudoPlayer1.getText());
			this.boardPanel.getPlayers().get(1).setPseudo(start.pseudoPlayer2.getText());
			this.boardPanel.getPlayers().get(2).setPseudo(start.pseudoPlayer3.getText());
			this.boardPanel.getPlayers().get(3).setPseudo(start.pseudoPlayer4.getText());
			
			this.boardPanel.getPlayers().get(0).setTypeOfPlayer((String)start.choiceTypePlayer1.getSelectedItem());
			this.boardPanel.getPlayers().get(1).setTypeOfPlayer((String)start.choiceTypePlayer2.getSelectedItem());
			this.boardPanel.getPlayers().get(2).setTypeOfPlayer((String)start.choiceTypePlayer3.getSelectedItem());
			this.boardPanel.getPlayers().get(3).setTypeOfPlayer((String)start.choiceTypePlayer4.getSelectedItem());
			
			
			this.desktopFrame.scoreBoardInternalFrame.labelPlayer1.setText(this.boardPanel.getPlayers().get(0).getPseudo()+" : "+"-"+this.boardPanel.getPlayers().get(0).getFinalSquare().get(116).HowManyPawn()+"-");
			this.desktopFrame.scoreBoardInternalFrame.labelPlayer2.setText(this.boardPanel.getPlayers().get(1).getPseudo()+" : "+"-"+this.boardPanel.getPlayers().get(1).getFinalSquare().get(216).HowManyPawn()+"-");
			this.desktopFrame.scoreBoardInternalFrame.labelPlayer3.setText(this.boardPanel.getPlayers().get(2).getPseudo()+" : "+"-"+this.boardPanel.getPlayers().get(2).getFinalSquare().get(316).HowManyPawn()+"-");
			this.desktopFrame.scoreBoardInternalFrame.labelPlayer4.setText(this.boardPanel.getPlayers().get(3).getPseudo()+" : "+"-"+this.boardPanel.getPlayers().get(3).getFinalSquare().get(416).HowManyPawn()+"-");			
		
		}
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
			
			
			
			if (pawnSelected && pawnCanBePlay(currentPawn)) { //If a pawn is selected
				
				if(currentPawn.isAtHome()) { //if the pawn selected is at home
					
					if(isBlockOnStartSquare(currentPlayer) == false && diceto6()) {  //If there is no block on the start square of the player && the player roll a 6
						
						moveOutHome(currentPawn);
						this.repaint();
						turn();
					}
					
				}else if(isBlockOnWay(currentPawn, this.boardPanel.getPath().getPathMap()) == false) {  //If there is no block on the way to the target square
					
					moveIfPlayerType2(currentPawn, currentPlayer);
					this.repaint();
					
					if(currentPlayer.hasFinishedGame()) {
						currentPlayer.setRank(this.currentRank+1);
						this.currentRank++;
					}					
					
					if(gameOver()) {
						this.buttonsPanel.dicePanel.setVisible(false);
						this.desktopFrame.scoreBoardInternalFrame.setVisible(false);
						this.desktopFrame.gameOverInternalFrame.rankTextArea.setText(this.boardPanel.ranking());
						this.desktopFrame.gameOverInternalFrame.setVisible(true);
					
					}else {
						if(diceto6()) {
							turn();
						}else {
							nextPlayer();
						}
					}
				}
			}
		}
	}

	

	public void moveIfPlayerType2(Pawn pawn, Player player) {
		
		int indexTargetSquare = (this.diceNumber + pawn.getCurrentSquare().getiD()) % 52;
		BoardSquare targetSquare = this.boardPanel.getPath().getPathMap().get(indexTargetSquare);

		if (isOppositePawnOnTargetSquare(player, targetSquare) && targetSquare.isSafe() == false) {  //If there is an opposite pawn on the target square

			moveAndEat(pawn);

		} else if (pawn.hasAllPathTraveled(this.diceNumber)) {    //If the pawn has traveled the complete path

			if (pawn.getCurrentSquare().getiD() + this.diceNumber <= 100 * player.getPlayerID() + 16) {        //If the target square is not out of the limit of the final line of the player
				
				moveFinalSquares(pawn);
			}


		} else {        //If it is a simple forward movement without any other action on the way
			move(pawn, indexTargetSquare);
			pawn.setNbSquareTraveled(pawn.getNbSquareTraveled() + this.diceNumber);
		}
	}
	
	
	

	
	/***
	 *
	 * @param player
	 * @param targetSquare
	 * @return true if there is an opposite pawn on the target square
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
	
	public boolean isBlockOnWay(Pawn pawn, HashMap<Integer, BoardSquare> path) {
				
		if(pawn.hasAllPathTraveled(this.diceNumber)) {		//If the player will be on the final line or is on the final line
						
			int finalSquareOfPathID = this.boardPanel.getPlayers().get(pawn.getPlayerID()-1).getStartSquare().getiD()-2;

			if(pawn.getCurrentSquare().getiD() == finalSquareOfPathID) {		//If the player is on the square just before the final line
			
				for(int i=1; i<this.diceNumber+1;i++) {		//For each square of the final line until the target square
					if(this.boardPanel.getPlayers().get(pawn.getPlayerID()-1).getFinalSquare().get(100*pawn.getPlayerID()+10+i).HowManyPawn() == 2) {

						return true;
					}
				}				
				
			}else if(pawn.getCurrentSquare().getiD() < 52) {		//If the player will be on the final line
								
				int diceBefore = 50-pawn.getNbSquareTraveled();
				int diceAfter = this.diceNumber-diceBefore;
				
				for(int i=1; i<diceBefore+1; i++) {		//For each square before the final line
					if(path.get((pawn.getCurrentSquare().getiD()+i)%52).HowManyPawn() == 2) {
						return true;
					}
				}
				
				for(int i=1; i<diceAfter+1;i++) {		//For each square of the final line until the target square
					if(this.boardPanel.getPlayers().get(pawn.getPlayerID()-1).getFinalSquare().get((100*pawn.getPlayerID())+10+i).HowManyPawn() == 2) {
						return true;
					}
				}
				
			}else{		//If the player is on the final square
				
				for(int i=1; i<this.diceNumber+1;i++) {		//For each square of the final line until the target square
					if(this.boardPanel.getPlayers().get(pawn.getPlayerID()-1).getFinalSquare().get(100*pawn.getPlayerID()+10+i).HowManyPawn() == 2) {

						return true;
					}
				}
			}
			
		}else {		//If the player is on the path and the target square too
			
			for(int i=1; i<this.diceNumber+1; i++) {
				
				if(path.get((pawn.getCurrentSquare().getiD()+i)%52).HowManyPawn() == 2) {

					return true;
				}
			}
		}

		return false;
	}


	
	/****
	 *
	 * Change the attribute currentPlayer of this class with the next player 
	 *
	 */
	
	public void nextPlayer() {
		
		this.desktopFrame.scoreBoardInternalFrame.labelPlayer1.setText(this.boardPanel.getPlayers().get(0).getPseudo()+" : "+"-"+this.boardPanel.getPlayers().get(0).getFinalSquare().get(116).HowManyPawn()+"-");
		this.desktopFrame.scoreBoardInternalFrame.labelPlayer2.setText(this.boardPanel.getPlayers().get(1).getPseudo()+" : "+"-"+this.boardPanel.getPlayers().get(1).getFinalSquare().get(216).HowManyPawn()+"-");
		this.desktopFrame.scoreBoardInternalFrame.labelPlayer3.setText(this.boardPanel.getPlayers().get(2).getPseudo()+" : "+"-"+this.boardPanel.getPlayers().get(2).getFinalSquare().get(316).HowManyPawn()+"-");
		this.desktopFrame.scoreBoardInternalFrame.labelPlayer4.setText(this.boardPanel.getPlayers().get(3).getPseudo()+" : "+"-"+this.boardPanel.getPlayers().get(3).getFinalSquare().get(416).HowManyPawn()+"-");			
	
				
		if(this.currentPlayer < 4) {
			this.currentPlayer++;
		}else {
			this.currentPlayer = 1;
		}
		addLogsAndNotification("It's the turn of player " + this.currentPlayer);
		turn();
	}
	
	
	/****
	 * 
	 * @param player
	 * 
	 * Check for each pawn of the player if it can move
	 *
	 */
	
	public boolean canMovePawn(Player player) {
		
		if(player.hasFinishedGame()) {
			addLogsAndNotification("Player n°"+player.getPlayerID()+" has already finished the game");
			return false;
		}else {
		
			int count=0;
			for ( Pawn pawn : player.getPawn()){
				if(pawnCanBePlay(pawn)){
					count++;
				}
			}
			
			if (count == 0) {
				addLogsAndNotification("Player n°"+player.getPlayerID()+" cannot play, must pass the turn");
			}
			
			return count>0 ; 
		}
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
		
		Player player = this.boardPanel.getPlayers().get(this.currentPlayer-1);
		int indexTargetSquare = player.getFinalSquare().get(100 * this.currentPlayer + 16).getiD();
		
		move(pawn, indexTargetSquare);
		pawn.setNbSquareTraveled(pawn.getNbSquareTraveled()+this.diceNumber);
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
		
		if(IDSquare/100 > 0 && IDSquare%100 > 10){
			targetSquare = this.boardPanel.getPlayers().get(this.currentPlayer-1).getFinalSquare().get(IDSquare);
		}else if(IDSquare > 100 && IDSquare < 105){
			targetSquare = pawn.getHomeSquare();
		}else if(IDSquare > 200 && IDSquare < 205){
			targetSquare = pawn.getHomeSquare();
		}else if(IDSquare > 300 && IDSquare < 305){
			targetSquare = pawn.getHomeSquare();
		}else if(IDSquare > 400 && IDSquare < 405){
			targetSquare = pawn.getHomeSquare();
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
	
		BoardSquare target;
		
		for (Pawn pawn : player.getPawn()) {
			
			target = this.boardPanel.getPath().getPathMap().get((pawn.getCurrentSquare().getiD()+this.diceNumber)%52);
			if (isOppositePawnOnTargetSquare(this.boardPanel.getPlayers().get(this.currentPlayer-1), target) && pawnCanBePlay(pawn) && !isBlockOnWay(pawn, this.boardPanel.getPath().getPathMap()) && pawn.hasAllPathTraveled(this.diceNumber) == false && target.isSafe() == false) {  //If there is an opposite pawn on the target square
				return pawn;
			}
		}
		return null;
	}
	
	
	/****
	 *
	 * @param pawn
	 * 
	 * move the pawn to his home square
	 *
	 */
	
	public void moveAtHome(Pawn pawn) {
		
		move(pawn, pawn.getHomeSquare().getiD());
		pawn.setNbSquareTraveled(0);
		addLogsAndNotification("Pawn n°"+pawn.getPawnID()+" of player n°"+pawn.getPlayerID()+"is back at home");
	}

	
	/****
	 *
	 * @param target
	 * 
	 * return the pawn of the target square in parameter to his home square
	 *
	 */
	
	
	public void eat(BoardSquare target) {
		
		target.getListOfPawnOn().get(0).ToString();
		moveAtHome(target.getListOfPawnOn().get(0));
		
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
		addLogsAndNotification("Player n°"+pawn.getPlayerID()+" move his pawn n°"+pawn.getPawnID());
		eat(targetSquare);
		move(pawn,targetSquare.getiD());
		pawn.setNbSquareTraveled(pawn.getNbSquareTraveled() + this.diceNumber);
		
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
	 * @param player
	 * @return boolean
	 * 
	 * Return true if there is a block on the start square of the player in parameter, else return false 
	 *
	 */
	
	public boolean isBlockOnStartSquare(Player player) {
		
		return player.getStartSquare().HowManyPawn() > 1;
	}
		
	/****
	 *
	 * @param pawn
	 * @return boolean
	 * 
	 * Return true if the pawn is in the final square of the final line 
	 *
	 */
	
	public boolean isOnFinalSquare(Pawn pawn) {
		return pawn.getCurrentSquare().getiD()%100 == 16 && pawn.getCurrentSquare().getiD() > 51;
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
		
		if(isOnFinalSquare(pawn)) {
			return false;
		
		}else if(IsInHome(pawn)){    //If the pawn is at home
			
			if (isBlockOnStartSquare(this.boardPanel.getPlayers().get(pawn.getPlayerID()-1))) {
				
				return false;
			}else {
				return diceto6();
			}
			
		}else if(IsInFinalsSquares(pawn)) { 	//If the pawn is in the final line
			
			if(isBlockOnWay(pawn, this.boardPanel.getPlayers().get(pawn.getPlayerID()-1).getFinalSquare())) {
					return false;
			}else {
				return pawn.getCurrentSquare().getiD() + this.diceNumber <= this.boardPanel.getPlayers().get(this.currentPlayer - 1).getFinalSquare().get((100 * (this.currentPlayer) + 16)).getiD();
			}
			
		}else {		//If the pawn is on the path			
						
			if(isBlockOnWay(pawn, this.boardPanel.getPath().getPathMap()) || isTargetSafeAndTaken(pawn)){
					return false;
			}else {
				return pawn.getCurrentSquare().getiD() + this.diceNumber <= this.boardPanel.getPlayers().get(this.currentPlayer - 1).getFinalSquare().get((100 * (this.currentPlayer) + 16)).getiD();
			}
		}
	}
	
	
	
	public boolean isTargetSafeAndTaken(Pawn pawn) {
		
		BoardSquare target = this.boardPanel.getPath().getPathMap().get((pawn.getCurrentSquare().getiD()+this.diceNumber)%52);
		
		return target.isSafe() == true && isOppositePawnOnTargetSquare(this.boardPanel.getPlayers().get(pawn.getPlayerID()-1), target);
		
	}
	
	
	
	/****
	 *
	 * @param pawn
	 * 
	 * Move the pawn on his start square
	 *
	 */
	
	public void moveOutHome(Pawn pawn){
		
		BoardSquare targetSquare = this.boardPanel.getPlayers().get(this.currentPlayer-1).getStartSquare();
		
		if(isOppositePawnOnTargetSquare(this.boardPanel.getPlayers().get(pawn.getPlayerID()-1), targetSquare)) {
			eat(targetSquare);
		}
		
		move(pawn, targetSquare.getiD());
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
			
			if (this.diceto6() && APawnIsInHome(currentPlayer)!=null && isBlockOnStartSquare(currentPlayer) == false) {
				System.out.println("cond1");
				moveOutHome(APawnIsInHome(currentPlayer));
			}else{
				
				if(canFinish(currentPlayer)!=null){
					System.out.println("cond2");
					MoveInFinalSquare(canFinish(currentPlayer));
					
				}else if(canEat(currentPlayer)!=null){
					System.out.println("cond3");
					moveAndEat(canEat(currentPlayer));
	
				}else if(canGoFinalSquares()!=null){
					System.out.println("cond4");
					moveFinalSquares(canGoFinalSquares());
	
				}else if(canDoaBloc()!=null){
					System.out.println("cond5");
					moveForaBloc(canDoaBloc());
	
				}else{
					System.out.println("cond6");
					playTheDefaultPawn();
				}
			}
			
			
			
			this.repaint();
						
			if(currentPlayer.hasFinishedGame()) {
				currentPlayer.setRank(this.currentRank+1);
				this.currentRank++;
			}
			
			if(gameOver()) {
				this.buttonsPanel.dicePanel.setVisible(false);
				this.desktopFrame.scoreBoardInternalFrame.setVisible(false);
				this.desktopFrame.gameOverInternalFrame.rankTextArea.setText(this.boardPanel.ranking());
				this.desktopFrame.gameOverInternalFrame.setVisible(true);
			}else {
				if(diceto6()) {
					turn();
				}else {
					nextPlayer();
				}
			}
			
		}else {
			nextPlayer();
		}
		
		
	}
	
	/**
	 * @return boolean
	 * 
	 * return true if all players have finished, else return false
	 */
	
	public boolean gameOver() {
		
		int count = 0;
		
		for(Player player: this.boardPanel.getPlayers()) {
			if(player.hasFinishedGame()) {
				count++;
			}
		}
		
		return count==4;
	}
	
	
	
	/**
	 * change the current player to the next player
	 */
	public void changeCurrentPlayer(){
		this.currentPlayer=boardPanel.getPlayers().get((this.currentPlayer)%4).getPlayerID();
	}

	/**
	 * @return boolean
	 * 
	 * if the dice is a 6 the player can replay
	 */
	public boolean diceto6(){
		return this.diceNumber == 6;
	}



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
