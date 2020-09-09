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
public class SecondJankenMain extends JankenUtils {
	/** じゃんけんゲームのユーティリティクラス */
	private JankenUtils util;
//	/** コンソール表示のユーティリティクラス */
//	private ConsoleUtils console;

	/**
	 * メインメソッドの実装をオブジェクト指向プログラミングっぽく
	 * クラスの継承を使用して実装しなおしてみる。
	 * @param args
	 */
	public static void main(String[] args) {
		// 0.じゃんけんゲーム起動
		SecondJankenMain main = new SecondJankenMain();
		// 1.勝敗判定MAP作成 -> JannkenUtilsで行っているので処理なし

		// CPUの手を生成する部品(JavaSE APIを使用) ※「JavaSE API」という言い方はあまりしない。。。
		Random random = new Random();

		// 追加実装: 「じゃんけん」と「あいこ」の判定が行えてない
		boolean isJanken= true;
		// 無限ループ
		while(true) {
			// 2.「じゃんけん」or「あいこで」のメッセージ表示
			main.printJankenAiko();
			// 3.ユーザーの入力(待ち)
			String input = main.acceptInput();
			// 追加実装する(入力チェック)
			if (main.inputCheck(input) == false) {
				System.out.println("0-2の値を入力してください。");
				continue;
			}
			// 追加実装する(プレーヤーとCPUの手を表示する)
			// CPUの手を取得する(JavaSEのAPIを使用するのでテストしない)
			String cpuTe = String.valueOf(random.nextInt(2));
			// 4.「ポン！」or「しょ！」を表示
			main.printPonOrSho(true);
			//main.printSho();
			// <追加実装>
			main.printTe(input, cpuTe);
			// 5.勝敗判定
			JankenConst judge = main.judgeWinLoose(input, "0");
			// 6.勝敗判定の表示
			main.printJudge(judge);
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
	 * フィールド変数にインスタンスを生成して設定する。
	 */
	public SecondJankenMain() {
		// 親クラスのコンストラクタを起動する
		super();
//		console = new ConsoleUtils();
	}

	/**
	 * 「じゃんけん」、「あいこを表示する」
	 */
	private void printJankenAiko() {
		// 追加実装: で修正した部分だが、設計時には判定フラグを引数に持っていた。
		ConsoleUtils.printJankenAiko(true);
	}

//	/** 親クラスにて実装しているのでコメントアウト
//	 * 入力受付。
//	 *
//	 * @return 入力した値
//	 */
//	private String acceptInput() {
//		return util.acceptInput();
//	}
//
//	/**
//	 * 「しょ！」を表示する
//	 */
//	private void printSho() {
//		// TODO-[追加実装の時に引数を追加、「ポン！」のケースも追加する]
//		System.out.println("Sho!");
//	}

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

//	/** 親クラスにて実装しているのでコメントアウト
//	 * 勝敗判定を取得する
//	 *
//	 * @param playerTe プレーヤーの手
//	 * @param cpuTe CPUの手
//	 * @return 勝敗結果(JankenConstで定義)
//	 * @see jp.zenryoku.class.JankenConst
//	 */
//	private JankenConst judgeWinLoose(String playerTe, String cpuTe) {
//		return util.judgeWinLoose(playerTe, cpuTe);
//	}

	/**
	 * 勝敗判定を表示し、繰り返すかどうかを返却する。
	 *
	 * @param judge 勝敗結果
	 * @return boolean true: 終了する / false 繰り返す
	 */
	private boolean printJudge(JankenConst judge) {
		boolean isFinish = true;
		try {
			isFinish = ConsoleUtils.printJudge(judge);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			// 想定外の入力があったのでアプリを強制終了する
			System.exit(-1);
		}
		return isFinish;
	}

//	/** 親クラスにて実装しているのでコメントアウト
//	 * ＜追加実装＞
//	 * 入力チェックを行う
//	 *
//	 * @param input 入力した値
//	 * @return true: 入力OK / false 想定外の入力
//	 */
//	private boolean inputCheck(String input) {
//		return util.inputCheck(input);
//	}
//
//	/** 親クラスにて実装しているのでコメントアウト
//	 * ＜追加実装＞
//	 * プレーヤーの手とCPUの手を表示する。
//	 *
//	 * @param playerTe プレーヤーの手
//	 * @param cpuTe CPUの手
//	 */
//	private void printTe(String playerTe, String cpuTe) {
//		util.printTe(playerTe, cpuTe);
//	}
}
