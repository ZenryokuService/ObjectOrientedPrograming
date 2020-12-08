package jp.zenryoku.procon.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import jp.zenryoku.procon.server.ProConServerConst;

public class ClientSuperCls {
	/** 処理の停止フラグ */
	protected boolean isStop;
	/** ソケット */
	protected Socket socket;

	/**
	 * クライアントから、サーバーへ接続するためのソケットを作成する。
	 *
	 * @param hostName 接続するサーバーのホスト(PCの名前、IPアドレス)
	 * @throws IOException サーバーへの接続に失敗
	 */
	public ClientSuperCls(String hostName) throws IOException {
		socket = new Socket(hostName, ProConServerConst.SERVER_PORT);
	}

	public void connectServer() throws IOException {
		if (socket.isConnected() == false) {
			throw new IOException("接続が切れました。");
		}
	}

	/**
	 * プロコンRPGを開始する。
	 */
	protected void exevuteRpg() throws Exception {
		// 初期リクエストを送信する
		this.sendRequest(ProConServerConst.ACCESS_CD);

		// サーバーとの通信処理
		while (isStop) {
			// サーバーからのレスポンスを取得する
			String response = this.getResponse();
			String request = handleResponse(response);

			// 処理の待機処理(U16プロコンサーバーに習う)
			Thread.sleep(ProConServerConst.WAIT);

			if (request.startsWith("finish")) {
				System.out.println("プロコンRPGを終了します。");
				break;
			}
			this.sendRequest(request);
		}
	}

	/**
	 * サーバーからのレスポンスを処理して、リクエストを送信する。
	 *
	 * @param response サーバーからのレスポンス
	 * @return request サーバーへ送信するリクエスト
	 */
	public String handleResponse(String response) {
		System.out.println("クライアント：" + response);
		return response;
	}
	/**
	 * リクエストをプロコンサーバーへ送信する。
	 *
	 * @param message
	 * @throws IOException
	 */
	public void sendRequest(String message) throws IOException {
		if (isEmpty(message)) {
			throw new IOException("リクエストメッセージが入っていません");
		}
	}

	public String getResponse() throws IOException {
		BufferedReader response = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		String line = null;
		StringBuilder build = new StringBuilder();
		while ((line = response.readLine()) != null) {
			build.append(line + ProConServerConst.SEP);
		}
		return build.toString();
	}

	public boolean isEmpty(String val) {
		if (val == null || "".equals(val)) {
			return true;
		}
		return false;
	}
}
