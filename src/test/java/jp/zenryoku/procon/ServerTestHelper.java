package jp.zenryoku.procon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import jp.zenryoku.procon.server.ProConServerConst;

public abstract class ServerTestHelper {
	/** サーバー */
	protected ServerSocket server;
	/** 改行文字 */
	protected static final String SEP = System.lineSeparator();
	protected boolean isStop = false;

	protected void runServer() throws Exception {
		try {
			server = new ServerSocket(ProConServerConst.SERVER_PORT);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			throw new Exception("サーバー起動に失敗しました。");
		}
	}

	protected void clientSocket(int num, String mes) {
		try {
			System.out.println("クライアント：テスト開始: " + num);
			Socket socket = new Socket("localhost", ProConServerConst.SERVER_PORT + num);
			System.out.println("クライアント：接続確認  " + socket.isConnected());
			PrintWriter in = new PrintWriter(socket.getOutputStream());
			System.out.println("クライアント：送信開始");
			in.write(mes + SEP);
			in.flush();
			System.out.println("クライアント：送信終了");
			BufferedReader out = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			System.out.println("クライアント：受信開始");
			StringBuilder line = new StringBuilder();
			int ch = -1;
			while((ch = out.read()) != -1) {
				char val = (char) ch;
				line.append(val);
				if (ch == 10 || ch == 13) {
					break;
				}
			}
			System.out.println("クライアント；受信: " + line.toString());
			socket.close();
//			server.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
