package project.modelRefactored;

import project.tui.ItemUIRepresentation;

public final class Mushroom extends SingleBoardItem {

    public Mushroom(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    public String toString() {
        return ItemUIRepresentation.MUSHROOM.getRepresentation();
    }

    @Override
    public boolean isObstacle() {
        return true;
    }
}
