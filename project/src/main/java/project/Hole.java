package project;

import java.util.List;
import java.util.Optional;

public class Hole extends BoardItem {

	private static final Character HOLE_DISPLAY_CHARACTER = 'H';
	private Optional<BoardItem> containingItem;
	
	public Hole(Coordinate coordinate) {
		super(HOLE_DISPLAY_CHARACTER);
		this.setCoordinate(coordinate);
		this.containingItem = Optional.empty();
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinates.clear();
		this.coordinates.add(coordinate);
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
	
	public BoardItem removeContainingItem() throws HoleIsEmptyException {
		if (this.containingItem.isEmpty()) {
			throw new HoleIsEmptyException("there is no item in the hole");
		}
		
		BoardItem item = this.containingItem.get();
		this.containingItem = Optional.empty();
				
		return item;
	}
	
}
