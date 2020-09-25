package jp.zenryoku.practice.lv2renshu;

public class Mondai5 {
	public static void main(String[] args) {
		// プログラム引数の取得
		String param = args[0];
		if ("Hello".equals(param)) {
			System.out.println("Hello World");
		} else if ("Test".equals(param)) {
			System.out.println("Testing now!");
		} else {
			System.out.println("Please input \"Hello\" or \"Test\"");
		}
	}
}
