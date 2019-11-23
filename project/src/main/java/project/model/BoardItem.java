package project.model;

import io.atlassian.fugue.Either;
import io.atlassian.fugue.Pair;
import project.tui.ItemUIRepresentation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public abstract class BoardItem implements MaybeObstacle {
    /**
     * Coordinates for the board item
     */
    private final Either<Coordinate, Pair<Coordinate, Coordinate>> coordinate;

    /**
     * The item's UI representation (a character that represents the item).
     */
    protected ItemUIRepresentation uIRepresentation =
            ItemUIRepresentation.EMPTY;

    /**
     * Creates the board item with a pair of coordinates.
     * @param coordinate The pair of coordinates of the item.
     */
    public BoardItem(Pair<Coordinate, Coordinate> coordinate) {
        this.coordinate = Either.right(coordinate);
    }

    public Either<Coordinate, ArrayList<Coordinate>> getCoordinate(){
        if (coordinate.isLeft()) {
            return Either.left(coordinate.left().get());
        }
        else {
            ArrayList<Coordinate> coords = new ArrayList<>();
            coords.add(coordinate.right().get().left());
            coords.add(coordinate.right().get().right());
            return Either.right(coords);
        }
    }

    /**
     * Creates the board item with a single coordinate.
     * @param coordinate The coordinate of the board item.
     */
    public BoardItem(Coordinate coordinate) {
        this.coordinate = Either.left(coordinate);
    }

    /**
     * Returns the item's string representation.
     * @return The string representation.
     */
    @Override
    public String toString() {
        return this.uIRepresentation.getRepresentation();
    }

    /**
     * Checks if this item is equal to another.
     * @param o The object being compared.
     * @return True if they represent the same board item.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null) return false;

        if (this.getClass() != o.getClass()) {
            return false;
        }

        BoardItem boardItem = (BoardItem) o;

        return (this.uIRepresentation.equals(boardItem.uIRepresentation) &&
                this.coordinate.equals(boardItem.coordinate)
        );
    }
}
