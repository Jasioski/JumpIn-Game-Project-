package project;


public class Hole extends ContainerItem {

    public Hole(Coordinate coordinate) {
        super(coordinate, ItemUIRepresentation.HOLE_EMPTY);
    }

    public Hole(int row, int column) {
        this(new Coordinate(row, column));
    }

    @Override
    public Containable removeContainingItem() throws HoleIsEmptyException {
        Containable containable = super.removeContainingItem();

        this.UIRepresentation = ItemUIRepresentation.HOLE_EMPTY;

        return containable;
    }

    @Override
    public void contain(Containable containable) throws HoleAlreadyHasRabbitException {
        super.contain(containable);

        if (containable.getClass() == Rabbit.class) {
            this.UIRepresentation = ItemUIRepresentation.HOLE_OCCUPIED_RABBIT;
        } else if (containable.getClass() == Mushroom.class) {
            this.UIRepresentation = ItemUIRepresentation.HOLE_MUSHROOM;
        }
    }
}
