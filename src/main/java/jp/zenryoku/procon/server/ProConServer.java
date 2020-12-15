package jp.zenryoku.procon.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

import jp.zenryoku.procon.client.ClientData;
import lombok.Data;

@Data
public class ProConServer extends Observable implements Runnable {
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

	/** SocketからBufferedReaderを取得する */
	private BufferedReader getBufferdReader() throws IOException {
		return new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	/** SocketからPrintWriterを取得する */
	private PrintWriter getPrintWriter() throws IOException {
		return new PrintWriter(socket.getOutputStream());
	}

	/** SocketからObjectOutputStreamを取得する */
	private ObjectOutputStream getObjectOutputStream() throws IOException {
		return new ObjectOutputStream(socket.getOutputStream());
	}

	/**
	 * 初回リクエスト送受信、クラスオブジェクトを受ける。
	 */
	private void firstRequest() throws IOException, ClassNotFoundException {
		// クライアントからのデータを送信
		ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		ClientData firstRequest = (ClientData) input.readObject();
		System.out.println("Server AccessCode: " + firstRequest.getAccessCd());
		System.out.println("Server Name: " + firstRequest.getName());
		System.out.println("Server BirthDay: " + firstRequest.getBirthDay());

		// 画面側にイメージなどを設定する
		sendDataToView();
		// レスポンスを返却
		PrintWriter firstResponse = new PrintWriter(socket.getOutputStream());
		firstResponse.println("ok");
		firstResponse.flush();

		// 監視オブジェクトへ通知
		notifyObservers(firstRequest);
	}

	private void sendDataToView() {
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

//				request.close();
//				response.close();
				//isStop = false;
			}
		} catch (IOException ie) {
			ie.printStackTrace();
		} catch ( ClassNotFoundException e) {
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
