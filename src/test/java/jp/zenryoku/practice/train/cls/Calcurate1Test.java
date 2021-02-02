package jp.zenryoku.practice.train.cls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author takunoji
 *
 * 2021/02/02
 */
public class Calcurate1Test {
	@Test
	public void Hikizan() {
		// テスト対象クラス
		Calcurate1 target = new Calcurate1();
		int answer = target.hikizan(2, 2);
		// 実行結果の確認をする
		assertEquals(0, answer);
		int answer2 = target.hikizan(5, 2);
		// 実行結果の確認をする
		assertTrue(answer2 != 1);

	}
}
