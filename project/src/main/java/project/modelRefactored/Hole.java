package project.modelRefactored;

import com.google.common.base.Optional;
import io.atlassian.fugue.Either;
import io.atlassian.fugue.Pair;
import org.pcollections.PMap;
import project.model.Direction;

public final class Hole extends SingleBoardItem implements Elevated {

    public final Optional<Containable> containingItem;

    public Hole(Coordinate coordinate, Optional<Containable> containingItem) {
        super(coordinate);
        this.containingItem = containingItem;
    }


    @Override
    public boolean isObstacle() {
        if (containingItem.isPresent()) {
            if (containingItem.get() instanceof MaybeObstacle) {
                return ((MaybeObstacle) containingItem.get()).isObstacle();
            }
        }
        return false;
    }

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
