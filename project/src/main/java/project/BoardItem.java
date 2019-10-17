package project;

public class BoardItem {

	private transient int row;
	private transient int column;
	
	public BoardItem(int row, int column) {
		this.row = row;
		this.column = column;	
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}