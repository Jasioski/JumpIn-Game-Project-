package project.model;

import io.atlassian.fugue.Either;

import java.util.ArrayList;

/**
 * Specifies a board item with a single coordinate.
 */
public abstract class SingleBoardItem extends BoardItem{

    public SingleBoardItem(Coordinate coordinate) {
        super(coordinate);
    }

    public Coordinate getSingleCoordinate(){
        return this.getCoordinate().left().get();
    }
}
