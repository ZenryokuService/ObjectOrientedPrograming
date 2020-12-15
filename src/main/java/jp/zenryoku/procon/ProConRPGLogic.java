package jp.zenryoku.procon;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jp.zenryoku.procon.client.ClientData;
import jp.zenryoku.procon.server.ProConServer;
import jp.zenryoku.procon.server.ProConServerConst;
import jp.zenryoku.rpg.Games;
import lombok.Data;

@SuppressWarnings("restriction")
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
		String[] args = new String[] {title};
		// JavaFXスタート
		launch(args);
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
	@Data
	private class MainServer extends Thread implements Observer {
		/** サーバ */
		private ServerSocket server;
		/** サーバー停止フラグ  */
		private boolean isStop;
		/** プロコンサーバーMap */
		private Map<String, ProConServer> map;

		public MainServer() throws Exception {
			map = new HashMap<String, ProConServer>();
			server = new ServerSocket(ProConServerConst.SERVER_PORT);
		}

		public void run() {
			isStop = false;
			ExecutorService service = Executors.newCachedThreadPool();

			try {
				int count = 1;
				String key = "Player";
				while (isStop == false) {
					System.out.println("Run FxServer");
					Socket socket = server.accept();

					ProConServer pro = new ProConServer(socket);
					map.put(key + count, pro);
					service.submit(pro);
					count++;
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

		/**
		 * ProConServerの変更通知を受け取る。
		 * @param proconServer Observableクラス(ProConServer)
		 * @param param Observerbale#notifyObserversメソッドに渡す引数
		 */
		@Override
		public void update(Observable proconServer, Object param) {
			ClientData res = (ClientData) param;
			res.getName();
			res.getBirthDay();
			res.getImage();
			//proConServer.
		}
	}
}
