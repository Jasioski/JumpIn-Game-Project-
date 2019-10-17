package project;

public class BoardItem {

	private int row;
	private int column;
	private Character displayCharacter;
	
	public BoardItem(int row, int column, Character displayCharacter) {
		this.row = row;
		this.column = column;
		this.displayCharacter = displayCharacter;
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public Character getDisplayCharacter() {
		return displayCharacter;
	}
	

	// TODO: these should be removed once the attemptMove is implemented
	public void setRow(int row) {
		this.row = row;
	}
	
	// TODO: these should be removed once the attemptMove is implemented
	public void setColumn(int column) {
		this.column = column;
	}
	
	@Override
	public String toString() {
		return "" + row + "," + column + ":" + getDisplayCharacter();
	}
	
	
}