package project.modelRefactored;

import org.pcollections.PMap;
import project.model.Direction;

public class Rabbit extends SingleBoardItem implements Containable {

    public Rabbit(Coordinate coordinate) {
        super(coordinate);
    }

    private Coordinate computeCoordinateFromDirection(Direction direction) {

        Coordinate current = this.coordinate.left().get();

        switch (direction) {
            case RIGHT:
                return new Coordinate(current.row, current.column + 1);
            case LEFT:
                return new Coordinate(current.row, current.column - 1);
            case DOWN:
                return new Coordinate(current.row + 1, current.column);
            case UP:
                return new Coordinate(current.row - 1, current.column);
            default:
                throw new IllegalArgumentException("Invalid Direction.");
        }
    }

    public Rabbit jump(Direction direction, PMap<Coordinate, BoardItem> slice) {
       return jump(direction, slice, false);
    }

    private Rabbit jump(Direction direction, PMap<Coordinate, BoardItem> slice
            , boolean isCurrentlyJumping) {
        Coordinate coordinate = computeCoordinateFromDirection(direction);

        Rabbit jumpingRabbit = new Rabbit(coordinate);

        // Check if the new coordinate is at an obstacle
        // TODO: wrap pmap with type safe getter
        // TODO: find a way to make this a compile time check instead of
        //  using instance of
        BoardItem item = slice.get(coordinate);

        // Found obstacle
        if (item.isObstacle()){
            return jumpingRabbit.jump(Direction.RIGHT, slice, true);
        }

        // Not found obstacle
        if (isCurrentlyJumping) {
           return jumpingRabbit;
        } else {
            return this;
        }
    }

    @Override
    public boolean isObstacle() {
        return true;
    }
}
