package project;

import java.util.ArrayList;
import java.util.List;

public abstract class BoardItem {

	protected List<Coordinate> coordinates;
	protected ItemUIRepresentation UIRepresentation;
	
	public BoardItem(ItemUIRepresentation UIRepresentation) {
		this.coordinates = new ArrayList<Coordinate>();
		
		this.UIRepresentation = UIRepresentation;
	}
	
	/**	
	 * Returns a copy to prevent writes
	 * @return copy of the coordinates
	 */
	public List<Coordinate> getCoordinates() {
		return new ArrayList<Coordinate>(this.coordinates);
	}
	
	public ItemUIRepresentation getUIRepresentation() {
		return UIRepresentation;
	}
	
	@Override
	public String toString() {
		return this.UIRepresentation.getRepresentation();
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

		return (this.UIRepresentation.equals(boardItem.UIRepresentation) &&
			this.coordinates.equals(boardItem.coordinates)
		);
	}

	
}