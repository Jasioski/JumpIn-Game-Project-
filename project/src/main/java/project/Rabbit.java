package project;

public class Rabbit extends BoardItem {

	private static final Character TYPE = 'R';
	
	public Rabbit(int row, int column) {
		super(row, column, TYPE);
	}
}
