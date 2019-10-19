package project;

import java.util.List;

public interface Jumpable {
	 List<Coordinate> jump(Direction direction, List<BoardItem> slice) throws JumpObstacleException;
}
