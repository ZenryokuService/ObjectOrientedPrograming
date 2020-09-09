package jp.zenryoku.tutorial.classes;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import jp.zenryoku.tutorial.calsses.ConsoleUtils;
import jp.zenryoku.tutorial.calsses.JankenConst;

public class ConsoleUtilsTest {
	/** テスト対象クラス */
	private static ConsoleUtils target;
	/** ログ出力 */
	private static final Logger LOG = LoggerFactory.getLogger(ConsoleUtilsTest.class);
	/** 標準出力確認 */
	private static final ByteArrayOutputStream console = new ByteArrayOutputStream();
	/** 改行コード */
	private static final String lineSeparator = System.lineSeparator();

	/**
	 * すべてのテストケースを実行するための準備をする。
	 */
	@BeforeClass
	public static void initClass() {
		target = new ConsoleUtils();
		System.setOut(new PrintStream(console));
	}

	/**
	 * このテストクラスの実行終了後に行うべき後始末。
	 * 基本的には、フィールド変数のインスタンスなどを開放するが、今回のフィールド変数は
	 * 静的フィールド(staticフィールド)なので、アプリ終了時に解放されるので処理なし。
	 */
	@AfterClass
	public static void terminatedClass() {
		// 標準出力を元に戻す
		System.setOut(System.out);
	}

	/**
	 * テストを実行する準備をする
	 */
	@Before
	public void testInit() {
		// 標準出力を空にする
		console.reset();
	}

	/**
	 * 「じゃんけん」を表示する
	 */
	@Test
	public void testPrintJankenAiko_True() {
		target.printJankenAiko(true);
		assertEquals("じゃんけん ..." + lineSeparator, console.toString());
	}

	/**
	 * 「あいこ」を表示する
	 */
	@Test
	public void testPrintJankenAiko_False() {
		target.printJankenAiko(false);
		assertEquals("あいこで ..." + lineSeparator, console.toString());
	}

	/**
	 * 「Sho!」を表示する
	 */
	@Test
	public void testPintSho() {
		target.printSho();
		assertEquals("Sho!" + lineSeparator, console.toString());
	}

	/**
	 * プレーヤーの勝利の表示
	 */
	@Test
	public void testPrintJudge_WIN() {
		target.printJudge(JankenConst.YOU_WIN);
		assertEquals("YOU WIN!" + lineSeparator, console.toString());
	}

	/**
	 * プレーヤーの勝利の表示
	 */
	@Test
	public void testPrintJudge_LOOSE() {
		target.printJudge(JankenConst.YOU_LOOSE);
		assertEquals("YOU LOOSE!" + lineSeparator, console.toString());
	}

	/**
	 * 引き分けの表示
	 */
	@Test
	public void testPrintJudge() {
		target.printJudge(JankenConst.AIKO);
		assertEquals("DRAW!" + lineSeparator, console.toString());
	}

}
