package project;

public class Fox extends BoardItem {

	private transient int tailRow;
	private transient int tailColumn;

	private static final Character TYPE = 'F';
	
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
		super(headRow, headColumn, TYPE);

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
}
