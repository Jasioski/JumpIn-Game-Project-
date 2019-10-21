package project;


public class Hole extends ContainerItem {

    private static final Character HOLE_DISPLAY_CHARACTER = 'H';

    public Hole(Coordinate coordinate) {
        super(coordinate, HOLE_DISPLAY_CHARACTER);
    }

    public Hole(int row, int column) {
        super(row, column, HOLE_DISPLAY_CHARACTER);
    }
}
