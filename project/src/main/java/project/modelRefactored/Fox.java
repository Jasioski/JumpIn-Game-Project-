package project.modelRefactored;

import io.atlassian.fugue.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pcollections.PMap;
import project.model.exceptions.SlideWrongOrientationException;

/**
 * A class that represents a fox on the board, which can slide across the board to move.
 */
public class Fox extends BoardItem {

    private static Logger logger = LogManager.getLogger(Fox.class);

    public final Orientation orientation;
    private final Coordinate tail;

    /**
     * Ensures that given head and tail coordinates do not conflict with each other
     * @param coordinate The coordinates of the head and tail.
     * @param orientation the orientation of the fox.
     */
    public Fox(Pair<Coordinate, Coordinate> coordinate, Orientation orientation) {
        super(coordinate.left());
        this.tail = coordinate.right();
        this.orientation = orientation;
    }

    /**
     * Method used to determine what next coordinate should be checked when sliding the fox
     * @param destinationCoordinate The coordinate of where the fox wants to end up at
     * @return nextCoordinates The next coordinates that should be checked when sliding the fox
     * @throws SlideWrongOrientationException
     */
    private Coordinate computeNextCoordinate(Coordinate destinationCoordinate)
            throws SlideWrongOrientationException {
        Coordinate nextCoordinates;
        if (this.orientation == Orientation.HORIZONTAL) {
            if (this.tail.row !=  destinationCoordinate.row) {
                throw new SlideWrongOrientationException("Fox is oriented horizontally");
            }
            if (this.tail.column - destinationCoordinate.column > 0) {
                nextCoordinates = new Coordinate(this.tail.row, this.tail.column - 1); //row stays same if sliding horizontally
            } else {
                nextCoordinates = new Coordinate(this.tail.row, this.tail.column + 1); //row stays same if sliding horizontally
            }
        } else {
            if (this.tail.column != destinationCoordinate.column) {
                throw new SlideWrongOrientationException("Fox is oriented vertically");
            }
            if (this.tail.row - destinationCoordinate.row > 0) {
                nextCoordinates = new Coordinate(this.tail.row - 1, this.tail.column); //column stays same if sliding horizontally
            } else {
                nextCoordinates = new Coordinate(this.tail.row + 1, this.tail.column); //column stays same if sliding horizontally
            }
        }

        return nextCoordinates;
    }

    /**
     * Checks which slide should be used
     * @param slice A slice of the board used to get the item at a given coordinate
     * @param destinationCoordinate The coordinate that the Fox wants to end up at
     * @return slidingFox A new Fox at the destinationCoordinate or at same location if the slide failed
     */
    public Fox slide(PMap<Coordinate, BoardItem> slice, Coordinate destinationCoordinate) {
        if (this.orientation == orientation.HORIZONTAL) {
            return slideHorizontal(slice, destinationCoordinate);
        } else {
            return slideVertical(slice, destinationCoordinate);
        }
    }

    /**
     * Checks if sliding the Fox horizontally to the destination coordinate is valid
     * @param slice A slice of the board used to get the item at a given coordinate
     * @param destinationCoordinate The coordinate that the Fox wants to end up at
     * @return slidingFox A new Fox at the destinationCoordinate or at same location if the slide failed
     */
    public Fox  slideHorizontal(PMap<Coordinate, BoardItem> slice, Coordinate destinationCoordinate) {
        
        Pair foxCoords = Pair.pair(super.coordinate, this.tail);
        Fox slidingFox = new Fox(foxCoords, this.orientation);

        return slidingFox;
    }

    /**
     * Checks if sliding the Fox vertically to the destination coordinate is valid
     * @param slice A slice of the board used to get the item at a given coordinate
     * @param destinationCoordinate The coordinate that the Fox wants to end up at
     * @return slidingFox A new Fox at the destinationCoordinate or at same location if the slide failed
     */
    public Fox  slideVertical(PMap<Coordinate, BoardItem> slice, Coordinate destinationCoordinate) {
        //TODO: Add logic to check if obstacle is hit
        Pair foxCoords = Pair.pair(super.coordinate, this.tail);
        Fox slidingFox = new Fox(foxCoords, this.orientation);

        return slidingFox;
    }

    /**
     * Returns whether this object can be treated as an obstacle
     * @return true The Fox object is an obstacle
     */
    @Override
    public boolean isObstacle() {
        return true;
    }
}
