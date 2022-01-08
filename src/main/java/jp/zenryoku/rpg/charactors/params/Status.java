package jp.zenryoku.rpg.charactors.params;

import java.util.Map;

import lombok.Data;

/**
 * プレーヤーのステータスを表現する。
 * ＜基本ステータス＞
 * <ol>
 * <li>力：筋力、力の発揮力(発勁力)を示す</li>
 * <li>敏：素早さ、俊敏性、悟る(察する)ことが早い</li>
 * <li>体：体格、体の頑丈さを示す</li>
 * <li>器：きようさ、働きのあること、才能があることを示す(能力に大きさ)</li>
 * <li>学：学習力、理解力、新しい物事を取り入れる力</li>
 * <li>命：命中率、狙いを定める、計画を進め目的にたどり着く力</li>
 * <li>精：心身の力、生物としてして純粋な生きる力</li>
 * <li>感：物事に触れて生ずる心の動きの大きさ</li>
 * <li>信：誠実、嘘がない、ヒトから信頼される力の大きさ</li>
 * <li>霊：精神、肉体に宿り体を支配するもの、はかり知ることのできない不思議な働きを感じ・使う力</li>
 * </ol>
 *
 * ＜実用ステータス＞
 * <ul>
 * <li>近攻：1 + 2 + 3</li>
 * <li>中攻：1 + 2 + 4</li>
 * <li>長攻：1 + 3 + 4</li>
 * <li>射攻：2 + 4 + 6</li>
 * <li>防御：2 + 3 + 7</li>
 * <li>回避：2 + 5 + 8</li>
 * <li>気配：2 + 7 + 8</li>
 * <li>成長：5 + 7 + 8</li>
 * <li>移動：2 + 3 + 6</li>
 * <li>技術：4 + 5 + 7</li>
 * <li>運気：5 + 9 + 10</li>
 * </ul>
 *
 * @author 実装者の名前
 */
@Data
public abstract class Status {
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

	/** TODO-[2022-01-04: 使用方法が決まってない]
	 * セフィロトのパスを使用して、タロットの大アルカナを表現する。
	 *  「12」であれば、セフィラの１－２のパスを示し、「710」であれば７－１０のパスを示す。
	 */
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
	protected String[] yoga;

}
