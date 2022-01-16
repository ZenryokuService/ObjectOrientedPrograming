package jp.zenryoku.tutorial.level2;

import org.junit.jupiter.api.Test;

public class MarubatsuConsoleTest {
    @Test
    public void testPrintGameStart() {
        // テストを実行するのに直接メソッドを呼び出します。
        MarubatsuConsole.printGameStart();
    }

    @Test
    public void testPrintAtatiorNot() {
        // 入力=0(〇), 判定の結果「あたり」※目視確認します。
        MarubatsuConsole.printAtatiorNot(true, 0);
        // 入力=0(〇), 判定の結果「はずれ」※目視確認します。
        MarubatsuConsole.printAtatiorNot(false, 0);
        // 入力=1(×), 判定の結果「あたり」※目視確認します。
        MarubatsuConsole.printAtatiorNot(true, 1);
        // 入力=1(×), 判定の結果「はずれ」※目視確認します。
        MarubatsuConsole.printAtatiorNot(false, 1);
    }
}
