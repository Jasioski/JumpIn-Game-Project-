package project;

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
		Coordinate tail = new Coordinate(0,0);
		
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
			logger.debug("\n" + board.toString());
		}
	}

}
