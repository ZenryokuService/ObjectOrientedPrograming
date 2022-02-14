package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.data.ParamGenerator;
import jp.zenryoku.rpg.data.RpgItemType;
import jp.zenryoku.rpg.exception.RpgException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

/**
 * 計算ユーティリティクラスのテスト
 */
public class CalcUtilsTest {
    private static CalcUtils target;

    @BeforeAll
    public static void init() {
        target = CalcUtils.getInstance();
    }

    @Test
    public void testThrowDice() {
        try {
            int res = target.throwDice("1D6");
            assertTrue(res > 0 || res < 7);

            int res1 = target.throwDice("2D6");
            assertTrue(res > 0 || res < 13);

            int res2 = target.throwDice("3D6");
            assertTrue(res > 0 || res < 19);

            int res3 = target.throwDice("1D10");
            assertTrue(res > 0 || res < 11);

            int res4 = target.throwDice("2D10");
            assertTrue(res > 0 || res < 21);

            int res5 = target.throwDice("3D10");
            assertTrue(res > 0 || res < 7);

            int res6 = target.throwDice("1D8");
            assertTrue(res > 0 || res < 9);

            int res7 = target.throwDice("2D8");
            assertTrue(res > 0 || res < 17);

            int res8 = target.throwDice("3D8");
            assertTrue(res > 0 || res < 33);
        } catch (Exception e) {
            fail("想定外のエラーです。" + e.getMessage());
        }
    }

    @Test
    public void testThrowDiceError() {
        try {
            target.throwDice("3C20");
            fail("エラーが出る想定");
        } catch (RpgException e) {

        }
    }

    //@Test
    public void testCalcRpgData() {
        BufferedReader buf = null;
        try {
//            PlayerCharactor player = new PlayerCharactor("test");
//            player.setMa
            buf = Files.newBufferedReader(Paths.get("src/test/resources", "testParamGeneratorItem.txt"));
            buf.readLine();
            ParamGenerator.getInstance().createItemTypeMap(buf);
//            target.calcRpgData();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

}
