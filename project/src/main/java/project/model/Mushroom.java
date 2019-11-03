package project.model;

import project.tui.ItemUIRepresentation;

import java.util.List;

/**
 * A class representing a mushroom item on the board.
 */
public class Mushroom extends BoardItem implements Containable {

    /**
     * Creates the mushroom at a specific row and column on the board.
     * @param row The row where the mushroom is being created.
     * @param column The column where the mushroom is being created.
     */
    public Mushroom(int row, int column) {
        this(new Coordinate(row, column));
    }

    /**
     * Creates the mushroom at a coordinate on the board.
     * @param coordinate The coordinate where the mushroom is created.
     */
    public Mushroom (Coordinate coordinate) {
        super(ItemUIRepresentation.MUSHROOM);
        this.setCoordinate(coordinate);
    }

    /**
     * Setst the coordinates of the mushroom.
     * @param coordinate The coordinates where the item is being placed.
     */
    @Override
    public void setCoordinate(Coordinate coordinate) {
        this.coordinates.clear();
        this.coordinates.add(coordinate);
    }

    /**
     * Sets the coordinates of the mushroom using a list of coordinates.
     * @param coordinates A list of the item's coordinates.
     * @throws IllegalArgumentException If there is more than one coordinate.
     */
    @Override
    public void setCoordinates(List<Coordinate> coordinates) throws
            IllegalArgumentException {
            if (coordinates.size() != 1) {
                throw new IllegalArgumentException("can only add a coordinate "
                        + "of length 1");
            }

        this.setCoordinate(coordinates.get(0));
    }
}
