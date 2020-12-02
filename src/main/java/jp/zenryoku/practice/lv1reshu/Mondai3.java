package jp.zenryoku.practice.lv1reshu;

public class Mondai3 {
	public static void main(String[] args) {
		// 引数なしの場合を考慮に入れる
		if (args.length == 0) {
			System.out.println("プログラム引数が渡されていません。");
			// 返却ちが「void」の時にreturn;とやるとメソッドの処理を終了することができる。
			return;
		}
		if ("Hello World".equals(args[0])) {
			System.out.println("Hello World!");
		} else {
			System.out.println("Bye");
		}
	}

}
