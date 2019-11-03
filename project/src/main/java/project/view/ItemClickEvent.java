package project.view;

import project.model.BoardItem;
import project.model.Coordinate;

public class ItemClickEvent {
    public Coordinate coordinate;

    public ItemClickEvent(Coordinate itemCoordinate) {
        this.coordinate = itemCoordinate;
    }

}
