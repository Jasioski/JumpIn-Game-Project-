package project;

import java.util.List;

public class EmptyBoardItem extends BoardItem {

	private static final Character TYPE = 'E';

	public EmptyBoardItem(int row, int column) {
		this(new Coordinate(row, column));
	}

	public EmptyBoardItem(Coordinate coordinate) {
		super(TYPE);
		this.setCoordinate(coordinate);
	}

	public void setCoordinate(Coordinate coordinate) {
	
		this.coordinates.clear();
		this.coordinates.add(coordinate);
	}

	@Override
	public void setCoordinates(List<Coordinate> coordinates) throws
	IllegalArgumentException {
		if (coordinates.size() != 1) {
			if (coordinates.size() != 1) {
				throw new IllegalArgumentException("can only add a coordinate "
						+ "of length 1");
			}
		}
		
		this.setCoordinate(coordinates.get(0));
	}


}
