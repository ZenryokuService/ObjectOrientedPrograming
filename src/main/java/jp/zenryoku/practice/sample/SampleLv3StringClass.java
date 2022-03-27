package jp.zenryoku.practice.sample;

/**
 * Stringクラスの扱い方のサンプル。
 * @author teacher
 *
 * 2020/11/17
 */
public class SampleLv3StringClass {
	public static void main(String[] args) {
		// 文字列型の変数を宣言
		String a;
		// 同様に初期化
		String b = "初期化";
		// String#equals()の使い方
		boolean isSame = b.equals("初期化");
		boolean notSame = b.equals("aaa");

		// isSameはTrueが入っている
		if (isSame) {
			System.out.println("isSame = " + isSame);
		}
		// notSameにはFalseが入っている
		if (notSame == false) {
			System.out.println("notSame = " + notSame);
		}

		// String#toUppaerCase()の使い方
		String c = "abcde";
		System.out.println("toUpperCase(): " + c.toUpperCase());

		// String#sunstring()の使い方
		// 2文字目から4文字目を取得する
		System.out.println("substring(): " + c.substring(1, 4));

		// ちょいテク
		if ("初期化".equals(b)) {
			System.out.println("ちょいテク");
		}
	}
}
