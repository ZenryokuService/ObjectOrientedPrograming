package jp.zenryoku.rpg;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import jp.zenryoku.RpgLogic;
import jp.zenryoku.rpg.charactors.Player;
import jp.zenryoku.rpg.charactors.monsters.Monster;
import jp.zenryoku.rpg.constants.RpgConst;
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
	 * 初期表示を行う。
	 * 1. タイトル表示
	 * 2. NewGameかContinue(未実装)
	 * 3. 選択された方のシーンを表示
	 *
	 */
	@Override
	public void init(String title) {
		StringBuilder build = new StringBuilder();
		String line = null;
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
	}



	/**
	 * データの更新処理。
	 * @input String 選択したコマンド番号
	 * @return true: 次の処理 false: もう一度
	 */
	@Override
	public boolean updateData(String input) {
		// 入力チェック
		if (CheckerUtils.isCommandInput(input, "[0-2]")) {
			executeScene();
		}

		return true;
	}

	/**
	 * 更新したデータを表示する。
	 * 戦闘終了時にはTRUEを返却する。
	 */
	@Override
	public boolean render() {

		return true;
	}

	/**
	 * 選択したシーンを呼び出す処理。
	 */
	private void action(Player player, Monster monster, String input) {
	}

	@Override
	public RpgConst getEndStatus() {
		return status;
	}

	/**
	 * バトルシーンの実装
	 */
	@Override
	protected void executeScene() {
		scene.playScene();
	}
}
