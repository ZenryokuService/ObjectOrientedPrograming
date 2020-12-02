package jp.zenryoku.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ProgServer {
	/** サーバ */
	private ServerSocket server;
	/** サーバー停止フラグ  */
	private boolean isStop;

	/**
	 * コンストラクタ。
	 * サーバーをポート番号
	 */
	public ProgServer() throws Exception {
		isStop = false;
		try {
			server = new ServerSocket(ProgServerConst.SERVER_PORT);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("サーバーの起動に失敗しました。" + e.getMessage());
		}
	}

	public void run() throws Exception {
		while(isStop == false) {
			// リクエストの受け付け
			Socket socket = server.accept();
			new ProgRPGThread(socket).start();
		}
	}
}
