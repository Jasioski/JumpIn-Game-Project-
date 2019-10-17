package project;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EmptyBoardItemTest {

	@Test
	public void testConstructor() {
		int row = 0;
		int column = 1;
		Character type = 'E';
		
		EmptyBoardItem emptyItem = new EmptyBoardItem(row, column);
		
		assertEquals(emptyItem.getRow(), row, "row should be the same");
		assertEquals(emptyItem.getColumn(), column, 
				"column should be the same");
		assertEquals(emptyItem.getDisplayCharacter(), type, 
				"type should be the same");
		
	}
	
	@Test
	/**	
	 * Set the row
	 */
	void testSetRow() {
		int initialRow = 0;
		int finalRow = 1;
		
		EmptyBoardItem item = new EmptyBoardItem(0,0);
		
		assertEquals(initialRow, item.getRow(), "row should be the initial");
		
		item.setRow(finalRow);

		assertEquals(finalRow, item.getRow(), "row should be the final");
	}
}
