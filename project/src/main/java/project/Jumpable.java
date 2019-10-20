package project;

import java.util.List;

public interface Jumpable {
	public List<Coordinate> jump(Direction direction, List<BoardItem> slice) throws JumpObstacleException, JumpFailedNoObstacleException, JumpFailedOutOfBoundsException;
}
