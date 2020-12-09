package jp.zenryoku.procon.client;

import java.io.Serializable;

/**
 * クライアント(ゲームプレーヤー)のデータクラス。
 *
 * @author 実装者の名前
 *
 */
public class ClientData implements Serializable {
	/** アクセスコード */
	private String accessCd;
	/** プレーヤーの名前 */
	private String name;
	/** 生年月日 */
	private String birthDay;
	/** 32 x 32の画像データ(透過PNG) */
	byte[] image;

	/**
	 * @return accessCd
	 */
	public String getAccessCd() {
		return accessCd;
	}
	/**
	 * @param accessCd セットする accessCd
	 */
	public void setAccessCd(String accessCd) {
		this.accessCd = accessCd;
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
	 * @return birthDay
	 */
	public String getBirthDay() {
		return birthDay;
	}
	/**
	 * @param birthDay セットする birthDay
	 */
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	/**
	 * @return image
	 */
	public byte[] getImage() {
		return image;
	}
	/**
	 * @param image セットする image
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}



}
