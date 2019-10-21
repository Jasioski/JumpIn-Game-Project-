package project;

import java.util.List;

public class Mushroom extends BoardItem implements Containable {

    public Mushroom(int row, int column) {
        this(new Coordinate(row, column));
    }

    public Mushroom (Coordinate coordinate) {
        super(ItemUIRepresentation.MUSHROOM);
        this.setCoordinate(coordinate);
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {

        this.coordinates.clear();
        this.coordinates.add(coordinate);
    }

    @Override
    public void setCoordinates(List<Coordinate> coordinates) throws
            IllegalArgumentException {
            if (coordinates.size() != 1) {
                throw new IllegalArgumentException("can only add a coordinate "
                        + "of length 1");
            }

        this.setCoordinate(coordinates.get(0));
    }
}
