package jp.zenryoku.tutorial.level2;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.matchers.JUnitMatchers.either;

public class MarubatsuUtilsTest {
    /** テスト対象クラス(全てstaticメソッドなのでインスタンス不要) */
    private static MarubatsuUtils target;

    @Test
    public void testGetScanner() {
        Scanner scan = MarubatsuUtils.getScanner();
        // インスタンスが取得できていることを確認
        assertNotNull(scan);
    }
    @Test
    public void testNextInt() {
        int res = MarubatsuUtils.nextInt(2);
        // インスタンスが取得できていることを確認
        boolean isRondom = res == 0 || res == 1;
        assertTrue(isRondom);
    }

    @Test
    public void testIsAtari() {
        assertTrue(MarubatsuUtils.judgeAtariOrNot(0, 0));
        assertTrue(MarubatsuUtils.judgeAtariOrNot(1, 1));

        assertFalse(MarubatsuUtils.judgeAtariOrNot(0, 1));
        assertFalse(MarubatsuUtils.judgeAtariOrNot(1, 0));
    }
}
