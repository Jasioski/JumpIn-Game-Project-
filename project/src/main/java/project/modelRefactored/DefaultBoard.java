package project.modelRefactored;

import com.google.common.base.Optional;
import io.atlassian.fugue.Pair;

public class DefaultBoard {
    private Board board;

    public DefaultBoard() {
        board = new Board(5, 5);
        Mushroom mush1 = new Mushroom(new Coordinate(0,0));
        Hole holeTopLeft = new Hole(new Coordinate(0, 0),
                Optional.of(mush1));
        Rabbit rabbitLeft = new Rabbit(new Coordinate(2, 0));
        ElevatedBoardItem elevatedLeft =
                new ElevatedBoardItem(new Coordinate(2, 0),
                        Optional.of(rabbitLeft));
        Hole holeBottomLeft = new Hole(new Coordinate(4,0),
                Optional.absent());
        //1st column end

        Fox fox1 = new Fox(Pair.pair(new Coordinate(1, 1),
                new Coordinate(0,1)), Orientation.VERTICAL);
        //2nd column end

        ElevatedBoardItem elevatedTop =
                new ElevatedBoardItem(new Coordinate(0, 2),
                        Optional.absent());

        Hole holeMiddle = new Hole(new Coordinate(2,2),
                Optional.absent());
        Fox fox2 = new Fox(Pair.pair(new Coordinate(3, 2),
                new Coordinate(3,3)), Orientation.HORIZONTAL);

        Rabbit rabbitBottom = new Rabbit(new Coordinate (4, 2));
        ElevatedBoardItem elevatedBottom =
                new ElevatedBoardItem(new Coordinate(4, 2),
                        Optional.of(rabbitBottom));
        //3rd column end

        Rabbit rabbitMidTop = new Rabbit(new Coordinate(0, 3));
        Mushroom mush2 = new Mushroom(new Coordinate(1,3));
        //4th column end

        Rabbit rabbitTopRight = new Rabbit(new Coordinate(0, 4));
        Hole holeTopRight = new Hole(new Coordinate(0, 4),
                Optional.of(rabbitTopRight));
        ElevatedBoardItem elevatedMidRight =
                new ElevatedBoardItem(new Coordinate(2, 4),
                        Optional.absent());
        Hole holeBottomRight = new Hole(new Coordinate(4, 4),
                Optional.absent());
        //5th column end

        board = board.setItem(holeTopLeft);
        board = board.setItem(elevatedLeft);
        board = board.setItem(holeBottomLeft);
        board = board.setItem(fox1);
        board = board.setItem(elevatedTop);
        board = board.setItem(holeMiddle);
        board = board.setItem(fox2);
        board = board.setItem(elevatedBottom);
        board = board.setItem(rabbitMidTop);
        board = board.setItem(holeTopRight);
        board = board.setItem(elevatedMidRight);
        board = board.setItem(holeBottomRight);
        board = board.setItem(mush2);

    }

    public Board getDefaultBoard() {
        return board;
    }

    @Override
    public String toString() {
        return board.toString();
    }

}
