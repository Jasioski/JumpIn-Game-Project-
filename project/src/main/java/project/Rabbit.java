package project;

public class Rabbit extends BoardItem {

	private static final Character RABBIT_DISPLAY_CHARACTER = 'R';
	
	public Rabbit(int row, int column) {
		super(row, column, RABBIT_DISPLAY_CHARACTER);
	}

}
