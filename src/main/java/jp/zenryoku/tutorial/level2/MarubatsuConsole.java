package jp.zenryoku.tutorial.level2;

import java.util.Scanner;

public class MarubatsuConsole {
    /**
     * 〇×あてゲームの開始文言を表示
     */
    public static void printGameStart() {
        // 表示内容をちょっとおしゃれにする。
        System.out.println("******************************");
        System.out.println("*「〇×あてゲーム」 0: 〇 1: ×。 *");
        System.out.println("******************************");
        System.out.println("※0は「〇」を表し1は「×」を表します。");
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
     * @param input 入力した値
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
