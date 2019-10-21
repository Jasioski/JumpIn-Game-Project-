package project;

public class ElevatedBoardItem extends ContainerItem {

    private static final Character ELEVATED_DISPLAY_CHARACTER = 'U';

    public ElevatedBoardItem(Coordinate coordinate) {
        super(coordinate, ELEVATED_DISPLAY_CHARACTER);
    }

    public ElevatedBoardItem(int row, int column) {
        super(row, column, ELEVATED_DISPLAY_CHARACTER);
    }

}
