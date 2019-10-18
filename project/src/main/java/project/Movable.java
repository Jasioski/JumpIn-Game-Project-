package project;

import java.util.List;

public interface Movable {
	public List<Coordinate> move (Direction direction, int spaces, List<BoardItem> slice);
}
