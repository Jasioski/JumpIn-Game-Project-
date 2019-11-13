package project.model;

import java.io.Serializable;

/**
 * Interface for items that can be contained in another item, eg. a Rabbit which can be contained in a hole.
 */
public interface Containable extends Serializable{
    /**
     * Sets the coordinates of the containable item.
     * @param coordinate The coordinates where the item is being placed.
     */
    void setCoordinate (Coordinate coordinate);
}
