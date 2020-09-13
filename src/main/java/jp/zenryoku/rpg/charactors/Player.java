package jp.zenryoku.rpg.charactors;

import jp.zenryoku.rpg.item.equip.Armor;
import jp.zenryoku.rpg.item.equip.MainWepon;

/**
 * プレーヤーを表現するクラス。
 * 名前の最大文字数は６文字
 *
 * @author 実装者の名前
 */
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
	/** 攻撃力 */
	private int attack;
	/** 防御力 */
	private int diffence;
	/** 戦闘可能フラグ */
	private boolean canBattle;

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
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name セットする name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return hP
	 */
	public int getHP() {
		return HP;
	}
	/**
	 * @param hP セットする hP
	 */
	public void setHP(int hP) {
		HP = hP;
	}
	/**
	 * @return mP
	 */
	public int getMP() {
		return MP;
	}
	/**
	 * @param mP セットする mP
	 */
	public void setMP(int mP) {
		MP = mP;
	}
	/**
	 * @return level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level セットする level
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * @return attack
	 */
	public int getAttack() {
		return attack;
	}
	/**
	 * @param attack セットする attack
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}
	/**
	 * @return diffence
	 */
	public int getDiffence() {
		return diffence;
	}
	/**
	 * @param diffence セットする diffence
	 */
	public void setDiffence(int diffence) {
		this.diffence = diffence;
	}
	/**
	 * @return mainWepon
	 */
	public MainWepon getMainWepon() {
		return mainWepon;
	}
	/**
	 * @param mainWepon セットする mainWepon
	 */
	public void setMainWepon(MainWepon mainWepon) {
		this.mainWepon = mainWepon;
	}
	/**
	 * @return armor
	 */
	public Armor getArmor() {
		return armor;
	}
	/**
	 * @param armor セットする armor
	 */
	public void setArmor(Armor armor) {
		this.armor = armor;
	}
	/**
	 * @return canBattle
	 */
	public boolean isCanBattle() {
		return canBattle;
	}
	/**
	 * @param canBattle セットする canBattle
	 */
	public void setCanBattle(boolean canBattle) {
		this.canBattle = canBattle;
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
	 * 戦闘不能化判定する。
	 * @return ture: 戦闘不能 / false: 戦闘可能
	 */
	public boolean isUnableToFigit() {
		return getHP() <= 0;
	}
}
