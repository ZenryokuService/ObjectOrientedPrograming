package jp.zenryoku.tutorial;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

/**
 * じゃんけんゲームのテストクラス。各手は以下のように定義する。
 * グー= "0"
 * チョキ= "1"
 * パー= "2"
 * プレーヤーの勝利="0"
 * プレーヤーの敗北="1"
 *
 * @author 実装者の名前
 */
@Disabled
@TestInstance(Lifecycle.PER_CLASS)
public class FirstJankenMainTest {
	/** テストクラス */
	private static FirstJankenMain target;
	/** ログ出力 */
	private static final Logger LOG = LoggerFactory.getLogger(FirstJankenMainTest.class);
	/** 標準出力確認 */
	private static final ByteArrayOutputStream console = new ByteArrayOutputStream();
	/** 改行コード */
	private static final String lineSeparator = System.lineSeparator();
	/** グー */
	private static final String GU = "0";
	/** チョキ */
	private static final String CHOKI = "1";
	/** パー */
	private static final String PA = "2";
	/** プレーヤーの勝ち */
	private static final String YOU_WIN = "0";
	/** プレーヤーの負け */
	private static final String YOU_LOOSE = "1";
	/** 引き分け */
	private static final String AIKO = "2";
	/** 終了するときのフラグ */
	private static final boolean FINISH = true;
	/** もう一度やるときのフラグ */
	private static final boolean ONE_MORE = false;
	/** じゃんけんの時に表示する表 */
	private static final String printTable = "****************" + lineSeparator
			+ "*グー   = 0    *" + lineSeparator
			+ "*チョキ = 1    *" + lineSeparator
			+ "*パー   = 2    *" + lineSeparator
			+ "****************" + lineSeparator;


	/**
	 * java.lang.refrectionを使用してプライベート修飾子のメソッドを取得します。
	 * ※privateは外部から参照することができないのでアクセス権を変更する必要がある。
	 * 　実装方法: clazz.setAccessible(true);
	 *
	 * @param clazz テスト対象クラス
	 * @param methodName テストするメソッド名
	 * @args 起動するメソッドの引数
	 */
	private Method getPrivateMethod(Class clazz, String methodName, Class<?> ... paramType) {
		// テスト対象クラスを返却する
		Method testMethod = null;
		try {
			testMethod = clazz.getDeclaredMethod(methodName, paramType);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return testMethod;
	}

	/**
	 * すべてのテストケースを実行するための準備をする。
	 */
	@BeforeAll
	public static void initClass() {
		// テスト対象クラスのインスタンス生成
		target = new FirstJankenMain();
		// 標準出力を確認するための設定
		System.setOut(new PrintStream(console));
	}

	/**
	 * このテストクラスの実行終了後に行うべき後始末。
	 * 基本的には、フィールド変数のインスタンスなどを開放するが、今回のフィールド変数は
	 * 静的フィールド(staticフィールド)なので、アプリ終了時に解放されるので処理なし。
	 */
	@AfterAll
	public static void terminatedClass() {
		// 標準出力を元に戻す
		System.setOut(System.out);
	}

	/**
	 * 勝敗判定MAP作成処理。
	 */
	@BeforeEach
	public void testInit() {
		// テストするメソッドを取得する ※警告が出るが、引数なし、返却ちなしのメソッドなので良しとする
		Method test = getPrivateMethod(target.getClass(), "createJudgeMap", null);
		// テストを実行する
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// ※警告が出るが、引数なし、返却ちなしのメソッドなので良しとする
			test.invoke(target, null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("メソッドの起動時に問題が発生しました。");
		}
	}

	/**
	 * フィールド変数に使用するMapクラスが作成できることを確認。
	 * ※テスト準備処理で勝敗判定MAP作成済み。
	 * ＜追加実装＞
	 * ・手マップをフィールドに追加
	 * ・手マップ、勝敗判定マップの内容確認
	 */
	@Test
	public void testCreateJudgeMap() {
		// 実行結果の確認、フィールド変数が生成できたか確認する。
		String judgeName = "judgeMap";
		String teName = "teMap";
		// judgeMapの確認
		Map<String, Integer> mapJudge = null;
		// teMapの確認
		Map<String, String> mapTe = null;
		try {
			Field judgeField = target.getClass().getDeclaredField(judgeName);
			Field teField = target.getClass().getDeclaredField(teName);
			if (judgeField != null && teField != null) {
				LOG.info(() ->"フィールド取得成功 ");
			} else {
				fail("フィールド変数が生成されていません。");
			}
			// アクセス権を変更する
			judgeField.setAccessible(true);
			teField.setAccessible(true);
			mapJudge =  (Map<String, Integer>) judgeField.get(target);
			mapTe = (Map<String, String>) teField.get(target);
			if (mapJudge != null && mapTe != null) {
				// judgeMapの内容確認
				assertEquals(new Integer(YOU_WIN), mapJudge.get(GU + CHOKI)) ;
				assertEquals(new Integer(YOU_WIN), mapJudge.get(CHOKI + PA)) ;
				assertEquals(new Integer(YOU_WIN), mapJudge.get(PA + GU)) ;
				assertEquals(new Integer(YOU_LOOSE), mapJudge.get(GU + PA)) ;
				assertEquals(new Integer(YOU_LOOSE), mapJudge.get(CHOKI + GU)) ;
				assertEquals(new Integer(YOU_LOOSE), mapJudge.get(PA + CHOKI)) ;
				assertEquals(new Integer(AIKO), mapJudge.get(GU + GU)) ;
				assertEquals(new Integer(AIKO), mapJudge.get(CHOKI + CHOKI)) ;
				assertEquals(new Integer(AIKO), mapJudge.get(PA + PA)) ;
				// teMapの内容確認
				assertEquals("グー", mapTe.get(GU)) ;
				assertEquals("チョキ", mapTe.get(CHOKI)) ;
				assertEquals("パー", mapTe.get(PA)) ;
			} else {
				fail("インスタンスが生成されていません");
			}
		} catch (ClassCastException e) {
			e.printStackTrace();
			fail("クラスのキャストに失敗しました。");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			fail("フィールド変数:が見つかりません");
		} catch (SecurityException e) {
			e.printStackTrace();
			fail("セキュリティ違反がありました。");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスができません。");
		}
	}

	/**
	 * printJankenAikoのテストを行う。
	 * 引数にTrueを渡した場合
	 */
	@Test
	public void testPrintJankenAiko_True() {
		console.reset();
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "printJankenAiko", boolean.class);
		// テストを実行する
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// ※警告が出るが、引数なし、返却ちなしのメソッドなので良しとする
			test.invoke(target, true);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("メソッドの起動時に問題が発生しました。");
		}
		// println()で出力されたものには改行コードがついている。
		assertEquals(printTable + "じゃんけん ..." + lineSeparator, console.toString());
	}

	/**
	 * printJankenAikoのテストを行う。
	 * 引数にFalseを渡した場合
	 */
	@Test
	public void testPrintJankenAiko_False() {
		console.reset();
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "printJankenAiko", boolean.class);
		// テストを実行する
		Object res = null;
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// ※警告が出るが、引数なし、返却ちなしのメソッドなので良しとする
			res = test.invoke(target, false);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("メソッドの起動時に問題が発生しました。");
		}
		assertEquals(printTable + "あいこで ..." + lineSeparator, console.toString());
	}

	/**
	 * acceptInputのテストを行う。
	 * 標準入力に「Hello」と入力した場合にテストOKとなる。
	 */
	@Test
	public void testAcceptInput() {
		console.reset();
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "acceptInput", null);
		// テストを実行する
		Object res = null;
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// ※警告が出るが、引数なし、返却ちなしのメソッドなので良しとする
			LOG.info(()->"*** Helloと入力するテスト ***");

			res = test.invoke(target, null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("メソッドの起動時に問題が発生しました。");
		}
		// 標準入力なので、入力値を固定する必要がある。
		assertEquals("Hello", res.toString());
	}

	/**
	 * printShoのテストを行う。
	 * テストケース作成時に、「ポン！」と「しょ！」を
	 * 使い分ける必要があることに気が付いたので、メソッド名と処理内容を変更。
	 */
	@Test
	public void testPrintPonOrSho_True() {
		console.reset();
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "printPonOrSho", boolean.class);
		// テストを実行する
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// ※警告が出るが、引数なし、返却ちなしのメソッドなので良しとする
			test.invoke(target, true);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("メソッドの起動時に問題が発生しました。");
		}

		assertEquals("ポン！" + lineSeparator, console.toString());
	}

	/**
	 * printShoのテストを行う。
	 * テストケース作成時に、「ポン！」と「しょ！」を
	 * 使い分ける必要があることに気が付いたので、メソッド名と処理内容を変更。
	 */
	@Test
	public void testPrintPonOrSho_False() {
		console.reset();
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "printPonOrSho", boolean.class);
		// テストを実行する
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// ※警告が出るが、引数なし、返却ちなしのメソッドなので良しとする
			test.invoke(target, false);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("メソッドの起動時に問題が発生しました。");
		}

		assertEquals("しょ！" + lineSeparator, console.toString());
	}

	/**
	 * judgeWinLooseのテストを行う。
	 * ケース１：プレーヤーの勝ち
	 */
	@Test
	public void testJudgeWinLoose1() {
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "judgeWinLoose", String.class, String.class);
		// テストを実行する
		Object res = null;
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// グー(プレーヤー)とチョキ(CPU)でプレーヤーの勝ち
			res = (int) test.invoke(target, GU, CHOKI);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("メソッドの起動時に問題が発生しました。");
		}
		assertEquals(YOU_WIN, res.toString());
	}


	/**
	 * judgeWinLooseのテストを行う。
	 * ケース２：プレーヤーの勝ち
	 */
	@Test
	public void testJudgeWinLoose2() {
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "judgeWinLoose", String.class, String.class);
		// テストを実行する
		Object res = null;
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// グー(プレーヤー)とチョキ(CPU)でプレーヤーの勝ち
			res = test.invoke(target, CHOKI, PA);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("メソッドの起動時に問題が発生しました。");
		}
		assertEquals(YOU_WIN, res.toString());
	}

	/**
	 * judgeWinLooseのテストを行う。
	 * ケース３：プレーヤーの勝ち
	 */
	@Test
	public void testJudgeWinLoose3() {
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "judgeWinLoose", String.class, String.class);
		// テストを実行する
		Object res = null;
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// プレーヤーの勝ち
			res = test.invoke(target, PA, GU);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("メソッドの起動時に問題が発生しました。");
		}
		assertEquals(YOU_WIN, res.toString());
	}

	/**
	 * judgeWinLooseのテストを行う。
	 * ケース４：プレーヤーの負け
	 */
	@Test
	public void testJudgeWinLoose4() {
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "judgeWinLoose", String.class, String.class);
		// テストを実行する
		Object res = null;
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// プレーヤーの勝ち
			res = test.invoke(target, GU, PA);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("メソッドの起動時に問題が発生しました。");
		}
		assertEquals(YOU_LOOSE, res.toString());
	}

	/**
	 * judgeWinLooseのテストを行う。
	 * ケース５：プレーヤーの負け
	 */
	@Test
	public void testJudgeWinLoose5() {
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "judgeWinLoose", String.class, String.class);
		// テストを実行する
		Object res = null;
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// プレーヤーの勝ち
			res = test.invoke(target, CHOKI, GU);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("メソッドの起動時に問題が発生しました。");
		}
		assertEquals(YOU_LOOSE, res.toString());
	}

	/**
	 * judgeWinLooseのテストを行う。
	 * ケース６：プレーヤーの負け
	 */
	@Test
	public void testJudgeWinLoose6() {
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "judgeWinLoose", String.class, String.class);
		// テストを実行する
		Object res = null;
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// プレーヤーの勝ち
			res = test.invoke(target, PA, CHOKI);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("メソッドの起動時に問題が発生しました。");
		}
		assertEquals(YOU_LOOSE, res.toString());
	}

	/**
	 * printJudgeのテストを行う。
	 * 勝敗判定を表示する(プレーヤーの勝利)
	 */
	@Test
	public void testPrintJudge_WIN() {
		console.reset();
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "printJudge", int.class);
		// テストを実行する
		boolean res = false;
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// プレーヤーの勝ち(文字列からINT型に変換
			res = (boolean) test.invoke(target, Integer.parseInt(YOU_WIN));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("メソッドの起動時に問題が発生しました。");
		}
		assertEquals(FINISH, res);
	}

	/**
	 * 不足ケースのため追加
	 * printJudgeのテストを行う。
	 * 勝敗判定を表示する(プレーヤーの負け)
	 */
	@Test
	public void testPrintJudge_LOOSE() {
		console.reset();
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "printJudge", int.class);
		// テストを実行する
		boolean res = false;
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// プレーヤーの勝ち(文字列からINT型に変換
			res = (boolean) test.invoke(target, Integer.parseInt(YOU_LOOSE));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("メソッドの起動時に問題が発生しました。");
		}
		assertEquals(FINISH, res);
	}

	/**
	 * 不足ケースのため追加
	 * printJudgeのテストを行う。
	 * 勝敗判定を表示する(あいこの時)
	 */
	@Test
	public void testPrintJudge_AIKO() {
		console.reset();
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "printJudge", int.class);
		// テストを実行する
		boolean res = false;
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// プレーヤーの勝ち(文字列からINT型に変換
			res = (boolean) test.invoke(target, Integer.parseInt(AIKO));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("メソッドの起動時に問題が発生しました。");
		}
		assertEquals(ONE_MORE, res);
	}

	/**
	 * ＜追加実装＞
	 * プレーヤーの手とCPUの手を表示する処理を実装していなかったので
	 * 追加分のテストを行う
	 */
	@Test
	public void testPrintTe() {
		console.reset();
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "printTe", String.class, String.class);
		// テストを実行する
		boolean res = false;
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// プレーヤーの勝ち(文字列からINT型に変換
			test.invoke(target, GU, CHOKI);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("メソッドの起動時に問題が発生しました。");
		}
		assertEquals("ユーザー：グー" + lineSeparator + "CPU：チョキ" + lineSeparator, console.toString());
	}

	@Test
	public void testInputCheck() {
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "inputCheck", String.class);
		// テストを実行する
		boolean res = false;
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// プレーヤーの勝ち(文字列からINT型に変換
			assertTrue((boolean) test.invoke(target, GU));
			assertTrue((boolean) test.invoke(target, CHOKI));
			assertTrue((boolean) test.invoke(target, PA));
			// 想定外の値１
			assertFalse((boolean) test.invoke(target, "3"));
			// 想定外の値２
			assertFalse((boolean) test.invoke(target, "-1"));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("メソッドの起動時に問題が発生しました。");
		}
	}

}
