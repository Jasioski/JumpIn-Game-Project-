package project.modelRefactored;

import com.google.common.base.Optional;
import io.atlassian.fugue.Either;
import io.atlassian.fugue.Pair;
import org.pcollections.PMap;
import project.model.Direction;
import project.tui.ItemUIRepresentation;

public class Hole extends ContainerItem {
    /**
     * Constructs a new hole with a coordinate and optional item.
     *
     * @param coordinate     The coordinate where the hole is located.
     * @param containingItem The optional item that it can contain.
     */
    public Hole(Coordinate coordinate, Optional<Containable> containingItem) {
        super(coordinate, containingItem);
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
    }


    public Pair<ContainerItem, Either<Rabbit, ContainerItem>> jump(Direction direction, PMap<Coordinate, BoardItem> slice) throws InvalidMoveException {
        if (!containingItem.isPresent()){
            if (! (containingItem.get() instanceof  Rabbit)) {
                throw new InvalidMoveException("The hole does not contain a rabbit.");
            }
        }

        //Jump the rabbit out of the hole
        Rabbit jumpingRabbit = (Rabbit) this.containingItem.get();
        Either<Rabbit, ContainerItem> rabbitOrContainerItem = jumpingRabbit.jump(direction, slice);

        //Create a new empty hole in this one's place
        Hole emptyContainerItem = new Hole(this.coordinate.left().get(), Optional.absent());

        return Pair.pair(emptyContainerItem, rabbitOrContainerItem);
    }
}
