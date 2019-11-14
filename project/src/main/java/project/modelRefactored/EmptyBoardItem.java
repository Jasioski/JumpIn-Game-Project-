package project.modelRefactored;

import project.tui.ItemUIRepresentation;

public class EmptyBoardItem extends SingleBoardItem {

    public EmptyBoardItem(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    public String toString() {
        return ItemUIRepresentation.EMPTY.getRepresentation();
    }
}
