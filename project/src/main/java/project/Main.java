package project;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {


	@SuppressWarnings("PMD.UseVarargs")
	public static void main(String[] args) {

		Logger logger = LogManager.getLogger(Main.class);

		logger.info("Starting main");

		Board board = new Board(5);
		
		Coordinate head = new Coordinate(0,0);
		Coordinate tail = new Coordinate(0,1);
		
		Fox fox = new Fox(head, tail);
		
		try {
			board.setItem(fox.getCoordinates(), fox);
			
//			int moveSpaces = 1;
//			board.move(Direction.RIGHT, head, moveSpaces);
//			
		} catch (BoardItemNotEmptyException e) {
			if (logger.isErrorEnabled()) {
				logger.catching(Level.ERROR, e);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("printing board");
			//logger.debug("\n" + board.toString());
			logger.debug("game state" + board.getCurrentGameState());
		}

		    //JumpIn client


		do {
		    System.out.println("\n" + board.toString());
		    System.out.println("Please type one of the following commands");
		    System.out.println("1) Jump Rabbit [row(e.g., 1)][colums e.g., 2] (current coordinates) Direction(Right, Left, Up, Down)");
		    System.out.println("2) Slide Fox [row(e.g., 1)][colums e.g., 2] Number of boad uints/ spaces (e.g., 2)");
		    System.out.println("Sample commands: \n Jump Rabbit [1][2] Right");
		    System.out.println("Sample commands: \n Slide Fox [1][2] 2");
		    
		    
		  //ToDO if error messaage, invalid move
		    Scanner userInput = new Scanner(System.in);
		    String[] commands  = userInput.toString().split(" ");
		    	String moveType = commands[0];
		    	String itemType = commands[1];
		    	String coordinates = commands[2];
		    	int unitsToMove = -1;
		    	String direction = "";
		    	//Checking if the last part of the command is an Integer or a string
		    	try {
		    		//if integer, assign it to unitsToMove
		    		unitsToMove = Integer.parseInt(commands[3]);    	
		    	
		    	}catch(Exception e) {
		    		//if String assign it to direction
		    		direction = commands[3];
		    	}		    	
		    	userInput.close();
		    	
		    	System.out.println("assa" + moveType + "cdjsnc" + itemType + "dcdcds" +  coordinates + "csdscd" + unitsToMove + "dlndldsn"
		    			+ direction);
		    } while (board.currentGameState == GameState.IN_PROGRESS);
		    	System.out.println(board.getCurrentGameState());
		}
		

	

}
