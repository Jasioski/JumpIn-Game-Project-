package com.sysc3110.project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MainTest {

	@Test
	void testHelloWorldMessage() {
		String msg = Main.HelloWorldMessage();
		assertEquals(msg, "Hello, world!");
	}

}
