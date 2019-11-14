package project;

import io.atlassian.fugue.Either;
import org.junit.jupiter.api.Test;
import project.modelRefactored.*;

import static org.junit.jupiter.api.Assertions.*;

public class BoardRefactoredTest {

    @Test
    void testConstructor() {
        int rows = 5;
        int columns = 5;

        Board board = new Board(5, 5);
        assertEquals(rows, board.numberOfRows, "number of rows should be same");
        assertEquals(columns, board.numberOfColumns, "number of columns should be same");
        assertNotNull(board.getItems());
        assertEquals(rows * columns, board.getItems().size(), "itemsInBoard should be row times by columns");
    }

    @Test
    void testSetItem() {
        Board board = new Board(5, 5);
        Coordinate itemCoordinate = new Coordinate(1, 1);
        Mushroom item = new Mushroom(new Coordinate(1, 1));

        // todo: make sure the old board did not change

        Board modifiedBoard = board.setItem(board, item);
        BoardItem modifiedItem = modifiedBoard.getItems().get(itemCoordinate);

        assertNotNull(modifiedItem);
        assertEquals(item, modifiedItem, "The items should be same");
    }

    @Test
    void testGetItem() {
        Board board = new Board(5, 5);
        Coordinate itemCoordinate = new Coordinate(1, 1);
        Mushroom item = new Mushroom(new Coordinate(1, 1));

        // todo: make sure the old board did not change

        Board modifiedBoard = board.setItem(board, item);
        BoardItem modifiedItem = board.getItem(itemCoordinate);

        assertNotNull(modifiedItem);
        assertEquals(item, modifiedItem, "The items should be same");

    }


}
