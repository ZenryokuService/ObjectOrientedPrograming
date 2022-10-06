package jp.zenryoku.study.designpattern;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * SingletonPracクラスのテストを行う。
 *
 * @see jp.zenryoku.study.designpattern.SingletonPrac
 */
public class SingletonPracTest {
    /** テスト対象クラス */
    private static SingletonPrac target;

    /**
     * テスト実行準備
     */
    @BeforeAll
    public static void init() {
        target = SingletonPrac.getInstance();
    }

    /**
     * SingletonPracのプロパティを表示するメソッド
     * @param prac SingletonPrac
     */
    private void printProperties(SingletonPrac prac) {
        System.out.println("名前：" + prac.getName());
        System.out.println("年齢：" + prac.getAge());
        System.out.println("血液型：" + prac.getBloodType());
    }

    /**
     * SingletonPracのプロパティを表示するメソッド
     * @param prac SingletonPrac
     */
    private void printProperties(String prefix, SingletonPrac prac) {
        System.out.println(prefix);
        System.out.println("名前：" + prac.getName());
        System.out.println("年齢：" + prac.getAge());
        System.out.println("血液型：" + prac.getBloodType());
    }

    @Test
    public void testSingleton() {
        // デフォルトの値を表示、すでにインスタンスを生成している
        printProperties("1回目", target);
        // プロパティの値を変更する。
        target.setName("test1");
        target.setAge(3);
        target.setBloodType('B');

        // 別の変数を使用して、明示的に別インスタンスにも対応する
        SingletonPrac ppp = SingletonPrac.getInstance();
        printProperties("2回目", ppp);
    }

    @Test
    public void testNoSingleton() {
        // デフォルトの値を表示、すでにインスタンスを生成している
        printProperties("No Singleton 1回目", target);
        // プロパティの値を変更する。
        target.setName("test1");
        target.setAge(3);
        target.setBloodType('B');

        // 別の変数を使用して、明示的に別インスタンスにも対応する
        SingletonPrac ppp = new SingletonPrac("tarotaro", 2, 'B');
        printProperties("No Singleton 2回目", ppp);
    }
}
