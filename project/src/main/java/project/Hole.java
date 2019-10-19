package project;

import java.util.List;
import java.util.Optional;

public class Hole extends BoardItem {

	private static final Character HOLE_DISPLAY_CHARACTER = 'H';
	private Optional<Rabbit> containingItem;
	
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

	public Optional<Rabbit> getContainingItem() {
		return this.containingItem;
	}

	// TODO: merge with containRabbit
	public void setContainingItem(Rabbit rabbit) {
		this.containingItem = Optional.of(rabbit);
		rabbit.setCoordinate(this.getCoordinate());
	}

	// todo: merge with removeRabbit maybe
	public Rabbit removeContainingItem() throws HoleIsEmptyException {
		if (this.containingItem.isEmpty()) {
			throw new HoleIsEmptyException("there is no item in the hole");
		}
		
		Rabbit rabbit = this.containingItem.get();
		this.containingItem = Optional.empty();
				
		return rabbit;
	}

	public void containRabbit(Rabbit rabbit) throws HoleAlreadyHasRabbitException {
		if (this.containingItem.isPresent()) {
			throw new HoleAlreadyHasRabbitException("the hole already has a " +
					"rabbit");
		}

		this.setContainingItem(rabbit);
	}
}
