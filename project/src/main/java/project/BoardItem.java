package project;

import java.util.ArrayList;
import java.util.List;

public abstract class BoardItem {

	private List<Coordinate> coordinates;
	private Character displayCharacter;
	
	public BoardItem(Coordinate coordinate, Character displayCharacter) {
		this.coordinates = new ArrayList<Coordinate>();
		
		this.coordinates.add(coordinate);
		
		this.displayCharacter = displayCharacter;
	}
	
	public List<Coordinate> getCoordinates() {
		return this.coordinates;
	}
	
	public BoardItem(int row, int column, Character displayCharacter) {
		this(new Coordinate(row, column), displayCharacter);
	}
	
	public Character getDisplayCharacter() {
		return displayCharacter;
	}
	
	@Override
	public String toString() {
		return "" + getDisplayCharacter();
	}
	
	public abstract void setCoordinate(Coordinate coordinate);
	public abstract void setCoordinates(List<Coordinate> coordinates);
	
	
}