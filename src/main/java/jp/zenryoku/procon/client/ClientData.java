package jp.zenryoku.procon.client;

import java.awt.image.BufferedImage;
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
	/** プレーヤー番号 */
	private int playerNo;
	/** プレーヤーの名前 */
	private String name;
	/** 生年月日 */
	private String birthDay;
	/** 32 x 32の画像データ(透過PNG) */
	byte[] imageByte;
	/** 32 x 32の画像データ(透過PNG) */
	BufferedImage image;

	/** 送信するコマンド */
	private String command;
}
