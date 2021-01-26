package jp.zenryoku.rpg.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Check処理のテストクラス。
 *
 * @author 実装者の名前
 */
public class CheckerUtilsTest {

	/**
	 * CheckerUtils#isGusuのテスト
	 */
	@Test
	public void testIsGusu() {
		assertFalse(CheckerUtils.isGusu(1));
		assertTrue(CheckerUtils.isGusu(2));
		assertFalse(CheckerUtils.isGusu(3));
		assertTrue(CheckerUtils.isGusu(4));
	}

	/**
	 * CheckerUtils#isMultiByteStrのテスト
	 */
	public void testIsMultiByteStr(String value) {
		assertTrue(CheckerUtils.isMultiByteStr("あああ"));
		assertFalse(CheckerUtils.isMultiByteStr("aaaa"));
	}

	/**
	 * CheckerUtils#isCommandInputのテスト
	 */
	@Test
	public void testCommandInput() {
		assertTrue(CheckerUtils.isCommandInput("1", "[1-3]"));
		assertTrue(CheckerUtils.isCommandInput("2", "[1-3]"));
		assertTrue(CheckerUtils.isCommandInput("3", "[1-3]"));
		assertFalse(CheckerUtils.isCommandInput("0", "[1-3]"));
	}

	@Test
	public void testCHeckDigit() {
		assertEquals(3, CheckerUtils.createCheckDigit("201"));
		assertEquals(5, CheckerUtils.createCheckDigit("2012"));
		assertEquals(4, CheckerUtils.createCheckDigit("20129"));
		assertEquals(7, CheckerUtils.createCheckDigit("201293"));

		// チェックディジット付きの注文番号生成
		String tyumonNo = "201293";
		String digit = CheckerUtils.createCheckDigit(tyumonNo).toString();
		String data = tyumonNo + digit;

		// チェックディジットを使用してチェックする

		// 最後の一文字を取得
		String checkDigit = data.substring(data.length() - 1);
		System.out.println("Digit: " + checkDigit);
		assertTrue("7".equals(checkDigit));

	}
}
