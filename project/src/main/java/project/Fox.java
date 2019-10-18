package project;

import java.util.List;

public class Fox extends BoardItem {

	private int tailRow;
	private int tailColumn;

	private static final Character FOX_DISPLAY_CHARACTER = 'F';
	
	private static void validateArguments(int headRow, int headColumn, int tailRow, int tailColumn)
			throws IllegalArgumentException {

		if (headColumn == tailColumn && headRow == tailRow) {
			throw new IllegalArgumentException("The fox cannot have its tail and head in the same position");
		}

		if (Math.abs(headColumn - tailColumn) > 1 || Math.abs(headRow - tailRow) > 1) {
			throw new IllegalArgumentException(
					"The fox cannot have its tail more than a unit " + "a way from its head");
		}

		if (Math.abs(headColumn - tailColumn) == Math.abs(headRow - tailRow)) {
			throw new IllegalArgumentException("The fox cannot have its tail diagonal to its head");
		}
	}

	public Fox(int headRow, int headColumn, int tailRow, int tailColumn) {
		super(headRow, headColumn, FOX_DISPLAY_CHARACTER);

		validateArguments(headRow, headColumn, tailRow, tailColumn);

		this.tailRow = tailRow;
		this.tailColumn = tailColumn;
	}

	public int getTailRow() {
		return tailRow;
	}

	public int getTailColumn() {
		return tailColumn;
	}

	@Override
	public void setCoordinate(Coordinate coordinate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCoordinates(List<Coordinate> coordinates) {
		// TODO Auto-generated method stub
		
	}
}
