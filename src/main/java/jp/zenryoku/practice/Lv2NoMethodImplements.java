package jp.zenryoku.practice;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Lv2NoMethodImplements {
	public static void main(String[] args) {
		/* 勝敗判定フラグ：勝ち(ユーザー) */
		final int YOU_WIN = 0;
		/* 勝敗判定フラグ：負け(ユーザー) */
		final int YOU_LOOSE = 1;
		/* 勝敗判定フラグ：あいこ(ユーザー) */
		final int AIKO = 2;
		/* グー */
		final String GU = "0";
		/* チョキ */
		final String CHOKI = "1";
		/* パー */
		final String PA = "2";

		// 勝敗判定マップを作成
		Map<String, Integer> hanteiMap = new HashMap<String, Integer>();
		// プレーヤーの勝ちケース
		hanteiMap.put(GU + CHOKI, YOU_WIN);
		hanteiMap.put(CHOKI + PA, YOU_WIN);
		hanteiMap.put(PA + GU, YOU_WIN);
		// プレーヤーの負けケース
		hanteiMap.put(GU + PA, YOU_LOOSE);
		hanteiMap.put(CHOKI + GU, YOU_LOOSE);
		hanteiMap.put(PA + CHOKI, YOU_LOOSE);
		// あいこのケース
		hanteiMap.put(GU + GU, AIKO);
		hanteiMap.put(CHOKI + CHOKI, AIKO);
		hanteiMap.put(PA + PA, AIKO);

		// 手のマップ
		Map<String, String> teMap = new HashMap<String, String>();
		teMap.put(GU, "グー");
		teMap.put(CHOKI, "チョキ");
		teMap.put(PA, "パー");

		// CPUの手を生成するJavaAPI
		Random random = new Random();
		// じゃんけん愛顧のフラグ
		boolean isJanken = true;

		// 無限ループ
		while(true) {
			// 「じゃんけん」または「あいこ」
			if (isJanken) {
				System.out.println("じゃんけん ...");
			} else {
				System.out.println("あいこで ...");
			}
			// ユーザーの入力を受ける
			Scanner scan = new Scanner(System.in);
			String input = scan.nextLine();

			// CPUの手を取得する(JavaSEのAPIを使用するのでテストしない)
			String cpuTe = String.valueOf(random.nextInt(2));

			// 4.「ポン！」or「しょ！」を表示
			if (isJanken) {
				System.out.println("ポン！");
			} else {
				System.out.println("しょ！");
			}

			// 勝敗判定用のキーを取得
			String key = input + cpuTe;
			int result = hanteiMap.get(key);

			//
			boolean isFinish = true;
			// 勝敗判定結果を表示する
			switch(result) {
			case YOU_WIN:
				System.out.println("YOU WIN!");
				isFinish = true;
				isJanken = true;
				break;
			case YOU_LOOSE:
				System.out.println("YOU LOOSE!");
				isFinish = true;
				isJanken = true;
				break;
			case AIKO:
				isFinish = false;
				isJanken = false;
				System.out.println("DRAW!");
				break;
			}
			if (isFinish) {
				break;
			}
		}
	}

}
