package project;

import com.sun.java.accessibility.util.GUIInitializedListener;

import java.util.List;
import java.util.Optional;

// TODO: think of a better name for this class
public abstract class ContainerItem extends BoardItem {

	private Optional<Rabbit> containingItem;
	private ItemUIRepresentation emptyRepresentation;

	// TODO: there should be no default displayCharacter

	public ContainerItem(Coordinate coordinate, ItemUIRepresentation emptyRepresentation) {
		super(emptyRepresentation);
		this.emptyRepresentation = emptyRepresentation;

		this.setCoordinate(coordinate);
		this.containingItem = Optional.empty();
	}

	public ContainerItem(int row, int column, ItemUIRepresentation emptyRepresentation) {
		this(new Coordinate(row, column), emptyRepresentation);
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

		this.containingItem = Optional.of(rabbit);
		rabbit.setCoordinate(this.getCoordinate());
	}
}
