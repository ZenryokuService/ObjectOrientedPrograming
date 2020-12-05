package jp.zenryoku.procon.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ProConServer extends Thread {
	/** サーバ */
	private Socket socket;
	/** サーバー停止フラグ  */
	private boolean isStop;
	/** クライアントからのリクエスト */
	private BufferedReader request;
	/** クライアントへのレスポンス */
	private PrintWriter response;

	/**
	 * コンストラクタ。
	 * サーバーをポート番号
	 */
	public ProConServer(Socket socket) throws Exception {
		isStop = false;
		this.socket = socket;
	}

	@Override
	public void run() {
		System.out.println("ProConServer開始" + socket.getLocalPort());
		String line = null;
		try {
//			while(isStop == false) {
				System.out.println("Accept");
				System.out.println("Server: InputStream: " + socket.getInetAddress());
				// クライアントからの送信
				request = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				System.out.println("Server: OutputStream");
				// レスポンス
				response = new PrintWriter(socket.getOutputStream());
			while((line = request.readLine()) != null) {
				System.out.println("サーバー受信: " + line);
				response.println(line);
				response.flush();
				System.out.println("サーバー送信: " + line);
			}
			//isStop = false;
//		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void finalize() {
		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			socket = null;
		}
	}
}
