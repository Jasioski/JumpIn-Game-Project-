package project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardItemTest {

	@Test
	public void testConstructor() {
		int row = 0;
		int column = 1;
		
		BoardItem boardItem = new BoardItem(row, column);
		
		assertEquals(boardItem.getRow(), row);
		assertEquals(boardItem.getColumn(), column);
	}

}
