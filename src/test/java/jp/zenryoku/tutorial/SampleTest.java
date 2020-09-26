package jp.zenryoku.tutorial;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * JUnitのサンプル実装クラス
 *
 * @author 実装者の名前
 */
public class SampleTest {
	/** 月曜を示すフラグ */
	public final int MON = 0;
	/** 月曜を示すフラグ */
	public final int TUE = 1;
	/** 月曜を示すフラグ */
	public final int WED = 2;
	/** 月曜を示すフラグ */
	public final int THU = 3;
	/** 月曜を示すフラグ */
	public final int FRI = 4;
	/** 月曜を示すフラグ */
	public final int SAT = 5;
	/** 月曜を示すフラグ */
	public final int SUN = 6;

	/** テスト対象クラス */
	private static Sample target;

	@BeforeClass
	public static void initClass() {
		// テスト対象クラスのインスタンス化
		target = new Sample();
	}

	/**
	 * テストの前処理
	 */
	@Before
	public void init() {
		// 曜日マップの作成
		target.createYobiMap();
	}

	/**
	 * 曜日マップの作成結果の確認テスト
	 */
	@Test
	public void testCreateYobiMap() {
		// 作成した予備マップを取得する
		Map<String, Integer> yobi = target.getHantei();
		// int型とInteger型で「あいまい」なのでintからInteger型に変換する
		Assert.assertEquals(new Integer(MON), yobi.get("MON"));
		Assert.assertEquals(new Integer(TUE), yobi.get("TUE"));
		Assert.assertEquals(new Integer(WED), yobi.get("WED"));
		Assert.assertEquals(new Integer(THU), yobi.get("THU"));
		Assert.assertEquals(new Integer(FRI), yobi.get("FRI"));
		Assert.assertEquals(new Integer(SAT), yobi.get("SAT"));
		Assert.assertEquals(new Integer(SUN), yobi.get("SUN"));
	}
}
