package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.TestUtils;
import jp.zenryoku.rpg.charactors.Player;
import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.data.ParamGenerator;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.status.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

    @Test
    public void testRelatedSymbol() {
        try {
            PlayerCharactor player = TestUtils.initRpgConfig();
            List<RpgStatus> list = target.relatedSymbols("(POW + WEV) * (1 + (0.1 * JLV))"
                    , player.getStatusMap(), player.getOptionalMap());
            assertEquals(3, list.size());
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testCalcEffect() {
        PlayerCharactor player = TestUtils.initRpgConfig();
        PlayerParty party = RpgConfig.getInstance().getParty();
        party.setMoney(0);
        player.setHP(15);
        player.setMP(5);
        Map<String, RpgStatus> map = player.getStatusMap();
        map.get("POW").setValue(5);
        map.get("INT").setValue(6);
        map.get("AGI").setValue(7);
        map.get("DEX").setValue(8);
        map.get("KSM").setValue(9);
        party.setPlayer(player);
        try {
            // マスタカテゴリ対象の項目(通貨はパラメータマップで拡張する)
            target.calcEffect("NIG", "+", 100);
            assertEquals(100, party.getMoney());
            target.calcEffect("ZHP", "+", 10);
            assertEquals(25, player.getHP());
            target.calcEffect("ZMP", "+", 10);
            assertEquals(15, player.getMP());
            // ステータスマップの項目
            target.calcEffect("POW", "+", 3);
            assertEquals(8, player.getStatusMap().get("POW").getValue());
            target.calcEffect("INT", "+", 2);
            assertEquals(8, player.getStatusMap().get("INT").getValue());
            target.calcEffect("AGI", "+", 1);
            assertEquals(8, player.getStatusMap().get("AGI").getValue());
            target.calcEffect("DEX", "+", 0);
            assertEquals(8, player.getStatusMap().get("DEX").getValue());
            target.calcEffect("KSM", "+", -1);
            assertEquals(8, player.getStatusMap().get("KSM").getValue());
            // ステータス異常(ZHP-10TS4)

            //target.calcEffect("POI", "+", 1, "");


        } catch (RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetTargetField() {
        TestUtils.initRpgConfig();
        PlayerParty party = PlayerParty.getInstance();
        party.setMoney(100);

        try {
            PlayerCharactor player = new PlayerCharactor("test");
            party.setPlayer(player);
            player.setHP(10);
            player.setMP(15);
            RpgConfig conf = RpgConfig.getInstance();
            player.setStatusMap(conf.getStatusMap());
            player.setOptionalMap(conf.getOptionStatusMap());

            List<Class<?>> inte = new ArrayList<>();
            Field hp = target.findTargetField("HP", inte);
            assertEquals("Player", inte.get(0).getSimpleName());
            assertEquals(10, hp.get(player));
            hp.set(player, 5);
            assertEquals(5, hp.get(player));

            inte.clear();
            Field mp = target.findTargetField("MP", inte);
            assertEquals("Player", inte.get(0).getSimpleName());
            assertEquals(15, mp.get(player));
            mp.set(player, 10);
            assertEquals(10, mp.get(player));

            inte.clear();
            Field statusMap = target.findTargetField("statusMap", inte);
            assertEquals("PlayerCharactor", inte.get(0).getSimpleName());
            assertEquals(1, ((Map<String, RpgStatus>)statusMap.get(player)).get("POW").getValue());

            inte.clear();
            Field mon = target.findTargetField("money", inte);
            assertEquals("PlayerParty", inte.get(0).getSimpleName());
            assertEquals(100, mon.get(player));
            mon.set(party, 110);
            assertEquals(110, mon.get(player));


        } catch (RpgException | IllegalAccessException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    //@Test
    public void testTestMuch() {
        assertEquals(100, target.testMutch("3:A", "0:A"));
    }

    @Test
    public void testCalcEndSceneProbablity() {
        assertEquals(100.0, target.calcEndSceneProbablity("END_SCENE 1"));
        assertEquals(100.0, target.calcEndSceneProbablity("END_SCENE 10"));
        assertEquals(100.0, target.calcEndSceneProbablity("END_SCENE 999"));
        assertEquals(100.0, target.calcEndSceneProbablity("END_SCENE C"));
        assertFalse(target.calcEndSceneProbablity("END_SCENE a1") == 100.0);
        assertFalse(target.calcEndSceneProbablity("END_SCWNE 1") == 100.0);
        assertFalse(target.calcEndSceneProbablity("END_SCEN 1") == 100.0);
        assertFalse(target.calcEndSceneProbablity("END_SCWNE1") == 100.0);
    }
}
