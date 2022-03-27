package jp.zenryoku.practice.train.cls;

/**
 * @author takunoji
 *
 * 2021/02/02
 */
public class Calcurate1 {
	/**
	 * 足し算を行うメソッド
	 * @param left 左側の値
	 * @param right 右側の値
	 * @return 足し算の結果
	 */
	public int addNumber(int left, int right) {
		return left + right;
	}

	/**
	 * 足し算を行うメソッド
	 * @param left 左側の値
	 * @param right 右側の値
	 * @param isNumber 数字かどうかのフラグ
	 * @return 足し算の結果
	 */
	@Deprecated
	public int addNumber(int left, int right, boolean isNumber) {
		int answer = 0;
		if (isNumber) {
			answer = left + right;
		} else {
			String ans = String.valueOf(left) + String.valueOf(right);
			answer = Integer.parseInt(ans);
		}
		return answer;
	}

	/**
	 * 引き算を行う。
	 * @param left 左の
	 * @param right 右の
	 * @return 引き算結果
	 */
	public int hikizan(int left, int right) {
		return left - right;
	}
}
