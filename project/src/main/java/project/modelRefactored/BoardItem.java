package project.modelRefactored;

import io.atlassian.fugue.Either;
import io.atlassian.fugue.Pair;

import java.util.List;

public abstract class BoardItem {
    public final Either<Coordinate, Pair<Coordinate, Coordinate>> coordinate;

    public BoardItem(Pair<Coordinate, Coordinate> coordinate) {
        this.coordinate = Either.right(coordinate);
    }

    public BoardItem(Coordinate coordinate) {
        this.coordinate = Either.left(coordinate);
    }

}
