package project;

import java.util.List;

public class EmptyBoardItem extends BoardItem {

	private static final Character TYPE = 'E';

	private boolean isElevated;
	
	public EmptyBoardItem(int row, int column, boolean isElevated) {
		this(new Coordinate(row, column), isElevated);
	}

	public EmptyBoardItem(int row, int column) {
		this(new Coordinate(row, column), false);
	}

	public EmptyBoardItem(Coordinate coordinate) {
		this(coordinate, false);
	}

	public EmptyBoardItem(Coordinate coordinate, boolean isElevated) {
		super(TYPE);
		this.setCoordinate(coordinate);
		this.isElevated = isElevated;
	}

	public boolean getIsElevated() {return this.isElevated; }

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
