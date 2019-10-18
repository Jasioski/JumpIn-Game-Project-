package project;

import java.util.Objects;

public class Coordinate {
	public int row;
	public int column;
	
	public Coordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public Coordinate(Coordinate coordinate) {
		this.row = coordinate.row;
		this.column = coordinate.column;
	}
	
	@Override 
	public boolean equals (Object o) {
		if (this == o) return true;
		
		if (o == null) return false;
		
		if (this.getClass() != o.getClass())
			return false;
		
		Coordinate coordinate = (Coordinate) o;
		
		return (this.row == coordinate.row && 
				this.column == coordinate.column);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
	
	@Override
	public String toString() {
		return "" + row + ":" + column;
	}
}
