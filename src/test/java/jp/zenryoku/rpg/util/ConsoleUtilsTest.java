package jp.zenryoku.rpg.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.zenryoku.rpg.TextRpgLogic;
import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.RpgItem;
import jp.zenryoku.rpg.data.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.item.Items;
import jp.zenryoku.rpg.item.equip.Armor;
import jp.zenryoku.rpg.item.equip.MainWepon;
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
	/** 設定クラス */
	private static RpgConfig conf;
	/** ログ出力 */
	private static final Logger LOG = LoggerFactory.getLogger(FirstJankenMainTest.class);
	/** 標準出力確認 */
	private static final ByteArrayOutputStream console = new ByteArrayOutputStream();
	/** 改行コード */
	private static final String SEP = System.lineSeparator();

	/**
	 * プライベートメソッドの取得、アクセスを許可して返却する。
	 * @param methodName 取得するメソッド名
	 * @param cls 引数のクラスオブジェクト(可変長)
	 * @return 対象のメソッド
	 * @throws RpgException 想定外のエラー
	 */
	private Method getTargetMethod(String methodName, Class<?>... cls) throws RpgException {
		Method mes = null;
		try {
			mes = target.getClass().getDeclaredMethod(methodName, cls);
			mes.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mes;
	}

	@BeforeAll
	public static void initClass() {
		target = ConsoleUtils.getInstance();
		// 標準出力の出力先を変更する
		System.setOut(new PrintStream(console));
		// 設定クラスのインスタンス
		conf = RpgConfig.getInstance();
		createPlayerStatus(true);

	}
	/**
	 * テストの準備
	 */
	@BeforeEach
	public void init() {
		console.reset();
	}

	/**
	 * プレーヤーのステータスを生成する
	 * @param isValue　値をセットするときにTURE
	 */
	private static void createPlayerStatus(boolean isValue) {
		List<RpgStatus> statuses = new ArrayList<>();
		RpgStatus pow = new RpgStatus();
		pow.setName("ちから");
		pow.setKigo("POW");
		pow.setDiscription("PPPPPの説明");
		pow.setValue(1);
		statuses.add(pow);
		RpgStatus agi = new RpgStatus();
		agi.setName("すばやさ");
		agi.setKigo("AGI");
		agi.setDiscription("すばやさの説明");
		agi.setValue(2);
		statuses.add(agi);
		RpgStatus nt = new RpgStatus();
		nt.setName("かしこさ");
		nt.setKigo("INT");
		nt.setDiscription("かしこさの説明");
		nt.setValue(3);
		statuses.add(nt);
		RpgStatus dex = new RpgStatus();
		dex.setName("きようさ");
		dex.setKigo("DEX");
		dex.setDiscription("きようさの説明");
		dex.setValue(4);
		statuses.add(dex);
		RpgStatus ksm = new RpgStatus();
		ksm.setName("カリスマ");
		ksm.setKigo("KSM");
		ksm.setDiscription("カリスマの説明");
		ksm.setValue(5);
		statuses.add(ksm);

		Map<String, RpgStatus> map = new HashMap<>();
		statuses.forEach(data -> {
			map.put(data.getKigo(), data);
		});

		// ステ－タスマップ
		RpgStatus atk = new RpgStatus();
		atk.setName("こうげきりょく");
		atk.setKigo("ATK");
		atk.setDiscription("攻撃力の説明");
		if (isValue) atk.setValue(6);
		RpgStatus def = new RpgStatus();
		def.setName("防御力");
		def.setKigo("DEF");
		def.setDiscription("防御力の説明");
		if (isValue) def.setValue(7);
		map.put("ATK", atk);
		map.put("DEF", def);
		conf.setStatusMap(map);

		// パラメータマップ
		Map<String, RpgData> params = new HashMap<>();
		params.put("ATK", atk);
		params.put("DEF", def);

		conf.setParamMap(params);
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

		String expect = "**** test123  ****"+ SEP
				+ "* LV: 20         *" + SEP
				+ "* HP: 200        *" + SEP
				+ "* MP: 100        *" + SEP
				+ "******************" + SEP + SEP;
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

		String expect = "**** プレーヤ  ****" + SEP
				+ "* LV: 1           *" + SEP
				+ "* HP: 3           *" + SEP
				+ "* MP: 1           *" + SEP
				+ "*******************" + SEP + SEP;
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

		String expect = "**** あ  ****" + SEP
				+ "* LV: 1     *" + SEP
				+ "* HP: 3     *" + SEP
				+ "* MP: 1     *" + SEP
				+ "*************" + SEP + SEP;
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

		String expect = "**** test1  ****" + SEP
				+ "* LV: 1        *" + SEP
				+ "* HP: 3        *" + SEP
				+ "* MP: 1        *" + SEP
				+ "****************" + SEP + SEP;
		assertEquals(expect, console.toString());
	}

	@Test
	public void testAppendLine() {
		StringBuilder build = new StringBuilder();
		try {
			Method mes = this.getTargetMethod("appendLine", StringBuilder.class, String.class, boolean.class, int.class);
			int res0 = (int) mes.invoke(target,build, "are", false, 4);
			//assertEquals("", build.toString());
			assertEquals(50, res0);

			build = new StringBuilder();
			int res0_1 = (int) mes.invoke(target,build, "are", false, 10);
			assertEquals("**************************** are  ****************************", build.toString());
			assertEquals(62, res0_1);

			build = new StringBuilder();
			int res0_2 = (int) mes.invoke(target,build, "area", false, 10);
			assertEquals("**************************** area ****************************", build.toString());
			assertEquals(62, res0_2);

			build = new StringBuilder();
			int res = (int) mes.invoke(target,build, "あああ", true, 30);
			assertEquals(93, res);

			build = new StringBuilder();
			int res_1 = (int) mes.invoke(target,build, "あああ", true, 14);
			assertEquals("************************** あああ  **************************", build.toString());
			assertEquals(61, res_1);

			build = new StringBuilder();
			int res_2 = (int) mes.invoke(target,build, "ああああ", true, 14);
			assertEquals("**************************** ああああ  ****************************", build.toString());
			assertEquals(67, res_2);

			build = new StringBuilder();
			int res2 = (int) mes.invoke(target,build, "ああああ", true, 30);
			assertEquals(99, res2);

			build = new StringBuilder();
			int res3 = (int) mes.invoke(target,build, "ああああ123", true, 4);
			//assertEquals("", build.toString());
			assertEquals(56, res3);

		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testAppendLine2() {
		StringBuilder build = new StringBuilder();
		try {
			Method mes = this.getTargetMethod("appendLine", StringBuilder.class, String.class, boolean.class, int.class);
			int res = (int) mes.invoke(target,build, "あああ", true, 29);
			assertEquals(91, res);
				assertEquals("***************************************** あああ  *****************************************", build.toString());

			build = new StringBuilder();
			int res2 = (int) mes.invoke(target,build, "aaa", false, 10);
			assertEquals(62, res2);
			assertEquals("**************************** aaa  ****************************", build.toString());
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testToByteLength() {
		try {
			Method mes = this.getTargetMethod("appendSpace", String.class, int.class);

		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testAppendSpace() {
		try {
			Method mes = this.getTargetMethod("toByteLength", String.class);

			String test1 = "nameA";
			int len = (int) mes.invoke(target, test1);
			assertEquals(5, len);

			String test2 = "あああ";
			int len1 = (int) mes.invoke(target, test2);
			assertEquals(6, len1);

			String test3 = "あああ123";
			int len2 = (int) mes.invoke(target, test3);
			assertEquals(9, len2);


		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testAppendSpace2() {
		try {
			Method mes = this.getTargetMethod("appendSpace", String.class, int.class);
			String sp = (String) mes.invoke(target, "* ちから: 6", 20);
			assertEquals("         ", sp);
			String sp1 = (String) mes.invoke(target, "* すばやさ: 1", 20);
			assertEquals("       ", sp1);
			String sp2 = (String) mes.invoke(target, " 防御力: 9", 20);
			assertEquals("          ", sp2);
			String sp3 = (String) mes.invoke(target, " ごうげきりょく: 2", 20);
			assertEquals("  ", sp3);
			String sp4 = (String) mes.invoke(target, " ごうげ: 4", 20);
			assertEquals("          ", sp4);
			String sp5 = (String) mes.invoke(target, " 防御力: 7", 30);
			assertEquals(20, sp5.length());
			String sp6 = (String) mes.invoke(target, "", 30);
			assertEquals(30, sp6.length());
			String sp7 = (String) mes.invoke(target, " ぼうぐ: なし", 30);
			assertEquals(17, sp7.length());
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testSobiSize() {
		try {
			Method mes = this.getTargetMethod("getSobiNameLen", Items.class, boolean.class);
			MainWepon main = new MainWepon("たけのこ");
			main.setOffence(21);
			Armor arm = new Armor("かわのよろい");
			arm.setDiffence(12);

			int sp = (Integer) mes.invoke(target, main, true);
			assertEquals(12, sp);
			int sp1 = (Integer) mes.invoke(target, arm, true);
			assertEquals(16, sp1);
			int sp2 = (Integer) mes.invoke(target, null, true);
			assertEquals(4, sp2);
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testMaxSobiSize() {
		try {
			PlayerCharactor player = new PlayerCharactor("こたろう");
			player.setLevel(10);
			// 名前の文字数は、全角は４文字、半角８文字まで
			//player.setName();
			player.setHP(999);
			player.setMP(999);
			player.setStatusMap(conf.getStatusMap());
			MainWepon wep = new MainWepon("やまだの");
			wep.setOffence(12);
			player.setMainWepon(wep);
			Armor arm = new Armor("みかわし");
			arm.setDiffence(10);
			player.setArmor(arm);
			Method mes = this.getTargetMethod("maxSobiSize", PlayerCharactor.class);
			int sp = (Integer) mes.invoke(target, player);
			assertEquals(12, sp);

		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * プレーヤーセ生成時、総部確認時に表示するフルステータス。
	 */
	@Test
	public void testPrintStatus() {

		try {

			// プレーヤーは一人
			PlayerCharactor player = new PlayerCharactor("tes");
			player.setLevel(1);
			// 名前の文字数は、全角は４文字、半角８文字まで
			//player.setName();
			player.setHP(3);
			player.setMP(1);
			player.setStatusMap(conf.getStatusMap());

			target.printStatus(player);
			LOG.info(() -> SEP + console.toString());

			String expect = "********************** tes  **********************" + SEP
					+ "* LV: 1                  * ぶき: なし            *" + SEP
					+ "* HP: 3                  * ぼうぐ: なし          *" + SEP
					+ "* MP: 1                  *                       *" + SEP
					+ "* ちから: 1              * こうげきりょく: 0     *" + SEP
					+ "* すばやさ: 2            * 防御力: 0             *" + SEP
					+ "* かしこさ: 3            *                       *" + SEP
					+ "* きようさ: 4            *                       *" + SEP
					+ "* カリスマ: 5            *                       *" + SEP
					+ "**************************************************" + SEP + SEP;
			assertEquals(expect, console.toString());
		} catch (RpgException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * プレーヤーセ生成時、総部確認時に表示するフルステータス。
	 */
	@Test
	public void testPrintStatusA() {

		try {

			// プレーヤーは一人
			PlayerCharactor player = new PlayerCharactor("テスト");
			player.setLevel(1);
			// 名前の文字数は、全角は４文字、半角８文字まで
			//player.setName();
			player.setHP(3);
			player.setMP(1);
			player.setStatusMap(conf.getStatusMap());

			target.printStatus(player);
			LOG.info(() -> SEP + console.toString());

			String expect = "**************** テスト  ****************" + SEP
					+ "* LV: 1              * ぶき: なし       *" + SEP
					+ "* HP: 3              * ぼうぐ: なし     *" + SEP
					+ "* MP: 1              *                  *" + SEP
					+ "* ちから: 1          * こうげきりょく: 0*" + SEP
					+ "* すばやさ: 2        * 防御力: 0        *" + SEP
					+ "* かしこさ: 3        *                  *" + SEP
					+ "* きようさ: 4        *                  *" + SEP
					+ "* カリスマ: 5        *                  *" + SEP
					+ "*****************************************" + SEP + SEP;
			assertEquals(expect, console.toString());
		} catch (RpgException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * プレーヤーセ生成時、総部確認時に表示するフルステータス。
	 */
	@Test
	public void testPrintStatusB() {

		try {

			// プレーヤーは一人
			PlayerCharactor player = new PlayerCharactor("ああれｇれ");
			player.setLevel(1);
			// 名前の文字数は、全角は４文字、半角８文字まで
			//player.setName();
			player.setHP(3);
			player.setMP(1);
			player.setStatusMap(conf.getStatusMap());

			target.printStatus(player);
			LOG.info(() -> SEP + console.toString());

			String expect = "******************** ああれｇれ  ********************" + SEP
					+ "* LV: 1                    * ぶき: なし             *" + SEP
					+ "* HP: 3                    * ぼうぐ: なし           *" + SEP
					+ "* MP: 1                    *                        *" + SEP
					+ "* ちから: 1                * こうげきりょく: 0      *" + SEP
					+ "* すばやさ: 2              * 防御力: 0              *" + SEP
					+ "* かしこさ: 3              *                        *" + SEP
					+ "* きようさ: 4              *                        *" + SEP
					+ "* カリスマ: 5              *                        *" + SEP
					+ "*****************************************************" + SEP + SEP;
			assertEquals(expect, console.toString());
		} catch (RpgException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testPrintStatusC() {

		try {

			// プレーヤーは一人
			PlayerCharactor player = new PlayerCharactor("ああああ123");
			player.setLevel(1);
			// 名前の文字数は、全角は４文字、半角８文字まで
			//player.setName();
			player.setHP(3);
			player.setMP(1);
			player.setStatusMap(conf.getStatusMap());

			target.printStatus(player);
			LOG.info(() -> SEP + console.toString());

			String expect = "********************* ああああ123  *********************" + SEP
					+ "* LV: 1                     * ぶき: なし              *" + SEP
					+ "* HP: 3                     * ぼうぐ: なし            *" + SEP
					+ "* MP: 1                     *                         *" + SEP
					+ "* ちから: 1                 * こうげきりょく: 0       *" + SEP
					+ "* すばやさ: 2               * 防御力: 0               *" + SEP
					+ "* かしこさ: 3               *                         *" + SEP
					+ "* きようさ: 4               *                         *" + SEP
					+ "* カリスマ: 5               *                         *" + SEP
					+ "********************************************************" + SEP + SEP;
			assertEquals(expect, console.toString());
		} catch (RpgException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	/**
	 * プレーヤーセ生成時、総部確認時に表示するフルステータス。
	 */
	@Test
	public void testPrintStatus2() {

		try {
			// プレーヤーは一人
			PlayerCharactor player = new PlayerCharactor("こたろう");
			player.setLevel(10);
			// 名前の文字数は、全角は４文字、半角８文字まで
			//player.setName();
			player.setHP(999);
			player.setMP(999);
			player.setStatusMap(conf.getStatusMap());
			MainWepon wep = new MainWepon("やまだの");
			wep.setOffence(12);
			player.setMainWepon(wep);
			Armor arm = new Armor("みかわし");
			arm.setDiffence(10);
			player.setArmor(arm);

			target.printStatus(player);
			LOG.info(() -> SEP + console.toString());

			String expect = "************************** こたろう  **************************" + SEP
					+ "* LV: 10                        * ぶき: やまだの(12)          *" + SEP
					+ "* HP: 999                       * ぼうぐ: みかわし(10)        *" + SEP
					+ "* MP: 999                       *                             *" + SEP
					+ "* ちから: 1                     * こうげきりょく: 6           *" + SEP
					+ "* すばやさ: 2                   * 防御力: 7                   *" + SEP
					+ "* かしこさ: 3                   *                             *" + SEP
					+ "* きようさ: 4                   *                             *" + SEP
					+ "* カリスマ: 5                   *                             *" + SEP
					+ "***************************************************************" + SEP + SEP;
			assertEquals(expect, console.toString());
		} catch (RpgException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * プレーヤーセ生成時、総部確認時に表示するフルステータス。
	 */
	@Test
	public void testPrintStatus3() {

		try {
			// プレーヤーは一人
			PlayerCharactor player = new PlayerCharactor("あああ");
			player.setLevel(10);
			// 名前の文字数は、全角は４文字、半角８文字まで
			//player.setName();
			player.setHP(999);
			player.setMP(999);
			player.setStatusMap(conf.getStatusMap());
			MainWepon wep = new MainWepon("やまだ");
			wep.setOffence(12);
			player.setMainWepon(wep);
			Armor arm = new Armor("みかわしの");
			arm.setDiffence(10);
			player.setArmor(arm);

			target.printStatus(player);
			LOG.info(() -> SEP + console.toString());

			String expect = "************************** あああ  **************************" + SEP
					+ "* LV: 10                       * ぶき: やまだ(12)           *" + SEP
					+ "* HP: 999                      * ぼうぐ: みかわしの(10)     *" + SEP
					+ "* MP: 999                      *                            *" + SEP
					+ "* ちから: 1                    * こうげきりょく: 6          *" + SEP
					+ "* すばやさ: 2                  * 防御力: 7                  *" + SEP
					+ "* かしこさ: 3                  *                            *" + SEP
					+ "* きようさ: 4                  *                            *" + SEP
					+ "* カリスマ: 5                  *                            *" + SEP
					+ "*************************************************************" + SEP + SEP;
			assertEquals(expect, console.toString());
		} catch (RpgException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * プレーヤーセ生成時、総部確認時に表示するフルステータス。
	 */
	@Test
	public void testPrintStatus4() {

		try {
			// プレーヤーは一人
			PlayerCharactor player = new PlayerCharactor("aea");
			player.setLevel(10);
			// 名前の文字数は、全角は４文字、半角８文字まで
			//player.setName();
			player.setHP(999);
			player.setMP(999);
			player.setStatusMap(conf.getStatusMap());
			MainWepon wep = new MainWepon("やまだ");
			wep.setOffence(12);
			player.setMainWepon(wep);
			player.setArmor(null);

			target.printStatus(player);
			LOG.info(() -> SEP + console.toString());

			String expect = "**************************** aea  ****************************" + SEP
					+ "* LV: 10                       * ぶき: やまだ(12)            *" + SEP
					+ "* HP: 999                      * ぼうぐ: なし                *" + SEP
					+ "* MP: 999                      *                             *" + SEP
					+ "* ちから: 1                    * こうげきりょく: 6           *" + SEP
					+ "* すばやさ: 2                  * 防御力: 7                   *" + SEP
					+ "* かしこさ: 3                  *                             *" + SEP
					+ "* きようさ: 4                  *                             *" + SEP
					+ "* カリスマ: 5                  *                             *" + SEP
					+ "**************************************************************" + SEP + SEP;
			assertEquals(expect, console.toString());
		} catch (RpgException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * プレーヤーセ生成時、総部確認時に表示するフルステータス。
	 */
	@Test
	public void testPrintStatus5() {

		try {
			// プレーヤーは一人
			PlayerCharactor player = new PlayerCharactor("aea");
			player.setLevel(10);
			// 名前の文字数は、全角は４文字、半角８文字まで
			//player.setName();
			player.setHP(999);
			player.setMP(999);
			player.setStatusMap(conf.getStatusMap());
			MainWepon wep = new MainWepon("たんけん");
			wep.setOffence(12);
			player.setMainWepon(wep);
			player.setArmor(null);

			target.printStatus(player);
			LOG.info(() -> SEP + console.toString());

			String expect = "****************************** aea  ******************************" + SEP
					+ "* LV: 10                         * ぶき: たんけん(12)            *" + SEP
					+ "* HP: 999                        * ぼうぐ: なし                  *" + SEP
					+ "* MP: 999                        *                               *" + SEP
					+ "* ちから: 1                      * こうげきりょく: 6             *" + SEP
					+ "* すばやさ: 2                    * 防御力: 7                     *" + SEP
					+ "* かしこさ: 3                    *                               *" + SEP
					+ "* きようさ: 4                    *                               *" + SEP
					+ "* カリスマ: 5                    *                               *" + SEP
					+ "******************************************************************" + SEP + SEP;
			assertEquals(expect, console.toString());
		} catch (RpgException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
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

	@Test
	public void testPrintRightSideStatus_A() {
		try {
			Method mes = getTargetMethod("printRightSideStatus", StringBuilder.class, String.class, boolean.class, int.class, int.class);
			StringBuilder build = new StringBuilder();
			String vStr = "";
			mes.invoke(target, build, vStr, true, 30, 30);
			assertEquals(30, build.toString().length());

			build = new StringBuilder();
			mes.invoke(target, build, vStr, false, 10, 10);
			// テストの結果は改行コード(2byte)が入っている
			//assertEquals("", build.toString());
			assertEquals(11, build.toString().length());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testPrintRightSideStatus_B() {
		try {
			Method mes = getTargetMethod("printRightSideStatus", String.class, String.class, boolean.class, boolean.class, int.class, int.class);
			StringBuilder build = new StringBuilder();
			String nameStr = "ぶき";
			String valStr = "やまだの(12)";
			// 偶数のマルチバイト underLine=63のケース(sotowaku = underLine % 2 == 0 ? underLine / 2 : underLine / 2 + 1;)
			String res = (String) mes.invoke(target, nameStr, valStr, true, true, 8, 30);// 32 - 2
			assertEquals(" ぶき: やまだの(12)           *" + SEP, res);

			String res1 = (String) mes.invoke(target, "こうげきりょく", "0", true, false, 4, 27);//
			assertEquals(" こうげきりょく: 0         *" + SEP, res1);

			String res2 = (String) mes.invoke(target, "こうげきりょく", "0", true, false, 4, 	31);//
			assertEquals(" こうげきりょく: 0             *" + SEP, res2);

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	/**
	 * コンソールクリアのテスト
	 */
	public void testClearConsole() {
		System.out.println("********* Testing *********");
		target.clearConsole();
		assertEquals("", console.toString());
	}

	//@Test
	public void testPrintConfig() {

	}


}
