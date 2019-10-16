package com.sysc3110.project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BoardItemTest {

	@Test
	void testConstructor() {
		int row = 0;
		int column = 1;
		String name = "TestItem";
		
		BoardItem boardItem = new BoardItem(row, column, name);
		
		assertEquals(boardItem.getRow(), row);
		assertEquals(boardItem.getColumn(), column);
		assertEquals(boardItem.getName(), name);
	}

}
