package project;

import com.sun.java.accessibility.util.GUIInitializedListener;

import java.util.List;
import java.util.Optional;

// TODO: think of a better name for this class
public abstract class ContainerItem extends BoardItem {

	private Optional<Containable> containingItem;
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

	public Optional<Containable> getContainingItem() {
		return this.containingItem;
	}

	// todo: merge with removeRabbit maybe
	public Containable removeContainingItem() throws HoleIsEmptyException {
		if (this.containingItem.isEmpty()) {
			throw new HoleIsEmptyException("there is no item in the hole");
		}
		
		Containable containable = this.containingItem.get();
		this.containingItem = Optional.empty();
				
		return containable;
	}

	public void contain(Containable containable) throws HoleAlreadyHasRabbitException {
		if (this.containingItem.isPresent()) {
			// TODO: ContainerAlreadyHasContainableException
			throw new HoleAlreadyHasRabbitException("the hole already has a " +
					"rabbit");
		}

		this.containingItem = Optional.of(containable);
		containable.setCoordinate(this.getCoordinate());
	}
}
