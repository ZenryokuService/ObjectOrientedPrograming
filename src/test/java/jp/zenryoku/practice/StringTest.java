package jp.zenryoku.practice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StringTest {

	/** String#equalsのテスト */
	@Test
	public void test01() {
		String st = "aaa";
		String s1 = "aaa";
		String s2 = "bbb";
		String s3 = "ccc";

		if (st.equals(s1)) {
			System.out.println("st == s1");
		} else {
			System.out.println("st != s1");
		}

		if (st.equals(s2)) {
			System.out.println("st != s2");
		} else {
			System.out.println("st == s2");
		}

		if (s3.equals(s1)) {
			System.out.println("s3 == s1");
		} else {
			System.out.println("s3 != s1");
		}
	}

	/**
	 * JavaDocをみて、入力(引数)と出力(返却値)を確認後、実装して動かしてみる。
	 * {@link String#substring(int)}のテストケース
	 */
	@Test
	public void testSubstring() {
		String target = "abcdefg";
		// 2番目の文字を取得する
		String res = target.substring(1, 2);
		System.out.println("2番目の文字: ");
		// 取得結果が正しいか確認
		assertEquals("b", res);
		// 一番初めの文字を取得
		String res1 = target.substring(0, 1);
		assertEquals("a", res1);
		// 一番最後の文字
		String res2 = target.substring(target.length() -1, target.length());
		assertEquals("g", res2);
		// 初めから4文字分を切り出す
		String res3 = target.substring(0, 4);
		assertEquals("abcd", res3);
	}
}
