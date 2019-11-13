package project.model;

import project.tui.ItemUIRepresentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An generic BoardItem which can be extended to describe anything placed on the board.
 */
public abstract class BoardItem implements Serializable {

	/**
	 * The coordinates of the item on the board.
	 */
	protected List<Coordinate> coordinates;

	/**
	 * The item's UI representation (a character that represents the item).
	 */
	protected ItemUIRepresentation UIRepresentation;

	/**
	 * Constructor that creates the BoardItem with its UI representation.
	 * @param UIRepresentation The item's UI representation.
	 */
	public BoardItem(ItemUIRepresentation UIRepresentation) {
		this.coordinates = new ArrayList<Coordinate>();
		this.UIRepresentation = UIRepresentation;
	}

	/**
	 * Returns a copy of the item's coordinates, which prevents the coordinates from being written on.
	 * @return A list containing the item's coordinates.
	 */
	public List<Coordinate> getCoordinates() {
		return new ArrayList<Coordinate>(this.coordinates);
	}

	/**
	 * Returns the item's UI representation.
	 * @return The item's UI representation.
	 */
	public ItemUIRepresentation getUIRepresentation() {
		return UIRepresentation;
	}

	/**
	 * Returns a string representation of the item, which in this case is its UI representation character.
	 * @return The item's UI representation character.
	 */
	@Override
	public String toString() {
		return this.UIRepresentation.getRepresentation();
	}

	/**
	 * Sets the item's coordinates. Can contain one or multiple coordinates, for long items like the fox.
	 * @param coordinates A list of the new coordinates.
	 */
	public abstract void setCoordinates(List<Coordinate> coordinates);

	/**
	 * Checks if the item is equal to another board item.
	 * @param o The object being compared to.
	 * @return Boolean containing the object's equality.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null) return false;

		if (this.getClass() != o.getClass()) {
			return false;
		}

		BoardItem boardItem = (BoardItem) o;

		return (this.UIRepresentation.equals(boardItem.UIRepresentation) &&
				this.coordinates.equals(boardItem.coordinates)
		);
	}
}