package project;

import java.util.List;

public class Rabbit extends BoardItem {

	private static final Character RABBIT_DISPLAY_CHARACTER = 'R';
	
	public Rabbit(int row, int column) {
		this(new Coordinate(row, column));
	}

	public Rabbit(Coordinate coordinate) {
		super(coordinate, RABBIT_DISPLAY_CHARACTER);
	}

	@Override
	public void setCoordinate(Coordinate coordinate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCoordinates(List<Coordinate> coordinates) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Get the first coordinate
	 * Rabbits only have a single coordinate
	 */
	public Coordinate getCoordinate() {
		return this.getCoordinates().get(0);
	}

}
