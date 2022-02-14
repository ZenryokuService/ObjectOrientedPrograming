package jp.zenryoku.rpg.item.equip;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.RpgItem;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.item.Items;

public class MainWepon extends Items {
	/** 攻撃力 */
	private int offence;
	/** 熟練度 */
	private int jlv;

	public MainWepon(String name) {
		super(name);
	}
	/**
	 * コンストラクタ。
	 * itemType:種類の記号
	 * itemValueKigo:効果記号と値
	 * money: 金額
	 * sideEffectValue: 副作用
	 *
	 * @param item RpgItem
	 */
	public MainWepon(RpgItem item) throws RpgException {
		super(item.getName());
		String valueKigo = item.getItemValueKigo();
		String kigo = valueKigo.substring(0,3);
		String value = valueKigo.substring(3);

		if (isDebug) System.out.println("kigo: " + kigo + " : value: " + value);

		if (RpgConst.WEV.equals(kigo) == false) {
			throw new RpgException(MessageConst.ERR_SETTING_OBJECT.toString() + ": " + kigo);
		}
		String ope = value.substring(0,1);
		String v = value.substring(1);
		int val = Integer.parseInt(v);
		this.setOffence(val);
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

	/**
	 * @return jvl
	 */
	public int getJlv() {
		return jlv;
	}

	/**
	 * @param jlv セットする jvl
	 */
	public void setJlv(int jlv) {
		this.jlv =  jlv;
	}
}
