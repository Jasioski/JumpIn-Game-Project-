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

    public boolean equals(Object o) {
        
        if (this == o) return true;

        if (o == null) return false;

        if (this.getClass() != o.getClass())
            return false;

        Move move = (Move) o;


        return (item.equals(move.item) &&
                initial.equals(move.initial) &&
                direction.equals(move.direction) &&
                ending.equals(move.ending));

    }

    public String toString()  {
        String str = "";
        str += "Item: " + item.getClass() + " from: " + initial + " to: " + ending;

        return str;
    }

}
