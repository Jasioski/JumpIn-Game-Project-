package project.solver;

import project.model.BoardItem;
import project.model.Coordinate;
import project.model.Direction;

public class Move {
    public BoardItem item;
    public Coordinate initial;
    public Coordinate ending;
    public Direction direction;

    public Move(BoardItem item, Direction direction) {
        this(item, direction, null, null); //todo: refactor the null (smelly code)
    }

    public Move(BoardItem item, Direction direction, Coordinate initial, Coordinate ending) { //for slideable need end coords
        this.item = item;
        this.direction = direction;
        this.initial = initial;
        this.ending = ending;
    }
}
