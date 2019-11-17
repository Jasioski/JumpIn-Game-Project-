package project.modelRefactored;

import io.atlassian.fugue.Either;
import io.atlassian.fugue.Pair;
import project.tui.ItemUIRepresentation;

public abstract class BoardItem implements MaybeObstacle {
    public final Either<Coordinate, Pair<Coordinate, Coordinate>> coordinate;

    /**
     * The item's UI representation (a character that represents the item).
     */
    protected ItemUIRepresentation uIRepresentation =
            ItemUIRepresentation.EMPTY;

    public BoardItem(Pair<Coordinate, Coordinate> coordinate) {
        this.coordinate = Either.right(coordinate);
    }

    public BoardItem(Coordinate coordinate) {
        this.coordinate = Either.left(coordinate);
    }

}
