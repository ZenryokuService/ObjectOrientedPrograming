package jp.zenryoku.tutorial;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

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
	/** グー */
	private static final String GU = "0";
	/** チョキ */
	private static final String CHOKI = "1";
	/** パー */
	private static final String PA = "2";

	/** 勝敗判定MAP **/
	private Map<String, Integer> judgeMap;
	/** 手のMAP(追加実装) */
	private Map<String, String> teMap;

	/**
	 * メインメソッド
	 * @param args プログラム引数
	 */
	public static void main(String[] args) {
		// 0.じゃんけんゲーム起動
		FirstJankenMain main = new FirstJankenMain();
		// 1.勝敗判定MAP作成
		main.createJudgeMap();
		Random random = new Random();

		// 追加実装: 「じゃんけん」と「あいこ」の判定が行えてない
		boolean isJanken= true;
		// 無限ループ
		while(true) {
			// 2.「じゃんけん」or「あいこで」のメッセージ表示
			main.printJankenAiko(isJanken);
			// 3.ユーザーの入力(待ち)
			String input = main.acceptInput();
			if (main.inputCheck(input) == false) {
				System.out.println("0-2の値を入力してください。");
				continue;
			}
			// CPUの手を取得する(JavaSEのAPIを使用するのでテストしない)
			String cpuTe = String.valueOf(random.nextInt(2));
			// 4.「ポン！」or「しょ！」を表示
			main.printPonOrSho(isJanken);
			// <追加>
			main.printTe(input, cpuTe);
			// 5.勝敗判定
			int judge = main.judgeWinLoose(input, cpuTe);
			// 6.勝敗判定の表示
			if (main.printJudge(judge)) {
				// 追加実装：「じゃんけん」と「あいこ」の判定が行えてない
				isJanken = true;
				break;
			} else {
				// 追加実装：「じゃんけん」と「あいこ」の判定が行えてない
				isJanken = false;
			}
		}
		// 7.じゃんけんゲーム終了
	}

	/**
	 * 1.勝敗判定MAP作成
	 */
	private void createJudgeMap() {
		// 勝敗判定MAPのインスタンスを生成する
		judgeMap = new HashMap<String, Integer>();
		// プレーヤーの勝ちケース
		judgeMap.put(GU + CHOKI, YOU_WIN);
		judgeMap.put(CHOKI + PA, YOU_WIN);
		judgeMap.put(PA + GU, YOU_WIN);
		// プレーヤーの負けケース
		judgeMap.put(GU + PA, YOU_LOOSE);
		judgeMap.put(CHOKI + GU, YOU_LOOSE);
		judgeMap.put(PA + CHOKI, YOU_LOOSE);
		// あいこのケース
		judgeMap.put(GU + GU, AIKO);
		judgeMap.put(CHOKI + CHOKI, AIKO);
		judgeMap.put(PA + PA, AIKO);

		// 手のマップ
		teMap = new HashMap<String, String>();
		teMap.put(GU, "グー");
		teMap.put(CHOKI, "チョキ");
		teMap.put(PA, "パー");
	}

	/**
	 * 2.「じゃんけん」or「あいこで」のメッセージ表示
	 *
	 * @param isJanken true: 「じゃんけん」false: 「あいこ」
	 */
	private void printJankenAiko(boolean isJanken) {
		// 追加実装、各手と入力値の票を表示する
		System.out.println("****************");
		System.out.println("*グー   = 0    *");
		System.out.println("*チョキ = 1    *");
		System.out.println("*パー   = 2    *");
		System.out.println("****************");
		// isJankenがtrueの時は「じゃんけん」を表示する
		if (isJanken) {
			System.out.println("じゃんけん ...");
		} else {
			System.out.println("あいこで ...");
		}
	}

	/**
	 * 3.ユーザーの入力(待ち)
	 *
	 * @return 0: グー, 1: チョキ 2: パー
	 */
	private String acceptInput() {
		// System.in = 標準入力
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		return input;
	}

	/**
	 * 4.「ポン！」or「しょ！」を表示
	 */
	private void printPonOrSho(boolean isJanken) {
		if (isJanken) {
			// 「じゃんけん」の場合は「ポン！」
			System.out.println("ポン！");
		} else {
			// 「あいこで」の場合は「しょ！」
			System.out.println("しょ！");
		}
	}

	/**
	 * 5.勝敗判定
	 *
	 * @param playerTe プレーヤーの手
	 * @param cpuTe CPUの手
	 * @return 勝敗判定 true: プレーヤーの勝ち false: プレーヤーの負け
	 */
	private int judgeWinLoose(String playerTe, String cpuTe) {
		// 勝敗判定MAPのキーはプレーヤーの手とCPUの手を連結したもの
		// 例：「01」＝ プレーヤー「グー」、CPU「チョキ」
		// 勝敗判定マップから勝敗判定結果を取得する。
		String key = playerTe + cpuTe;
		int result = judgeMap.get(key);
		return result;
	}

	/**
	 * 6.勝敗判定の表示
	 *
	 * @param resultJudge 判定結果
	 * @return true: 終了 false: もう一度
	 */
	private boolean printJudge(int resultJudge) {
		boolean isFinish = true;
		// 勝敗判定結果を表示する
		switch(resultJudge) {
		case YOU_WIN:
			System.out.println("YOU WIN!");
			break;
		case YOU_LOOSE:
			System.out.println("YOU LOOSE!");
			break;
		case AIKO:
			isFinish = false;
			System.out.println("DRAW!");
			break;
		}
		return isFinish;
	}

	/**
	 * ＜追加実装＞
	 * プレーヤーの手とCPUの手を表示する。
	 *
	 * @param playerTe プレーヤーの手
	 * @param cpuTe CPUの手
	 */
	private void printTe(String playerTe, String cpuTe) {
		System.out.println("ユーザー：" + teMap.get(playerTe));
		System.out.println("CPU：" + teMap.get(cpuTe));
	}

	/**
	 * ＜追加実装＞
	 * じゃんけんの手として適当な値であるか判定する。
	 *
	 * @param input ユーザー入力
	 * @return true; じゃんけんの手として適当な値 / false: じゃんけんの手として不適当な値
	 */
	private boolean inputCheck(String input) {
		// 判定フラグ
		boolean isJankenTe = false;
		// 正規表現で判定する
		if (input.matches("[0-2]")) {
			isJankenTe = true;
		}
		return isJankenTe;
	}
}
