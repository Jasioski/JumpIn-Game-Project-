package project;

import java.util.List;

public class EmptyBoardItem extends BoardItem {

	private static final Character TYPE = 'E';
	
	public EmptyBoardItem(int row, int column) {
		super(row, column, TYPE);	
	}
	
	public EmptyBoardItem(Coordinate coordinate) {
		super(coordinate, TYPE);	
	}

	@Override
	public void setCoordinate(Coordinate coordinate) {
		this.getCoordinates().clear();
		this.getCoordinates().add(coordinate);		
	}

	@Override
	public void setCoordinates(List<Coordinate> coordinates) throws
	IllegalArgumentException {
		
	}


}
