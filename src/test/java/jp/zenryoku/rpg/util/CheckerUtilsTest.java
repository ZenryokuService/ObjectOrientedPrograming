package jp.zenryoku.rpg.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Check処理のテストクラス。
 *
 * @author 実装者の名前
 */
public class CheckerUtilsTest {

	@Test
	public void testIsStartSceneLine() {
		assertTrue(CheckerUtils.isStartSceneLine("0:A"));
		assertTrue(CheckerUtils.isStartSceneLine("-1:A"));
		assertTrue(CheckerUtils.isStartSceneLine("1:V"));
		assertTrue(CheckerUtils.isStartSceneLine("999:X"));
		assertFalse(CheckerUtils.isStartSceneLine("1:AA"));
		assertFalse(CheckerUtils.isStartSceneLine("1000:A"));
	}
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
	 * @param value 検証する値
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
		assertFalse(CheckerUtils.isStartEffectScene("<effect:MN+1000>"));
		assertTrue(CheckerUtils.isStartEffectScene("<effect:POI%1>"));
		assertTrue(CheckerUtils.isStartEffectScene("<effect:ITM-たんけん3>"));
		assertTrue(CheckerUtils.isStartEffectScene("<effect:ITM+やくそう1>"));
		assertTrue(CheckerUtils.isStartEffectScene("<effect:ZHP-200>"));
		assertFalse(CheckerUtils.isStartEffectScene("<effect:ZHP+20TS4>"));

		checker("<effect:ITM+やくそう1>", "ITM", "+", "やくそう1");
		checker("<effect:MNY-200>", "MNY", "-", "200");
		checker("<effect:ITM-たんけん3>", "ITM", "-", "たんけん3");
	}

	@Test
	public void testIsStartWithTSEffectScene() {
		assertTrue(CheckerUtils.isStartWithTSEffectScene("<effect:MNY+100TS2>"));
		assertFalse(CheckerUtils.isStartWithTSEffectScene("<effect:MN+1000TS1>"));
		assertTrue(CheckerUtils.isStartWithTSEffectScene("<effect:POI%1TS2>"));
		assertTrue(CheckerUtils.isStartWithTSEffectScene("<effect:ITM-たんけん3TS2>"));
		assertTrue(CheckerUtils.isStartWithTSEffectScene("<effect:ITM+やくそう1TS2>"));
		assertTrue(CheckerUtils.isStartWithTSEffectScene("<effect:ZHP-200TS2>"));
		assertTrue(CheckerUtils.isStartWithTSEffectScene("<effect:ZHP+20TS4>"));

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

	@Test
	public void testMasterCatNum() {
		assertTrue(CheckerUtils.isMasterCatNum("-"));
		assertTrue(CheckerUtils.isMasterCatNum("PLY0"));
		assertFalse(CheckerUtils.isMasterCatNum(""));
	}

	@Test
	public void testIsTS() {
		assertFalse(CheckerUtils.isTS("POW+10"));
		assertFalse(CheckerUtils.isTS("POWW+10"));
		assertFalse(CheckerUtils.isTS("POW+1000TS1"));
		assertTrue(CheckerUtils.isTS("POW+999TS1"));
		assertTrue(CheckerUtils.isTS("POW+10%TS3"));
		assertTrue(CheckerUtils.isTS("POW+10T"));
		assertTrue(CheckerUtils.isTS("AGI+10TS"));
		assertTrue(CheckerUtils.isTS("AGI+10TS1"));
	}

	@Test
	public void testIsStartShopScene() {
		assertTrue(CheckerUtils.isStartShopScene("<item:駅前店>"));
		assertTrue(CheckerUtils.isStartShopScene("<item:ShopName>"));
		assertTrue(CheckerUtils.isStartShopScene("<item:店舗A>"));
		assertFalse(CheckerUtils.isStartShopScene("<item:店舗１２３４５６７８９>"));
		assertFalse(CheckerUtils.isStartShopScene("<monster:0>"));
	}

	@Test
	public void testIsZenkaku() {
		assertTrue(CheckerUtils.isZenkaku("店舗１２３４５６７"));
		assertTrue(CheckerUtils.isZenkaku("駅前店"));
		assertTrue(CheckerUtils.isZenkaku("店舗あああ"));
		assertFalse(CheckerUtils.isZenkaku("店舗１２３４５６７８９"));
		assertFalse(CheckerUtils.isZenkaku("itemA"));
		assertFalse(CheckerUtils.isZenkaku("店舗A"));
	}

	@Test
	public void testIsStartBattleScene() {
		assertTrue(CheckerUtils.isStartBattleScene("<monster:0>"));
		assertTrue(CheckerUtils.isStartBattleScene("<monster:0-3>"));
		assertTrue(CheckerUtils.isStartBattleScene("<monster:34-57>"));
		assertTrue(CheckerUtils.isStartBattleScene("<monster:10-999>"));
		assertFalse(CheckerUtils.isStartBattleScene("<monster:a-b>"));
		assertFalse(CheckerUtils.isStartBattleScene("<monster:1-1000>"));
	}

	@Test
	public void testIsStartSelectNextScene() {
		assertTrue(CheckerUtils.isStartSelectNextScene("<1:9>"));
		assertTrue(CheckerUtils.isStartSelectNextScene("<1:10>"));
		assertFalse(CheckerUtils.isStartSelectNextScene("<0:9>"));
		assertFalse(CheckerUtils.isStartSelectNextScene("<0:10>"));
		assertFalse(CheckerUtils.isStartSelectNextScene("<1:100>"));
	}

	@Test
	public void testIsStartEventFlgScene() {
		assertTrue(CheckerUtils.isStartEventFlgScene("<evflg:1:flg=1>"));
		assertTrue(CheckerUtils.isStartEventFlgScene("<evflg:10:flg=1>"));
		assertTrue(CheckerUtils.isStartEventFlgScene("<evflg:999:flg=aaa>"));
		assertFalse(CheckerUtils.isStartEventFlgScene("<evflg:-1:flg=1>"));
		assertFalse(CheckerUtils.isStartEventFlgScene("<evflg:1000:flg=1>"));
		assertFalse(CheckerUtils.isStartEventFlgScene("<evflg:1a01:flg=1>"));
		assertTrue(CheckerUtils.isStartEventFlgScene("<evflg:1:NULL>"));
		assertTrue(CheckerUtils.isStartEventFlgScene("<evflg:1:null>"));

		assertTrue(CheckerUtils.isStartEventFlgScene("<evflg: 1: flg=1>"));
		assertTrue(CheckerUtils.isStartEventFlgScene("<evflg: 10: flg=1>"));
		assertTrue(CheckerUtils.isStartEventFlgScene("<evflg: 999: flg=aaa>"));
		assertFalse(CheckerUtils.isStartEventFlgScene("<evflg: -1: flg=1>"));
		assertFalse(CheckerUtils.isStartEventFlgScene("<evflg: 1000: flg=1>"));
		assertFalse(CheckerUtils.isStartEventFlgScene("<evflg: 1a01: flg=1>"));
		assertTrue(CheckerUtils.isStartEventFlgScene("<evflg: 1: NULL>"));
		assertTrue(CheckerUtils.isStartEventFlgScene("<evflg: 1: null>"));

	}

	@Test
	public void testIsGetEvFlgLine() {
		assertTrue(CheckerUtils.isGetEvFlgLine("<evget:1:1>"));
		assertTrue(CheckerUtils.isGetEvFlgLine("<evget: 1: a>"));
		assertTrue(CheckerUtils.isGetEvFlgLine("<evget: 1: A>"));
		assertFalse(CheckerUtils.isGetEvFlgLine("<evflg: 1: 1>"));
		assertTrue(CheckerUtils.isGetEvFlgLine("<evget:10:1a>"));
		assertTrue(CheckerUtils.isGetEvFlgLine("<evget: 10: 1A>"));
		assertFalse(CheckerUtils.isGetEvFlgLine("<evflg: 10: 1A>"));
		assertTrue(CheckerUtils.isGetEvFlgLine("<evget:999:99a>"));
		assertTrue(CheckerUtils.isGetEvFlgLine("<evget: 999: 99A>"));
		assertFalse(CheckerUtils.isGetEvFlgLine("<evflg: 999: 99Z>"));
	}
}
