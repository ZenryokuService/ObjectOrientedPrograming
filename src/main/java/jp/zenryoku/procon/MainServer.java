package jp.zenryoku.procon;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import jp.zenryoku.procon.server.ProConServer;
import jp.zenryoku.procon.server.ProConServerConst;
import lombok.Data;

/**
 * クライアントのリクエストを受け付けるサーバー。
 * 通称プロコンサーバー
 *
 * @author 実装者の名前
 */
@Data
public class MainServer extends Thread {
	/** ProConRPGLogic */
	private ProConRPGLogic logic;
	/** サーバ */
	private ServerSocket server1;
	private ServerSocket server2;
	private ServerSocket server3;
	private ServerSocket server4;

	/** サーバー停止フラグ  */
	private boolean isStop;

	public MainServer(ProConRPGLogic logic) throws Exception {
		this.logic = logic;
		server1 = new ServerSocket(ProConServerConst.CLIENT_1_PORT);
		server2 = new ServerSocket(ProConServerConst.CLIENT_2_PORT);
		server3 = new ServerSocket(ProConServerConst.CLIENT_3_PORT);
		server4 = new ServerSocket(ProConServerConst.CLIENT_4_PORT);
	}

	@Override
	public void run() {
		isStop = false;
		ExecutorService service = Executors.newFixedThreadPool(4);

		try {
			System.out.print("Run FxServer");
			Socket socket1 = server1.accept();
//			Socket socket2 = server2.accept();
//			Socket socket3 = server3.accept();
//			Socket socket4 = server4.accept();
			System.out.println(" accept!");

			ProConServer pro1 = new ProConServer(socket1, logic);
//			ProConServer pro2 = new ProConServer(socket2, logic);
//			ProConServer pro3 = new ProConServer(socket3, logic);
//			ProConServer pro4 = new ProConServer(socket4, logic);

			service.submit(pro1);
//			service.submit(pro2);
//			service.submit(pro3);
//			service.submit(pro4);

			synchronized(pro1) {
				System.out.println("sync MainServer");
				while(pro1.isReady() == false) {
					pro1.wait();
				}
				Platform.runLater(() -> {
					try {
						logic.setClientData(pro1.getFirstRequest(), ProConServerConst.PLAYER1_NO);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
			// 初回リクエストを受ける
//			ClientData data2 = pro2.getFirstRequest();
//			ClientData data3 = pro3.getFirstRequest();
//			ClientData data4 = pro4.getFirstRequest();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (server1 != null) server1.close();
				if (server2 != null) server2.close();
				if (server3 != null) server3.close();
				if (server4 != null) server4.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

	public void finalize() {
		try {
			if (server1 != null) server1.close();
			if (server2 != null) server2.close();
			if (server3 != null) server3.close();
			if (server4 != null) server4.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			clearSocket();
		}
	}

	private void clearSocket() {
		server1 = null;
		server2 = null;
		server3 = null;
		server4 = null;
	}
}