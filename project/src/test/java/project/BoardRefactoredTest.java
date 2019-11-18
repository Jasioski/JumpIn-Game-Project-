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

    }

    @Test
    void testGetItem() {
        Board board = new Board(5, 5);
        Coordinate itemCoordinate = new Coordinate(1, 1);
        Mushroom mushroom = new Mushroom(new Coordinate(1, 1));

        // Apply set transformation
        Board modifiedBoard = board.setItem(mushroom);

        // Make sure the old board has not changed
        assertNotEquals(mushroom, board.getItem(itemCoordinate), "the old " +
                        "board should not have changed");

        // Make sure the new board has changed
        BoardItem modifiedItem = modifiedBoard.getItem(itemCoordinate);

        assertNotNull(modifiedItem);
        assertEquals(mushroom, modifiedItem, "The items should be same");

    }


}
