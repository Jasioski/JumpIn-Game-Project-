package project;

import java.util.List;
import java.util.Optional;

public class Hole extends BoardItem {

	private static final Character HOLE_DISPLAY_CHARACTER = 'H';
	private Optional<BoardItem> containingItem;
	
	public Hole(Coordinate coordinate) {
		super(coordinate, HOLE_DISPLAY_CHARACTER);
		
		this.containingItem = Optional.empty();
	}

	@Override
	public void setCoordinate(Coordinate coordinate) {
		this.getCoordinates().clear();
		this.getCoordinates().add(coordinate);
	}

	@Override
	public void setCoordinates(List<Coordinate> coordinates) {
		if (coordinates.size() != 1) {
			throw new IllegalArgumentException("can only add a coordinate "
					+ "of length 1");
		}
		
		this.setCoordinate(coordinates.get(0));
	}
	
	public Coordinate getCoordinate () {
		return this.getCoordinates().get(0);
	}

	public Optional<BoardItem> getContainingItem() {
		return this.containingItem;
	}

	public void setContainingItem(Rabbit rabbit) {
		this.containingItem = Optional.of(rabbit);
		rabbit.setCoordinate(this.getCoordinate());
	}
	
}
