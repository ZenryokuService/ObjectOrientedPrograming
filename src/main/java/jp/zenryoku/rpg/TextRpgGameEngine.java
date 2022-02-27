package jp.zenryoku.rpg;

import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.util.ConsoleUtils;
import jp.zenryoku.rpg.util.MenuUtils;

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
	 * Threadクラスのメソッドをオーバーライド。コマンドの実行も行う。
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
			if (isDebug) System.out.println(MessageConst.ON_ROOT.toString());

			if (textRpgLogic.getSkipNextMessage() == false && textRpgLogic.getStatus() != RpgConst.CLEAR) {
				System.out.println(MessageConst.ON_ROOT);
				// 4. 入力受付
				input = textRpgLogic.acceptInput();
			}
			// ゲームの終了
			if ("bye".equals(input)) {
				System.out.println("ゲームを終了します。");
				break;
			}
			// ヘルプ
			if (input != null && input.startsWith("help")) {
				ConsoleUtils.getInstance().printConfig(input);
			}
			try {
				// メニューの表示
				if (input != null && input.startsWith("menu")) {
					MenuUtils.getInstance().printMenu();
				}
				// ステータスの表示
				if (input != null && input.startsWith("status")) {
					PlayerCharactor player = PlayerParty.getInstance().getPlayer();
					ConsoleUtils.getInstance().printStatus(player);
					ConsoleUtils.getInstance().acceptInput("", false);
				}
				// 6. シーンの実行
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
