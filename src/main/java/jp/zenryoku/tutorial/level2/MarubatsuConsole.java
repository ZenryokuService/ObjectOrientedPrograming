package jp.zenryoku.tutorial.level2;

import java.util.Scanner;

public class MarubatsuConsole {
    /**
     * 〇×あてゲームの開始文言を表示
     */
    public static void printGameStart() {
        System.out.println("「〇×あてゲーム」 0: 〇 1: ×。");
    }

    /**
     * 〇×あてゲームの終了文言を表示
     */
    public static void printTerminated() {
        System.out.println("プログラムを終了します。");
    }

    /**
     * 当たったかどうかを表示する。
     * @param isAtari 当たり判定の結果
     */
    public static void printAtatiorNot(boolean isAtari, int input) {
        String value = input == 0 ? "〇" : "×";
        if (isAtari) {
            System.out.println("あたり：" + value);
        } else {
            System.out.println("はずれ：" + value);
        }
    }

    /**
     * 〇×あてゲームを続けるかを表示、Yes or Noを取得する
     * @param scan
     * @return true: 続ける  false: やめる
     */
    public static boolean printNextPlayOrNot(Scanner scan) {
        boolean playNext = false;
        System.out.println("続けますか？ 0: 続ける  1: やめる");
        int next = scan.nextInt();
        if (next == 1) {
            playNext = true;
        }
        return playNext;
    }
}
