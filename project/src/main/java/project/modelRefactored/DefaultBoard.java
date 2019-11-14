package project.modelRefactored;

public class DefaultBoard {
    private Board board;

    public DefaultBoard() {
        board = new Board(5, 5);
        Mushroom mush1 = new Mushroom(new Coordinate(0,0));
        Mushroom mush2 = new Mushroom(new Coordinate(1,3));

        board = board.setItem(mush1);
        board = board.setItem(mush2);
    }

    @Override
    public String toString() {
        return board.toString();
    }

}
