package jp.zenryoku.rpg.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import jp.zenryoku.rpg.charactors.Player;
import jp.zenryoku.tutorial.FirstJankenMainTest;

/**
 * ConsoleUtilsのテストクラス。
 * ConsoleUtils#printMessage(String)はJavaAPIを呼び出すだけなのでテスト不要。
 *
 * @author 実装者の名前
 */
@TestInstance(Lifecycle.PER_CLASS)
public class ConsoleUtilsTest {
	/** テスト対象クラス */
	private static ConsoleUtils target;
	/** ログ出力 */
	private static final Logger LOG = LoggerFactory.getLogger(FirstJankenMainTest.class);
	/** 標準出力確認 */
	private static final ByteArrayOutputStream console = new ByteArrayOutputStream();
	/** 改行コード */
	private static final String SEP = System.lineSeparator();

	@BeforeAll
	public static void initClass() {
		target = ConsoleUtils.getInstance();
		// 標準出力の出力先を変更する
		System.setOut(new PrintStream(console));
	}
	/**
	 * テストの準備
	 */
	@BeforeEach
	public void init() {
		console.reset();
	}

	/**
	 * ステータス表示のテスト:2桁
	 */
	@Test
	public void testPrintBattleStatus1() {
		// プレーヤーは一人
		Player player = new Player("test");
		// 名前の文字数は、全角は４文字、半角８文字まで
		//player.setName();
		player.setLevel(10);
		player.setHP(20);
		player.setMP(10);
		target.printBattleStatus(player);
		LOG.info(() -> SEP + console.toString());

		String expect = "**** test ****" + SEP
				+ "* LV: 10     *" + SEP
				+ "* HP: 20     *" + SEP
				+ "* MP: 10     *" + SEP
				+ "**************" + SEP + SEP;
		assertEquals(expect, console.toString());
	}

	/**
	 * ステータス表示のテスト:3桁
	 */
	@Test
	public void testPrintBattleStatus2() {
		// プレーヤーは一人
		Player player = new Player("test123");
		// 名前の文字数は、全角は４文字、半角８文字まで
		//player.setName();
		player.setLevel(20);
		player.setHP(200);
		player.setMP(100);
		target.printBattleStatus(player);
		LOG.info(() -> SEP + console.toString());

		String expect = "*** test123 ***"+ SEP
				+ "* LV: 20      *" + SEP
				+ "* HP: 200     *" + SEP
				+ "* MP: 100     *" + SEP
				+ "***************" + SEP + SEP;
		assertEquals(expect, console.toString());
	}

	/**
	 * ステータス表示のテスト:1桁
	 */
	@Test
	public void testPrintBattleStatus3() {
		// プレーヤーは一人
		Player player = new Player("プレーヤ");
		player.setLevel(1);
		// 名前の文字数は、全角は４文字、半角８文字まで
		//player.setName();
		player.setHP(3);
		player.setMP(1);
		target.printBattleStatus(player);
		LOG.info(() -> SEP + console.toString());

		String expect = "** プレーヤ ** " + SEP
				+ "* LV: 1      *" + SEP
				+ "* HP: 3      *" + SEP
				+ "* MP: 1      *" + SEP
				+ "**************" + SEP + SEP;
		assertEquals(expect, console.toString());
	}

	/**
	 * ステータス表示のテスト:1桁
	 */
	@Test
	public void testPrintBattleStatus4() {
		// プレーヤーは一人
		Player player = new Player("あ");
		player.setLevel(1);
		// 全角は４文字、半角８文字まで
		player.setHP(3);
		player.setMP(1);
		target.printBattleStatus(player);
		LOG.info(() -> SEP + console.toString());

		String expect = "***** あ *****" + SEP
				+ "* LV: 1      *" + SEP
				+ "* HP: 3      *" + SEP
				+ "* MP: 1      *" + SEP
				+ "**************" + SEP + SEP;
		assertEquals(expect, console.toString());
	}

	/**
	 * ステータス表示のテスト:1桁
	 */
	@Test
	public void testPrintBattleStatus5() {
		// プレーヤーは一人
		Player player = new Player("test1");
		player.setLevel(1);
		// 名前の文字数は、全角は４文字、半角８文字まで
		//player.setName();
		player.setHP(3);
		player.setMP(1);
		target.printBattleStatus(player);
		LOG.info(() -> SEP + console.toString());

		String expect = "**** test1 ****" + SEP
				+ "* LV: 1       *" + SEP
				+ "* HP: 3       *" + SEP
				+ "* MP: 1       *" + SEP
				+ "***************" + SEP + SEP;
		assertEquals(expect, console.toString());
	}
	/**
	 * 行動選択肢リストの表示。
	 */
	@Test
	public void testPrintCommandList() {
		Player player = new Player("test");
		target.printCommandList(player);
		String expect = "1: たたかう" + SEP + "2: ぼうぎょ" + SEP + "3: にげる" + SEP;
		assertEquals(expect, console.toString());
	}

	/**
	 * コンソールクリアのテスト
	 */
	public void testClearConsole() {
		System.out.println("********* Testing *********");
		target.clearConsole();
		assertEquals("", console.toString());
	}


}
