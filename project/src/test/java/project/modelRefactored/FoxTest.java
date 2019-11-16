package project.modelRefactored;

import io.atlassian.fugue.Option;
import io.atlassian.fugue.Pair;
import org.junit.jupiter.api.Test;

import com.google.common.base.Optional;

import static org.junit.jupiter.api.Assertions.*;
public class FoxTest {

    void testConstructor() {

    }

    @Test
    void slideIntoHole() {
        //slice setup
        //F F H E
        Coordinate destination = new Coordinate(0, 2);
        Fox slidingFox = new Fox(Pair.pair(new Coordinate(0,0), new Coordinate(0, 1)), Orientation.HORIZONTAL);
        Board board = new Board(1, 4);
        board = board.setItem(slidingFox);
        board = board.setItem(new Hole(destination, Optional.absent()));

        Board finalBoard = board;
        assertThrows(Exception.class, () -> {
            slidingFox.slide(finalBoard.getRowSlice(0), 1 , Direction.RIGHT);
            System.out.println("TESTSTAJNSTNUIASTNUINAST");
        });

    }
}

//brb