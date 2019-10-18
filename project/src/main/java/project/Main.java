package project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {


	@SuppressWarnings("PMD.UseVarargs")
	public static void main(String[] args) {

		Logger logger = LogManager.getLogger(Main.class);

		logger.info("Starting main");

		Board board = new Board(5);
		
		try {
			board.setItem(0, 0, new Rabbit(0,0));
		} catch (BoardItemNotEmptyException e) {
			// TODO Auto-generated catch block

			if (logger.isErrorEnabled()) {
				logger.error(e.getStackTrace());
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("printing board");
			logger.debug("\n" + board.toString());
		}
	}

}
