package project.modelRefactored;

import com.google.common.base.Optional;
import io.atlassian.fugue.Either;
import io.atlassian.fugue.Pair;
import org.pcollections.PMap;
import project.model.Direction;
import project.tui.ItemUIRepresentation;

public class ElevatedBoardItem extends ContainerItem {
    /**
     * Constructs a new hole with a coordinate and optional item.
     *
     * @param coordinate     The coordinate where the hole is located.
     * @param containingItem The optional item that it can contain.
     */
    public ElevatedBoardItem(Coordinate coordinate, Optional<Containable> containingItem) {
        super(coordinate, containingItem);
        if (containingItem.isPresent()) {
            if (containingItem.get() instanceof Rabbit) {
                this.uIRepresentation =
                        ItemUIRepresentation.ELEVATED_RABBIT;
            }
            else if (containingItem.get() instanceof Mushroom) {
                this.uIRepresentation = ItemUIRepresentation.ELEVATED_MUSHROOM;
            }
        }

        else {
            this.uIRepresentation = ItemUIRepresentation.ELEVATED;
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
        ElevatedBoardItem emptyContainerItem = new ElevatedBoardItem(this.coordinate.left().get(), Optional.absent());

        return Pair.pair(emptyContainerItem, rabbitOrContainerItem);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}

        if (o == null) {return false;}

        if (this.getClass() != o.getClass()) {return false;}

        ElevatedBoardItem elevatedBoardItem = (ElevatedBoardItem) o;

        if (elevatedBoardItem.coordinate.left().get().column ==
                this.coordinate.left().get().column) {

            if (elevatedBoardItem.coordinate.left().get().row ==
                    this.coordinate.left().get().row) {

                if (elevatedBoardItem.containingItem.isPresent() &&
                        this.containingItem.isPresent()) {

                    if (elevatedBoardItem.containingItem.get().getClass() ==
                            this.containingItem.get().getClass()) {
                        return true;
                    }

                }

                else if (!elevatedBoardItem.containingItem.isPresent() &&
                        !elevatedBoardItem.containingItem.isPresent()) {
                    return true;
                }

            }

        }

        return false;
    }
}
