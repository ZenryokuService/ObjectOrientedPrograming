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
	 * 更新したデータを表示する。
	 * 戦闘終了時にはTRUEを返却する。
	 */
	@Override
	public boolean render() {
		boolean endScene = executeScene();
		status = RpgConst.CLEAR;
		return endScene;
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
	 * Ver0.5: バトルシーンの実装
	 * Ver0.6: シーンの実装
	 */
	@Override
	protected boolean executeScene() {
		return scene.playScene();
	}
}
