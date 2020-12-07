package jp.zenryoku.procon;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jp.zenryoku.procon.server.ProConServer;
import jp.zenryoku.procon.server.ProConServerConst;
import jp.zenryoku.rpg.Games;

public class ProConRPGLogic extends Application implements Games {
	/** 画面のサーバー */
	private MainServer server;

	public ProConRPGLogic() throws Exception {

	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		System.out.println("Hello");
		// FXMLのロード
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ProConTitleView.fxml"));
		VBox root = null;

		try {
			root = (VBox) loader.load();
			root.setStyle("-fx-background-color: transparent;");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		scene.setFill(null);
		scene.getStylesheets().add(getClass().getResource("/views/ProConTitleView.css").toExternalForm());
		primaryStage.setTitle("Video Processing");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@Override
	public void init(String title) {
		// JavaFXスタート
		launch(title);
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

	/**
	 * ServreSocketを起動する。
	 *
	 * @throws Exception
	 */
	public void exeServer() throws Exception {
		server = new MainServer();
		server.start();
		System.out.println("Start FxServer");
	}

	public void finalize() {

	}
	/**
	 * クライアントのリクエストを受け付けるサーバー。
	 * 通称プロコンサーバー
	 *
	 * @author 実装者の名前
	 */
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
		public void finalize() {
			try {
				if (server != null) server.close();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				server = null;
			}
		}
	}
}
