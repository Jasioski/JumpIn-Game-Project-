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
			// TODO: remove this part of the enum, it is not neede
			return Direction.NONSPECIFIED;
		}

	}

	public static void main(String[] args) {
		Logger logger = LogManager.getLogger(Main.class);

		logger.info("Starting main");

		Board board = new Board(5);

		System.out.println(ANSIColor.RED + ItemUIRepresentation.HOLE_EMPTY.getRepresentation() + ANSIColor.RESET);

		Hole hole1 = new Hole(0, 0);
		Hole hole2 = new Hole(0, 4);
		Hole hole3 = new Hole(4, 0);
		Hole hole4 = new Hole(4, 4);
		Hole hole5 = new Hole(2, 2);

		Fox fox1 = new Fox(3,2,3,3);

		Rabbit rabbit1 = new Rabbit(4, 2);
		Mushroom mushroom1 = new Mushroom(2, 2);

		ElevatedBoardItem elevatedBoardItem1 = new ElevatedBoardItem(0,2);
		ElevatedBoardItem elevatedBoardItem2 = new ElevatedBoardItem(2,0);
		ElevatedBoardItem elevatedBoardItem3 = new ElevatedBoardItem(4,2);
		ElevatedBoardItem elevatedBoardItem4 = new ElevatedBoardItem(2,2);

		try {
			board.setItem(hole1);
			board.setItem(hole2);
			board.setItem(hole3);
			board.setItem(hole4);
			board.setItem(hole5);

			board.setItem(hole5);

			board.setItem(fox1);

			board.setItem(elevatedBoardItem1);
			board.setItem(elevatedBoardItem2);
			board.setItem(elevatedBoardItem3);
			board.setItem(elevatedBoardItem4);
			board.setItem(rabbit1);

			elevatedBoardItem1.contain(mushroom1);


		} catch (Exception e) {
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
				System.out.println("Sample command: \n Jump Rabbit 0,0 Right");
				System.out.println("Sample command: \n Slide Fox 0,2 2 Left");

				System.out.println("Please enter command: ");
				String userInput = scanner.nextLine();
				//System.out.println("dsfhdfsuhf:  " + userInput);
				String[] commands = userInput.toString().split(" ");


				int unitsToMove = -1;
				String userEnteredDirection = "";
				String[] rowColumn;
				int row = -1;
				int column = -1;
				String userEnteredCoordinates = "";
				String itemType = "";
				String moveType = "";

				if (!commands[0].equals("")) {


					moveType = commands[0];


					//TODO string to lowercase
					if (!commands[1].toString().equals("Fox") || !commands[1].toString().equals("Rabbit")) {
						itemType = commands[1];
					}

					if (!commands[2].equals("")) {
						userEnteredCoordinates = commands[2];
					}

					// Checking if the last part of the command is an Integer or a string
					try {
						// if integer, assign it to unitsToMove else; will get default value -1
						unitsToMove = Integer.parseInt(commands[3]);

					} catch (Exception e) {
						if (!commands[3].equals("Up") || !commands[3].equals("Down") || !commands[3].equals("Right") || !commands[3].equals("Left")) {
							userEnteredDirection = commands[3];
						}
					}
					// if String assign it to direction
					if (commands.length > 4) {
						if (!commands[4].equals("Up") || !commands[4].equals("Down") || !commands[4].equals("Right") || !commands[4].equals("Left")) {
							userEnteredDirection = commands[4];
						}
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
						board.slide(direction, unitsToMove, coordinates);

					}
				}
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
			} catch (NonSlideableException e) {
				// TODO rename slidable to slideable in all places
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
			catch (SlideHitElevatedException e) {
				System.out.println(
						"Warning: Action coud not be performed. An elevated item was encountered while sliding the fox to the new position."
								+ " Please enteer the command with different coordinates.");
			}

		} while (board.currentGameState == GameState.IN_PROGRESS);
		scanner.close();
		board.getCurrentGameState();
		System.out.println(board.toString());
		System.out.println(ANSIColor.GREEN + "Game has been solved " +
				"successfully!" + ANSIColor.RESET);
		
	}

}
