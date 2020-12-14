package jp.zenryoku.rpg.charactors.params;

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
public class Status {
	/** 1: 力 */
	private int riki;
	/** 2: 敏 */
	private int bin;
	/** 3: 体 */
	private int tai;
	/** 4: 器 */
	private int ki;
	/** 5: 学 */
	private int gaku;
	/** 6: 命 */
	private int mei;
	/** 7: 精 */
	private int sei;
	/** 8: 感 */
	private int kan;
	/** 9: 信 */
	private int sin;
	/** 10:霊 */
	private int rei;

	/**
	 * @return 近距離攻撃力
	 */
	public int nearAttack() {
		return riki + bin + tai;
	}

	/**
	 * @return 中距離攻撃力
	 */
	public int midAttack() {
		return riki + bin + ki;
	}

	/**
	 * @return 長距離攻撃力
	 */
	public int longAttack() {
		return riki + tai + ki;
	}

	/**
	 * @return 射撃攻撃力
	 */
	public int shootAttack() {
		return bin + ki + mei;
	}

	/**
	 * @return 防御力
	 */
	public int diffence() {
		return bin + tai + sei;
	}

	/**
	 * @return 回避力
	 */
	public int escape() {
		return bin + gaku + kan;
	}

	/**
	 * @return 成長力
	 */
	public int lvup() {
		return gaku + sei + kan;
	}

	/**
	 * @return 起動力
	 */
	public int move() {
		return bin + tai + mei;
	}

	/**
	 * @return 技術力
	 */
	public int technic() {
		return ki + gaku + sei;
	}

	/**
	 * @return 運気
	 */
	public int luck() {
		return gaku + sin + rei;
	}

	/**
	 * @return 魔法攻撃力
	 */
	public int magicAttack() {
		return mei + sei + rei;
	}

	/**
	 * @return 魔法回復力
	 */
	public int magicHeal() {
		return mei + sei + rei;
	}

	/**
	 * @return 補助魔法力
	 */
	public int magicUtil() {
		return kan + sin + rei;
	}

}
