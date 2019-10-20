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
		Coordinate tail = new Coordinate(0,1);
		
		Fox fox = new Fox(head, tail);
		Fox fox2 = new Fox(1,1,1,2);
		Rabbit r = new Rabbit(0,2);
		
		try {
			board.setItem(fox.getCoordinates(), fox);
			board.setItem(r.getCoordinates(),r);
			board.setItem(fox2.getCoordinates(),fox2);
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
		Coordinate c = new Coordinate(0,2);
		Coordinate c2 = new Coordinate(1,1);
		try {
		//board.slide(Direction.RIGHT,2, c);
		board.slide(Direction.DOWN, 1, new Coordinate(1, 1));
		//board.jump(Direction.DOWN, c);
		}catch(Exception e) {
			logger.error(e);
		}
		logger.debug("\n" + board.toString());
	}

}
