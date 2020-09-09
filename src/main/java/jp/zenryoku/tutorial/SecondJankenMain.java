package jp.zenryoku.tutorial;

import java.util.Random;

import jp.zenryoku.tutorial.calsses.ConsoleUtils;
import jp.zenryoku.tutorial.calsses.JankenConst;
import jp.zenryoku.tutorial.calsses.JankenUtils;

/**
 * じゃんけんゲームメインクラス。
 * オブジェクト指向プログラミングのパターンの実装
 *
 * @author 実装者の名前
 *
 */
public class SecondJankenMain {
	/** じゃんけんゲームのユーティリティクラス */
	private JankenUtils util;
	/** コンソール表示のユーティリティクラス */
	private ConsoleUtils console;

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// 0.じゃんけんゲーム起動
		SecondJankenMain main = new SecondJankenMain();
		// 1.勝敗判定MAP作成 -> JannkenUtilsで行っているので処理なし

		// CPUの手を生成する部品(JavaSE APIを使用) ※「JavaSE API」という言い方はあまりしない。。。
		Random random = new Random();

//		// 追加実装: 「じゃんけん」と「あいこ」の判定が行えてない
//		boolean isJanken= true;
		// 無限ループ
		while(true) {
			// 2.「じゃんけん」or「あいこで」のメッセージ表示
			main.printJankenAiko();
			// 3.ユーザーの入力(待ち)
			String input = main.acceptInput();
//			// TODO-[追加実装する]
//			if (main.inputCheck(input) == false) {
//				System.out.println("0-2の値を入力してください。");
//				continue;
//			}
//			// TODO-[追加実装する]
//			// CPUの手を取得する(JavaSEのAPIを使用するのでテストしない)
//			String cpuTe = String.valueOf(random.nextInt(2));
			// 4.「ポン！」or「しょ！」を表示
//			main.printPonOrSho(true);
			main.printSho();
//			// <追加実装>
//			main.printTe(input, cpuTe);
			// 5.勝敗判定
			JankenConst judge = main.judgeWinLoose(input, "0");
			// 6.勝敗判定の表示
			main.printJudge(judge);
//			if (main.printJudge(judge)) {
//				// 追加実装：「じゃんけん」と「あいこ」の判定が行えてない
//				isJanken = true;
//				break;
//			} else {
//				// 追加実装：「じゃんけん」と「あいこ」の判定が行えてない
//				isJanken = false;
//			}
		}
		// 7.じゃんけんゲーム終了
	}

	/**
	 * フィールド変数にインスタンスを生成して設定する。
	 */
	public SecondJankenMain() {
		util = new JankenUtils();
		console = new ConsoleUtils();
	}

	/**
	 * 「じゃんけん」、「あいこを表示する」
	 */
	private void printJankenAiko() {
		// TODO-[追加実装: で修正した部分だが、設計時には判定フラグを引数に持っていた。]
		console.printJankenAiko(true);
	}

	/**
	 * 入力受付。
	 *
	 * @return 入力した値
	 */
	private String acceptInput() {
		return util.acceptInput();
	}

	/**
	 * 「しょ！」を表示する
	 */
	private void printSho() {
		// TODO-[追加実装の時に引数を追加、「ポン！」のケースも追加する]
		System.out.println("Sho!");
	}

	/**
	 * 勝敗判定を取得する
	 *
	 * @param playerTe プレーヤーの手
	 * @param cpuTe CPUの手
	 * @return 勝敗結果(JankenConstで定義)
	 * @see jp.zenryoku.class.JankenConst
	 */
	private JankenConst judgeWinLoose(String playerTe, String cpuTe) {
		return util.judgeWinLoose(playerTe, cpuTe);
	}

	/**
	 * 勝敗判定を表示し、繰り返すかどうかを返却する。
	 *
	 * @param judge 照会結果照会結果
	 * @return boolean true: 終了する / false 繰り返す
	 */
	private boolean printJudge(JankenConst judge) {
		return console.printJudge(judge);
	}
 }
