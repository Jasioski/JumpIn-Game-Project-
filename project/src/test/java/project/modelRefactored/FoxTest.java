package project.modelRefactored;

import io.atlassian.fugue.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.google.common.base.Optional;
import project.model.Direction;
import project.model.exceptions.SlideHitObstacleException;
import project.model.exceptions.SlideWrongOrientationException;

import static org.junit.jupiter.api.Assertions.*;
public class FoxTest {

    private static Logger logger = LogManager.getLogger(FoxTest.class);

    @Test
    void testConstructor() {
        Coordinate head = new Coordinate(5,5);
        Coordinate tail = new Coordinate(6, 5);
        Pair<Coordinate, Coordinate> coords = Pair.pair(head, tail);

        Fox fox = new Fox(coords, Orientation.VERTICAL);

        Coordinate expectedHead = new Coordinate(fox.getHead());
        Coordinate expectedTail = new Coordinate(fox.getTail());

        assertEquals(head, expectedHead, "Head coordinates should be same");
        assertEquals(tail, expectedTail, "Tail coordinates should be same");
        assertEquals(Orientation.VERTICAL, fox.orientation, "Orientation should be same");
    }

    @Test
    void slideIntoHole() {
        //slice setup
        //F F H E
        Coordinate destination = new Coordinate(0, 2);
        Fox slidingFox = new Fox(Pair.pair(new Coordinate(0,0), new Coordinate(0, 1)),
                Orientation.HORIZONTAL);
        Board board = new Board(1, 4);
        board = board.setItem(slidingFox);
        board = board.setItem(new Hole(destination, Optional.absent()));

        Board finalBoard = board;
        assertThrows(Exception.class, () -> {
            slidingFox.slide(finalBoard.getRowSlice(0), 1 , Direction.RIGHT);
        });
    }

    @Test
    void slideCorrect() {
        //slice setup
        // F F E E
        // E E F F
        Fox slidingFox = new Fox(Pair.pair(new Coordinate(0,0), new Coordinate(0, 1)),
                Orientation.HORIZONTAL);
        Board board = new Board(1, 4);
        board = board.setItem(slidingFox);

        Coordinate expectedHead = new Coordinate(0, 2);
        Coordinate expectedTail = new Coordinate(0, 3);

        int moveSpaces = 2;
        try {
            Fox newFox = slidingFox.slide(board.getRowSlice(0), moveSpaces,
                    Direction.RIGHT);
            assertEquals(expectedTail, newFox.getTail(), "the tail should be at " +
                    "the expected coordinate");
            assertEquals(expectedHead, newFox.getHead(),
                    "The head should have moved here");
        } catch (Exception e) {
            logger.debug(e);
            fail();
        }

        // Test that the old fox did not change

        //
    }
}