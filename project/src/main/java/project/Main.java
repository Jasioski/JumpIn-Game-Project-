package project;

import java.util.Scanner;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {

	private static Direction StringToEnum(String direction) {	
		return Direction.valueOf(direction);
	}

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

		if (logger.isDebugEnabled()) {
			logger.debug("printing board");
		}

		// JumpIn client
		Scanner scanner = new Scanner(System.in);
		Coordinate coordinates = null;
		Direction direction = null;

		do {
			try {

          print("\n" + board.toString());
          print("Please type one of the following commands: ");
          print(ANSIColor.GREEN + "-> Jump Rabbit row (current row of Rabbit)" +
                  ", " +
                  "column " +
                  "(current column of Rabbit) Direction(Right, Left, Up, Down)" + ANSIColor.RESET );
          print(ANSIColor.GREEN + "-> Slide Fox row (e.g., 1), columns (e.g.," +
                  " 2) " +
                  "Number of " +
                  "board " +
                  "units / spaces (e.g., 2)" + ANSIColor.RESET);
          print(ANSIColor.YELLOW + "Sample commands: \n Jump Rabbit 0,0 Right" +
                  " \n " +
                  "Slide" +
                  " Fox " +
                  "0,2 2 Left" + ANSIColor.RESET);

          print(ANSIColor.CYAN + "Please enter command: " + ANSIColor.RESET);
          String userInput = scanner.nextLine();
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
              moveType = commands[0].toUpperCase();

              commands[1] = commands[1].toUpperCase();
              if (!commands[1].toString().equals("FOX") || !commands[1].toString().equals("RABBIT")) {
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
            	  if (commands.length == 4) {
            		commands[3] = commands[3].toUpperCase();  
            	  }
                  if (!commands[3].equals("UP") || !commands[3].equals("DOWN") || !commands[3].equals("RIGHT") || !commands[3].equals("LEFT")) {
                      userEnteredDirection = commands[3];
                  }
              }
              // if String assign it to direction
              if (commands.length > 4) {
            	  commands[4] = commands[4].toUpperCase();
                  if (!commands[4].equals("UP") || !commands[4].equals("DOWN") || !commands[4].equals("RIGHT") || !commands[4].equals("LEFT")) {
                      userEnteredDirection = commands[4];
                  }
              }


              trace(
                                 "moveType: " + moveType + " itemType: " + itemType + " coordinates: " + userEnteredCoordinates
                                 + " unitsToMove: " + unitsToMove + " direction: " + userEnteredDirection);
              rowColumn = userEnteredCoordinates.split(",", 2);
              row = Integer.parseInt(rowColumn[0]) - 1;

              column = Integer.parseInt(rowColumn[1]) - 1;
          }
          if (row == -1 || column == -1) {
              warn("Please enter correct coordinates in format e.g., row," +
                      "column i.e., 2,3");
          }
          coordinates = new Coordinate(row, column);
          direction = StringToEnum(userEnteredDirection);
          if ("JUMP".equals(moveType)) {
              // the input deals with the Rabbits

              board.jump(direction, coordinates);

          } else if ("SLIDE".equals(moveType)) {
              // deals with the Foxes
              if (unitsToMove != -1) {
                  board.slide(direction, unitsToMove, coordinates);

              }
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

        } while (board.currentGameState == GameState.IN_PROGRESS);
		scanner.close();
		board.getCurrentGameState();
		print(board.toString());
		print(ANSIColor.GREEN + "Game has been solved " +
                       "successfully!" + ANSIColor.RESET);
		
	}

}
