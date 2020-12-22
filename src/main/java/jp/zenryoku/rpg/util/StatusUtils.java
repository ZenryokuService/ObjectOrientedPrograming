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
	 * 生年月日から、「月」「日」「月と日の合計」「西暦の下2桁」を算出する。
	 * 余が数秘の表を作るための要素となる数を算出する。
	 *
	 * @param birthDate 生年月日(yyyyMMdd)
	 * @return ヨガ数秘の配列：[0]=月、[1]=日、[2]=月と日の合計、[3]=西暦の下2桁
	 */
	public static String[] createYogaSuhi(String birthDate) {
		String[] result = new String[9];
		// 月を算出
		result[0] = String.valueOf(StatusUtils.convertStringToInt(birthDate.substring(4,6)));
		// 日を算出
		result[1] = String.valueOf(StatusUtils.convertStringToInt(birthDate.substring(6,8)));
		// 月と日の合計を算出
		String v1 = String.valueOf(Integer.parseInt(result[0]) + Integer.parseInt(result[1]));
		result[2] = String.valueOf(StatusUtils.convertStringToInt(v1));
		// 西暦の下2桁を算出
		result[3] = String.valueOf(StatusUtils.convertStringToInt(birthDate.substring(2, 4)));
		// 西暦4桁
		result[4] = StatusUtils.convertStringToInt(birthDate.substring(0, 2), birthDate.substring(2, 4));
		// 4 + 5
		int gokei = Integer.parseInt(result[3]) + Integer.parseInt(result[4]);
		result [5] = String.valueOf(StatusUtils.convertStringToInt(String.valueOf(gokei)));
		// 2 + 4
		int gokei2 = Integer.parseInt(result[1]) + Integer.parseInt(result[3]);
		result [6] = String.valueOf(StatusUtils.convertStringToInt(String.valueOf(gokei2)));
		// 1 + 5
		int gokei3 = Integer.parseInt(result[0]) + Integer.parseInt(result[4]);
		result [7] = String.valueOf(StatusUtils.convertStringToInt(String.valueOf(gokei3)));
		// 生年月日
		result[8] = String.valueOf(StatusUtils.convertStringToInt(birthDate.substring(0, 4).toCharArray()));

//		Map<Integer, String> suhi = new HashMap<>();
		// 1. 誕生月
		// 2. 誕生日
		// 3. 1 + 2
		// 4. 西暦の下2桁
		// 5. 西暦4桁



		return result;
	}

	/**
	 * 西暦4桁を合計して数秘的合算を行う。
	 * @param seireki 西暦4桁
	 * @return
	 */
	public static int convertStringToInt(char[] seireki) {
		if (4 != seireki.length) {
			return ERROR;
		}
		int num = 0;

		for (char ch : seireki) {
			String str = String.valueOf(ch);
			num += Integer.parseInt(str);
		}

		while (num > 10) {
			num = convertStringToInt(String.valueOf(num));
		}
		return num;
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

	/**
	 * 数秘術演算、２桁の数字を１桁にする
	 * @param val 2桁の数字
	 * @return 1桁の数、エラーは0を返す
	 */
	public static String convertStringToInt(String val, String val2) {
		int num1 = convertStringToInt(val);
		int num2 = convertStringToInt(val2);
		int gokei = num1 + num2;

		while (gokei > 10) {
			gokei = convertStringToInt(String.valueOf(gokei));
		}
		return String.valueOf(gokei);
	}

	/**
	 * 二桁の数字文字列を、分割し合計する。
	 * 例；12 => 1 + 2 = 3
	 *
	 * @param val
	 * @return 数秘的な合計値
	 */
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
