package jp.zenryoku.rpg;

import java.io.IOException;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.util.CheckerUtils;

public class TextRpgLogic extends RpgLogic {

	/**
	 * コンストラクタ。
	 * フィールド変数のインスタンスを生成
	 */
	public TextRpgLogic() {
		super();
	}

	/**
	 * コンストラクタ。
	 * フィールド変数のインスタンスを生成
	 */
	public TextRpgLogic(String directory) {
		super(directory);
	}

	/**
	 * 初期表示を行う。
	 * 1. タイトル表示
	 * 2. NewGameかContinue(未実装)
	 * 3. 選択された方のシーンを表示
	 * 4. 「start」「continue」の選択処理を行う
	 *
	 */
	@Override
	public void init(String title) {
		StringBuilder build = new StringBuilder();
		String line = null;
		RpgScene result = null;
		try {
			while((line = reader.readLine()) != null) {
				build.append(line + SEP);
			}
			// ファイルを閉じる
			reader.close();
			// ガベージコレクションがいつ動くかわからないので
			// 先にメモリの開放
			reader = null;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		// 読み込んだタイトル画面の表示
		System.out.println(build.toString());
		boolean isOK = false;
		while (true) {
			String input = acceptInput();
			int in = 0;
			if (CheckerUtils.isCommandInput(input,"[1-2]")) {
				in = Integer.parseInt(input);
			}
			if (RpgConst.INIT_START.getStatus() == (in - 1)) {
				scene = sceneList.get(0);
//				scene = new CreatePlayerScene("0", "Z");
//				// プレーヤー生成の次は、シーンインデックス = 1に飛ぶ
//				scene.setNextIndex("1");
				break;
			} else if (RpgConst.INIT_CONTINUE.getStatus() == (in - 2)) {
				// TODO-[今後実装する]
				System.out.println("未実装です。");
			} else {
				System.out.println(MessageConst.ERR_INPUT.toString() + "1: start 2: continue");
			}
		}
	}

	@Override
	public RpgConst getEndStatus() {
		return status;
	}

	/**
	 * Ver0.5: バトルシーンの実装
	 * Ver0.6: シーンの実装
	 * 1. RpgScene#playScene()を実行。
	 * 2. シーンに設定されている、skipNextMessageをLogicクラスに設定する。
	 * 　　※これで、処理に反映される。
	 * 3. 次のシーンインデックスが終了ステータスの場合ゲーム終了する。
	 */
	@Override
	public boolean executeScene() throws Exception {
		if (isDebug) System.out.println(MessageConst.ON_SCENE.toString());
		scene.playScene();
		skipNextMessage = scene.getSkipNextMessage();
		String next = scene.nextIndex;

		if (isDebug) System.out.println("current Idx: " + scene.sceneIndex + " sceneType: " + scene.sceneType + " next sceneIndex: " + next);

		if (next == null) {
			throw new RpgException("次のシーン指定が不適切です。: ");
		}
		if (RpgConst.CLEAR.getType().equals(next)) {
			status = RpgConst.CLEAR;
			return true;
		} else if (RpgConst.GAME_OVER.getType().equals(next)) {
			status = RpgConst.GAME_OVER;
			return true;
		} else if (RpgConst.SAVE.getType().equals(next)) {
			status = RpgConst.SAVE;
			return true;
		}

		// 次のシーンをセットする
		setNextScene(next);
		return false;
	}
}
