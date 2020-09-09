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
//	/** テスト対象クラス */
//	private static ConsoleUtils target;
	/** ログ出力 */
	private static final Logger LOG = LoggerFactory.getLogger(ConsoleUtilsTest.class);
	/** 標準出力確認 */
	private static final ByteArrayOutputStream console = new ByteArrayOutputStream();
	/** 改行コード */
	private static final String lineSeparator = System.lineSeparator();
	/** じゃんけんの時に表示する表 */
	private static final String printTable = "****************" + lineSeparator
			+ "*グー   = 0    *" + lineSeparator
			+ "*チョキ = 1    *" + lineSeparator
			+ "*パー   = 2    *" + lineSeparator
			+ "****************" + lineSeparator;

	/**
	 * すべてのテストケースを実行するための準備をする。
	 */
	@BeforeClass
	public static void initClass() {
		// 静的メソッドに修正したのでインスタンス化は不要
//		target = new ConsoleUtils();
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
		ConsoleUtils.printJankenAiko(true);
		assertEquals(printTable + "じゃんけん ..." + lineSeparator, console.toString());
	}

	/**
	 * 「あいこ」を表示する
	 */
	@Test
	public void testPrintJankenAiko_False() {
		ConsoleUtils.printJankenAiko(false);
		assertEquals(printTable + "あいこで ..." + lineSeparator, console.toString());
	}

//	/**
//	 * 「Sho!」を表示する
//	 */
//	@Test
//	public void testPintSho() {
//		ConsoleUtils.printSho();
//		assertEquals("Sho!" + lineSeparator, console.toString());
//	}

	/**
	 * 「ポン！」か「しょ！」を表示する
	 */
	public void testPrintPonOrSho_True() {
		ConsoleUtils.printPonOrSho(true);
		assertEquals("ポン！" + lineSeparator, console.toString());
	}

	/**
	 * 「ポン！」か「しょ！」を表示する
	 */
	public void testPrintPonOrSho_False() {
		ConsoleUtils.printPonOrSho(false);
		assertEquals("しょ！" + lineSeparator, console.toString());
	}

	/**
	 * プレーヤーの勝利の表示
	 *
	 * @throws Exception 例外時の処理を実装しないのでTHROWS文にしている
	 */
	@Test
	public void testPrintJudge_WIN() throws Exception {
		ConsoleUtils.printJudge(JankenConst.YOU_WIN);
		assertEquals("YOU WIN!" + lineSeparator, console.toString());
	}

	/**
	 * プレーヤーの勝利の表示
	 *
	 * @throws Exception 例外時の処理を実装しないのでTHROWS文にしている
	 */
	@Test
	public void testPrintJudge_LOOSE() throws Exception {
		ConsoleUtils.printJudge(JankenConst.YOU_LOOSE);
		assertEquals("YOU LOOSE!" + lineSeparator, console.toString());
	}

	/**
	 * 引き分けの表示
	 *
	 * @throws Exception 例外時の処理を実装しないのでTHROWS文にしている
	 */
	@Test
	public void testPrintJudge() throws Exception {
		ConsoleUtils.printJudge(JankenConst.AIKO);
		assertEquals("DRAW!" + lineSeparator, console.toString());
	}

}
