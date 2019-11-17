package project.modelRefactored;

import com.google.common.base.Optional;
import io.atlassian.fugue.Pair;

public class DefaultBoard {
    private Board board;

    public DefaultBoard() {
        board = new Board(5, 5);
        Mushroom mush1 = new Mushroom(new Coordinate(0,0));
        Mushroom mush2 = new Mushroom(new Coordinate(1,3));
        Hole holeTopLeft = new Hole(new Coordinate(0, 0),
                Optional.of(mush1));
        Rabbit rabbitLeft = new Rabbit(new Coordinate(0, 2));
        Hole holeBottomLeft = new Hole(new Coordinate(0,4),
                Optional.absent());
        Fox fox1 = new Fox(Pair.pair(new Coordinate(1, 1),
                new Coordinate(0,1)), Orientation.VERTICAL);

        Hole holeMiddle = new Hole(new Coordinate(2,2),
                Optional.absent());




        board = board.setItem(holeTopLeft);
        board = board.setItem(rabbitLeft);
        board = board.setItem(holeBottomLeft);
        board = board.setItem(fox1);

        board = board.setItem(mush2);
    }

    @Override
    public String toString() {
        return board.toString();
    }

}
