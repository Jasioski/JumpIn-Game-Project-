package project.modelRefactored;

import com.google.common.base.Optional;
import io.atlassian.fugue.Pair;
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

    public Pair<Rabbit, Optional<Hole>> jump(Direction direction, PMap<Coordinate, BoardItem> slice) throws InvalidMoveException {
       return jump(direction, slice, false);
    }

    private Pair<Rabbit, Optional<Hole>> jump(Direction direction,
                                              PMap<Coordinate, BoardItem> slice
            , boolean isCurrentlyJumping) throws InvalidMoveException {
        Coordinate coordinate = computeCoordinateFromDirection(direction);

        Rabbit jumpingRabbit = new Rabbit(coordinate);

        // Check if the new coordinate is at an obstacle
        // TODO: wrap pmap with type safe getter
        // TODO: find a way to make this a compile time check instead of
        //  using instance of
        BoardItem item = slice.get(coordinate);

        // Found obstacle
        //Jumping Right
        if (item.isObstacle()){
            return jumpingRabbit.jump(direction, slice, true);
        }

        // Could be empty hole or empty item

        // R M E  ==> obstacle found, keep going,

        // R E E  => error

        // R M H => E M H(R)

        // Not found obstacle
        if (isCurrentlyJumping) {

            if (item instanceof Hole) {
                Hole newHole = new Hole(coordinate, Optional.of(jumpingRabbit));
                return Pair.pair(jumpingRabbit, Optional.of(newHole));
            } else {
                return Pair.pair(jumpingRabbit, Optional.absent());
            }
        } else {
            throw new InvalidMoveException();
        }
    }

    @Override
    public boolean isObstacle() {
        return true;
    }
}
