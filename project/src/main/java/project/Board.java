package project;

public class Board {
	
	private BoardItem[][] items;

	private int rows;
	private int columns;
	
	private static void validateArguments(int rows, int columns) throws IllegalArgumentException { 
		if (rows <= 0 || columns <= 0) { 
			throw new IllegalArgumentException("Rows and columns must be positive"
					+ "integers");
		}
	}
	
	public Board(int rows, int columns) {
		
		validateArguments(rows, columns);
		
		this.rows = rows;
		this.columns = columns;
		
		items = new BoardItem[rows][columns];
		
		// Initialize Board Items
		for (int row = 0; row < rows; row ++) {
			for (int column = 0; column < columns; column++) {
				items[row][column] = new EmptyBoardItem(row, column);
			}
		}
		
	}

	public Board(int dimension) {
		this(dimension, dimension);
	}

	public int getRows() {
		return columns;
	}

	public int getColumns() {
		return rows;
	}

	public BoardItem getItem(int row, int column) throws IllegalArgumentException {
		
		if (row < 0 || column < 0) {
			throw new IllegalArgumentException("row and column must be positive integers");
		}
		
		if (row >= this.rows || column >= this.columns) {
			throw new IllegalArgumentException("row and column must be within the range of the board");
		}
		
		return items[row][column];
	}
	
	@Override
	public String toString() {
		String str = "";
		
		for (int row = 0; row < rows; row ++) {
			for (int column = 0; column < columns; column++) {
				BoardItem item = items[row][column];
				str += item.toString();
				str += " ";
			}
			
			str += "\n";
		}
		
		return str;
	}

}
