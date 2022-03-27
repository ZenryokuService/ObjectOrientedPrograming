package jp.zenryoku.rpg.charactors.params;

import lombok.Data;

/**
 * プレーヤーのステータスを表現するクラス
 *
 * @author 実装者の名前
 *
 */
@Data
public class PlayerStatus extends Status {
	////////////////////////////
	//// ベースになるステータス ////
	////////////////////////////
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
	////////////////////////////
	//  戦闘に使用するステータス  //
	////////////////////////////
	/** 生命力 */
	private int HP;
	/** マジックポイント */
	private int MP;
	/** 攻撃力 */
	private int atk;
	/** 防御力 */
	private int def;


	/**
	 * コンストラクタ。
	 * @param status ステータスの値配列
	 * @throws Exception 想定外のエラー
	 */
	public PlayerStatus(int[] status) throws Exception {
		if (status.length != 10) {
			throw new Exception("ステータス配列長が不適切です。" + status.length);
		}
		getClass().getDeclaredField("pow").setInt(this, status[0]);
		getClass().getDeclaredField("bin").setInt(this, status[1]);
		getClass().getDeclaredField("tai").setInt(this, status[2]);
		getClass().getDeclaredField("ki").setInt(this, status[3]);
		getClass().getDeclaredField("gak").setInt(this, status[4]);
		getClass().getDeclaredField("mei").setInt(this, status[5]);
		getClass().getDeclaredField("sei").setInt(this, status[6]);
		getClass().getDeclaredField("kan").setInt(this, status[7]);
		getClass().getDeclaredField("sin").setInt(this, status[8]);
		getClass().getDeclaredField("rei").setInt(this, status[9]);

	}

	/**
	 * PlayerStatusクラスの足し算を行う。
	 *
	 * @param status PlayerStatus
	 */
	public void add(PlayerStatus status) {
		pow = pow + status.getPow();
		bin = bin + status.getBin();
		tai = tai + status.getTai();
		ki = ki + status.getKi();
		gak = gak  + status.getGak();
		mei = mei + status.getMei();
		sei = sei + status.getSei();
		kan = kan + status.getKan();
		sin = sin + status.getSin();
		rei = rei + status.getRei();
	}
}
