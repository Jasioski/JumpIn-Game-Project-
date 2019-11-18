package project.view;
import project.modelRefactored.Coordinate;

/**
 * An event thrown by objects when they are clicked, specifying their coordinates.
 */
public class ItemClickEvent {
    /**
     * The coordinate that the click event comes from.
     */
    public Coordinate coordinate;

    /**
     * Creates the click event, storing the item's coordinates.
     * @param itemCoordinate The coordinates of the item that sent the event.
     */
    public ItemClickEvent(Coordinate itemCoordinate) {
        this.coordinate = itemCoordinate;
    }

}
