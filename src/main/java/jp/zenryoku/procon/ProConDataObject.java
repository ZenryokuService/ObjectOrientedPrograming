package jp.zenryoku.procon;

import jp.zenryoku.procon.client.ClientData;

/**
 * finalクラスです。
 * シングルトンなので、不変のクラス、Thread間のデータ受け渡しに使用する。
 *
 * @author 実装者の名前
 *
 */
public final class ProConDataObject {
	private static ClientData clientData;

	private static ProConDataObject proConData;

	private ProConDataObject() {
	}

	/**
	 * 空のProConDataObjectを返します。
	 * @return ProConDataObject
	 */
	public synchronized ProConDataObject getInstance() {
		if (proConData == null) {
			proConData = new ProConDataObject();
		}
		proConData.setClientData(null);
		return this;
	}

	/**
	 * dataをセットしたProConDataObjectを返します。
	 *
	 * @param data
	 * @return ProConDataObject
	 */
	public static synchronized ProConDataObject getInstance(ClientData data) {
		if (proConData == null) {
			proConData = new ProConDataObject();
		}
		setClientData(data);
		return proConData;
	}

	public static synchronized boolean isClientData() {
		return clientData != null;
	}

	public static synchronized ClientData getClientData() {
		System.out.println("*** getClientData ***");
		return clientData;
	}

	public synchronized static void setClientData(ClientData data) {
		clientData = data;
	}
}
