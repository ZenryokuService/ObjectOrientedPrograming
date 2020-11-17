package jp.zenryoku.practice.steps;

public class Lv3IfStatement {
	public static void main(String[] args) {
		// 真偽値＝trueかfalseのどちらかが入る変数
		boolean yes = true;
		boolean no = false;

		// 宣言したときはデフォルト値が入る
		boolean def; // デフォルト値は「false」

		/* **************************
		 *      論理式
		 ****************************/
		int a = 1;
		int b = 1;
		Object num = "1";
		boolean b0 = a == b; // 等しいときにtrue
		boolean b1 = a != b; // 等しくないときにtrue
		boolean b2 = a <= b; // aがb以下(bを含む)のときtrue
		boolean b3 = a >= b; // aがb以上(bを含む)のときtrue
		boolean b4 = a < b; // aがbより下(bを含まない)のときtrue
		boolean b5 = a > b; // aがbより上(bを含まない)のときtrue
		boolean b6 = num instanceof String; // クラス型変数の型比較

		// IF文の書き方
		// if (論理式) {
		//   trueの時の処理
		// } else {
		//   falseの時の処理
		// }
		if ('a' == 'a') {
			System.out.println("'a' == 'a': TRUE");
		} else {
			System.out.println("'a' == 'a': FALSE");
		}

		if (b0) {
			System.out.println("1.等しいときにtrue");
		}
		if (a == b) {
			System.out.println("2.等しいときにtrue");
		}
		if (b1) {
			System.out.println("3.等しくないときにtrue");
		}
		if (a != b) {
			System.out.println("4.等しくないときにtrue");
		}
		if (b2) {
			System.out.println("5.等しいときにtrue");
		}
		if (a <= b) {
			System.out.println("6.等しいときにtrue");
		}
		if (b3) {
			System.out.println("7.等しくないときにtrue");
		}
		if (a >= b) {
			System.out.println("8.等しくないときにtrue");
		}
		// 値を変更する
		a = 3;
		b = 4;
		if (b4) {
			System.out.println("9.等しくないときにtrue");
		}
		if (a < b) {
			System.out.println("10.等しくないときにtrue");
		}
		// 値を変更する
		a = 2;
		b = 1;
		if (b5) {
			System.out.println("11.等しくないときにtrue");
		}
		if (a > b) {
			System.out.println("12.等しくないときにtrue");
		}
		if (b6) {
			System.out.println("13.等しくないときにtrue");
		}
		if (num instanceof String) {
			System.out.println("14.等しくないときにtrue");
		}
	}
}
