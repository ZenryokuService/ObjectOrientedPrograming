package jp.zenryoku.practice.sample;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author teacher
 *
 * 2020/11/16
 */
public class Lv2SampleCalculationTest {
	@SuppressWarnings("deprecation")
	@Test
	public void joyoSample01() {
		int left = 3;
		int right = 2;
		int answer = left % right;
		System.out.println("剰余１：" + answer);
		assertEquals(1, answer);

		double answer2 = 3 / 2;
		System.out.println("割り算: " + answer2);
		assertEquals(1.5, answer, 1.5);

		int answer3 = 3 / 2;
		System.out.println("割り算2: " + answer3);
		assertEquals(1.5, answer, 1.5);
		
		
	}
}
