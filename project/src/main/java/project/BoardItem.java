package project;

public class BoardItem {

	private transient int row;
	private transient int column;
	private transient Character type;
	
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