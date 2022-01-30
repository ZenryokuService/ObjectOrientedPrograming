package jp.zenryoku.rpg.item.equip;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.RpgItem;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.item.Items;

public class MainWepon extends Items {
	/** 攻撃力 */
	private int offence;

	public MainWepon(String name) {
		super(name);
	}
	/**
	 * コンストラクタ。
	 * @param item RpgItem
	 */
	public MainWepon(RpgItem item) throws RpgException {
		super(item.getName());
		String valueKigo = item.getItemValueKigo();
		String kigo = valueKigo.substring(0,3);
		String value = valueKigo.substring(3);

		if (isDebug) System.out.println("kigo: " + kigo + " : value: " + value);

		if (RpgConst.WEV.toString().equals(kigo) == false) {
			throw new RpgException(MessageConst.ERR_SETTING_OBJECT.toString() + ": " + kigo);
		}
		int val = Integer.parseInt(value);
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
}
