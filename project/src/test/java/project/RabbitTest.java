package project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RabbitTest {

	@Test
	void testConstructor() {
		int row = 0;
		int column = 1;
		Character expectedDisplayCharacter = 'R';
		
		Rabbit rabbit = new Rabbit(row, column);
		
		assertEquals(rabbit.getRow(), row, "row should be same");
		assertEquals(rabbit.getColumn(), column, "column should be same");
		assertEquals(rabbit.getDisplayCharacter(), expectedDisplayCharacter,
				"the display character should be " + expectedDisplayCharacter);
	}

}
