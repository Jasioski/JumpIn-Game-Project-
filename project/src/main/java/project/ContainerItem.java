package project;

import com.sun.java.accessibility.util.GUIInitializedListener;

import java.util.List;
import java.util.Optional;

/**
 * An item that can contain other items on the board.
 */
// TODO: think of a better name for this class
public abstract class ContainerItem extends BoardItem {

	/**
	 * A containable object that this item contains.
	 */
	private Optional<Containable> containingItem;

	/**
	 * The item's UI representation.
	 */
	private ItemUIRepresentation emptyRepresentation;

	// TODO: there should be no default displayCharacter

	/**
	 * Creates the container item with a specific coordinate and UI representation.
	 * @param coordinate The item's coordinate.
	 * @param emptyRepresentation The item's UI representation.
	 */
	public ContainerItem(Coordinate coordinate, ItemUIRepresentation emptyRepresentation) {
		super(emptyRepresentation);
		this.emptyRepresentation = emptyRepresentation;

		this.setCoordinate(coordinate);
		this.containingItem = Optional.empty();
	}

	/**
	 * Constructs the container item at a specific row and column.
	 * @param row The item's row.
	 * @param column The item's column.
	 * @param emptyRepresentation The item's UI representation.
	 */
	public ContainerItem(int row, int column, ItemUIRepresentation emptyRepresentation) {
		this(new Coordinate(row, column), emptyRepresentation);
	}

	/**
	 * Sets the coordinates of the item.
	 * @param coordinate The coordinate to be set.
	 */
	public void setCoordinate(Coordinate coordinate) {
		this.coordinates.clear();
		this.coordinates.add(coordinate);
	}

	/**
	 * Sets the coordinates of the item using a list of coordinates.
	 * @param coordinates The item's coordinates.
	 * @throws IllegalArgumentException If there is more than one coordinate in the list.
	 */
	@Override
	public void setCoordinates(List<Coordinate> coordinates) {
		if (coordinates.size() != 1) {
			throw new IllegalArgumentException("can only add a coordinate "
					+ "of length 1");
		}
		
		this.setCoordinate(coordinates.get(0));
	}

	/**
	 * Gets the coordinates of the item.
	 * @return The item's coordinates.
	 */
	public Coordinate getCoordinate () {
		return this.getCoordinates().get(0);
	}

	/**
	 * Gets the item contained by this container.
	 * @return The item contained by this one.
	 */
	public Optional<Containable> getContainingItem() {
		return this.containingItem;
	}

	/**
	 * Remove the item contained in this one.
	 * @return The item being removed.
	 * @throws HoleIsEmptyException if there is no item being contained.
	 */
	// todo: merge with removeRabbit maybe
	public Containable removeContainingItem() throws HoleIsEmptyException {
		if (this.containingItem.isEmpty()) {
			throw new HoleIsEmptyException("there is no item in the hole");
		}
		
		Containable containable = this.containingItem.get();
		this.containingItem = Optional.empty();
				
		return containable;
	}

	/**
	 * Adds a containable item to the container.
	 * @param containable The item being added.
	 * @throws HoleAlreadyHasRabbitException If the contained item already has a containable item.
	 */
	public void contain(Containable containable) throws HoleAlreadyHasRabbitException {
		if (this.containingItem.isPresent()) {
			throw new HoleAlreadyHasRabbitException("the hole already has a " +
					"rabbit");
		}

		this.containingItem = Optional.of(containable);
		containable.setCoordinate(this.getCoordinate());
	}
}
