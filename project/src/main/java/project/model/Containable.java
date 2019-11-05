package project.model;

/**
 * Interface for items that can be contained in another item, eg. a Rabbit which can be contained in a hole.
 */
public interface Containable {
    /**
     * Sets the coordinates of the containable item.
     * @param coordinate The coordinates where the item is being placed.
     */
    void setCoordinate (Coordinate coordinate);
}
