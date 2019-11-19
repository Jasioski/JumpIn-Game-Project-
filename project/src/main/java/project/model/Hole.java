package project.model;

import com.google.common.base.Optional;
import io.atlassian.fugue.Either;
import io.atlassian.fugue.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pcollections.PMap;
import project.tui.ItemUIRepresentation;

public class Hole extends ContainerItem {

    private static Logger logger = LogManager.getLogger(Board.class);

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

    @Override
    public boolean equals(Object o) {
        logger.trace("Checking hole!");
        if (this == o) {return true;}

        if (o == null) {return false;}

        if (this.getClass() != o.getClass()) {return false;}

        Hole hole = (Hole) o;

        if (hole.coordinate.left().get().column ==
                this.coordinate.left().get().column) {

            if (hole.coordinate.left().get().row ==
                    this.coordinate.left().get().row) {

                if (hole.containingItem.isPresent() &&
                        this.containingItem.isPresent()) {

                    if (hole.containingItem.get().getClass() ==
                            this.containingItem.get().getClass()) {
                        logger.trace("HOLE IS SAME!");
                        return true;
                    }

                }

                else if (!hole.containingItem.isPresent() &&
                        !hole.containingItem.isPresent()) {
                    logger.trace("HOLE IS SAME!");
                    return true;
                }

            }

        }

        return false;
    }
}
