package project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
public class MainTest {

	@Test
	void testHelloWorldMessage() {
		String msg = Main.HelloWorldMessage();
		assertEquals(msg, "Hello, world!");
	}

}
