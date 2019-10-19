package project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rabbit extends BoardItem implements Jumpable {

	private static final Character RABBIT_DISPLAY_CHARACTER = 'R';

	private boolean isCurrentlyJumping;

	public Rabbit(int row, int column) {
		this(new Coordinate(row, column));
	}

	public Rabbit(Coordinate coordinate) {
		super(RABBIT_DISPLAY_CHARACTER);
		this.setCoordinate(coordinate);
		isCurrentlyJumping = false;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinates.clear();
		this.coordinates.add(coordinate);

	}

	@Override
	public void setCoordinates(List<Coordinate> coordinates) {
		if (coordinates.size() != 1) {
			if (coordinates.size() != 1) {
				throw new IllegalArgumentException("can only add a coordinate "
						+ "of length 1");
			}
		}
		
		this.setCoordinate(coordinates.get(0));
	}

	/**
	 * Get the first coordinate
	 * Rabbits only have a single coordinate
	 */
	public Coordinate getCoordinate() {
		return this.getCoordinates().get(0);
	}

	@Override
	public List<Coordinate> jump(Direction direction, List<BoardItem> slice) throws JumpObstacleException, JumpFailedNoObstacleException, JumpFailedOutOfBoundsException {
		// TODO Auto-generated method stub
		List<Coordinate> newCoordinates = new ArrayList<>();

		List<Coordinate> oldCoordinates = this.getCoordinates();

		switch(direction) {
		case RIGHT:
			try {
				newCoordinates = this.jumpRight(slice);
			} catch (JumpObstacleException | JumpFailedNoObstacleException | JumpFailedOutOfBoundsException e) {
				this.setCoordinates(oldCoordinates);
				throw e;
			}
			break;
		default: 
			break;
		
		}
		return newCoordinates;
	}

	private List<Coordinate> jumpRight(List<BoardItem> slice) throws JumpObstacleException, JumpFailedNoObstacleException, JumpFailedOutOfBoundsException {
		Coordinate currentCoordinate = this.getCoordinate();
		Coordinate newCoordinate = new Coordinate(currentCoordinate.row, currentCoordinate.column + 1);

		// Get all coordinates in the slice without duplicates
		Set<Coordinate> sliceCoordinates = new HashSet<Coordinate>();

		for (BoardItem item : slice) {
			sliceCoordinates.addAll(item.getCoordinates());
		}

		// Check if we are in the board
		if (!sliceCoordinates.contains(newCoordinate)) {
			throw new JumpFailedOutOfBoundsException("Jumping the rabbit " +
					"caused the rabbit to go out of the slice");
		}

		// Check if we are hitting an obstacle
		// loop over all the items in the slice
		boolean hitObstacle = slice.stream().anyMatch(sliceItem -> {
			// check if is at the same coordinate as one of the new coordinates
			if (sliceItem.getCoordinates().contains(newCoordinate)) {
				// match if the item is not empty
				// and not the current item
				if ((sliceItem.getClass() != EmptyBoardItem.class)) {
					if (!(sliceItem.equals(this))) {
						return true;
					}
				}
			}
			// do not match if the item is empty or the current item
			return false;
		});

		// Hitting Obstacle
		if (hitObstacle) {
			this.setCoordinate(newCoordinate);
			isCurrentlyJumping = true;

			// Keep going
			return jumpRight(slice);
		}
		// Found empty spot
		else {
			List<Coordinate> newCoordinates = new ArrayList<>();

			// If we have jumped over something
			if (isCurrentlyJumping) {
				// Stay in the new spot
				newCoordinates.add(newCoordinate);
				this.setCoordinate(newCoordinate);
				isCurrentlyJumping = false;
				return newCoordinates;
			}

			// If we haven't jumped over anything then throw
			// Rabbits cannot jump to adjacent blocks,
			// they must jump over something
			else {
				throw new JumpFailedNoObstacleException("the rabbit did not " +
						"jump over anything");
			}
		}
	}

}
