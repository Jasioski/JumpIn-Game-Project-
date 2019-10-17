package project;

import static org.junit.Assert.*;

import org.junit.Test;

public class MainTest {

	@Test
	public void testHelloWorldMessage() {
		String msg = Main.HelloWorldMessage();
		assertEquals(msg, "Hello, world!");
	}

}
