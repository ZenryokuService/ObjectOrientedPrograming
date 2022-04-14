package jp.zenryoku.rpg;

import jp.zenryoku.rpg.bat.BatExcuter;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameMain {
	private static final boolean isDebug = false;
	/**
	 * ゲームを起動するクラス。メインメソッドがある。
	 * 今後、DBなど使用するときにはここでDBを起動する。
	 * ※Derbyを使用する予定
	 *
	 * @param args プログラム引数
	 */
	public static void main(String[] args) {
		if (isDebug) System.out.println("Param: " + args[0]);
		RpgLogic gameLogic = null;
		try {
			if (args.length != 0) {
				BatExcuter.executeChecker(args[0]);
				gameLogic = new TextRpgLogic(args[0]);
			} else {
				BatExcuter.executeChecker(args[0]);
				gameLogic = new TextRpgLogic();
			}
			TextRpgGameEngine engine  = new TextRpgGameEngine(gameLogic);

			engine.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("想定外のエラーで終了します。：" + e.getMessage());
			System.exit(-1);
		}
	}
 }
