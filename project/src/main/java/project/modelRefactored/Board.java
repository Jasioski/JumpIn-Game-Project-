package project.modelRefactored;

import io.atlassian.fugue.Either;
import org.pcollections.Empty;
import org.pcollections.HashTreePMap;
import org.pcollections.PMap;

import java.util.HashMap;
import java.util.List;


public class Board {

    public final int numberOfRows;
    public final int numberOfColumns;

    private PMap<Either<Coordinate, List<Coordinate>>, BoardItem> itemsInBoard;

    public Board(int rows, int columns) {
        itemsInBoard = HashTreePMap.empty();
        this.numberOfRows = rows;
        this.numberOfColumns = columns;

        // Initialize Board Items
        for (int row = 0; row < numberOfRows; row ++) {
            for (int column = 0; column < numberOfColumns; column++) {
                Coordinate currentCoordinate = new Coordinate(row, column);
                BoardItem itemToAdd = new EmptyBoardItem(currentCoordinate);
                itemsInBoard = itemsInBoard.plus(Either.left(currentCoordinate), itemToAdd);
            }
        }
    }

    private Board (Board board) {
        this.numberOfRows = board.numberOfRows;
        this.numberOfColumns = board.numberOfColumns;

        // todo: rename to items
        this.itemsInBoard = board.itemsInBoard;
    }

    public static Board setItem(Board board, BoardItem item) {
        Board thisBoard = new Board(board);

        thisBoard.itemsInBoard = thisBoard.itemsInBoard.plus(item.coordinate, item);

        return thisBoard;
    }


    public PMap<Either<Coordinate, List<Coordinate>>, BoardItem> getItems() {
        return itemsInBoard;
    }
    //todo: get individual item;
}
