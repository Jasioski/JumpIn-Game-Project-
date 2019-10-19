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
		}

		// JumpIn client

		Scanner scanner = new Scanner(System.in);

		do {
			System.out.println("\n" + board.toString());
			System.out.println("Please type one of the following commands");
			System.out.println(
					"1) Jump Rabbit row(e.g., 1),colums e.g., 2 (current coordinates) Direction(Right, Left, Up, Down)");
			System.out.println("2) Slide Fox row(e.g., 1), colums e.g., 2 Number of boad uints/ spaces (e.g., 2)");
			System.out.println("Sample command: \n Jump Rabbit 1,2 Right");
			System.out.println("Sample command: \n Slide Fox 1,1 2");

			System.out.println("Please enter command: ");
			String userInput = scanner.nextLine();
			// ToDO if error messaage, invalid move
			String[] commands = userInput.toString().split(" ");
			String moveType = commands[0];
			String itemType = commands[1];
			String coordinates = commands[2];
			int unitsToMove = -1;
			String direction = "";
			String[] rowColumn;
			String row = "";
			String column = "";
			// Checking if the last part of the command is an Integer or a string
			try {
				// if integer, assign it to unitsToMove else; will get default value -1
				unitsToMove = Integer.parseInt(commands[3]);

			} catch (Exception e) {
				// if String assign it to direction
				direction = commands[3];
			}

			System.out.println("assa" + moveType + "cdjsnc" + itemType + "dcdcds" + coordinates + "csdscd" + unitsToMove
					+ "dlndldsn" + direction);
			rowColumn = coordinates.split(",", 2);
			row = rowColumn[0];
			//column = rowColumn[1];
			System.out.println("dsdfsd" + rowColumn[1] );
		} while (board.currentGameState == GameState.IN_PROGRESS);
		scanner.close();
		System.out.println(board.getCurrentGameState());
	}
	

}
