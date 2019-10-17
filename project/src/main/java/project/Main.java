package project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {


	@SuppressWarnings("PMD.UseVarargs")
	public static void main(String[] args) {

		Logger logger = LogManager.getLogger(Main.class);

		logger.info("Starting main");

		Board board = new Board(5);

		board.toString();
	}

}
