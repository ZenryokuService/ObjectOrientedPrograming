package jp.zenryoku.practice.steps;

public class Lv1HelloWorld {

	public static void main(String[] args) {
		// 通常のハローワールド
		System.out.println("Hello World");

		// 変数を使用したハローワールド
		String message = "Hello World_1";
		System.out.println(message);

		// メソッドを使用してハローワールド
		// ※メインメソッドから呼ぶためには「static」をつける必要がある
		hello();

		// クラスを使用してハローワールド
		Lv1HelloWorld main = new Lv1HelloWorld();
		main.say();

	}

	public static void hello() {
		System.out.println("Hello World_2");
	}

	public void say() {
		System.out.println("Hello World_3");
	}
}
