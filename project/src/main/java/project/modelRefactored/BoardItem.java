package project.modelRefactored;

import io.atlassian.fugue.Either;
import java.util.List;

public abstract class BoardItem {
    public final Either<Coordinate, List<Coordinate>> coordinate;

    //Constructor for Fox gives a list of coordinates (two coordinates)
    public BoardItem(List<Coordinate> coordinate) {
        this.coordinate = Either.right(coordinate);
    }

    public BoardItem(Coordinate coordinate) {
        this.coordinate = Either.left(coordinate);
    }

}
