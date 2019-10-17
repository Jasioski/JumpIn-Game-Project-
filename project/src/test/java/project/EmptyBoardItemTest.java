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
		assertEquals(emptyItem.getType(), type, 
				"type should be the same");
	}

}
