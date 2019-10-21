package project;

import java.util.List;

/**
 * A board item that represents an empty space on the board.
 */
public class EmptyBoardItem extends BoardItem {
	/**
	 * Creates the empty board item with a specific row and column.
	 * @param row The row where the empty item will be placed.
	 * @param column The column where the empty item will be placed.
	 */
	public EmptyBoardItem(int row, int column) {
		this(new Coordinate(row, column));
	}

	/**
	 * Creates the empty board item with a coordinate on the board.
	 * @param coordinate The coordinate where the empty space should be placed.
	 */
	public EmptyBoardItem(Coordinate coordinate) {
		super(ItemUIRepresentation.EMPTY);
		this.setCoordinate(coordinate);
	}

	/**
	 * Sets a coordinate of the empty item.
	 * @param coordinate The coordinate being set.
	 */
	public void setCoordinate(Coordinate coordinate) {
	
		this.coordinates.clear();
		this.coordinates.add(coordinate);
	}

	/**
	 * Sets the coordinates of the empty board item.
	 * @param coordinates The list containing the item's coordinates.
	 * @throws IllegalArgumentException If there is more than one set of coordinates.
	 */
	@Override
	public void setCoordinates(List<Coordinate> coordinates) throws
	IllegalArgumentException {
		if (coordinates.size() != 1) {
			if (coordinates.size() != 1) {
				throw new IllegalArgumentException("can only add a coordinate "
						+ "of length 1");
			}
		}

		this.setCoordinate(coordinates.get(0));
	}
}
