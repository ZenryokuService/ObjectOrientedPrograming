package jp.zenryoku.rpg;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * テキストRPGのロジッククラスのテスト
 *
 * @author 実装者の名前
 */
public class TextRpgLogicTest {
	/** テスト対象クラス */
	private TextRpgLogic target;


	@Before
	public void init() {
		target = new TextRpgLogic();
	}

	/**
	 * 初期表示を行うテスト。
	 * 表示したい内容はすぐに変更する可能性が高いので、エラーがないことを確認する。
	 */
	@Test
	public void testInit() {
		try {
			target.init();
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * データを更新するテスト。
	 */
	public void testUpateData() {
	}

	/**
	 * 画面更新のテスト。
	 */
	public void testRender() {
	}
}
