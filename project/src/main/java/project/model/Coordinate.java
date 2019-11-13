package project.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * A coordinate on the board where an item can be located, consisting of its row and column.
 */
public class Coordinate implements Serializable {
	/**
	 * The row of the item.
	 */
	public int row;

	/**
	 * The column of the item.
	 */
	public int column;

	/**
	 * Constructs the coordinate with a specific row and column.
	 * @param row The coordinate's row.
	 * @param column The coordinate's column.
	 */
	public Coordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Constructs the coordinate with the same row and column.
	 * @param coordinate The row and column used for the coordinate.
	 */
	public Coordinate(Coordinate coordinate) {
		this.row = coordinate.row;
		this.column = coordinate.column;
	}

	/**
	 * Checks if this coordinate is equal to another.
	 * @param o The object being compared to.
	 * @return Boolean containing the object's equality.
	 */
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;

		if (o == null) return false;

		if (this.getClass() != o.getClass())
			return false;

		Coordinate coordinate = (Coordinate) o;

		return (this.row == coordinate.row &&
				this.column == coordinate.column);
	}

	/**
	 * Returns the hashcode of the coordinates.
	 * @return The coordinate's hashcode.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(row, column);
	}

	/**
	 * Returns a string representation of the coordinate.
	 * @return The string, in format row:column
	 */
	@Override
	public String toString() {
		return "" + row + ":" + column;
	}
}
