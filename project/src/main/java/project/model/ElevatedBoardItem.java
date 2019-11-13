package project.model;

import project.model.exceptions.HoleAlreadyHasRabbitException;
import project.model.exceptions.HoleIsEmptyException;
import project.tui.ItemUIRepresentation;

import java.util.Optional;

/**
 * An object representing an elevated piece on the board, which can contain another object.
 */
public class ElevatedBoardItem extends ContainerItem {

    /**
     * Creates the elevated item at a specific coordinate.
     * @param coordinate The coordinate of the elevated item.
     */
    public ElevatedBoardItem(Coordinate coordinate) {
        super(coordinate, ItemUIRepresentation.ELEVATED);
    }

    /**
     * Creates the elevated item at a specific row and column.
     * @param row The row of the elevated item.
     * @param column The column of the elevated item.
     */
    public ElevatedBoardItem(int row, int column) {
        this(new Coordinate(row, column));
    }

    /**
     * Removes and returns the item being contained.
     * @return The item being contained.
     * @throws HoleIsEmptyException If there is no item currently contained.
     */
    @Override
    public Containable removeContainingItem() throws HoleIsEmptyException {
        Containable containable = super.removeContainingItem();

        this.UIRepresentation = ItemUIRepresentation.ELEVATED;

        return containable;
    }

    public Optional<Containable> containsItem() {
        return super.getContainingItem();
    }

    /**
     * Adds an item being contained in the elevated object.
     * @param containable The item being added.
     * @throws HoleAlreadyHasRabbitException If there is already an object being contained.
     */
    @Override
    public void contain(Containable containable) throws HoleAlreadyHasRabbitException {
        super.contain(containable);

        if (containable.getClass() == Rabbit.class) {
            this.UIRepresentation = ItemUIRepresentation.ELEVATED_RABBIT;
        } else if (containable.getClass() == Mushroom.class) {
            this.UIRepresentation = ItemUIRepresentation.ELEVATED_MUSHROOM;
        }
    }
}
