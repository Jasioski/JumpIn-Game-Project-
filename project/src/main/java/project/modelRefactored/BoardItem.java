package project.modelRefactored;

import io.atlassian.fugue.Either;
import io.atlassian.fugue.Pair;

public abstract class BoardItem implements MaybeObstacle {
    public final Either<Coordinate, Pair<Coordinate, Coordinate>> coordinate;

    //TODO: determine if this constructor is necessary
    public BoardItem(Pair<Coordinate, Coordinate> coordinate) {
        this.coordinate = Either.right(coordinate);
    }

    public BoardItem(Coordinate coordinate) {
        this.coordinate = Either.left(coordinate);
    }

}
