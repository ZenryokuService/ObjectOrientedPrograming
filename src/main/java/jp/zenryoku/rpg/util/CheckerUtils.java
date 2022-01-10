package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.exception.RpgException;

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

	/**
	 * チェックディジットを生成する
	 * @param num コードなどの数字
	 * @return 生成したチェックディジット / NULL: エラーの時
	 */
	public static Integer createCheckDigit(String num) {
		if (num != null && !num.matches("[0-9]+")) {
			return null;
		}
		char[] ch = num.toCharArray();
		int gokei = 0;
		for (int i = 0; i < ch.length; i ++) {
			String val = String.valueOf(ch[i]);
			int valNum = Integer.parseInt(val);
			gokei += valNum;
		}
		int amari = gokei % 10;
		return amari;
	}

	/**
	 * チェックディジット付きの番号の検査をする。
	 *
	 * @param num チェックディジット(下1桁)を取り除いた数字
	 * @param digit チェックディジット(下1桁)
	 * @return true: 検査OK / false: 検査NG
	 */
	public static boolean checkCheckDigit(String num, String digit) {
		String no = num.substring(0, num.length() - 1);
		String dig = createCheckDigit(no).toString();

		return digit.equals(dig);
	}
}
