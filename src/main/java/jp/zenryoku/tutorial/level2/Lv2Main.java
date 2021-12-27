package jp.zenryoku.tutorial.level2;

import java.util.Scanner;

/**
 * Javaの基本レベル２：小さなレベルのプログラムを拡張する。
 * 今回は、〇か×か当てるゲーム。
 */
public class Lv2Main {
    /** 終了フラグ */
    private static boolean isFinish;

    public static void main(String[] arg) {
        isFinish = false;
        // 標準入力を受け取るクラスをインスタンス化
        Scanner scan = MarubatsuUtils.getScanner();

        while (true) {
            // ゲーム開始文言
            MarubatsuConsole.printGameStart();
            // 標準入力を受け取る
            int input = scan.nextInt();
            // isFinishがtrueならば処理終了。
            if (isFinish) {
                MarubatsuConsole.printTerminated();
                break;
            }
            // 0か1の値を返却する
            int res = MarubatsuUtils.nextInt(2);

            // 0: 〇 1: ×で当たったかどうかの判定
            boolean isAtari = MarubatsuUtils.judgeAtariOrNot(res, input);
            MarubatsuConsole.printAtatiorNot(isAtari, input);

            // 続けるのかどうか判定する
            if (MarubatsuConsole.printNextPlayOrNot(scan)) {
                break;
            }
        }
    }
}
