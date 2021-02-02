package jp.zenryoku.practice.steps;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Lv3StringAndDoubleTest {
	/** テスト対象 */
	private static Lv3StringAndDouble target;

	/** テスト準備 */
	@BeforeAll
	public static void init() {
		target = new Lv3StringAndDouble();
	}

	@Test
	public void test() {
		assertTrue(target.checkIsNumber("12.345"));
		assertTrue(target.checkIsNumber("2.3"));
		assertFalse(target.checkIsNumber("2.a2"));
		assertFalse(target.checkIsNumber("12.3456"));
		assertFalse(target.checkIsNumber("123.345"));

		assertTrue(target.checkIsNumber("2"));
		assertTrue(target.checkIsNumber("12345"));
	}

}
