package jp.zenryoku.tutorial;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

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
public class FirstJankenMainTest {
	/** テストクラス */
	private static FirstJankenMain target;
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

	/**
	 * java.lang.refrectionを使用してプライベート修飾子のメソッドを起動します。
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

	@BeforeClass
	public static void initClass() {
		// テスト対象クラスのインスタンス生成
		target = new FirstJankenMain();
		// 標準出力を確認するための設定
		System.setOut(new PrintStream(console));
	}

	@AfterClass
	public static void terminatedClass() {
		// 標準出力を元に戻す
		System.setOut(System.out);
	}

	/**
	 * フィールド変数に使用するMapクラスが作成できることを確認。
	 */
	@Test
	public void testCreateJudgeMap() {
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
			Assert.fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.fail("メソッドの起動時に問題が発生しました。");
		}

		// 実行結果の確認、フィールド変数が生成できたか確認する。
		String fieldName = "judgeMap";
		try {
			Field field = target.getClass().getDeclaredField(fieldName);
			if (field != null) {
				System.out.println("テスト成功、フィールド名: " + field.getName());
			} else {
				Assert.fail("フィールド変数が生成されていません。");
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			Assert.fail("フィールド変数:「" + fieldName + "」が見つかりません");
		} catch (SecurityException e) {
			e.printStackTrace();
			Assert.fail("セキュリティ違反がありました。");
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
		Method test = getPrivateMethod(target.getClass(), "printJankenAiko", Boolean.class);
		// テストを実行する
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// ※警告が出るが、引数なし、返却ちなしのメソッドなので良しとする
			test.invoke(target, true);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.fail("メソッドの起動時に問題が発生しました。");
		}
		// println()で出力されたものには改行コードがついている。
		Assert.assertEquals("Hello" + lineSeparator, console.toString());
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
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// ※警告が出るが、引数なし、返却ちなしのメソッドなので良しとする
			test.invoke(target, true);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.fail("メソッドの起動時に問題が発生しました。");
		}
		// println()で出力されたものには改行コードがついている。
		Assert.assertEquals("Hello" + lineSeparator, console.toString());
	}

	/**
	 * acceptInputのテストを行う。
	 * 標準入力に「Hello」と入力した場合にテストOKとなる。
	 */
	@Test
	public void testAcceptInput() {
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "acceptInput", null);
		// テストを実行する
		Object res = null;
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// ※警告が出るが、引数なし、返却ちなしのメソッドなので良しとする
			res = test.invoke(target, null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.fail("メソッドの起動時に問題が発生しました。");
		}
		// 標準入力なので、入力値を固定する必要がある。
		Assert.assertEquals("Hello", res.toString());
	}

	/**
	 * printShoのテストを行う。
	 */
	@Test
	public void testPrintSho() {
		console.reset();
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "printSho", null);
		// テストを実行する
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// ※警告が出るが、引数なし、返却ちなしのメソッドなので良しとする
			test.invoke(target, null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.fail("メソッドの起動時に問題が発生しました。");
		}

		Assert.assertEquals("Sho" + lineSeparator, console.toString());
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
			res = test.invoke(target, GU, CHOKI);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.fail("メソッドの起動時に問題が発生しました。");
		}
		Assert.assertEquals(YOU_WIN, res.toString());
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
			Assert.fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.fail("メソッドの起動時に問題が発生しました。");
		}
		Assert.assertEquals(YOU_WIN, res.toString());
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
			Assert.fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.fail("メソッドの起動時に問題が発生しました。");
		}
		Assert.assertEquals(YOU_WIN, res.toString());
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
			Assert.fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.fail("メソッドの起動時に問題が発生しました。");
		}
		Assert.assertEquals(YOU_LOOSE, res.toString());
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
			Assert.fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.fail("メソッドの起動時に問題が発生しました。");
		}
		Assert.assertEquals(YOU_LOOSE, res.toString());
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
			Assert.fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.fail("メソッドの起動時に問題が発生しました。");
		}
		Assert.assertEquals(YOU_LOOSE, res.toString());
	}

	/**
	 * printJudgeのテストを行う。
	 * 勝敗判定を表示する
	 */
	@Test
	public void testPrintJudge() {
		console.reset();
		// テストするメソッドを取得する
		Method test = getPrivateMethod(target.getClass(), "printJudge", int.class);
		// テストを実行する
		Object res = null;
		try {
			// プライベートメソッドのアクセスを可能にする(テストの時だけ使用するようにする)
			test.setAccessible(true);
			// プレーヤーの勝ち(文字列からINT型に変換
			res = test.invoke(target, Integer.parseInt(YOU_WIN));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("アクセスの仕方に問題があります。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.fail("引数に問題があります。");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.fail("メソッドの起動時に問題が発生しました。");
		}
		Assert.assertEquals(YOU_WIN, res.toString());
	}

}
