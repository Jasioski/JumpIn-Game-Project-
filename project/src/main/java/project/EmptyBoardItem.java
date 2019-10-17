package project;

public class EmptyBoardItem extends BoardItem {

	private static final Character TYPE = 'E';
	
	public EmptyBoardItem(int row, int column) {
		super(row, column, TYPE);	
	}

}
