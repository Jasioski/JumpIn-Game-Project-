package project.modelRefactored;

/**
 * Specifies a board item with a single coordinate.
 */
public abstract class SingleBoardItem extends BoardItem{

    public SingleBoardItem(Coordinate coordinate) {
        super(coordinate);
    }
}
