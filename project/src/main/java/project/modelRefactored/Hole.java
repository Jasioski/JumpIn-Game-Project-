package project.modelRefactored;

import com.google.common.base.Optional;

public class Hole extends ContainerItem {
    /**
     * Constructs a new hole with a coordinate and optional item.
     *
     * @param coordinate     The coordinate where the hole is located.
     * @param containingItem The optional item that it can contain.
     */
    public Hole(Coordinate coordinate, Optional<Containable> containingItem) {
        super(coordinate, containingItem);
    }
}
