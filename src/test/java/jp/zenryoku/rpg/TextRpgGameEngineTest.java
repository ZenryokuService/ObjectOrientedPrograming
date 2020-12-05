package jp.zenryoku.rpg;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 *
 * @author 実装者の名前
 *
 */
@TestInstance(Lifecycle.PER_CLASS)
public class TextRpgGameEngineTest {
	/** テスト対象クラス */
	private TextRpgGameEngine target;

	/**
	 * テスト準備
	 */
	@BeforeAll
	public void init() {
		Games game = new TextRpgLogic();
		target = new TextRpgGameEngine(game);
	}

	@AfterAll
	public void terminated() {
		target = null;
	}

	/**
	 * ゲームを実行するテスト
	 * {@link Thread#start()}
	 */
	@Test
	public void testStart() {
		target.start();
	}
}
