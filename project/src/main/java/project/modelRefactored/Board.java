package project.modelRefactored;

import io.atlassian.fugue.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pcollections.HashTreePMap;
import org.pcollections.PMap;

public class Board {

    public final int numberOfRows;
    public final int numberOfColumns;
    private static Logger logger = LogManager.getLogger(Board.class);

    //todo: override and throw exception on map.get
    private PMap<Coordinate, BoardItem> items;

    public Board(int rows, int columns) {
        items = HashTreePMap.empty();
        this.numberOfRows = rows;
        this.numberOfColumns = columns;

        // Initialize Board Items
        for (int row = 0; row < numberOfRows; row ++) {
            for (int column = 0; column < numberOfColumns; column++) {
                Coordinate currentCoordinate = new Coordinate(row, column);
                BoardItem itemToAdd = new EmptyBoardItem(currentCoordinate);
                items = items.plus(currentCoordinate, itemToAdd);
            }
        }
    }

	private Board (Board board) {
        this.numberOfRows = board.numberOfRows;
        this.numberOfColumns = board.numberOfColumns;

        // todo: rename to items
        this.items = board.items;
    }

	/**
	 * Set an item on a board while preserving purity
	 * @param item the item you want to add to the board
	 * @return a board which is a result of the applied transformation
	 */
	public Board setItem(BoardItem item) {
        Board modifiedBoard = new Board(this);

        if (item.coordinate.isLeft()) {
			logger.trace("found left item");
			Coordinate coordinate = item.coordinate.left().get();
			modifiedBoard.items = modifiedBoard.items.plus(coordinate, item);
		}

        if (item.coordinate.isRight()) {
        	logger.trace("found right type");
        	Pair<Coordinate,Coordinate> coordinate =
					item.coordinate.right().get();

        	modifiedBoard.items = modifiedBoard.items.plus(coordinate.left(), item);
			modifiedBoard.items = modifiedBoard.items.plus(coordinate.right(), item);
		}

		return modifiedBoard;
    }

    public BoardItem getItem(Coordinate coordinate) {
        return this.items.get(coordinate);
    }

	public PMap<Coordinate, BoardItem> getItems() {
        return items;
    }

	public PMap<Coordinate, BoardItem> getColumnSlice(int column) {
		PMap<Coordinate, BoardItem> slice = HashTreePMap.empty();

		for (int row = 0; row < numberOfRows; row++) {
			Coordinate coordinate = new Coordinate(row, column);
			slice = slice.plus(coordinate, getItem(coordinate));
		}

		return slice;
	}

	public PMap<Coordinate, BoardItem> getRowSlice(int row) {
		PMap<Coordinate, BoardItem> slice = HashTreePMap.empty();

		for (int column = 0; column < numberOfColumns; column++) {
			Coordinate coordinate = new Coordinate(row, column);
			slice = slice.plus(coordinate, getItem(coordinate));
		}

		return slice;
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
