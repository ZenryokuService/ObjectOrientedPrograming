package jp.zenryoku.rpg.charactors;

import jp.zenryoku.rpg.charactors.params.PlayerStatus;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.RpgItem;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.item.equip.Armor;
import jp.zenryoku.rpg.item.equip.MainWepon;
import jp.zenryoku.rpg.util.StatusUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * プレーヤーを表現するクラス。
 * 名前の最大文字数は６文字<br>
 * <table summary="各ステータスの説明(今後の実装になる)">
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
	protected String name;
	/** レベル */
	protected int level;
	/** 生命力 */
	protected int HP;
	/** 特殊能力(技能)の使用時に消費 */
	protected int MP;
	/** メイン武器 */
	protected MainWepon mainWepon;
	/** 防具 */
	protected Armor armor;
	/** 行動時の攻撃力 */
	protected int attack;
	/** 防御力 */
	protected int diffence;
	/** 戦闘可能フラグ */
	protected boolean canBattle;
	/** ステータスオブジェクト */
	protected PlayerStatus status;
	/** アイテム袋 */
	protected List<RpgItem> itemBag;

	public Player() {
		// リストのサイズを5で初期化
		itemBag = new ArrayList<RpgItem>(RpgConst.ITEM_HOLD_NUM);
	}
	/**
	 * コンストラクタ。
	 * デフォルト値を指定してPlayerを生成する。
	 *
	 * @param name プレーヤーの名前
	 */
	public Player(String name) {
		this();
		this.name = name;
		// レベル1の設定
		setLevel(1);
		setHP(20);
		setMP(10);
	}

	/**
	 * 生年月日よりPlayerStatusを生成してPlayerを生成。
	 *
	 * @param name Playerの名前
	 * @param birthDate 生年月日
	 * @throws Exception 想定外のエラー
	 */
	public Player(String name, String birthDate) throws Exception {
		this();
		String[] suhi = StatusUtils.createYogaSuhi(birthDate);
		PlayerStatus status = StatusUtils.createStatus(suhi);

		Player player = new Player(name);
		player.setStatus(status);
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
		return (int) ((double) armor.getDeffence() * 1.5);
	}

	@Command(index=3, commandName="にげる")
	public void escape(Player player) {
		// TODO-[逃げるの仕様を考える]
		System.out.println(player.getName() + "たちは、にげだした。");
	}

	public void addItem(RpgItem item) {
		// TODO-[アイテム保持可能数の実装]
		int current = itemBag.size();
		if (current > RpgConst.ITEM_HOLD_NUM) {
			System.out.println(MessageConst.CANNOT_HOLD.toString());
			return;
		}
		itemBag.add(item);
	}
	/**
	 * 戦闘不能か判定する。
	 * @return ture: 戦闘不能 / false: 戦闘可能
	 */
	public boolean isUnableToFigit() {
		return getHP() <= 0;
	}

	/**
	 * 武器を装備する
	 * @param wepon　武器クラス。
	 * @throws RpgException 想定外のエラー
	 */
	public void setMainWepon(MainWepon wepon) throws RpgException {
	}

	/**
	 * 防具を装備する
	 * @param arm 防具クラス。
	 */
	public void setArmor(Armor arm) {
	}
}
