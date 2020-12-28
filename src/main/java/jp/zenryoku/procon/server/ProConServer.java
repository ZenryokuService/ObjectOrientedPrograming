package jp.zenryoku.procon.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.sun.javafx.logging.Logger;

import jp.zenryoku.procon.ProConDataObject;
import jp.zenryoku.procon.ProConRPGLogic;
import jp.zenryoku.procon.client.ClientData;
import lombok.Data;

@Data
public class ProConServer implements Runnable {
	/** Logger */
	private Logger LOG = new Logger();
	/** サーバ */
	private Socket socket;
	/** サーバー停止フラグ  */
	private boolean isStop;
	/** クライアントからのリクエスト */
	private BufferedReader request;
	/** クライアントへのレスポンス */
	private PrintWriter response;
	/** クライアントからのデータ */
	private ClientData firstRequest;
	/** ProConRPGLogic */
	private ProConRPGLogic logic;
	/** 準備完了フラグ */
	private boolean isReady;

	/**
	 * コンストラクタ。
	 * サーバーをポート番号
	 */
	public ProConServer(Socket socket) throws Exception {
		isStop = false;
		isReady = false;
		this.socket = socket;
	}

	/**
	 * コンストラクタ。
	 * サーバーをポート番号
	 */
	public ProConServer(Socket socket, ProConRPGLogic logic) throws Exception {
		isStop = false;
		this.socket = socket;
		this.logic = logic;
	}

	/** SocketからBufferedReaderを取得する */
	protected BufferedReader getBufferdReader() throws IOException {
		return new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	/** SocketからPrintWriterを取得する */
	protected PrintWriter getPrintWriter() throws IOException {
		return new PrintWriter(socket.getOutputStream());
	}

	/** SocketからObjectOutputStreamを取得する */
	protected ObjectOutputStream getObjectOutputStream() throws IOException {
		return new ObjectOutputStream(socket.getOutputStream());
	}

	/**
	 * 初回リクエスト送受信、クラスオブジェクトを受ける。
	 */
	private void firstRequest() throws IOException, ClassNotFoundException, Exception {
		// クライアントからのデータを送信
		ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		firstRequest = (ClientData) input.readObject();
		LOG.addMessage("Server AccessCode: " + firstRequest.getAccessCd());
		System.out.println("Server Name: " + firstRequest.getName());
		System.out.println("Server BirthDay: " + firstRequest.getBirthDay());

		// 画面側にイメージなどを設定する
		sendDataToView(firstRequest, firstRequest.getPlayerNo());
		// レスポンスを返却
		PrintWriter firstResponse = new PrintWriter(socket.getOutputStream());
		firstResponse.println("ok");
		firstResponse.flush();


		// 通知
		synchronized(this) {
			System.out.println("sync ProConServer");
			setReady(true);
			notify();
		}
	}

	private void sendDataToView(ClientData data, int playerNo) throws Exception {
		isReady = true;
		// 処理リクエスト受信完了
		System.out.println("*** sendDataToView *** / " + data.getName());
		ProConDataObject.getInstance(data);

	}

	@Override
	public synchronized void run() {
		System.out.println("ProConServer開始" + socket.getLocalPort());
		String line = null;
		System.out.println("Accept");
		System.out.println("Server: InputStream: " + socket.getInetAddress());
		try {
			// クライアントからの送信
			this.firstRequest();
			Thread.sleep(300);
			while(isStop == false) {
				request = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// レスポンス
				response = new PrintWriter(socket.getOutputStream());
				int ch = -1;
				StringBuilder build = new StringBuilder();
				while((ch = request.read()) != -1) {
					build.append((char) ch);
					if (ch == 10 || ch == 13) {
						break;
					}
				}
				line = build.toString();
				if (line.startsWith("bye")) {
					System.out.println("プロコンサーバーを終了します。");
					break;
				}
				System.out.println("サーバー受信: " + line);
				// 改行を入れないと読み取りが終わらない
				response.println(line + ProConServerConst.SEP);
				System.out.println("サーバー送信: " + line);
				response.flush();

				//isStop = false;
			}
		} catch (IOException ie) {
			ie.printStackTrace();
		} catch ( ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
