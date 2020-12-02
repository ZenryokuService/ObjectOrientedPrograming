package jp.zenryoku.practice.steps;

public class Lv2Calculate {

	public static void main(String[] args) {
		// 【宣言】 データ型 変数名;
		// 【初期化】 データ型 変数名 = 値;
		int i = 0;
		int j = 1;
		double k = 0.1;
		double l = 1.2;

		Lv2Calculate main = new Lv2Calculate();
		// 整数
		System.out.println("*** 整数の演算 ***");
		main.tashi(i, j);
		main.hiki(i, j);
		main.kake(i, j);
		main.wari(i, j);
		main.joyo(j, j);

		// 少数
		System.out.println("*** 少数の演算 ***");
		main.tashi(k, l);
		main.hiki(k, l);
		main.kake(k, l);
		main.wari(k, l);
		main.joyo(k, l);

	}

	/* **********************************
	 *           整数値の計算           *
	 ************************************/
	public void tashi(int left, int right) {
		System.out.println("left + right = " + (left + right));
	}

	public void hiki(int left, int right) {
		System.out.println("left - right = " + (left - right));
	}

	public void kake(int left, int right) {
		System.out.println("left * right = " + (left * right));
	}

	public void wari(int left, int right) {
		System.out.println("left / right = " + (left / right));
	}

	public void joyo(int left, int right) {
		System.out.println("left % right = " + (left % right));
	}

	/* **********************************
	 *           少数値の計算           *
	 ************************************/
	public void tashi(double left, double right) {
		System.out.println("left + right = " + (left + right));
	}

	public void hiki(double left, double right) {
		System.out.println("left - right = " + (left - right));
	}

	public void kake(double left, double right) {
		System.out.println("left * right = " + (left * right));
	}

	public void wari(double left, double right) {
		System.out.println("left / right = " + (left / right));
	}

	public void joyo(double left, double right) {
		System.out.println("left % right = " + (left % right));
	}
}
