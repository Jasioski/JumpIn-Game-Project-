package project;

public class ElevatedBoardItem extends ContainerItem {

    public ElevatedBoardItem(Coordinate coordinate) {
        super(coordinate, ItemUIRepresentation.ELEVATED);
    }

    public ElevatedBoardItem(int row, int column) {
        this(new Coordinate(row, column));
    }

}
