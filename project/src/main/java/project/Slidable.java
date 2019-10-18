package project;

import java.util.List;

// TODO: Create Jumapble

public interface Slidable {
	public List<Coordinate> slide (Direction direction, int spaces, List<BoardItem> slice) throws SlideOutOfBoundsException;
}
