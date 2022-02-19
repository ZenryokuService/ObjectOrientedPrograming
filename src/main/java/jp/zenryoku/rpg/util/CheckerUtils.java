package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.RpgItem;
import jp.zenryoku.rpg.exception.RpgException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckerUtils {

	/**
	 * 引数がコメント行か判定する。
	 * @param line 読み込んだ、ストーリーテキスト
	 * @return true コメント行 false: 非コメント行
	 */
	public static boolean isComment(String line) {
		if (line.startsWith("# ") || line.startsWith("##")) {
			return true;
		}
		return false;
	}

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
		if (value == null) {
			return false;
		}
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

	/**
	 * 引数に和されたアイテムが武器、防具か判定する
	 *
	 * @param item　アイテムオブジェクト
 	* @return　true: 武器(3)か防具(4) false: それ以外のカテゴリ
	 */
	public static boolean isWepOrArm(RpgItem item) {
		boolean result = false;
		if (RpgConst.WEP.equals(item.getItemType())) {
			result = true;
		}
		if (RpgConst.ARM.equals(item.getItemType())) {
			result = true;
		}
		return result;
	}

	/**
	 * 引数に和されたアイテムが武器か判定する
	 *
	 * @param item　アイテムオブジェクト
	 * @return　true: 武器(3)か防具(4) false: それ以外のカテゴリ
	 */
	public static boolean isWep(RpgItem item) {
		boolean result = false;
		if (RpgConst.WEP.equals(item.getItemType())) {
			result = true;
		}
		return result;
	}

	/**
	 * 引数に和されたアイテムが武器か判定する
	 *
	 * @param item　アイテムオブジェクト
	 * @return　true: 武器(3)か防具(4) false: それ以外のカテゴリ
	 */
	public static boolean isArm(RpgItem item) {
		boolean result = false;
		if (RpgConst.ARM.equals(item.getItemType())) {
			result = true;
		}
		return result;
	}

	/**
	 * 開始かっこかどうか判定する
	 * @param kako ahr型の文字
	 * @return true: '('である　false: '('でない
	 */
	public static boolean isStartKako(char kako) {
		boolean isStartKako = false;
		if (kako == '(') {
			isStartKako = true;
		}
		return isStartKako;
	}

	/**
	 * 終了かっこかどうか判定する
	 * @param kako ahr型の文字
	 * @return true: ')'である　false: ')'でない
	 */
	public static boolean isEntKako(char kako) {
		boolean isEnsKako = false;
		if (kako == '(') {
			isEnsKako = true;
		}
		return isEnsKako;
	}

	/**
	 * 四則演算子または、剰余算の演算子か判定する。
	 * @param ope char型の文字
	 * @return true: 四則演算子または、剰余算の演算子 false: 四則演算子または、剰余算の演算子ではない。
	 */
	public static boolean isShisokuOperator(char ope) {
		Pattern pat = Pattern.compile("[+\\-*/%]");
		Matcher mat = pat.matcher(String.valueOf(ope));
		return mat.matches();
	}

	/**
	 * 数字かどうか判定する。ただし小数点「.」も数字と認識する。
	 * @param num 数字(char)
	 * @return ture: 数字である  false: 数字出ない
	 */
	public static boolean isNumber(char num) {
		Pattern pat = Pattern.compile("[0-9|\\.]");
		Matcher mat = pat.matcher(String.valueOf(num));
		return mat.matches();
	}

	/** スペースかどうか判定する */
	public static boolean isSpace(char st) {
		return st == ' ';
	}
}
