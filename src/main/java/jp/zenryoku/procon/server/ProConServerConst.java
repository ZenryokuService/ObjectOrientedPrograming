package jp.zenryoku.procon.server;

/**
 * プログラミング・コンテストRPGで使用する定数を定義する。
 *
 * @author 実装者の名前
 */
public enum ProConServerConst {
	RPG_TITLE,
	COMMAND_MOVE,
	COMMAND_SEARCH,
	COMMAND_ITTEM,
	COMMAND_ATTACK;

	/** サーバーの起動するポート番号 */
	public static final int SERVER_PORT = 8000;
	/** クライアント１の起動するポート番号 */
	public static final int CLIENT_1_PORT = 8020;
	/** クライアント２の起動するポート番号 */
	public static final int CLIENT_2_PORT = 8021;
	/** クライアント３の起動するポート番号 */
	public static final int CLIENT_3_PORT = 8022;
	/** クライアント４の起動するポート番号 */
	public static final int CLIENT_4_PORT = 8023;
	/** 改行コード */
	public static final String SEP = System.lineSeparator();
	/** プロコンサーバーアクセスコード */
	public static final String ACCESS_CD = "coderdojo";
	/** サーバーとの通信待ち時間 */
	public static final long WAIT = 500;
	/** イメージデータの最大サイズ */
	public static final int MAX_IMG_SIZE = 300;
	/** サーバー停止コマンド */
	public static final String BYE = "bye";
	/** Map画面開始コマンド */
	public static final String MAP_START = "start";
	/** 通常リクエスト */
	public static final String SEND_REQUEST = "request";


}
