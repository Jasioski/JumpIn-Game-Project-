package project.model;

import project.model.exceptions.HoleAlreadyHasRabbitException;
import project.model.exceptions.HoleIsEmptyException;
import project.tui.ItemUIRepresentation;

/**
 * A class representing a Hole on the board
 */
public class Hole extends ContainerItem {

    
    /**
     * Constructs a hole at a specific coordinate.
     * @param coordinate The coordinate of the hole being created.
     */
    public Hole(Coordinate coordinate) {
        super(coordinate, ItemUIRepresentation.HOLE_EMPTY);
    }

    /**
     * Creates a hole at a specific row and column.
     * @param row The row where the hole is being created.
     * @param column The column where the hole is being created.
     */
    public Hole(int row, int column) {
        this(new Coordinate(row, column));
    }

    /**
     * Removes and returns the item contained in the hole.
     * @return The item that was contained.
     * @throws HoleIsEmptyException If there was no item in the hole.
     */
    @Override
    public Containable removeContainingItem() throws HoleIsEmptyException {
        Containable containable = super.removeContainingItem();

        this.UIRepresentation = ItemUIRepresentation.HOLE_EMPTY;

        return containable;
    }

    /**
     * Places a containable item into the hole.
     * @param containable The item being added.
     * @throws HoleAlreadyHasRabbitException If the hole already has a containable item.
     */
    @Override
    public void contain(Containable containable) throws HoleAlreadyHasRabbitException {
        super.contain(containable);

        if (containable.getClass() == Rabbit.class) {
            this.UIRepresentation = ItemUIRepresentation.HOLE_OCCUPIED_RABBIT;
        } else if (containable.getClass() == Mushroom.class) {
            this.UIRepresentation = ItemUIRepresentation.HOLE_MUSHROOM;
        }
    }
}
