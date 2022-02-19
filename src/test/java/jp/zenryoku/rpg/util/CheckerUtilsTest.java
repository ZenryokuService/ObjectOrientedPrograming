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
	//@Test
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

	@Test
	public void testIsStartEffectScene() {
		assertTrue(CheckerUtils.isStartEffectScene("<effect:MNY+100>"));
		assertTrue(CheckerUtils.isStartEffectScene("<effect:POI+1>"));
		assertTrue(CheckerUtils.isStartEffectScene("<effect:ITM-たんけん3>"));
		assertTrue(CheckerUtils.isStartEffectScene("<effect:ITM+やくそう1>"));
		assertTrue(CheckerUtils.isStartEffectScene("<effect:MNY-200>"));

		checker("<effect:ITM+やくそう1>", "ITM", "+", "やくそう1");
		checker("<effect:MNY-200>", "MNY", "-", "200");
		checker("<effect:ITM-たんけん3>", "ITM", "-", "たんけん3");
	}

	private void checker(String line, String k, String o, String n) {
		// 記号 + (プラス) or -(マイナス) なまえ 個数の指定
		String effect = line.split(":")[1];
		// 記号
		String kigo = effect.substring(0,3);
		assertEquals(k, kigo);
		// 演算子(+ or -)
		String ope = effect.substring(3, 4);
		assertEquals(o, ope);
		// 名前
		String name = effect.substring(4, effect.length() - 1);
		assertEquals(n, name);
		int res = CheckerUtils.indexOfNum(n);
		System.out.println(n.substring(0, res));
	}

	@Test
	public void testIndexOfNum() {
		int res = CheckerUtils.indexOfNum("たかだのっば1");
		assertEquals(6, res);
		int res2 = CheckerUtils.indexOfNum("たかだ1234");
		assertEquals(3, res2);
	}
}
