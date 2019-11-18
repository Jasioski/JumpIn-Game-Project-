package project.modelRefactored;

import project.tui.ItemUIRepresentation;

public class EmptyBoardItem extends SingleBoardItem {

    public EmptyBoardItem(Coordinate coordinate) {
        super(coordinate);
        this.uIRepresentation = ItemUIRepresentation.EMPTY;
    }

    @Override
    public String toString() {
        return ItemUIRepresentation.EMPTY.getRepresentation();
    }

    @Override
    public boolean isObstacle() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}

        if (o == null) {return false;}

        if (this.getClass() != o.getClass()) {return false;}

        EmptyBoardItem empty = (EmptyBoardItem) o;

        if (empty.coordinate.left().get().column ==
                this.coordinate.left().get().column) {

            if (empty.coordinate.left().get().row ==
                    this.coordinate.left().get().row) {
                return true;
            }

        }

        return false;
    }
}
