package jp.zenryoku.tutorial.calsses;

import static jp.zenryoku.tutorial.calsses.JankenConst.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * じゃんけんゲームのユーティリティクラス。
 * じゃんけんをするときの勝敗判定、入力受付など処理部分を実装する
 * ※追加で入力チェックを行う
 *
 * @author 実装者の名前
 */
public class JankenUtils {
	/** 勝敗判定MAP **/
	private Map<String, JankenConst> judgeMap;
//	/** 手のMAP(追加実装) */
//	private Map<String, String> teMap;
	/** 入力受付 */
	private Scanner scan;

	/**
	 * コンストラクタ。
	 * newしたときに起動する、ここでフィールドの設定などを行うことが多い。
	 */
	public JankenUtils() {
		// 勝敗判定MAPのインスタンスを生成
		judgeMap = new HashMap<String, JankenConst>();
		createJudgeMap();
		// 入力受付クラスのインスタンスを生成
		scan = new Scanner(System.in);
	}

	/**
	 * 1.勝敗判定MAP作成
	 */
	private void createJudgeMap() {
		// 勝敗判定MAPのインスタンスを生成する
		judgeMap = new HashMap<String, JankenConst>();
		// プレーヤーの勝ちケース
		judgeMap.put(GU.toString() + CHOKI.toString(), YOU_WIN);
		judgeMap.put(CHOKI.toString() + PA.toString(), YOU_WIN);
		judgeMap.put(PA.toString() + GU.toString(), YOU_WIN);
		// プレーヤーの負けケース
		judgeMap.put(GU.toString() + PA.toString(), YOU_LOOSE);
		judgeMap.put(CHOKI.toString() + GU.toString(), YOU_LOOSE);
		judgeMap.put(PA.toString() + CHOKI.toString(), YOU_LOOSE);
		// あいこのケース
		judgeMap.put(GU.toString() + GU.toString(), AIKO);
		judgeMap.put(CHOKI.toString() + CHOKI.toString(), AIKO);
		judgeMap.put(PA.toString() + PA.toString(), AIKO);

//		// (追加実装)手のマップ
//		teMap = new HashMap<JankenConst, String>();
//		teMap.put(GU, "グー");
//		teMap.put(CHOKI, "チョキ");
//		teMap.put(PA, "パー");
	}

	/**
	 * 3.ユーザーの入力(待ち)
	 *
	 * @return 0: グー, 1: チョキ 2: パー
	 */
	public String acceptInput() {
		// System.in = 標準入力
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		return input;
	}

	/**
	 * 5.勝敗判定
	 *
	 * @param playerTe プレーヤーの手
	 * @param cpuTe CPUの手
	 * @return 勝敗判定 true: プレーヤーの勝ち false: プレーヤーの負け
	 */
	public JankenConst judgeWinLoose(String playerTe, String cpuTe) {
		// 勝敗判定MAPのキーはプレーヤーの手とCPUの手を連結したもの
		// 例：「01」＝ プレーヤー「グー」、CPU「チョキ」
		// 勝敗判定マップから勝敗判定結果を取得する。
		String key = playerTe + cpuTe;
		return judgeMap.get(key);
	}

	/**
	 * @return judgeMap
	 */
	public Map<String, JankenConst> getJudgeMap() {
		return judgeMap;
	}

	/**
	 * @param judgeMap セットする judgeMap
	 */
	public void setJudgeMap(Map<String, JankenConst> judgeMap) {
		this.judgeMap = judgeMap;
	}

//	/**
//	 * ＜追加実装＞
//	 * プレーヤーの手とCPUの手を表示する。
//	 *
//	 * @param playerTe プレーヤーの手
//	 * @param cpuTe CPUの手
//	 */
//	public void printTe(String playerTe, String cpuTe) {
//		System.out.println("ユーザー：" + teMap.get(playerTe));
//		System.out.println("CPU：" + teMap.get(cpuTe));
//	}


}
