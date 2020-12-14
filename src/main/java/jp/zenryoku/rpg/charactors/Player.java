package jp.zenryoku.rpg.charactors;

import jp.zenryoku.rpg.charactors.params.Status;
import jp.zenryoku.rpg.item.equip.Armor;
import jp.zenryoku.rpg.item.equip.MainWepon;
import lombok.Data;

/**
 * プレーヤーを表現するクラス。
 * 名前の最大文字数は６文字<br/>
 * <table>
 * <tr><td>共通項目</td><td>意味</td></tr>
 * <tr><td>攻撃力</td><td>現在装備している武器力 + 対象の射程攻撃力(ステータス)</td></tr>
 * <tr><td>防御力</td><td>現在装備している防具力 + 防御力(ステータス)</td></tr>
 * <tr><td>武器</td><td>メインとサブがあり、アビリティにより使用方法法が異なる</td></tr>
 * <tr><td>防具</td><td>防具、着脱は、Map画面でのみ可能</td></tr>
 * </table>
 * @author 実装者の名前
 */
@Data
public class Player {
	/** 名前 */
	private String name;
	/** レベル */
	private int level;
	/** 生命力 */
	private int HP;
	/** 特殊能力(技能)の使用時に消費 */
	private int MP;
	/** メイン武器 */
	private MainWepon mainWepon;
	/** 防具 */
	private Armor armor;
	/** 行動時の攻撃力 */
	private int attack;
	/** 防御力 */
	private int diffence;
	/** 戦闘可能フラグ */
	private boolean canBattle;
	/** ステータスオブジェクト */
	private Status status;
	/**
	 * コンストラクタ。
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
		// レベル1の設定
		setLevel(1);
		setHP(20);
		setMP(10);
	}

	/**
	 * 攻撃コマンド。
	 *
	 * @return 武器の攻撃力
	 */
	@Command(index=1, commandName="たたかう")
	public int attack() {
		return mainWepon.getOffence();
	}

	/**
	 * 防御コマンド。防御力を1.5倍にして小数点以下を切り捨てする。
	 * @return 防具の防御力
	 */
	@Command(index=2, commandName="ぼうぎょ")
	public int diffence() {
		return (int) ((double) armor.getDiffence() * 1.5);
	}

	@Command(index=3, commandName="にげる")
	public void escape(Player player) {
		// TODO-[逃げるの仕様を考える]
		System.out.println(player.getName() + "たちは、にげだした。");
	}

	/**
	 * 戦闘不能か判定する。
	 * @return ture: 戦闘不能 / false: 戦闘可能
	 */
	public boolean isUnableToFigit() {
		return getHP() <= 0;
	}
}
