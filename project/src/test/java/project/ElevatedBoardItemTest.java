package project;

import org.junit.jupiter.api.Test;
import project.model.Coordinate;
import project.model.ElevatedBoardItem;
import project.model.Mushroom;
import project.tui.ItemUIRepresentation;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevatedBoardItemTest {

    @Test
    /**
     * Contain a mushroom inside the hole
     */
    void testContainMushroom() {
        ElevatedBoardItem elevatedBoardItem = new ElevatedBoardItem(new Coordinate(0, 0));
        Mushroom mushroom = new Mushroom(1, 0);

        try {
            elevatedBoardItem.contain(mushroom);
        } catch (Exception e) {
            fail("Exception was thrown");
        }

        assertTrue(elevatedBoardItem.getContainingItem().isPresent(), "the elevated should" +
                "now contain something");
        assertEquals(mushroom, elevatedBoardItem.getContainingItem().get(), "the elevated should" +
                "now contain the mushroom");
        assertEquals(elevatedBoardItem.getCoordinates(), mushroom.getCoordinates(), "" +
                "the rabbits coordinates should be the same as the elevated" );
        assertEquals(ItemUIRepresentation.ELEVATED_MUSHROOM, elevatedBoardItem.getUIRepresentation(), "the mushroom is now in the hole");
    }

    @Test
    /**
     * Check display representation when Empty
     */
    void tetGetDisplayRepresentationEmpty() {
        ElevatedBoardItem elevatedBoardItem = new ElevatedBoardItem(new Coordinate(0, 0));

        assertTrue(!elevatedBoardItem.getContainingItem().isPresent(), "the elevated should not contain anything");
        assertEquals(ItemUIRepresentation.ELEVATED,
                elevatedBoardItem.getUIRepresentation(), "there should be " +
                        "nothing inside ");
    }

}
