package jp.zenryoku.mains;

/**
 * JavaDocです。FOR文のサンプル処理を作成します。
 * 配列の作成およびリストの作成も行います。
 */
public class SampleForLoop {
    public static void main(String[] args) {
        // int型の配列
        int[] numArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

        for (int i = 0; i < numArray.length; i++) {
            System.out.println("配列の" + i + "番目：" + numArray[i]);
        }
        /**
         * 上記の変数「i」はfor文の中でしかうごかないので
         * 下の変数「i」1はエラーにならない
         */
        System.out.println("**** 拡張ＦＯＲ文 ****");
        // 拡張FOR文を使用する場合
        int i = 0;
        for (int num : numArray) {
            System.out.println("配列の" + i + "番目：" + numArray[i]);
            i++;
        }
    }
}
