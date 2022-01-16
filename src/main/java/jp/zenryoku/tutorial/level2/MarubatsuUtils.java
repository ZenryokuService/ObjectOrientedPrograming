package jp.zenryoku.tutorial.level2;

import java.util.Random;
import java.util.Scanner;

public class MarubatsuUtils {
    /** 標準入力を受け取るクラス */
    private static Scanner scan;
    /** 乱数の生成クラス */
    private static Random rdm;

    /**
     * 標準入力を受け取るクラスを生成、取得する。
     * ※シングルトン実装
     * @return Scanner
     */
    public static Scanner getScanner() {
        if (scan == null) {
            scan = new Scanner(System.in);
        }
        return scan;
    }

    /**
     * 乱数生成クラスがインスタンス化されていなければ、インスタンス化します。
     * すでにインスタンス化しているときは、既存のインスタンスを使用します。
     * ※シングルトン実装
     *
     * @param bound
     * @return 生成した乱数
     * @see <a href="https://docs.oracle.com/javase/jp/8/docs/api/java/util/Random.html">Random</a>
     */
    public static int nextInt(int bound) {
        if (rdm == null) {
             rdm = new Random();
        }
        return rdm.nextInt(bound);
    }

    /**
     * 生成した乱数と、入力値が等しいか判定する。
     *
     * @param res 生成した乱数
     * @param input 入力値
     * @return true: 等しい　false: 違う
     */
    public static boolean judgeAtariOrNot(int res, int input) {
        // 0か1で「〇」「×」の判断をしている
        String result = res == 0 ? "〇" : "×";
        // 生成した乱数を表示する
        System.out.println("「〇」「×」の結果: " + result);
        // 0: 〇 1: ×で当たったかどうかの判定
        return res == input;
    }

}
