package jp.zenryoku.tutorial.calsses;

/**
 * じゃんけんゲームの定数クラス(列挙型)
 *
 * @author 実装者の名前
 */
public enum JankenConst {
	GU(0),
	CHOKI(1),
	PA(2),
	YOU_WIN(0),
	YOU_LOOSE(1),
	AIKO(2);

	/** 終了するときのフラグ */
	public static final boolean FINISH = true;
	/** もう一度やるときのフラグ */
	public static final boolean ONE_MORE = false;

	/** 定数の値 */
	private int value;

	private JankenConst(int value) {
		this.value = value;
	}

	/**
	 * 定数を文字列として返却
	 *
	 * @return String 定数の文字列化した値
	 */
	public String toString() {
		return String.valueOf(value);
	}

	/**
	 * 定数の値を返却する
	 *
	 * @return 定数の値
	 */
	public int getValue() {
		return value;
	}

}
