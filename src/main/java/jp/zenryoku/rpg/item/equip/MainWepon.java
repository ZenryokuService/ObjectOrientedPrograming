package jp.zenryoku.rpg.item.equip;

import jp.zenryoku.rpg.item.Items;

public class MainWepon extends Items {
	/** 攻撃力 */
	private int offence;

	/**
	 * コンストラクタ。
	 * @param name
	 */
	public MainWepon(String name) {
		super(name);
	}

	/**
	 * @return offence
	 */
	public int getOffence() {
		return offence;
	}

	/**
	 * @param offence セットする offence
	 */
	public void setOffence(int offence) {
		this.offence = offence;
	}
}
