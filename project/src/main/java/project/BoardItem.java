package project;

public class BoardItem {

	private int row;
	private int column;
	private Character type;
	
	public BoardItem(int row, int column, Character type) {
		this.row = row;
		this.column = column;	
		this.type = type;
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public Character getType() {
		return type;
	}
	
	public String toString() {
		return "" + row + "," + column + ":" + type;
	}
}