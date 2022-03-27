package jp.zenryoku.tutorial.calsses;

/**
 * コンソール表示のユーティリティクラス
 * 判定結果を受けそれに対する表示、初期表示など、コンソール出力処理を実装する
 * 各メソッドを静的メソッドに変更する※インスタンス変数などを使用しないため問題なし
 *
 * @author 実装者の名前
 */
public class JankenConsoleUtils {

	/**
	 * 2.「じゃんけん」or「あいこで」のメッセージ表示
	 *
	 * @param isJanken true: 「じゃんけん」false: 「あいこ」
	 */
	public static void printJankenAiko(boolean isJanken) {
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
	 * 4.「しょ！」を表示
	 */
	public void printSho() {
		System.out.println("Sho!");
	}

// 追加実装部分
	/**
	 * 4.「ポン！」or「しょ！」を表示
	 */
	public static void printPonOrSho(boolean isJanken) {
		if (isJanken) {
			// 「じゃんけん」の場合は「ポン！」
			System.out.println("ポン！");
		} else {
			// 「あいこで」の場合は「しょ！」
			System.out.println("しょ！");
		}
	}

	/**
	 * 6.勝敗判定の表示
	 *
	 * @param resultJudge 判定結果
	 * @return true: 終了 false: もう一度
	 */
	public static boolean printJudge(JankenConst resultJudge) /* 追加実装:*/ throws Exception {
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
		// 追加実装
		default:
			throw new Exception("想定外の勝敗判定です：" + resultJudge);
		}
		return isFinish;
	}

	/**
	 * ＜追加実装＞
	 * じゃんけんの手として適当な値であるか判定する。
	 *
	 * @param input ユーザー入力
	 * @return true; じゃんけんの手として適当な値 / false: じゃんけんの手として不適当な値
	 */
	public static boolean inputCheck(String input) {
		// 判定フラグ
		boolean isJankenTe = false;
		// 正規表現で判定する
		if (input.matches("[0-2]")) {
			isJankenTe = true;
		}
		return isJankenTe;
	}
}
