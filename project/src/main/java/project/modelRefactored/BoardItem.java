package project.modelRefactored;

import io.atlassian.fugue.Either;
import java.util.List;

public abstract class BoardItem {
    public final Either<Coordinate, List<Coordinate>> coordinate;

    public BoardItem(List<Coordinate> coordinate) {
        this.coordinate = Either.right(coordinate);
    }

    public BoardItem(Coordinate coordinate) {
        this.coordinate = Either.left(coordinate);
    }
}
