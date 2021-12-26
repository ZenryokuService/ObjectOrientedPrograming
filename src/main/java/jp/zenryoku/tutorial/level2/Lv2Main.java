package jp.zenryoku.tutorial.level2;

import java.util.Random;
import java.util.Scanner;

/**
 * Javaの基本レベル２：小さなレベルのプログラムを拡張する。
 * 今回は、〇か×か当てるゲーム。
 */
public class Lv2Main {
    /** 週r等フラグ */
    private static boolean isFinish;

    public static void main(String[] arg) {
        isFinish = false;
        // 標準入力を受け取るクラスをインスタンス化
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("「〇×あてゲーム」 0: 〇 1: ×。");
            // 標準入力を受け取る
            int input = scan.nextInt();
            // isFinishがtrueならば処理終了。
            if (isFinish) {
                System.out.println("プログラムを終了します。");
                break;
            }
            // 0か1の値を返却する
            Random rdm = new Random();
            int res = rdm.nextInt(1);

            // 0: 〇 1: ×で当たったかどうかの判定
            boolean isAtari = res == input;
            String value = input == 0 ? "〇" : "×";
            if (isAtari) {
                System.out.println("あたり：" + value);
            } else {
                System.out.println("はずれ：" + value);
            }
            System.out.println("続けますか？ 0: 続ける  1: やめる");
            int next = scan.nextInt();
            if (next == 1) {
                break;
            }
        }
    }
}
