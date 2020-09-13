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
	@Test
	public void testUpateData() {
		// マップを作成する必要があるため、初期表示を行う
		target.init();
		assertTrue(target.updateData("1"));
		assertTrue(target.updateData("2"));
		assertTrue(target.updateData("3"));
		assertFalse(target.updateData("4"));
	}

	/**
	 * 画面更新のテスト。
	 */
	@Test
	public void testRender() {
		target.init();
		assertTrue(target.updateData("1"));
		target.render();
	}
}
