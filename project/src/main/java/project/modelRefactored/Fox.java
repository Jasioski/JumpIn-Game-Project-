package project.modelRefactored;

import io.atlassian.fugue.Either;
import io.atlassian.fugue.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pcollections.PMap;
import project.model.Direction;
import project.model.exceptions.SlideHitObstacleException;
import project.model.exceptions.SlideWrongOrientationException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class that represents a fox on the board, which can slide across the board to move.
 */
public class Fox extends BoardItem {

    private static Logger logger = LogManager.getLogger(Fox.class);

    /**
     * The orientation of the fox
     */
    public final Orientation orientation;

    /**
     * Ensures that given head and tail coordinates do not conflict with each other
     * @param coords The pair of coordinates of the Fox.
     */
    private static void validateArguments(Pair<Coordinate, Coordinate> coords) {
        validateArguments(coords.left().row, coords.left().column, coords.right().row, coords.right().column);
    }

    /**
     * Ensures that the head and tail do not conflict with each other.
     * @param headRow The row of the fox's head.
     * @param headColumn The column of the fox's head.
     * @param tailRow The row of the fox's tail.
     * @param tailColumn The column of the fox's tail.
     * @throws IllegalArgumentException If the hea and tail conflict with eachother.
     */
    private static void validateArguments(int headRow, int headColumn, int tailRow, int tailColumn)
            throws IllegalArgumentException {

        if (headColumn == tailColumn && headRow == tailRow) {
            throw new IllegalArgumentException("The fox cannot have its tail and head in the same position");
        }

        if (Math.abs(headColumn - tailColumn) > 1 || Math.abs(headRow - tailRow) > 1) {
            throw new IllegalArgumentException(
                    "The fox cannot have its tail more than a unit " + "a way from its head");
        }

        if (Math.abs(headColumn - tailColumn) == Math.abs(headRow - tailRow)) {
            throw new IllegalArgumentException("The fox cannot have its tail diagonal to its head");
        }
    }

    /**
     * Ensures that given head and tail coordinates do not conflict with each other
     * @param coordinates The coordinates of the head and tail.
     * @param orientation the orientation of the fox.
     */
    public Fox(Pair<Coordinate, Coordinate> coordinates, Orientation orientation) {
        //TODO: calc orientation based on pair of coords
        super(coordinates);
        validateArguments(coordinates);
        this.orientation = orientation;
    }

    /**
     * Method used to determine what next coordinate should be checked when sliding the fox
     * @return nextCoordinates The next coordinates that should be checked when sliding the fox
     * @throws SlideWrongOrientationException
     */
    private Pair<Coordinate, Coordinate> computeNextCoordinates(Direction direction)
            throws SlideWrongOrientationException {
        Coordinate head = getHead();
        Coordinate tail = getTail();

        if (this.orientation == Orientation.HORIZONTAL) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                throw new SlideWrongOrientationException("Fox is oriented horizontally!");
            }
        } else if (this.orientation == Orientation.VERTICAL) {
            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                throw new SlideWrongOrientationException("Fox is oriented vertically!");
            }
        }

        if (direction == Direction.DOWN) {
            Coordinate newHead = new Coordinate(head.row + 1, head.column);
            Coordinate newTail = new Coordinate(tail.row + 1, tail.column);
            return Pair.pair(newHead, newTail);
        } else if (direction == Direction.UP) {
            Coordinate newHead = new Coordinate(head.row - 1, head.column);
            Coordinate newTail = new Coordinate(tail.row - 1, tail.column);
            return Pair.pair(newHead, newTail);
        } else if (direction == Direction.RIGHT) {
            Coordinate newHead = new Coordinate(head.row, head.column + 1);
            Coordinate newTail = new Coordinate(tail.row, tail.column + 1);
            return Pair.pair(newHead, newTail);
        } else if (direction == Direction.LEFT) {
            Coordinate newHead = new Coordinate(head.row, head.column - 1);
            Coordinate newTail = new Coordinate(tail.row, tail.column - 1);
            return Pair.pair(newHead, newTail);
        }

        throw new IllegalArgumentException("Invalid Direction!");
    }

    /**
     * Checks which slide should be used
     * @param slice A slice of the board used to get the item at a given coordinate
     * @param moveSpaces The spaces the Fox wants to move
     * @return slidingFox A new Fox at the destinationCoordinate or at same location if the slide failed
     */
    public Fox slide(PMap<Coordinate, BoardItem> slice, int moveSpaces, Direction direction) throws SlideWrongOrientationException, SlideHitObstacleException, InvalidMoveException {
        return performSlide(slice, moveSpaces, direction);
    }

    /**
     * Checks if sliding the Fox horizontally to the destination coordinate is valid
     * @param slice A slice of the board used to get the item at a given coordinate
     * @param moveSpaces The spaces the Fox wants to move
     * @return slidingFox A new Fox at the destinationCoordinate or at same location if the slide failed
     */
    public Fox performSlide(PMap<Coordinate, BoardItem> slice, int moveSpaces, Direction direction)
            throws SlideWrongOrientationException, InvalidMoveException {
        // Generate new coordinates
        Pair<Coordinate, Coordinate> nextCoordinates =
                this.computeNextCoordinates(direction);

        // Create new Fox
        Fox fox = new Fox(nextCoordinates, this.orientation);

        List<Coordinate> coordinates = new ArrayList<>();

        coordinates.add(nextCoordinates.left());
        coordinates.add(nextCoordinates.right());

        if (checkIfNotOnBoard(slice, coordinates)) {
            throw new InvalidMoveException("Move failed");
        }

        if (checkIfHitObstacle(slice, coordinates)) {
            throw new InvalidMoveException("Move failed");
        }

        if (moveSpaces > 1) {
            return fox.performSlide(slice, --moveSpaces, direction);
        }

        return fox;
    }

    private boolean checkIfHitObstacle(PMap<Coordinate, BoardItem> slice,
                                       List<Coordinate> coordinates ) {
        for (Coordinate coordinate: coordinates) {

            BoardItem item = slice.get(coordinate);

            if (item.isObstacle() && !item.equals(this)) {
                return true;
            }

            if (item instanceof ContainerItem) {
                return true;
            }
        }

        return false;
    }

    private boolean checkIfNotOnBoard(
            PMap<Coordinate, BoardItem> slice, List<Coordinate> nextCoordinates
    ) {
        HashSet<Coordinate> coordinateSet = new HashSet<>(slice.keySet());

        if (!coordinateSet.containsAll(nextCoordinates)) {
            return true;
        }

        return false;
    }


    /**
     * Returns whether this object can be treated as an obstacle
     * @return true The Fox object is an obstacle
     */
    @Override
    public boolean isObstacle() {
        return true;
    }

    public Coordinate getTail() {
       return coordinate.right().get().right();
    }

    public Coordinate getHead() {
        return coordinate.right().get().left();
    }


}
