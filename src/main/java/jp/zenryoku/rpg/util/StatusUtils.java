package jp.zenryoku.rpg.util;

/**
 * ステータス生成、そのほかステータス関連の処理の実装クラス。
 *
 * @author 作成者の名前
 */
public class StatusUtils {
	/** エラーのフラグ */
	public static final int ERROR = 0;

	/**
	 *
	 * @param bornDate 生年月日(yyyyMMdd)
	 * @return ヨガ数秘の配列
	 */
	public static String[] createYogaSuhi(String bornDate) {
		String[] result = new String[8];
		result[0] = String.valueOf(StatusUtils.convertStringToInt(bornDate.substring(4,6)));
		result[1] = String.valueOf(StatusUtils.convertStringToInt(bornDate.substring(6,8)));

		String v1 = String.valueOf(Integer.parseInt(result[0]) + Integer.parseInt(result[1]));
		result[2] = String.valueOf(StatusUtils.convertStringToInt(v1));

		result[3] = String.valueOf(StatusUtils.convertStringToInt(bornDate.substring(2, 4)));


		return result;
	}

	/**
	 * 数秘術演算、２桁の数字を１桁にする
	 * @param val 2桁の数字
	 * @return 1桁の数、エラーは0を返す
	 */
	public static int convertStringToInt(String val) {
		int check = checkReturnNum(val);
		while (check > 10) {
			check = convertStringToInt(String.valueOf(check));
		}
		// 値が１０以下になっている
		return check;
	}

	public static int checkReturnNum(String val) {
		// 2桁である確認
		if (val != null && val.length() != 2) {
			return ERROR;
		}
		String strLeft = val.substring(0,1);
		String strRight = val.substring(1,2);

		int left = Integer.parseInt(strLeft);
		int right = Integer.parseInt(strRight);

		return (left + right);
	}
}
