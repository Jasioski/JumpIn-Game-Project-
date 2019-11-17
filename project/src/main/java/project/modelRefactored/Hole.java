package project.modelRefactored;

import com.google.common.base.Optional;
import io.atlassian.fugue.Either;
import io.atlassian.fugue.Pair;
import org.pcollections.PMap;
import project.model.Direction;
import project.tui.ItemUIRepresentation;

/**
 * Represents a hole object on the board.
 */
public final class Hole extends SingleBoardItem implements Elevated {
    /**
     * The item that the hole may contain.
     */
    public final Optional<Containable> containingItem;

    /**
     * Constructs a new hole with a coordinate and optional item.
     * @param coordinate The coordinate where the hole is located.
     * @param containingItem The optional item that it can contain.
     */
    public Hole(Coordinate coordinate, Optional<Containable> containingItem) {
        super(coordinate);
        if (containingItem.isPresent()) {
            if (containingItem.get() instanceof Rabbit) {
                this.uIRepresentation =
                        ItemUIRepresentation.HOLE_OCCUPIED_RABBIT;
            }
            else if (containingItem.get() instanceof Mushroom) {
                this.uIRepresentation = ItemUIRepresentation.HOLE_MUSHROOM;
            }
        }

        else {
            this.uIRepresentation = ItemUIRepresentation.HOLE_EMPTY;
        }
        this.containingItem = containingItem;
    }

    /**
     * Returns true if the hole acts as an obstacle.
     * @return True if the hole contains an item.
     */
    @Override
    public boolean isObstacle() {
        if (containingItem.isPresent()) {
            if (containingItem.get() instanceof MaybeObstacle) {
                return ((MaybeObstacle) containingItem.get()).isObstacle();
            }
        }
        return false;
    }

    /**
     * Attempts to jump a rabbit out of a hole.
     * @param direction The direction that the rabbit must jump.
     * @param slice The slice where the jump occurs.
     * @return A pair containing the new empty hole, and either the rabbit or the new hole it is found in.
     * @throws InvalidMoveException If the hole is empty or the rabbit cannot jump.
     */
    public Pair<Hole, Either<Rabbit, Hole>> jump(Direction direction, PMap<Coordinate, BoardItem> slice) throws InvalidMoveException {
        if (!containingItem.isPresent()){
            throw new InvalidMoveException("The hole does not contain a rabbit.");
        }

        //Jump the rabbit out of the hole
        Rabbit jumpingRabbit = (Rabbit) this.containingItem.get();
        Either<Rabbit, Hole> jumpedRabbit = jumpingRabbit.jump(direction, slice);

        //Create a new empty hole in this one's place
        Hole emptyHole = new Hole (this.coordinate.left().get(), Optional.absent());

        return new Pair(emptyHole, jumpedRabbit);
    }
}
