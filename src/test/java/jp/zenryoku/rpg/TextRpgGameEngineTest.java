package jp.zenryoku.rpg;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author 実装者の名前
 *
 */
public class TextRpgGameEngineTest {
	/** テスト対象クラス */
	private TextRpgGameEngine target;

	/**
	 * テスト準備
	 */
	@Before
	public void init() {
		Games game = new TextRpgLogic();
		target = new TextRpgGameEngine(game);
	}

	@After
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
