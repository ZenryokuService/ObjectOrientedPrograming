package jp.zenryoku.procon.client;

import java.io.Serializable;

import lombok.Data;

/**
 * クライアント(ゲームプレーヤー)のデータクラス。
 *
 * @author 実装者の名前
 *
 */
@Data
public class ClientData implements Serializable {
	/** アクセスコード */
	private String accessCd;
	/** プレーヤーの名前 */
	private String name;
	/** 生年月日 */
	private String birthDay;
	/** 32 x 32の画像データ(透過PNG) */
	byte[] image;

	/** 送信するコマンド */
	private String command;
}
