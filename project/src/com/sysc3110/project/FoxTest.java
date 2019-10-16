package com.sysc3110.project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FoxTest {

	@Test
	void testConstructor() {
		int headRow = 0;
		int headColumn = 0;

		int tailRow = 1;
		int tailColumn = 0;

		Fox fox = new Fox(headRow, headColumn, tailRow, tailColumn);

		assertEquals(fox.getTailRow(), tailRow);
		assertEquals(fox.getTailColumn(), tailColumn);
	}

	@Test
	/**
	 * A failing condition as the head and tails should not be able to be at the
	 * same place.
	 */
	void testConstructorHeadAtTails() {
		int headRow = 0;
		int headColumn = 0;

		int tailRow = 0;
		int tailColumn = 0;

		assertThrows(IllegalArgumentException.class, () -> {
			new Fox(headRow, headColumn, tailRow, tailColumn);
		});
	}

	@Test
	/**
	 * A failing condition as the head and tail positions must be within one unit
	 * away.
	 */
	void testConstructorHeadFarFromTails() {
		int headRow = 0;
		int headColumn = 0;

		int tailRow = 3;
		int tailColumn = 0;

		assertThrows(IllegalArgumentException.class, () -> {
			new Fox(headRow, headColumn, tailRow, tailColumn);
		});
	}

	@Test
	/**
	 * A failing condition as the head and tail positions must not be diagonal.
	 */
	void testConstructorHeadDiagnoalFromTails() {
		int headRow = 0;
		int headColumn = 0;

		int tailRow = 1;
		int tailColumn = 1;

		assertThrows(IllegalArgumentException.class, () -> {
			new Fox(headRow, headColumn, tailRow, tailColumn);
		});
	}
}
