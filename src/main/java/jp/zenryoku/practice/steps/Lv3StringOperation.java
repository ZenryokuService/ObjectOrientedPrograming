package jp.zenryoku.practice.steps;

public class Lv3StringOperation {
	public static void main(String[] args) {
		// String型の宣言
		String str;
		// String型の初期化
		String moji = "moji";

		/* ********************
		 *      エラーになる
		 **********************/
		//int moji = "moji"; int型変数にString型のリテラルは設定できない
		// double moji = 'a'; double型変数にchar型のリテラルは設定でいない


		// Stringクラスは、char型の配列に変換できる
		char[] charArray = moji.toCharArray();


	}
}
