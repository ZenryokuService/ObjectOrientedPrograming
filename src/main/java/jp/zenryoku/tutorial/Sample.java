package jp.zenryoku.tutorial;

import java.util.HashMap;
import java.util.Map;

/**
 * サンプルクラス、サンプルコードを実行する。
 *
 * @author 実装者の名前
 */
public class Sample {
	/** 月曜を示すフラグ */
	public final int MON = 0;
	/** 月曜を示すフラグ */
	public final int TUE = 1;
	/** 月曜を示すフラグ */
	public final int WED = 2;
	/** 月曜を示すフラグ */
	public final int THU = 3;
	/** 月曜を示すフラグ */
	public final int FRI = 4;
	/** 月曜を示すフラグ */
	public final int SAT = 5;
	/** 月曜を示すフラグ */
	public final int SUN = 6;

	/** 曜日のMap */
	private Map<String, Integer> hantei;


	/**
	 * @return hantei
	 */
	public Map<String, Integer> getHantei() {
		return hantei;
	}


	/**
	 * @param hantei セットする hantei
	 */
	public void setHantei(Map<String, Integer> hantei) {
		this.hantei = hantei;
	}


	/**
	 * 月曜から日曜までのマップを作成する。
	 */
	public void createYobiMap() {
		hantei = new HashMap<String, Integer>();
		hantei.put("MON", MON);
		hantei.put("TUE", TUE);
		hantei.put("WED", WED);
		hantei.put("THU", THU);
		hantei.put("FRI", FRI);
		hantei.put("SAT", SAT);
		hantei.put("SUN", SUN);
	}
}
