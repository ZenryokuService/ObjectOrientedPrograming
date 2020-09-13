package jp.zenryoku.rpg.item;

/**
 * アイテムを表現するクラス。
 *
 * @author 実装者の名前
 */
public class Items {
	/** 名前 */
	private String name;
	/** 効果(実装計画が未定のため仮) */
	private Object effect;

	/**
	 * コンストラクタ、名前を設定する。
	 * @param name
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
