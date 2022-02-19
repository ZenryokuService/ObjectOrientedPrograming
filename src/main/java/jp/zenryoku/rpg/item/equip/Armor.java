package jp.zenryoku.rpg.item.equip;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.RpgItem;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.item.Items;

public class Armor extends Items {
	/** 防御力 */
	private int deffence;

	/**
	 * コンストラクタ。
	 * @param name
	 */
	public Armor(String name) {
		super(name);
	}

	/**
	 * コンストラクタ。
	 * itemType:種類の記号
	 * itemValueKigo:効果記号と値
	 * money: 金額
	 * sideEffectValue: 副作用
	 *
	 * @param item
	 * @throws RpgException
	 */
	public Armor(RpgItem item) throws RpgException {
		super(item.getName());
		String valueKigo = item.getItemValueKigo();
		String kigo = valueKigo.substring(0,3);
		String value = valueKigo.substring(3);

		 System.out.println("kigo: " + kigo + " : value: " + value);

		if (RpgConst.ARV.equals(kigo) == false) {
			throw new RpgException(MessageConst.ERR_SETTING_OBJECT.toString() + ": " + kigo);
		}
		String ope = value.substring(0,1);
		String v = value.substring(1);
		int val = Integer.parseInt(v);
		this.setDiffence(val);
	}

	/**
	 * @return diffence
	 */
	public int getDeffence() {
		return deffence;
	}

	/**
	 * @param diffence セットする diffence
	 */
	public void setDiffence(int diffence) {
		this.deffence = diffence;
	}

}
