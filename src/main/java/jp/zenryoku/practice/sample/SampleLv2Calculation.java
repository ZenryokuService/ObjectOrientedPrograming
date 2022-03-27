package jp.zenryoku.practice.sample;

/**
 * 四則計算を行う。
 * 
 * @author teacher
 * 2020/11/14
 */
public class SampleLv2Calculation {
	/**
	 * メインメソッド
	 * @param args プログラム引数
	 */
	public static void main(String[] args) {
		// メインメソッドの中では、newする必要がある
		SampleLv2Calculation main = new SampleLv2Calculation();
		// 計算開始を表示(引数なし、返り値なし)
		main.printStart();
		int left = 1;
		int right = 2;
		// 足し算を行う、メンバ・メソッドを呼び出す。(引数あり、返り値なし)
		main.printTashizan(left, right);
		// 足し算の結果を表示する
		main.printTashizan(left, right, "足し算の結果");

		// 引き算を行う
		main.printHikizan(left, right);
		main.printHikizan(left, right, "引き算の結果");

		// かけ算を行う
		main.printKakezan(left, right);
		main.printKakezan(left, right, "かけ算の結果");

		// わり算を行う
		main.printWarizan(left, right);
		main.printWarizan(left, right, "わり算の結果");

		// 剰余算を行う
		main.printJoyozan(left, right);
		main.printJoyozan(left, right, "剰余算の結果");

	}
	/**
	 * 処理開始の表示を行う。
	 */
	public void printStart() {
		System.out.println("*** Start Calculations ***");
	}

	/**
	 * 足し算の結果を表示する。
	 * 
	 * @param left 左の数
	 * @param right 右の数
	 */
	public void printTashizan(int left, int right) {
		// 足し算の結果を代入
		int answer = left + right;
		// 計算を表示
		System.out.println(left + " + " + right + " = " +  answer);
	}

	/**
	 * 足し算の結果を表示する。
	 * 上記のメソッドをオーバーロードする。
	 * 
	 * @param left 左の数
	 * @param right 右の数
	 * @param str 表示する文言
	 */
	public void printTashizan(int left, int right, String str) {
		// 足し算の結果を代入
		int answer = left + right;
		// 計算を表示
		System.out.println(str + ": " + left + " + " + right + " = " +  answer);
	}

	/**
	 * 引き算の結果を表示する。
	 * 
	 * @param left 左の数
	 * @param right 右の数
	 */
	public void printHikizan(int left, int right) {
		// 足し算の結果を代入
		int answer = left - right;
		// 計算を表示
		System.out.println(left + " - " + right + " = " +  answer);
	}

	/**
	 * 引き算の結果を表示する。
	 * 上記のメソッドをオーバーロードする。
	 * 
	 * @param left 左の数
	 * @param right 右の数
	 * @param str 表示する文言
	 */
	public void printHikizan(int left, int right, String str) {
		// 足し算の結果を代入
		int answer = left - right;
		// 計算を表示
		System.out.println(str + ": " + left + " - " + right + " = " +  answer);
	}

	/**
	 * かけ算の結果を表示する。
	 * 
	 * @param left 左の数
	 * @param right 右の数
	 */
	public void printKakezan(int left, int right) {
		// 足し算の結果を代入
		int answer = left * right;
		// 計算を表示
		System.out.println(left + " * " + right + " = " +  answer);
	}

	/**
	 * かけ算の結果を表示する。
	 * 上記のメソッドをオーバーロードする。
	 * 
	 * @param left 左の数
	 * @param right 右の数
	 * @param str 表示する文言
	 */
	public void printKakezan(int left, int right, String str) {
		// 足し算の結果を代入
		int answer = left * right;
		// 計算を表示
		System.out.println(str + ": " + left + " * " + right + " = " +  answer);
	}

	/**
	 * わり算の結果を表示する。
	 * 
	 * @param left 左の数
	 * @param right 右の数
	 */
	public void printWarizan(int left, int right) {
		// 足し算の結果を代入
		int answer = left / right;
		// 計算を表示
		System.out.println(left + " / " + right + " = " +  answer);
	}

	/**
	 * わり算の結果を表示する。
	 * 上記のメソッドをオーバーロードする。
	 * 
	 * @param left 左の数
	 * @param right 右の数
	 * @pram str 表示する文言
	 */
	public void printWarizan(int left, int right, String str) {
		// 足し算の結果を代入
		int answer = left / right;
		// 計算を表示
		System.out.println(str + ": " + left + " / " + right + " = " +  answer);
	}

	/**
	 * 剰余算の結果を表示する。
	 * 
	 * @param left 左の数
	 * @param right 右の数
	 */
	public void printJoyozan(int left, int right) {
		// 足し算の結果を代入
		int answer = left % right;
		// 計算を表示
		System.out.println(left + " % " + right + " = " +  answer);
	}

	/**
	 * 剰余算の結果を表示する。
	 * 上記のメソッドをオーバーロードする。
	 * 
	 * @param left 左の数
	 * @param right 右の数
	 * @param str 表示する文言
	 */
	public void printJoyozan(int left, int right, String str) {
		// 足し算の結果を代入
		int answer = left % right;
		// 計算を表示
		System.out.println(str + ": " + left + " % " + right + " = " +  answer);
	}

}
