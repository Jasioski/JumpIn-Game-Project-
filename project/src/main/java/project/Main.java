package project;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	private static Direction StringToEnum(String directon) {

		switch (directon) {
		case "Right":
			return Direction.RIGHT;
		case "Left":
			return Direction.LEFT;
		case "Up":
			return Direction.UP;
		case "Down":
			return Direction.DOWN;
		default:
			return Direction.NONSPECIFIED;
		}

	}

	public static void main(String[] args) {
		Logger logger = LogManager.getLogger(Main.class);

		logger.info("Starting main");

		Board board = new Board(5);

		Coordinate head = new Coordinate(0, 2);
		Coordinate tail = new Coordinate(0, 3);

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
			// logger.debug("\n" + board.toString());
		}

		// JumpIn client

		Scanner scanner = new Scanner(System.in);
		Coordinate coordinates = null;
		Direction direction = null;

		do {
			try {

				System.out.println("\n" + board.toString());
				System.out.println("Please type one of the following commands");
				System.out.println(
						"1) Jump Rabbit row(e.g., 1),colums e.g., 2 (current coordinates) Direction(Right, Left, Up, Down)");
				System.out.println("2) Slide Fox row(e.g., 1), colums e.g., 2 Number of boad uints/ spaces (e.g., 2)");
				System.out.println("Sample command: \n Jump Rabbit 1,2 Right");
				System.out.println("Sample command: \n Slide Fox 0,2 2 Left");

				System.out.println("Please enter command: ");
				String userInput = scanner.nextLine();
				System.out.println("dsfhdfsuhf:  " + userInput);
				String[] commands = userInput.toString().split(" ");
				
				
				int unitsToMove = -1;
				String userEnteredDirection = "";
				String[] rowColumn;
				int row = -1;
				int column = -1;
				String userEnteredCoordinates = "";
				String itemType = "";
				String moveType = "";

				if(!commands[0].equals("")) {
					
					if (!commands[0].equals("Jump") || !commands[0].equals("Slide")) {
						System.out.println("Please enter the correct move type e..g, either Jump or Slide");
					}
					
				moveType = commands[0];
				
				if (!commands[0].equals("Rabbit") || !commands[0].equals("Fox")) {
					System.out.println("Please enter the item type e..g, either Rabbit or Fox");
				}
				
				if(!commands[1].toString().equals("")) {
				itemType = commands[1];
				}
				
				if(!commands[2].equals("")) {
				userEnteredCoordinates = commands[2];
				}

				// Checking if the last part of the command is an Integer or a string
				try {
					// if integer, assign it to unitsToMove else; will get default value -1
					unitsToMove = Integer.parseInt(commands[3]);

				} catch (Exception e) {

				}
				// if String assign it to direction
				if(!commands[4].equals("")) {
				userEnteredDirection = commands[4];
				}
				

				System.out.println(
						"moveType: " + moveType + " itemType: " + itemType + " coordinates: " + userEnteredCoordinates
								+ " unitsToMove: " + unitsToMove + " direction: " + userEnteredDirection);
				rowColumn = userEnteredCoordinates.split(",", 2);
				row = Integer.parseInt(rowColumn[0]);
				
				column = Integer.parseInt(rowColumn[1]);
				}
				if (row == -1 || column == -1) {
					System.out.println("Please enter correct coordinates in format e.g., row,column i.e., 2,3");
				}
				coordinates = new Coordinate(row, column);
				direction = StringToEnum(userEnteredDirection);
				if (moveType.equals("Jump")) {
					// the input deals with the Rabbits

					board.jump(direction, coordinates);

				} else if (moveType.equals("Slide")) {
					// deals with the Foxes
					if (unitsToMove != -1) {
						System.out.println("here....");

						board.slide(direction, unitsToMove, coordinates);

					}
				}

			} catch (JumpObstacleException e) {
				// TODO GET RID OF IT
				e.printStackTrace();
			} catch (JumpFailedOutOfBoundsException e) {
				System.out.println(
						"Warning: Action coud not be performed. The coordinated were invalid. Please enter command with "
								+ " valid coordinates.");
			} catch (JumpFailedNoObstacleException e) {
				System.out.println(
						"Warning: Action coud not be performed. There was no obstacle to jump over. Please eneter command with "
								+ " different coordinates.");
			} catch (BoardItemNotEmptyException e) {
				System.out.println(
						"Warning: Action coud not be performed. The coordinates have already been occupied. Please enter command with "
								+ " different coordinates.");
			} catch (NonMovableItemException e) {
				// TODO CHANGE TO NOT SLIDEABLE
				System.out.println(
						"Warning: Action coud not be performed. The item is not slideable. Please enter the command with either Fox or Rabbit.");
			} catch (SlideOutOfBoundsException e) {
				System.out.println(
						"Warning: Action coud not be performed. The coordinates for Fox are invalid. Please enter the command with valid coordinates.");
			} catch (SlideHitObstacleException e) {
				System.out.println(
						"Warning: Action coud not be performed. An obstacle was encountered while sliding the fox to the new position."
								+ " Please enteer the command with different coordinates.");

			}
			
		} while (board.currentGameState == GameState.IN_PROGRESS);
		scanner.close();
		System.out.println(board.getCurrentGameState());
	}

}
