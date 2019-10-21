package project;

public class ElevatedBoardItem extends ContainerItem {

    public ElevatedBoardItem(Coordinate coordinate) {
        super(coordinate, ItemUIRepresentation.ELEVATED);
    }

    public ElevatedBoardItem(int row, int column) {
        this(new Coordinate(row, column));
    }

    @Override
    public Containable removeContainingItem() throws HoleIsEmptyException {
        Containable containable = super.removeContainingItem();

        this.UIRepresentation = ItemUIRepresentation.ELEVATED;

        return containable;
    }

    @Override
    public void contain(Containable containable) throws HoleAlreadyHasRabbitException {
        super.contain(containable);

        if (containable.getClass() == Rabbit.class) {
            this.UIRepresentation = ItemUIRepresentation.ELEVATED_RABBIT;
        } else if (containable.getClass() == Mushroom.class) {
            this.UIRepresentation = ItemUIRepresentation.ELEVATED_MUSHROOM;
        }
    }
}
