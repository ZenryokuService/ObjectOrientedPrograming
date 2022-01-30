package jp.zenryoku.rpg.item.equip;

import jp.zenryoku.rpg.data.RpgItem;
import jp.zenryoku.rpg.item.Items;

public class Armor extends Items {
	/** 防御力 */
	private int diffence;

	/**
	 * コンストラクタ。
	 * @param name
	 */
	public Armor(String name) {
		super(name);
	}

	public Armor(RpgItem item) {
		super(item.getName());
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

}
