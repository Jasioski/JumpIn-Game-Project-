package project.modelRefactored;

import io.atlassian.fugue.Pair;
import org.pcollections.PMap;
import project.model.exceptions.SlideWrongOrientationException;

public class Fox extends BoardItem {

    public final Orientation orientation;
    private final Coordinate tail;

    public Fox(Pair<Coordinate, Coordinate> coordinate, Orientation orientation) {
        super(coordinate.left());
        this.tail = coordinate.right();
        this.orientation = orientation;
    }

    private Pair<Coordinate, Coordinate> computeNextCoordinate(Coordinate destinationCoordinate)
            throws SlideWrongOrientationException {
        Pair nextCoordinates;
        if (this.orientation == Orientation.HORIZONTAL) {
            if (this.tail.row !=  destinationCoordinate.row) {
                throw new SlideWrongOrientationException("Fox is oriented horizontally");
            }
            if (this.tail.column - destinationCoordinate.column > 0) {
                nextCoordinates = Pair.pair(this.tail.row, this.tail.column - 1); //row stays same if sliding horizontally
            } else {
                nextCoordinates = Pair.pair(this.tail.row, this.tail.column + 1); //row stays same if sliding horizontally
            }
        } else {
            if (this.tail.column != destinationCoordinate.column) {
                throw new SlideWrongOrientationException("Fox is oriented vertically");
            }
            if (this.tail.row - destinationCoordinate.row > 0) {
                nextCoordinates = Pair.pair(this.tail.row - 1, this.tail.column); //column stays same if sliding horizontally
            } else {
                nextCoordinates = Pair.pair(this.tail.row + 1, this.tail.column); //column stays same if sliding horizontally
            }
        }

        return nextCoordinates;
    }

    public Fox slide(PMap<Coordinate, BoardItem> slice, Coordinate destinationCoordinate) {
        if (this.orientation == orientation.HORIZONTAL) {
            return slideHorizontal(slice, destinationCoordinate);
        } else {
            return slideVertical(slice, destinationCoordinate);
        }
    }

    public Fox  slideHorizontal(PMap<Coordinate, BoardItem> slice, Coordinate destinationCoordinate) {
        //TODO: Add logic to check if obstacle is hit
        Pair foxCoords = Pair.pair(super.coordinate, this.tail);
        Fox slidingFox = new Fox(foxCoords, this.orientation);

        return slidingFox;
    }

    public Fox  slideVertical(PMap<Coordinate, BoardItem> slice, Coordinate destinationCoordinate) {
        //TODO: Add logic to check if obstacle is hit
        Pair foxCoords = Pair.pair(super.coordinate, this.tail);
        Fox slidingFox = new Fox(foxCoords, this.orientation);

        return slidingFox;
    }

    @Override
    public boolean isObstacle() {
        return true;
    }
}
