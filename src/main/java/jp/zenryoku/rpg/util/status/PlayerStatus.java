package jp.zenryoku.rpg.util.status;

import java.util.Map;

import lombok.Data;

/**
 * プレーヤーのステータスを表現するクラス
 *
 * @author 実装者の名前
 *
 */
@Data
public class PlayerStatus {
	/** 1:力 */
	public static final String POW = "1";
	/** 2:敏 */
	public static final String BIN = "2";
	/** 3:体 */
	public static final String TAI = "3";
	/** 4:器 */
	public static final String KI = "4";
	/** 5:学 */
	public static final String GAK = "5";
	/** 6:命 */
	public static final String MEI = "6";
	/** 7:精 */
	public static final String SEI = "7";
	/** 8:感 */
	public static final String KAN = "8";
	/** 9:信 */
	public static final String SIN = "9";
	/** 10:霊  */
	public static final String REI = "10";


	/** セフィロト */
	public Map<String, Integer> sephiroth;
	/** The Fool */
	public static final String THE_FOOL = "12";
	/** The Magicial */
	public static final String THE_MAGICIAN = "13";
	/** The High Preestess */
	public static final String THE_PRIESTESS = "16";
	/** The Empress */
	public static final String THE_EMPRESS = "23";
	/** The Emperor */
	public static final String THE_EMPEROR = "26";
	/** The Hierophant */
	public static final String THE_HIEROPHANT = "24";
	/** The Lovers */
	public static final String THE_LOVERS = "36";
	/** The Chariot */
	public static final String THE_CHARIOT = "35";
	/** Strength */
	public static final String STRENGTH = "45";
	/** The Hermit */
	public static final String THE_HERMIT = "46";
	/** Wheel of Fortune */
	public static final String WHEEL_OF_FORTUNE = "47";
	/** 11.Justice */
	public static final String JUSTICE = "56";
	/** 12.The Hanged Man */
	public static final String THE_HANGED_MAN= "58";
	/** 13.Death */
	public static final String DEATH = "67";
	/** 14.Temperance */
	public static final String TEMPERANCE = "69";
	/** 15.The Devil */
	public static final String THE_DEVIL = "68";
	/** 16.The Tower */
	public static final String THE_TOWER = "78";
	/** 17.The Star */
	public static final String THE_STAR = "79";
	/** 18.The Moon */
	public static final String THE_MOON = "710";
	/** 19.The Sun */
	public static final String THE_SUN = "89";
	/** 20.Judgement */
	public static final String JUDGEMENT = "810";
	/** 21.The World */
	public static final String THE_WORLD = "910";

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

	/** コンストラクタ */
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
