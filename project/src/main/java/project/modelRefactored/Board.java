package project.modelRefactored;

import io.atlassian.fugue.Either;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pcollections.HashTreePMap;
import org.pcollections.PMap;

import java.util.List;


public class Board {

    public final int numberOfRows;
    public final int numberOfColumns;
    private static Logger logger = LogManager.getLogger(Board.class);

    //todo: override and throw exception on map.get
    private PMap<Either<Coordinate, List<Coordinate>>, BoardItem> items;

    public Board(int rows, int columns) {
        items = HashTreePMap.empty();
        this.numberOfRows = rows;
        this.numberOfColumns = columns;

        // Initialize Board Items
        for (int row = 0; row < numberOfRows; row ++) {
            for (int column = 0; column < numberOfColumns; column++) {
                Coordinate currentCoordinate = new Coordinate(row, column);
                BoardItem itemToAdd = new EmptyBoardItem(currentCoordinate);
                items = items.plus(Either.left(currentCoordinate), itemToAdd);
            }
        }
    }

    private Board (Board board) {
        this.numberOfRows = board.numberOfRows;
        this.numberOfColumns = board.numberOfColumns;

        // todo: rename to items
        this.items = board.items;
    }

    public static Board setItem(Board board, BoardItem item) {
        Board thisBoard = new Board(board);

        thisBoard.items = thisBoard.items.plus(item.coordinate, item);

        return thisBoard;
    }

    //todo: make getItem for listType (Either.right)
    private BoardItem getItem(Coordinate coordinate) {
        return this.items.get(Either.left(coordinate));
    }


    public PMap<Either<Coordinate, List<Coordinate>>, BoardItem> getItems() {
        return items;
    }

    //todo: rewrite the toString()
    @Override
	public String toString() {
		String str = "";

		String rowLine = "";

		for (int i = 0; i < numberOfRows; i++) {
			rowLine += "--------";
		}

		String columnLine = "" ;

		// column header
		for (int i = 0; i < numberOfRows; i++) {
			columnLine += "     " + (i + 1 ) + " ";
		}

		columnLine += "\n";

		for (int row = 0; row < numberOfRows; row ++) {

			if (row == 0) {
				str += columnLine;
			}

			str += rowLine;
			str += "\n";

			str += ""+ (row+1);
			for (int column = 0; column < numberOfColumns; column++) {
				BoardItem item =  getItem(new Coordinate(row, column));

				str += " | ";
				//test code
				if (item.toString().length() == 10) {
					str += " " + item.toString() + " ";
				}
				else if (item.toString().length() == 11) {
					str += " " + item.toString();
				}
				else {
					logger.error("badly sized ui text");
				}

				str += " ";
			}

			str += " |\n";
		}

		str += rowLine;

		return str;
	}
}
