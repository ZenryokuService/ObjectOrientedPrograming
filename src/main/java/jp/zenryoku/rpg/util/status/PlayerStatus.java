package jp.zenryoku.rpg.util.status;

import lombok.Data;

/**
 * プレーヤーのステータスを表現するクラス
 *
 * @author 実装者の名前
 *
 */
@Data
public class PlayerStatus {
	/** ヨガ数秘 */
	private String[] yoga;
	/** 1:力 */
	private int pow;
	/** 2:敏 */
	private int bin;
	/** 3:体 */
	private int tai;
	/** 4:器 */
	private int ki;
	/** 5:学 */
	private int gak;
	/** 6:命 */
	private int mei;
	/** 7:精 */
	private int sei;
	/** 8:感 */
	private int kan;
	/** 9:信 */
	private int sin;
	/** 10:霊  */
	private int rei;
}
