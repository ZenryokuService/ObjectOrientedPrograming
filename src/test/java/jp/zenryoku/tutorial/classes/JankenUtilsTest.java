package jp.zenryoku.tutorial.classes;

import static jp.zenryoku.tutorial.calsses.JankenConst.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import jp.zenryoku.tutorial.calsses.JankenConst;
import jp.zenryoku.tutorial.calsses.JankenUtils;

@Disabled
@TestInstance(Lifecycle.PER_CLASS)
public class JankenUtilsTest {
	/** テスト対象クラス */
	private static JankenUtils target;
	/** ログ出力 */
	private static final Logger LOG = LoggerFactory.getLogger(JankenUtilsTest.class);

	/**
	 * すべてのテストケースを実行するための準備をする。
	 */
	@BeforeAll
	public static void initClass() {
		target  = new JankenUtils();
	}

	/**
	 * 勝敗判定MAP作成の確認
	 */
	@Test
	public void testCreateJudgeMap() {
		Map<String, JankenConst> map = target.getJudgeMap();
		assertNotNull(map);
		assertEquals(YOU_WIN, map.get(GU.toString() + CHOKI.toString()));
		assertEquals(YOU_WIN, map.get(CHOKI.toString() + PA.toString()));
		assertEquals(YOU_WIN, map.get(PA.toString() + GU.toString()));
		assertEquals(YOU_LOOSE, map.get(GU.toString() + PA.toString()));
		assertEquals(YOU_LOOSE, map.get(CHOKI.toString() + GU.toString()));
		assertEquals(YOU_LOOSE, map.get(PA.toString() + CHOKI.toString()));
		assertEquals(AIKO, map.get(GU.toString() + GU.toString()));
		assertEquals(AIKO, map.get(CHOKI.toString() + CHOKI.toString()));
		assertEquals(AIKO, map.get(PA.toString() + PA.toString()));
	}


	/**
	 * 入力テスト「Hello」と入力する
	 */
	@Test
	public void testAcceptInput() {
		LOG.info(() -> "*** Helloと入力するテスト ***");
		String input = target.acceptInput();
		assertEquals("Hello", input);
	}

	/**
	 * 勝敗判定のテスト
	 */
	@Test
	public void testJudgeWinLoose() {
		assertEquals(YOU_WIN, target.judgeWinLoose(GU.toString(), CHOKI.toString()));
		assertEquals(YOU_WIN, target.judgeWinLoose(CHOKI.toString(), PA.toString()));
		assertEquals(YOU_WIN, target.judgeWinLoose(PA.toString(), GU.toString()));
		assertEquals(YOU_LOOSE, target.judgeWinLoose(GU.toString(), PA.toString()));
		assertEquals(YOU_LOOSE, target.judgeWinLoose(CHOKI.toString(), GU.toString()));
		assertEquals(YOU_LOOSE, target.judgeWinLoose(PA.toString(), CHOKI.toString()));
		assertEquals(AIKO, target.judgeWinLoose(GU.toString(), GU.toString()));
		assertEquals(AIKO, target.judgeWinLoose(CHOKI.toString(), CHOKI.toString()));
		assertEquals(AIKO, target.judgeWinLoose(PA.toString(), PA.toString()));
	}
}
