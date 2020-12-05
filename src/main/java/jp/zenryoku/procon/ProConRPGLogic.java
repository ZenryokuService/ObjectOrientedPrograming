package jp.zenryoku.procon;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Application;
import javafx.stage.Stage;
import jp.zenryoku.procon.server.ProConServer;
import jp.zenryoku.procon.server.ProConServerConst;
import jp.zenryoku.rpg.Games;

public class ProConRPGLogic extends Application implements Games {
	/** 画面のサーバー */
	private MainServer server;

	public ProConRPGLogic() throws Exception {
		server = new MainServer();
		server.start();
		System.out.println("Start FxServer");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//
	}

	@Override
	public void init(String title) {
		// JavaFXスタート
		//launch(title);
	}
	@Override
	public String acceptInput() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean updateData(String input) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean render() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public void exeServer() throws Exception {
	}

	private class MainServer extends Thread {
		/** サーバ */
		private ServerSocket server;
		/** サーバー停止フラグ  */
		private boolean isStop;

		public MainServer() throws Exception {
			server = new ServerSocket(ProConServerConst.SERVER_PORT);
		}

		public void run() {
			isStop = false;
			try {
				while (isStop == false) {
					System.out.println("Run FxServer");
					Socket socket = server.accept();
					new ProConServer(socket).start();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				server = null;
			}
		}
	}
}
