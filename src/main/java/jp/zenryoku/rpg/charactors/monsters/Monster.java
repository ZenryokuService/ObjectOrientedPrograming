package jp.zenryoku.rpg.charactors.monsters;

import jp.zenryoku.rpg.charactors.NonPlayer;
import jp.zenryoku.rpg.item.equip.Armor;
import jp.zenryoku.rpg.item.equip.MainWepon;

public class Monster extends NonPlayer {

	/** 改行コード */
	private final String SEP = System.lineSeparator();

	/**
	 * コンストラクタ。
	 * 今回は、まおうのみなので、コンストラクタプロパティ設定を行う。
	 * @param name
	 */
	public Monster(String name) {
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
			diffenceValue = armor.getDiffence() + getDiffence();
		}
		return diffenceValue;
	}

}
