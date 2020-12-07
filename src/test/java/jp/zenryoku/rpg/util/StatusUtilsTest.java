package jp.zenryoku.rpg.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StatusUtilsTest {

	@Test
	public void testGetTanjoGetu() {
		String[] yoga = StatusUtils.createYogaSuhi("19800328");
		assertEquals("3", yoga[0]);
		assertEquals("10", yoga[1]);
		assertEquals("4", yoga[2]);
	}


	@Test
	public void testConvertStringToInt() {
		assertEquals(8, StatusUtils.convertStringToInt("44"));
		assertEquals(4, StatusUtils.convertStringToInt("13"));
		assertEquals(10, StatusUtils.convertStringToInt("19"));
		assertEquals(2, StatusUtils.convertStringToInt("29"));
		assertEquals(3, StatusUtils.convertStringToInt("03"));
		assertEquals(10, StatusUtils.convertStringToInt("28"));

	}
}
