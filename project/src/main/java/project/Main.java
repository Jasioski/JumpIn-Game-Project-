package project;

import java.util.Scanner;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    public static Logger logger = LogManager.getLogger(Main.class);

    @SuppressWarnings("PMD")
	public static void print(String message) {
	    System.out.println(message);
    }

    @SuppressWarnings("PMD")
    public static void warn(String message) {
        System.out.println(ANSIColor.RED + message + ANSIColor.RESET);
    }

    @SuppressWarnings("PMD")
    public static void trace(String message) {
        logger.trace(message);
    }

    @SuppressWarnings("PMD.UseVarargs")
    public static void main(String[] args) {

        print("Starting JumpIn");

		Board board = new Board(5);

		Hole hole1 = new Hole(0, 0);
		Hole hole2 = new Hole(0, 4);
		Hole hole3 = new Hole(4, 0);
		Hole hole4 = new Hole(4, 4);
		Hole hole5 = new Hole(2, 2);

		Fox fox1 = new Fox(3,2,3,3);
        Fox fox2 = new Fox(0,1,1,1);

		Rabbit rabbit1 = new Rabbit(4, 2);
        Rabbit rabbit2 = new Rabbit(2, 0);

        Mushroom mushroom2 = new Mushroom(0, 0);

		ElevatedBoardItem elevatedBoardItem1 = new ElevatedBoardItem(0,2);
		ElevatedBoardItem elevatedBoardItem2 = new ElevatedBoardItem(2,0);
		ElevatedBoardItem elevatedBoardItem3 = new ElevatedBoardItem(4,2);
        ElevatedBoardItem elevatedBoardItem4 = new ElevatedBoardItem(4,3);

		try {
			board.setItem(hole1);
			board.setItem(hole2);
			board.setItem(hole3);
			board.setItem(hole4);
			board.setItem(hole5);

			board.setItem(fox1);
            board.setItem(fox2);

			board.setItem(elevatedBoardItem1);
			board.setItem(elevatedBoardItem2);
			board.setItem(elevatedBoardItem3);
            board.setItem(elevatedBoardItem4);
			board.setItem(rabbit1);
            board.setItem(rabbit2);

            board.setItem(mushroom2);

		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.catching(Level.ERROR, e);
			}
		}

		// JumpIn client
		Scanner scanner = new Scanner(System.in);

	    JumpInClient client = new JumpInClient();

		while (board.currentGameState == GameState.IN_PROGRESS) {
		
			try {
				print(client.getPrompt(board));

				String userInput = scanner.nextLine();
				JumpInClient.Command command = client.parseInput(userInput);

				if (command instanceof JumpInClient.RabbitCommand) {
					board.jump(command.direction, command.coordinate);
				}

				if (command instanceof JumpInClient.FoxCommand) {
					JumpInClient.FoxCommand foxCommand =
							(JumpInClient.FoxCommand) command;

					board.slide(foxCommand.direction, foxCommand.moveSpaces,
							foxCommand.coordinate);
				}

			} catch (JumpFailedOutOfBoundsException e) {
				warn(
						"Warning: Action could not be performed. You tried to jump out of the board. Please try again!");
			} catch (JumpFailedNoObstacleException e) {
				warn(
						"Warning: Action could not be performed. There was no obstacle to jump over. Please try again!");
			} catch (BoardItemNotEmptyException e) {
				warn(
						"Warning: Action could not be performed. The coordinates have already been occupied. Please try again!");
			} catch (NonSlideableException e) {
				warn(
						"Warning: Action could not be performed. The item is not slideable. Please try again!");
			} catch (SlideOutOfBoundsException e) {
				warn(
						"Warning: Action could not be performed. You tried to slide out of bound. Please try again!");
			} catch (SlideHitObstacleException e) {
				warn(
						"Warning: Action could not be performed. An obstacle was encountered while sliding the fox. Please try again!");
			}
			catch (SlideHitElevatedException e) {
				warn(
						"Warning: Action could not be performed. An elevated item was encountered while sliding the fox to the new position."
								+ " Please try again!");
			} catch (HoleIsEmptyException e) {
				warn(
						"Warning: Action could not be performed. The hole is empty. Please try again!");
			}
			catch(Exception e) {
				warn("Invalid input, please try again");
			}

		} 
		scanner.close();
		board.getCurrentGameState();
		print(board.toString());
		print(ANSIColor.GREEN + "Game has been solved " +
				"successfully!" + ANSIColor.RESET);

	}

}
