package jp.zenryoku.procon.client;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

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
		isStop = false;
	}

	private void connectServer() throws IOException {
		if (socket.isConnected() == false) {
			throw new IOException("接続が切れました。");
		}
	}

	/** SocketからBufferedReaderを取得する */
	private BufferedReader getBufferdReader() throws IOException {
		return new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	/** SocketからPrintWriterを取得する */
	private PrintWriter getPrintWriter() throws IOException {
		return new PrintWriter(socket.getOutputStream());
	}

	/** SocketからObjectOutputStreamを取得する */
	public ObjectOutputStream getObjectOutputStream() throws IOException {
		return new ObjectOutputStream(socket.getOutputStream());
	}

	/**
	 * プロコンサーバーへ初回リクエストを送信する。
	 *
	 * @param サーバーを止めるフラグ ture: サーバー停止リクエスト false: サーバーにコマンドを送信
	 * @throws IOException
	 */
	public void firstRequest(boolean callStop) throws IOException {
		BufferedReader input = this.getBufferdReader();
		ObjectOutputStream output = this.getObjectOutputStream();
		// 送受信するクラス
		ClientData data = createInitData();
		// 初期リクエストを送信する
		this.sendRequest(output, data);

		System.out.println("FirstRequest Client: " + this.getResponse(input));

		if (callStop) {
			this.sendRequest(new PrintWriter(socket.getOutputStream()), ProConServerConst.BYE);
		} else {
			this.sendRequest(new PrintWriter(socket.getOutputStream()), ProConServerConst.SEND_REQUEST);
		}
	}

	/**
	 * Map画面でコマンドの送受信を行う。
	 *
	 * @param response サーバーからのレスポンス
	 * @return 次に実行するコマンド
	 */
	public void commandRequest(String response) throws IOException {
		String res = "response: " + response;
		PrintWriter request = this.getPrintWriter();
		System.out.println("Client Send: " + res);
		request.write(res);
		request.flush();
	}

	/**
	 * プロコンRPGを開始する。
	 */
	public void exevuteRpg() throws Exception {
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		// 送受信するクラス
		ClientData data = createInitData();
		// 初期リクエストを送信する
		this.sendRequest(output, data);

		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		System.out.println("exevuteRpg Client: " + this.getResponse(input));

		output = new ObjectOutputStream(socket.getOutputStream());
		this.sendRequest(new PrintWriter(socket.getOutputStream()), "bye");
		// サーバーとの通信処理
		while (isStop == false) {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new ObjectOutputStream(socket.getOutputStream());
			// サーバーからのレスポンスを取得する
			String response = this.getResponse(input);
			String request = handleResponse(response);
System.out.println("exevuteRpg Client Recieve: " + request);
			// 処理の待機処理(U16プロコンサーバーに習う)
//			Thread.sleep(ProConServerConst.WAIT);
			data.setCommand("bye");
			this.sendRequest(output, data);
			//isStop = true;
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
	public void sendRequest(PrintWriter request, String message) throws IOException {
		if (isEmpty(message)) {
			throw new IOException("リクエストメッセージが入っていません");
		}
		request.println(message);
		request.flush();
//		request.close();
	}

	/**
	 * リクエストをプロコンサーバーへ送信する。
	 *
	 * @param message
	 * @throws IOException
	 */
	public void sendRequest(ObjectOutputStream request, ClientData message) throws IOException {
		if (isEmpty(message)) {
			throw new IOException("リクエストメッセージが入っていません");
		}
		request.writeObject(message);
		request.flush();
//		request.close();
	}


	/**
	 * サーバーから受信したデータを受け取る。
	 *
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getResponse(BufferedReader response) throws IOException {

		String line = null;
		StringBuilder build = new StringBuilder();
		int ch = -1;
		System.out.println("*** Client ***");
		while ((ch = response.read()) != -1) {
			build.append((char) ch);
			if (ch == 10 || ch == 13) {
				break;
			}
		}
		//response.close();
		System.out.println("Client Recieve: " + build.toString());
		return build.toString();
	}

	public boolean isEmpty(String val) {
		if (val == null || "".equals(val)) {
			return true;
		}
		return false;
	}

	public boolean isEmpty(Object val) {
		if (val == null || "".equals(val)) {
			return true;
		}
		return false;
	}

	/**
	 * 初期送信データの生成
	 * 1. ユーザーの名前
	 * 2. 生年月日
	 * 3. 32 x 32の画像ファイル
	 */
	protected ClientData createInitData() {
		ClientData data = new ClientData();
		data.setAccessCd(ProConServerConst.ACCESS_CD);
		data.setName("Takunoji");
		data.setBirthDay("19800328");
		data.setImage(imageToByte("gost"));


		return data;

	}

	/**
	 * イメージファイルを取得して、バイト配列に変換する
	 * 取得するイメージファイルは、src/main/resources/char以下のPNGファイル)
	 *
	 * @param fileName ファイルの名前(拡張子は除く)
	 * @return
	 */
	protected byte[] imageToByte(String fileName) {
		byte[] result = null;
		try {
			URL imgUrl = Paths.get("resources/char_img/" + fileName + ".png").toUri().toURL();
			BufferedImage img = ImageIO.read(imgUrl);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			// イメージをバイト配列に書き込み
			ImageIO.write(img, "png", outStream);

			result = ByteBuffer.allocate(ProConServerConst.MAX_IMG_SIZE).putInt(outStream.size()).array();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private void aaaa() {

	}
 }
