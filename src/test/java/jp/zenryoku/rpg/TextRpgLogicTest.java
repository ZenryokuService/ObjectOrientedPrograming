package jp.zenryoku.rpg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * テキストRPGのロジッククラスのテスト
 *
 * @author 実装者の名前
 */
@TestInstance(Lifecycle.PER_CLASS)
public class TextRpgLogicTest {
	/** テスト対象クラス */
	private TextRpgLogic target;


	@BeforeAll
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
			target.init("title");
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * データを更新するテスト。
	 */
	@Test
	public void testUpateData() {
	}

	/**
	 * 画面更新のテスト。
	 */
	public void testRender() {
	}
}
