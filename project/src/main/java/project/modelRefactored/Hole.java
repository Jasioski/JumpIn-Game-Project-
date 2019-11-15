package project.modelRefactored;

import com.google.common.base.Optional;

public final class Hole extends SingleBoardItem {

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
}
