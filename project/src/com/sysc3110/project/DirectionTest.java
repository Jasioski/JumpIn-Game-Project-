package com.sysc3110.project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DirectionTest {

	@Test
	void testLeft() {
		assertEquals("LEFT", Direction.LEFT.name());
	}
	
	void testRight() {
		assertEquals("RIGHT", Direction.RIGHT.name());
	}
	
	void testUp() {
		assertEquals("UP", Direction.UP.name());
	}
	
	void testDown() {
		assertEquals("DOWN", Direction.DOWN.name());
	}

}
