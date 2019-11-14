package project.modelRefactored;

import project.tui.ItemUIRepresentation;

public class Mushroom extends SingleBoardItem {

    public Mushroom(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    public String toString() {
        return ItemUIRepresentation.MUSHROOM.getRepresentation();
    }
}
