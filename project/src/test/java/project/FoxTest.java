package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FoxTest {

	@Test
	void testConstructor() {
		int headRow = 0;
		int headColumn = 0;
		int tailRow = 1;
		int tailColumn = 0;
		
		Character expectedDisplayCharacter = 'F';

		Fox fox = new Fox(headRow, headColumn, tailRow, tailColumn);

		assertEquals(fox.getTailRow(), tailRow, "tailRow should be the same");
		assertEquals(fox.getTailColumn(), tailColumn, "tailColumn should be"
				+ "the same");
		assertEquals(fox.getDisplayCharacter(), expectedDisplayCharacter,
				"the display character should be " + expectedDisplayCharacter);
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
