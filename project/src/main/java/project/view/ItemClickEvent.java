package project.view;

import project.model.BoardItem;

public class ItemClickEvent {
    public BoardItem item;

    public ItemClickEvent(BoardItem item) {
        this.item = item;
    }

}
