package project;

import java.util.ArrayList;
import java.util.List;

public abstract class BoardItem {

	protected List<Coordinate> coordinates;
	private Character displayCharacter;
	
	public BoardItem(Character displayCharacter) {
		this.coordinates = new ArrayList<Coordinate>();
		
		this.displayCharacter = displayCharacter;
	}
	
	/**	
	 * Returns a copy to prevent writes
	 * @return copy of the coordinates
	 */
	public List<Coordinate> getCoordinates() {
		return new ArrayList<Coordinate>(this.coordinates);
	}
	
	public Character getDisplayCharacter() {
		return displayCharacter;
	}
	
	@Override
	public String toString() {
		return "" + getDisplayCharacter();
	}
	
	public abstract void setCoordinates(List<Coordinate> coordinates);

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null) return false;

		if (this.getClass() != o.getClass()) {
			return false;
		}

		BoardItem boardItem = (BoardItem) o;

		return (this.displayCharacter.equals(boardItem.displayCharacter) &&
			this.coordinates.equals(boardItem.coordinates)
		);
	}

	
}