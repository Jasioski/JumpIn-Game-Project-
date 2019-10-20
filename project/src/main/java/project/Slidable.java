package project;

import java.util.List;

public interface Slidable {
	public List<Coordinate> slide (Direction direction, int spaces, List<BoardItem> slice) throws SlideOutOfBoundsException, SlideHitObstacleException;
}
