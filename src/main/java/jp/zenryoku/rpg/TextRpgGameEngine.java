package jp.zenryoku.rpg;

/**
 * テキストRPG(戦闘シーンのみ)を実装する。
 *
 * @author 作成者の名前
 */
public class TextRpgGameEngine extends Thread {
	/** テキストRPGクラス */
	private Games textRpgLogic;

	/**
	 * コンストラクタ。テキストRPGのロジックを実装したクラスを保持する。
	 *
	 * @param gameLogic ロジックを実装したクラス
	 */
	public TextRpgGameEngine(Games gameLogic) {
		textRpgLogic = gameLogic;
	}
	/**
	 * Threadクラスのメソッドをオーバーライド。
	 * これで、マルチスレッドでの処理が可能になる。
	 * @see フローチャート
	 */
	@Override
	public void start() {
		// 0.ゲーム起動のための準備処理
		// 1. 戦闘開始の文言「XXXがあらわれた！」を表示する
		textRpgLogic.init();
		// 2.ゲームループ開始
		// 3.初期表示(ステータスの表示)
		while(true) {
			// 4. 入力受付
			String input = textRpgLogic.acceptInput();
			if ("bye".contentEquals(input)) {
				System.out.println("ゲームを終了します。");
				break;
			}
			// 5. データの更新処理
			if (textRpgLogic.updateData(input) == false) {
				continue;
			}
			// 6. 画面(コンソール)の更新
			if (textRpgLogic.render()) {
				// TRUEが返ってきた場合は、戦闘終了
				System.out.println("たたかいが、おわりました。");
				break;
			}
		}
	}
}
