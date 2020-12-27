package jp.zenryoku.procon;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jp.zenryoku.procon.client.ClientData;
import jp.zenryoku.procon.server.ProConServerConst;
import jp.zenryoku.rpg.Games;

public class ProConRPGLogic extends Application implements Games {
	/** 画面のサーバー */
	private MainServer server;
	/** プレーヤー１のデータ */
	private ClientData data1;
	/** プレーヤー１の名前 */
	@FXML
	private Label player1Name;
	/** プレーヤー１参加・未参加 */
	@FXML
	private Label player1Attend;
	/** プレーヤー１の画像 */
	@FXML
	private ImageView player1Image;

	/** プレーヤー２のデータ */
	private ClientData data2;
	/** プレーヤー２の名前 */
	@FXML
	private Label player2Name;
	/** プレーヤー２参加・未参加 */
	@FXML
	private Label player2Attend;
	/** プレーヤー２の画像 */
	@FXML
	private ImageView player2Image;

	/** プレーヤー３のデータ */
	private ClientData data3;
	/** プレーヤー３の名前 */
	@FXML
	private Label player3Name;
	/** プレーヤー３参加・未参加 */
	@FXML
	private Label player3Attend;
	/** プレーヤー３の画像 */
	@FXML
	private ImageView player3Image;

	/** プレーヤー４のデータ */
	private ClientData data4;
	/** プレーヤー４の名前 */
	@FXML
	private Label player4Name;
	/** プレーヤー４参加・未参加 */
	@FXML
	private Label player4Attend;
	/** プレーヤー４の画像 */
	@FXML
	private ImageView player4Image;


	public ProConRPGLogic() throws Exception {
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			exeServer();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		primaryStage.initStyle(StageStyle.TRANSPARENT);

		// FXMLのロード
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ProConTitleView.fxml"));
		VBox root = null;

		try {
			root = (VBox) loader.load();
			loader.setRoot(root);
			root.setStyle("-fx-background-color: transparent;");
		} catch (Exception e) {
			e.printStackTrace();
		}

		setComponents(loader);

		Scene scene = new Scene(root);
		scene.setFill(null);
		scene.getStylesheets().add(getClass().getResource("/views/ProConTitleView.css").toExternalForm());
		primaryStage.setTitle("Video Processing");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void setComponents(FXMLLoader loader) {
		player1Name = (Label) loader.getNamespace().get("player1Name");
		player2Name = (Label) loader.getNamespace().get("player2Name");
		player3Name = (Label) loader.getNamespace().get("player3Name");
		player4Name = (Label) loader.getNamespace().get("player4Name");

		player1Attend = (Label) loader.getNamespace().get("player1Attend");
		player2Attend = (Label) loader.getNamespace().get("player2Attend");
		player3Attend = (Label) loader.getNamespace().get("player3Attend");
		player4Attend = (Label) loader.getNamespace().get("player4Attend");

		player1Image = (ImageView) loader.getNamespace().get("player1Image");
		player2Image = (ImageView) loader.getNamespace().get("player2Image");
		player3Image = (ImageView) loader.getNamespace().get("player3Image");
		player4Image = (ImageView) loader.getNamespace().get("player4Image");
	}

	@Override
	public void init(String title) {
		System.out.println("title");
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
		server = new MainServer(this);
		server.start();
		System.out.println("MainServer Start");
	}

	public void finalize() {
		server = null;
		data1 = null;
		player1Name = null;
		player1Attend = null;
		player1Image = null;
		data2 = null;
		player2Name = null;
		player2Attend = null;
		player2Image = null;
		data3 = null;
		player3Name = null;
		player3Attend = null;
		player3Image = null;
		data4 = null;
		player4Name = null;
		player4Attend = null;
		player4Image = null;

	}

	private BufferedImage createBufferedImage(byte[] imgByte) throws IOException {
		InputStream in = new ByteArrayInputStream(imgByte);
		return ImageIO.read(in);
	}

	public synchronized void setClientData(ClientData data, int playerNo) throws Exception {
		System.out.println("*** setClientData : " + player1Name + " / " + data + " / " + playerNo + " ***");
		switch (playerNo) {
		case ProConServerConst.PLAYER1_NO:
			data1 = data;
			player1Name.setText(data.getName());
			player1Attend.setText(ProConServerConst.ATTEND);
			setPlayerImage(createBufferedImage(data.getImage()), playerNo);
			break;
		case ProConServerConst.PLAYER2_NO:
			data2 = data;
			player2Name.setText(data.getName());
			player2Attend.setText(ProConServerConst.ATTEND);
			setPlayerImage(createBufferedImage(data.getImage()), playerNo);
			break;
		case ProConServerConst.PLAYER3_NO:
			data3 = data;
			player3Name.setText(data.getName());
			player3Attend.setText(ProConServerConst.ATTEND);
			setPlayerImage(createBufferedImage(data.getImage()), playerNo);
			break;
		case ProConServerConst.PLAYER4_NO:
			data4 = data;
			player4Name.setText(data.getName());
			player4Attend.setText(ProConServerConst.ATTEND);
			setPlayerImage(createBufferedImage(data.getImage()), playerNo);
			break;
		default:
			throw new Exception("player番号	が不適切です。" + playerNo);
		}
	}

	private void setPlayerImage(BufferedImage playerImage, int playerNo) throws Exception {
		switch (playerNo) {
		case ProConServerConst.PLAYER1_NO:
			player1Image.setImage(SwingFXUtils.toFXImage(playerImage, null));
			break;
		case ProConServerConst.PLAYER2_NO:
			player2Image.setImage(SwingFXUtils.toFXImage(playerImage, null));
			break;
		case ProConServerConst.PLAYER3_NO:
			player3Image.setImage(SwingFXUtils.toFXImage(playerImage, null));
			break;
		case ProConServerConst.PLAYER4_NO:
			player4Image.setImage(SwingFXUtils.toFXImage(playerImage, null));
			break;
		default:
			throw new Exception("playerカウンターが不適切です。" + playerNo);
		}
	}
}
