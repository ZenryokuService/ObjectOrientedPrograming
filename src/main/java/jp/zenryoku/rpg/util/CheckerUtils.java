package jp.zenryoku.rpg.util;

public class CheckerUtils {

	/**
	 * 引数が偶数か判定する。
	 *
	 * @param value 判定する値
	 * @return true: 偶数 / false: 奇数
	 */
	public static boolean isGusu(int value) {
		if (value % 2 == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 文字列が、全角か半角か判定する。
	 * @param value 判定する文字列
	 * @return true: 全角文字列 / false: 半角文字列
	 */
	public static boolean isMultiByteStr(String value) {
		int strLen = value.length();
		int byteLen = value.getBytes().length;
		if (strLen != byteLen) {
			return true;
		}
		return false;
	}

	/**
	 * コマンドの入力として適当か判定する。
	 * @input 入力した文字
	 * @regrex 正規表現 ※入力判定用
	 * @return true: 入力として適当 / false: 入力として不適当
	 */
	public static boolean isCommandInput(String input, String regrex) {
		if(input.matches(regrex)) {
			return true;
		}
		return false;
	}
}
