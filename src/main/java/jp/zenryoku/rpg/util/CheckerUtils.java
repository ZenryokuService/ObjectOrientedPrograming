package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.items.RpgItem;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.item.equip.Armor;
import jp.zenryoku.rpg.item.equip.MainWepon;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckerUtils {
	private static final String SEP = System.lineSeparator();
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
	 * @param input 入力した文字
	 * @param regrex 正規表現 ※入力判定用
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
	 * すでに装備済みかどうかの判定を行う。
	 * @param select 選択している装備品
	 * @param player プレーヤーオブジェクト
	 * @return true: 装備あり false: 装備なし
	 */
	public static boolean alreadySobied(RpgItem select, PlayerCharactor player) {
		String sobiStr = select == null ? "なし" : select.getName();
		String wepStr = player.getMainWepon() == null ? "なし" : player.getMainWepon().getName();
		String armStr = player.getArmor() == null ? "なし" : player.getArmor().getName();
		if (sobiStr.equals(wepStr)) {
			System.out.println(MessageConst.ALREADY_SOBIED.toString() + " : " + wepStr);
			return true;
		}
		if (sobiStr.equals(armStr)) {
			System.out.println(MessageConst.ALREADY_SOBIED.toString() + " : " + armStr);
			return true;
		}
		return false;
	}

	/**
	 * ストーリーテキストの１行が「シーンインデックス:シーンタイプ」の形になっているか判定する。
	 * 例：「数字:大文字のアルファベット」　1:A -\> シーンインデックス１ 内部の文字を表示して次のシーンへ飛ぶ
	 * @param line ストーリーテキストの１行
	 * @return true: シーン定義開始行 false: シーン開始行ではない
	 */
	public static boolean isStartSceneLine(String line) {
		if (line.matches("[0-9]{0,1000}:[A-Z]")) {
			return true;
		}
		return false;
	}

	/**
	 * ストーリーテキストの１行が次のシーンを選択する部分の定義か判定する。<br>
	 * 最後は「END_SCENE 次のシーンインデックス」になっていること
	 * 例：<br>
	 * <pre>
	 * \<1:9\>
	 * 1. 店舗に移動します。 4
	 * 2. パーティーステータスが変化します。 5
	 * 3. アイテムの取得などします。 6
	 * 4. ゲーム終了します。 3
	 * 5. バトルシーンに移動します。 2
	 * 6. プレーヤー生成。 9
	 * 7. ゲームオーバー 7
	 * 8. 終了(保存) 8
	 * </pre>
	 * @param line ストーリーテキストの1行
	 * @return true: シーンを選択定義開始行 false: シーンを選択定義開始行ではない
	 */
	public static boolean isStartSelectNextScene(String line) {
		if (line.matches("\\<[1-9]\\:[1-9]\\>")) {
			return true;
		}
		return false;
	}

	/**
	 * ストーリーテキストの１行がアイテムショップのシーンを定義しているか判定する。<br>
	 * 最後は「\</item\>」となっていること。
	 *
	 * @param line ストーリーテキストの１行
	 * @return true: アイテムショップ定義開始行 false: アイテムショップ開始行ではない
	 */
	public static boolean isStartShopScene(String line) {
		if (line.matches("\\<item\\:[a-zA-Z]{3,10}\\>")) {
			return true;
		}
		return false;
	}

	public static boolean isStartEffectScene(String line) {
		if (line.matches("\\<effect\\:[A-Z]{3}[+\\-*/%][0-9]{1,1000}\\>")) {
			return true;
		}
		//if (line.matches("\\<effect:ITM[+\\-*/%][0-9]{1,1000}:\\.*,\\.*\\>")) {
		if (line.matches("\\<effect:ITM[+\\-*/%].*[0-9]{1,1000}\\>")) {
			return true;
		}
		return false;
	}

	public static boolean isStartWithTSEffectScene(String line) {
		if (line.matches("\\<effect\\:[A-Z]{3}[+\\-*/%][0-9]{1,1000}[T|S]{1,2}[0-9]\\>")) {
			return true;
		}
		//if (line.matches("\\<effect:ITM[+\\-*/%][0-9]{1,1000}:\\.*,\\.*\\>")) {
		if (line.matches("\\<effect:ITM[+\\-*/%].*[0-9]{1,1000}[T|S]{1,2}[0-9]\\>")) {
			return true;
		}
		return false;
	}

	/**
	 * 空文字もしくは、改行コードのみかどうか判定する。
	 *
	 * @param line　ストーリーテキストの１行
	 * @return true: 空文字もしくは、改行コード false: 空文字もしくは、改行コードではない
	 */
	public static boolean isEmpptyOrSep(String line) {
		if (line.equals("") || line.equals(SEP) || line.equals("#" + SEP)) {
			return true;
		}
		return false;
	}

	/**
	 * コメント行かどうか判定する。「# 」「##」で始まる行。
	 * @param line ストーリーテキストの１行
	 * @return true: コメント行 false: コメント行でない
	 */
	public static boolean isCommentLine(String line) {
		if (line.startsWith("# ")) {
			return true;
		}
		return false;
	}

	/**
	 * 引数の記号がデフォルトステータス(HP, MP, LVなど)かどうか判定する。
	 * @param kigo 記号
	 * @return true: デフォルトステータス false: デフォルトステータスでない
	 */
	public static boolean isDefaultStatusKigo(String kigo) {
		if (kigo != null && kigo.matches(RpgConst.REG_DEFAULT_KIGO.toString())) {
			return true;
		}
		return false;
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

	/**
	 * 【未使用】スペースかどうか判定する
	 * @param st　文字列
	 * @return true: スペースである　false: スペースでない
	 */
	public static boolean isSpace(char st) {
		return st == ' ';
	}

	/**
	 * 数字の始まる位置を返却する。
	 * @param str 検査対象文字列
	 * @return 数字の出現する位置(0からはじまる)
	 */
	public static int indexOfNum(String str) {
		int pos = 0;
		char[] ch = str.toCharArray();
		Pattern pat = Pattern.compile("[0-9]");
		for (int i = 0; i < ch.length; i++) {
			String val = String.valueOf(ch[i]);
			Matcher mat = pat.matcher(val);
			if (mat.matches()) {
				pos = i;
				break;
			}
		}
		return pos;
	}

	/**
	 * 四則演算しの始まる位置を返却する。
	 * @param str 検査対象文字列
	 * @return 数字の出現する位置(0からはじまる)
	 */
	public static int indexOfOpe(String str) {
		boolean isFind = false;
		int pos = 0;
		char[] ch = str.toCharArray();
		Pattern pat = Pattern.compile("[+\\-*/]");
		for (int i = 0; i < ch.length; i++) {
			String val = String.valueOf(ch[i]);
			Matcher mat = pat.matcher(val);
			if (mat.matches()) {
				pos = i;
				isFind = true;
				break;
			}
		}
		// 見つからない場合は-1を返却
		return isFind ? pos : -1;
	}

	/**
	 * "-"を許容する、”[A-Z]{3}[0-9]{1,2}"にマッチする文字列
	 * @param str 検証する文字列
	 * @return [A-Z]{3}[0-9]{1,2}にマッチする
	 */
	public static boolean isMasterCatNum(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		if ("-".equals(str)) {
			return true;
		}
		return str.matches(RpgConst.REG_MASTERT_DEF_FOR_PARAM);
	}

	public static boolean isTS(String kokaSiki) {
		boolean isTS = false;
		if (kokaSiki != null && kokaSiki.matches(RpgConst.REG_EFFECT_TXT_2)) {
			isTS = kokaSiki.contains("T") || kokaSiki.contains("S");
		}
		return isTS;
	}
}
