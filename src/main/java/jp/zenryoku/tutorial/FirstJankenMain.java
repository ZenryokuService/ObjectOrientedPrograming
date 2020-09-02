package jp.zenryoku.tutorial;

import java.util.Map;

/**
 * じゃんけんゲームメインクラス。
 * オブジェクト指向プログラミングでないパターンの実装
 *
 * @author 実装者の名前
 *
 */
public class FirstJankenMain {
	/** 勝敗判定フラグ：勝ち(ユーザー) */
	private static final int YOU_WIN = 0;
	/** 勝敗判定フラグ：負け(ユーザー) */
	private static final int YOU_LOOSE = 1;
	/** 勝敗判定フラグ：あいこ(ユーザー) */
	private static final int AIKO = 2;

	/** 勝敗判定MAP **/
	private Map<String, Boolean> judgeMap;

	/**
	 * メインメソッド
	 * @param args プログラム引数
	 */
	public static void main(String[] args) {
		// 0.じゃんけんゲーム起動
		FirstJankenMain main = new FirstJankenMain();
		// 1.勝敗判定MAP作成
		main.createJudgeMap();

		// 無限ループ
		while(true) {
			// 2.「じゃんけん」or「あいこで」のメッセージ表示
			main.printJankenAiko(true);
			// 3.ユーザーの入力(待ち)
			String input = main.acceptInput();
			// 4.「ポン！」or「しょ！」を表示
			main.printSho();
			// 5.勝敗判定
			int judge = main.judgeWinLoose(input, "0");
			// 6.勝敗判定の表示
			if (main.printJudge(judge)) {
				break;
			}
		}
		// 7.じゃんけんゲーム終了
	}

	/**
	 * 1.勝敗判定MAP作成
	 */
	private void createJudgeMap() {
		// 勝敗判定MAPのインスタンスを生成する
	}

	/**
	 * 2.「じゃんけん」or「あいこで」のメッセージ表示
	 *
	 * @param isJanken true: 「じゃんけん」false: 「あいこ」
	 */
	private void printJankenAiko(boolean isJanken) {
		// isJankenがtrueの時は「じゃんけん」を表示する
	}

	/**
	 * 3.ユーザーの入力(待ち)
	 *
	 * @return 0: グー, 1: チョキ 2: パー
	 */
	private String acceptInput() {
		// System.in = 標準入力
		return null;
	}

	/**
	 * 4.「しょ！」を表示
	 */
	private void printSho() {
	}

	/**
	 * 5.勝敗判定
	 * @param playerTe プレーヤーの手
	 * @param cpuTe CPUの手
	 * @return 勝敗判定 true: プレーヤーの勝ち false: プレーヤーの負け
	 */
	private int judgeWinLoose(String playerTe, String cpuTe) {
		// 勝敗判定MAPのキーはプレーヤーの手とCPUの手を連結したもの
		// 例：「01」＝ プレーヤー「グー」、CPU「チョキ」
		return YOU_WIN;
	}

	/**
	 * 6.勝敗判定の表示
	 *
	 * @param resultJudge 判定結果
	 * @return true: 終了 false: もう一度
	 */
	private boolean printJudge(int resultJudge) {
		// 勝敗判定結果を表示する
		return true;
	}
}
