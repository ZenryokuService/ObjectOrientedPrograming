package jp.zenryoku.rpg.charactors.monsters;

import jp.zenryoku.rpg.charactors.NonPlayer;
import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.item.equip.Armor;
import jp.zenryoku.rpg.item.equip.MainWepon;

public class Monster extends PlayerCharactor {

	/** 改行コード */
	private final String SEP = System.lineSeparator();
	/** 話すフラグ */
	private boolean isTalk;
	/** 話の内容 */
	private String message;

	/**
	 * コンストラクタ。
	 * 今回は、まおうのみなので、コンストラクタプロパティ設定を行う。
	 * @param name モンスターの名前
	 */
	public Monster(String name) throws RpgException {
		// 名前
		super(name);
		// レベル
		setLevel(1);
		// HP
		setHP(30);
		// MP
		setMP(5);
		// 攻撃力
		setAttack(5);
		// 防御力
		setDiffence(3);
		// 話をする
		setTalk(true);
		// 表示するメッセージ
		setMessage("「がっはっは！よくきたなぁ、おまえのゆうきに、さんじをおくろう。" + SEP
				+ "しかし、ここがおまえのさいごだ！わがちからにくっぷくするがよい。」");
	}


	/**
	 * コンストラクタ。
	 * 今回は、まおうのみなので、コンストラクタプロパティ設定を行う。
	 * @param name モンスターの名前
	 */
	public Monster(String name, int lv, int hp, int mp, int atk, int def, boolean isTalk, String message) throws RpgException {
		// 名前
		super(name);
		// レベル
		setLevel(lv);
		// HP
		setHP(hp);
		// MP
		setMP(mp);
		// 攻撃力
		setAttack(atk);
		// 防御力
		setDiffence(def);
		// 話をする
		setTalk(isTalk);
		// 表示するメッセージ
		setMessage(message);
	}

	public boolean isTalk() {
		return isTalk;
	}

	public void setTalk(boolean talk) {
		isTalk = talk;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * モンスターの場合、武器を使用しないケースがある。
	 */
	@Override
	public int attack() {
		int attackValue = getAttack();
		MainWepon mainWepon = getMainWepon();
		if (mainWepon != null) {
			attackValue = getAttack() + getMainWepon().getOffence();
		}
		return attackValue;
	}

	/**
	 * モンスター防御。
	 */
	public int diffence() {
		Armor armor = getArmor();
		int diffenceValue = getDiffence();
		if (armor != null) {
			diffenceValue = armor.getDeffence() + getDiffence();
		}
		return diffenceValue;
	}

}
