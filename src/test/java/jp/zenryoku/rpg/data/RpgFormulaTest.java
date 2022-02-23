package jp.zenryoku.rpg.data;

import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.data.status.RpgFormula;
import jp.zenryoku.rpg.data.status.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RpgFormulaTest {
    private static RpgFormula target;
    private static PlayerCharactor player;
    @BeforeAll
    public static void init() throws RpgException {
        Map<String, RpgData> map = new HashMap<>();
        List<RpgData> list = new ArrayList<>();
        // ちから
        RpgData pow = new RpgData();
        pow.setKigo("POW");
        pow.setValue(1);
        list.add(pow);
        map.put(pow.getKigo(), pow);

        // 武器攻撃力
        RpgData wev = new RpgData();
        wev.setKigo("WEV");
        wev.setValue(2);
        list.add(wev);
        map.put(wev.getKigo(), wev);

        // 防具防御撃力
        RpgData arv = new RpgData();
        arv.setKigo("ARV");
        arv.setValue(3);
        list.add(arv);
        map.put(arv.getKigo(), arv);

        RpgData agi = new RpgData();
        agi.setKigo("AGI");
        agi.setValue(4);
        list.add(agi);
        map.put(agi.getKigo(), agi);

        RpgData atk = new RpgData();
        atk.setKigo("ATK");
        atk.setValue(5);
        list.add(atk);
        map.put(atk.getKigo(), atk);

        RpgData def = new RpgData();
        def.setKigo("DEF");
        def.setValue(6);
        list.add(def);
        map.put(def.getKigo(), def);

        RpgData itm = new RpgData();
        itm.setKigo("ITM");
        itm.setValue(7);
        list.add(itm);
        map.put(itm.getKigo(), itm);

        RpgData ksm = new RpgData();
        ksm.setKigo("KSM");
        ksm.setValue(8);
        list.add(ksm);
        map.put(ksm.getKigo(), ksm);

        RpgData dex = new RpgData();
        dex.setKigo("DEX");
        dex.setValue(9);
        list.add(dex);
        map.put(dex.getKigo(), dex);

        RpgData bpk = new RpgData();
        bpk.setKigo("BPK");
        bpk.setValue(10);
        list.add(bpk);
        map.put(bpk.getKigo(), bpk);

        RpgData inta = new RpgData();
        inta.setKigo("INT");
        inta.setValue(11);
        list.add(inta);
        map.put(inta.getKigo(), inta);

        RpgData jvl = new RpgData();
        jvl.setKigo("JLV");
        jvl.setValue(12);
        list.add(jvl);
        map.put(jvl.getKigo(), jvl);

        RpgData itv = new RpgData();
        itv.setKigo("ITV");
        itv.setValue(13);
        list.add(itv);
        map.put(itv.getKigo(), itv);

        RpgData mpw = new RpgData();
        mpw.setKigo("MPW");
        mpw.setValue(14);
        list.add(mpw);
        map.put(mpw.getKigo(), mpw);

        RpgData tsm = new RpgData();
        tsm.setKigo("TSM");
        tsm.setValue(15);
        list.add(tsm);
        map.put(tsm.getKigo(), tsm);

        Map<String, RpgStatus> statusMap = new HashMap<>();
        Set<String> set = map.keySet();
        for (RpgData data  : list) {
            RpgStatus status = new RpgStatus();
            status.setKigo(data.getKigo());
            status.setValue(data.getValue());
        }
        RpgConfig conf = RpgConfig.getInstance();
        conf.setParamMap(map);
        conf.setStatusMap(statusMap);
        player = new PlayerCharactor("test");
        player.setStatusMap(statusMap);

    }

    @Test
    public void testFormula() {
        target = new RpgFormula("(POW + WEV) * (1 + (0.1 * JLV))");
        int res = target.formula(player);
        assertEquals(7, res);
        target = new RpgFormula("(POW + AGI+DEX) / 3 + ARV");
        int res1 = target.formula(player);
        assertEquals(8, res1);
        target = new RpgFormula(" (INT + DEX+KSM) / 3  + MPW");
        int res2 = target.formula(player);
        assertEquals(23, res2);
        target = new RpgFormula("(INT + DEX + AGI) / 3 + TSM ");
        int res3 = target.formula(player);
        assertEquals(23, res3);
        target = new RpgFormula("(((INT + DEX) / 2 ) * 0.1) * ITV");
        int res4 = target.formula(player);
        assertEquals(13, res4);
        target = new RpgFormula("( 1 + KSM * 0.1 )");
        int res5 = target.formula(player);
        assertEquals(2, res5);
        target = new RpgFormula("(AGI + INT + DEX) / 100");
        double res6 = target.formula(player, true);
        assertEquals(0.24, res6);
        target = new RpgFormula("(POW + DEX) / 2 + BPK");
        int res7 = target.formula(player);
        assertEquals(15, res7);
    }
}
