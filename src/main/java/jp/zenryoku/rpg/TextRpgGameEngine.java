package jp.zenryoku.rpg;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.exception.RpgException;

/**
 * テキストRPG(戦闘シーンのみ)を実装する。
 *
 * @author 作成者の名前
 */
public class TextRpgGameEngine extends Thread {
	/** でバックモード */
	public final boolean isDebug = false;
	/** テキストRPGクラス */
	private RpgLogic textRpgLogic;

	/**
	 * コンストラクタ。テキストRPGのロジックを実装したクラスを保持する。
	 *
	 * @param gameLogic ロジックを実装したクラス
	 */
	public TextRpgGameEngine(RpgLogic gameLogic) {
		textRpgLogic = gameLogic;
	}
	/**
	 * Threadクラスのメソッドをオーバーライド。
	 * これで、マルチスレッドでの処理が可能になる。
	 */
	@Override
	public void run() {
		// 0.ゲーム起動のための準備処理
		textRpgLogic.init("title");
		// プレーヤー生成
		try {
			textRpgLogic.executeScene();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		if (isDebug) textRpgLogic.getSceneList().forEach(scene -> {
			System.out.println("Index: " + scene.sceneIndex);
		});

		while(true) {
			String input = null;
			if (textRpgLogic.getSkipNextMessage() == false && textRpgLogic.getStatus() != RpgConst.CLEAR) {
				System.out.println("<次へ>");
				// 4. 入力受付
				input = textRpgLogic.acceptInput();
			}
			if ("bye".equals(input)) {
				System.out.println("ゲームを終了します。");
				break;
			}
//			// 5. データの更新処理
//			if (textRpgLogic.updateData(input) == false) {
//				continue;
//			}
			// 6. シーンの実行
			try {
				if (textRpgLogic.executeScene()) {
					// TRUEが返ってきた場合は、終了
					if (textRpgLogic.getEndStatus() == RpgConst.SAVE) {
						System.out.println(MessageConst.END_SAVE.toString());
					} else if (textRpgLogic.getEndStatus() == RpgConst.GAME_OVER) {
						System.out.println(MessageConst.END_GAMEOVER.toString());
					} else {
						System.out.println(MessageConst.END_FIN.toString());
					}
					break;
				}
			} catch(RpgException re) {
				System.out.println(re.getMessage());
				re.printStackTrace();
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				break;
			}
		}
	}
}
