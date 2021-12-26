package jp.zenryoku.rpg;

import jp.zenryoku.rpg.constants.RpgConst;

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
	 */
	@Override
	public void run() {
		// 0.ゲーム起動のための準備処理
		// 1. 戦闘開始の文言「XXXがあらわれた！」を表示する
		textRpgLogic.init("title");
		// 2.ゲームループ開始
		// 3.初期表示(ステータスの表示)
		while(true) {
			// 4. 入力受付
			String input = textRpgLogic.acceptInput();
			if ("bye".equals(input)) {
				System.out.println("ゲームを終了します。");
				break;
			}
			// 5. データの更新処理
			if (textRpgLogic.updateData(input) == false) {
				continue;
			}
			// 6. 画面(コンソール)の更新
			if (textRpgLogic.render()) {
				// TRUEが返ってきた場合は、終了
				if (textRpgLogic.getEndStatus() == RpgConst.SAVE) {
					System.out.println("お疲れ様でした。次の冒険で会いましょう。");
				} else {
					System.out.println("たたかいが、おわりました。");
				}
				break;
			}
		}
	}
}
