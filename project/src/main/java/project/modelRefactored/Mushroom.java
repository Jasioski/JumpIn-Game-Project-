package project.modelRefactored;

import project.tui.ItemUIRepresentation;

public final class Mushroom extends SingleBoardItem implements Containable {

    public Mushroom(Coordinate coordinate) {
        super(coordinate);
        this.uIRepresentation = ItemUIRepresentation.MUSHROOM;
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
