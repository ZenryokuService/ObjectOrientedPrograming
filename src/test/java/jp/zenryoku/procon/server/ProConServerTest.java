package jp.zenryoku.procon.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jp.zenryoku.procon.ServerTestHelper;


public class ProConServerTest extends ServerTestHelper {
	/** テスト対象 */
	private ProConServer target;

	private boolean isStop;

	private class SocketRunnable implements Runnable {
		public void run() {
			try {
				server = new ServerSocket(ProConServerConst.SERVER_PORT);
				System.out.println("サーバー：受信開始");
				while(isStop == false) {
					Socket socket =server.accept();
					new ProConServer(socket).start();
					System.out.println("サーバー：スレッド開始");
				}
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				fail("通信に失敗しました");
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				fail("想定外のエラーです。");
			}
		}
	}

	@BeforeEach
	public void init() {
		Thread thread = new Thread(new SocketRunnable());
		thread.start();
	}
	@Test
	public void testConstructor() {
		this.clientSocket(0, "First");
		this.clientSocket(0, "Second");
		this.clientSocket(0, "Third");
		this.clientSocket(0, "Fourth");
		isStop = true;
//		try {
//			System.out.println("クライアント：テスト開始");
//			Socket socket = new Socket("localhost", ProConServerConst.SERVER_PORT);
//			System.out.println("クライアント：接続確認  " + socket.isConnected());
//			PrintWriter in = new PrintWriter(socket.getOutputStream());
//			System.out.println("クライアント：送信開始");
//			in.write("Testing!" + SEP);
//			in.flush();
//			System.out.println("クライアント：送信終了");
//			BufferedReader out = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//			System.out.println("クライアント：受信開始");
//			StringBuilder line = new StringBuilder();
//			int ch = -1;
//			while((ch = out.read()) != -1) {
//				char val = (char) ch;
//				line.append(val);
//				if (ch == 10 || ch == 13) {
//					break;
//				}
//			}
//			System.out.println("クライアント；受信: " + line.toString());
//			socket.close();
////			server.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
