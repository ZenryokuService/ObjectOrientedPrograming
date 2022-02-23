package jp.zenryoku.rpg;

public class GameMain {
	/**
	 * ゲームを起動するクラス。メインメソッドがある。
	 * 今後、DBなど使用するときにはここでDBを起動する。
	 * ※Derbyを使用する予定
	 *
	 * @param args プログラム引数
	 */
	public static void main(String[] args) {
		try {
			RpgLogic gameLogic = new TextRpgLogic();
			TextRpgGameEngine engine  = new TextRpgGameEngine(gameLogic);

			engine.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("想定外のエラーで終了します。：" + e.getMessage());
			System.exit(-1);
		}
	}
 }
