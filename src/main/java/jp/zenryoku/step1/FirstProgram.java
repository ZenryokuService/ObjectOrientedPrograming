package steps.java.step1;

/**
 * 初めのプログラム「Hello World」の実装。
 * そして、簡単な絵をかいてみる。
 *
 */
public class FirstProgram {
	/** メインメソッド */
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}

	/**
	 * ハローワールドが動かせたら、「＊(アスタリスク)」を使用して
	 * 簡単な絵をかいてみましょう
	 * 例えば四角を描く
	 */
	public static void firstChallenge() {
		System.out.println("*****");
		System.out.println("*   *");
		System.out.println("*   *");
		System.out.println("*****");
	}

	/**
	 * ハローワールドが動かせたら、「＊(アスタリスク)」を使用して
	 * 簡単な絵をかいてみましょう
	 * 例えば三角を描く
	 */
	public static void firstChallenge2() {
		System.out.println("   *");
		System.out.println("  * *");
		System.out.println(" *   *");
		System.out.println("*******");
	}

	/**
	 * ハローワールドが動かせたら、「＊(アスタリスク)」を使用して
	 * 簡単な絵をかいてみましょう
	 * 例えば逆三角を描く
	 */
	public static void firstChallenge3() {
		System.out.println("*******");
		System.out.println(" *   *");
		System.out.println("  * *");
		System.out.println("   *");
	}

	/**
	 * ハローワールドが動かせたら、「＊(アスタリスク)」を使用して
	 * 簡単な絵をかいてみましょう
	 * 例えばひし形を描く
	 */
	public static void firstChallenge4() {
		System.out.println("   * ");
		System.out.println(" *   *");
		System.out.println("*     *");
		System.out.println(" *   *");
		System.out.println("   *");
	}

	/**
	 * ハローワールドが動かせたら、「＊(アスタリスク)」を使用して
	 * 簡単な絵をかいてみましょう
	 * 例えば大文字焼を描く
	 */
	public static void firstChallenge5() {
		System.out.println("                **");
		System.out.println("              **  **");
		System.out.println("            **      **");
		System.out.println("          **     *     **");
		System.out.println("        **   * * * * *   **");
		System.out.println("      **         *         **");
		System.out.println("    **         *   *         **");
		System.out.println(" **         *         *         **");
		System.out.println("** * ** ** ** * * * ** * ** ** * **");
	}

	/**
	 * 「+」演算子を使用してみよう。
	 * 「+」演算子は、文字列と文字列をくっつけます。
	 * 他の「四則演算」の演算子は、計算で使用します。
	 */
	public static void usePlusOperator() {
		System.out.println("文字列と文字列を連結します。");
		// "abc" + "def": 「"」で囲っていると文字列になります。
		System.out.println("abc" + "def" + "ghi");
		// "abc" + 1: 文字列に整数(INT型)を足します。
		System.out.println("abc" + 1);
		// "abc" + (1 + 2): 文字列に整数(INT型)を足し算。「()」で処理の順序を指定できます。
		System.out.println("abc" + (1 + 2));
		// "def" + (3 - 1): 文字列に整数(INT型)を引き算します。「()」で処理の順序を指定できます。
		System.out.println("def" + (3 - 1));
		// "ghi" + (4 * 2): 文字列に整数(INT型)を掛け算します。「()」で処理の順序を指定できます。
		System.out.println("def" + (4 * 2));
		// "jkl" + (4 / 2): 文字列に整数(INT型)を割り算します。「()」で処理の順序を指定できます。
		System.out.println("jkl" + (6 / 2));

		// Question1 :「3 掛ける 4 足す 12」をプログラムで書いてください。
		// Question2 :「1 引く 4 割る 3」をプログラムで書いてください。
		// Question3 :「1辺が4cmの正三角形の面積を計算して「～cm」と表示するプログラムを書いてください。
	}
}
