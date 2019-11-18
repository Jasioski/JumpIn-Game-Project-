package project.modelRefactored;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.tui.ItemUIRepresentation;

public final class Mushroom extends SingleBoardItem implements Containable {

    private static Logger logger = LogManager.getLogger(Board.class);

    public Mushroom(int row, int column) {
        super(new Coordinate(row, column));
    }

    public Mushroom(Coordinate coordinate) {
        super(coordinate);
        this.uIRepresentation = ItemUIRepresentation.MUSHROOM;
    }

    @Override
    public boolean isObstacle() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        logger.trace("Checking mushroom!");
        if (this == o) {return true;}

        if (o == null) {return false;}

        if (this.getClass() != o.getClass()) {return false;}

        Mushroom mushroom = (Mushroom) o;

        if (mushroom.coordinate.left().get().column ==
                this.coordinate.left().get().column) {

            if (mushroom.coordinate.left().get().row ==
                    this.coordinate.left().get().row) {
                logger.trace("Mushroom IS SAME!");
                return true;
            }

        }

        return false;
    }
}
