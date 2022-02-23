package jp.zenryoku.rpg.item;

import jp.zenryoku.rpg.data.items.RpgItemType;

/**
 * アイテムを表現するクラス。
 *
 * @author 実装者の名前
 */
public class Items {
	protected static final boolean isDebug = false;
	/** 名前 */
	private String name;
	/** アイテムタイプ */
	private RpgItemType itemType;

	/** 効果(実装計画が未定のため仮) */
	private Object effect;

	/**
	 * コンストラクタ、名前を設定する。
	 * @param name アイテム名
	 */
	public Items(String name) {
		this.name = name;
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
	 * @return effect
	 */
	public Object getEffect() {
		return effect;
	}
	/**
	 * @param effect セットする effect
	 */
	public void setEffect(Object effect) {
		this.effect = effect;
	}

}
