package jp.zenryoku.rpg.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jp.zenryoku.rpg.charactors.params.PlayerStatus;

public class StatusUtilsTest {

	/**
	 * 誕生日の月を数秘の計算で番号を算出する処理。
	 * <Ex></br>
	 * 10/28であれば、「1 + 0 = 1」と「2 + 8 = 10」
	 */
	@Test
	public void testGetTanjoGetu() {
		String[] yoga = StatusUtils.createYogaSuhi("19800328");
		assertEquals("3", yoga[0]);
		assertEquals("10", yoga[1]);
		assertEquals("4", yoga[2]);
		assertEquals("8", yoga[3]);
		assertEquals("9", yoga[4]);
		assertEquals("8", yoga[5]);
		assertEquals("9", yoga[6]);
		assertEquals("3", yoga[7]);
		assertEquals("4", yoga[8]);
	}


	@Test
	public void testConvertStringToInt() {
		assertEquals(8, StatusUtils.convertStringToInt("44"));
		assertEquals(4, StatusUtils.convertStringToInt("13"));
		assertEquals(10, StatusUtils.convertStringToInt("19"));
		assertEquals(2, StatusUtils.convertStringToInt("29"));
		assertEquals(3, StatusUtils.convertStringToInt("03"));
		assertEquals(10, StatusUtils.convertStringToInt("28"));
		assertEquals(8, StatusUtils.convertStringToInt("80"));
	}

	@Test
	public void testCreatePlayerStatus() throws Exception {
		String[] suhi = StatusUtils.createYogaSuhi("19991204");

		 System.out.println("*** Denug ***");
		for (String su : suhi) System.out.println(su);

		PlayerStatus status = StatusUtils.createStatus(suhi);
		assertEquals(0, status.getPow());// 0
		assertEquals(0, status.getBin());// 0
		assertEquals(1, status.getTai());// 1
		assertEquals(3, status.getKi());// 3
		assertEquals(0, status.getGak());// 0
		assertEquals(0, status.getMei());// 0
		assertEquals(1, status.getSei());// 1
		assertEquals(1, status.getKan());// 1
		assertEquals(1, status.getSin());// 1
		assertEquals(2, status.getRei());// 2

	}
}
