package project.solverRefactored;

import project.model.Direction;
import project.modelRefactored.BoardItem;
import project.modelRefactored.Coordinate;

public class Move {

    public final BoardItem item;
    public final Coordinate initial;
    public final Coordinate ending;

    public final Direction direction;

    public Move(BoardItem item, Direction direction, Coordinate initial,
                Coordinate ending) {

        // Debug info
        this.item = item;
        this.direction = direction;

        this.initial = initial;
        this.ending = ending;
    }

    public boolean equals(Move move) {
        if (!(this.item == move.item)) {
            return false;
        }
        if (!(this.initial == move.initial)) {
            return false;
        }
        if (!(this.direction == move.direction)) {
            return false;
        }
        if (!(this.ending == move.ending)) {
            return false;
        }
        return true;
    }

    public String toString()  {
        String str = "";
        str += "Item: " + item.getClass() + " from: " + initial + " to: " + ending;

        return str;
    }

}
