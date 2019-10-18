package project;

import java.util.List;

public class Rabbit extends BoardItem {

	private static final Character RABBIT_DISPLAY_CHARACTER = 'R';
	
	public Rabbit(int row, int column) {
		this(new Coordinate(row, column));
	}

	public Rabbit(Coordinate coordinate) {
		super(RABBIT_DISPLAY_CHARACTER);
		this.setCoordinate(coordinate);
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinates.clear();
		this.coordinates.add(coordinate);
	}

	@Override
	public void setCoordinates(List<Coordinate> coordinates) {
		if (coordinates.size() != 1) {
			if (coordinates.size() != 1) {
				throw new IllegalArgumentException("can only add a coordinate "
						+ "of length 1");
			}
		}
		
		this.setCoordinate(coordinates.get(0));
	}

	/**
	 * Get the first coordinate
	 * Rabbits only have a single coordinate
	 */
	public Coordinate getCoordinate() {
		return this.getCoordinates().get(0);
	}

}
