package jp.zenryoku.practice.sample;

/**
 * はじめのプログラム、ハローワールドの説明用、デモクラス。
 * 
 * @author teacher
 * 2020/11/14
 */
public class SampleLv1Hello {
	/**
	 * メインメソッドの定義、Javaは必ずメインメソッドが動く。
	 * 
	 * @param args　プログラム引数
	 */
	public static void main(String[] args) {
		// はじめのプログラム
		System.out.println("Hello World!");

		// メソッドの呼び出し
		printLine();
		// 応用その１(変数を使う)
		String str = "Hello World!その1";
		System.out.println(str);

		// メソッドの呼び出し
		printLine();
		// 応用その２(文字列を連結する)
		System.out.println("Hello " + "World!その2");

		// メソッドの呼び出し
		printLine();
		// 応用その３(2行出力)
		System.out.println("Hello " + "World!その3_1");
		System.out.println("Hello " + "World!その3_2");
	
		// メソッドの呼び出し
		printLine();
		// 応用その４(1行出力)
		System.out.print("Hello World!その4_1");
		System.out.print("Hello World!その4_2");

		// メソッドの呼び出し
		printLine();
		// 応用その４(2行出力)
		String separator = System.lineSeparator();
		System.out.print("Hello World!その4_1" + separator);
		System.out.print("Hello World!その4_2");

	}

	/**
	 * 実行した処理の区切りを表示する。
	 */
	public static void printLine() {
		String SEP = System.lineSeparator();
		System.out.println(SEP + "****************" + SEP);
	}
}
