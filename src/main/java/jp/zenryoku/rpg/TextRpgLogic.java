package jp.zenryoku.rpg;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import jp.zenryoku.rpg.charactors.Player;
import jp.zenryoku.rpg.charactors.monsters.Monster;

public class TextRpgLogic implements Games {
	/** 標準入力 */
	private Scanner scan;
	/** タイトル画面デザイン */
	private BufferedReader reader;

	/**
	 * コンストラクタ。
	 * フィールド変数のインスタンスを生成
	 */
	public TextRpgLogic() {
		scan = new Scanner(System.in);
		try {
			reader = Files.newBufferedReader(Paths.get("src/main/resources", "title.txt"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * 初期表示を行う。
	 * 1. タイトル表示
	 * 2. NewGameかContinue(未実装)
	 * 3. 選択された方のシーンを表示
	 *
	 * @see フローチャート
	 */
	@Override
	public void init(String title) {
		// 改行コード
		String SEP = System.lineSeparator();
		// title.txtの読み込み
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
	 * 入力受付処理。
	 * ※JavaAPIを呼び出すだけなので、テスト不要。
	 */
	@Override
	public String acceptInput() {
		// 入力受付を返却する(一行分)
		return scan.nextLine();
	}

	/**
	 * データの更新処理。
	 */
	@Override
	public boolean updateData(String input) {
		// 入力チェック

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
}
