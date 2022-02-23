package jp.zenryoku.rpg;

import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.data.*;
import jp.zenryoku.rpg.data.items.RpgItem;
import jp.zenryoku.rpg.data.status.RpgFormula;
import jp.zenryoku.rpg.data.status.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.item.equip.Armor;
import jp.zenryoku.rpg.item.equip.MainWepon;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestUtils {

    public static PlayerCharactor initRpgConfig() {
        Map<String, RpgData> map = new HashMap<>();
        List<RpgData> list = new ArrayList<>();
        List<RpgData> optList = new ArrayList<>();
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
        optList.add(wev);
        map.put(wev.getKigo(), wev);

        // 防具防御撃力
        RpgData arv = new RpgData();
        arv.setKigo("ARV");
        arv.setValue(3);
        optList.add(arv);
        map.put(arv.getKigo(), arv);

        // すばやさ
        RpgData agi = new RpgData();
        agi.setKigo("AGI");
        agi.setValue(4);
        list.add(agi);
        map.put(agi.getKigo(), agi);

        // 攻撃力
        RpgData atk = new RpgData();
        atk.setKigo("ATK");
        atk.setValue(5);
        optList.add(atk);
        map.put(atk.getKigo(), atk);

        // 防御力
        RpgData def = new RpgData();
        def.setKigo("DEF");
        def.setValue(6);
        optList.add(def);
        map.put(def.getKigo(), def);

        RpgData itm = new RpgData();
        itm.setKigo("ITM");
        itm.setValue(7);
        optList.add(itm);
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
        optList.add(bpk);
        map.put(bpk.getKigo(), bpk);

        RpgData inta = new RpgData();
        inta.setKigo("INT");
        inta.setValue(11);
        list.add(inta);
        map.put(inta.getKigo(), inta);

        RpgData jvl = new RpgData();
        jvl.setKigo("JLV");
        jvl.setValue(12);
        optList.add(jvl);
        map.put(jvl.getKigo(), jvl);

        RpgData itv = new RpgData();
        itv.setKigo("ITV");
        itv.setValue(13);
        list.add(itv);
        map.put(itv.getKigo(), itv);

        RpgData mpw = new RpgData();
        mpw.setKigo("MPW");
        mpw.setValue(14);
        optList.add(mpw);
        map.put(mpw.getKigo(), mpw);

        RpgData tsm = new RpgData();
        tsm.setKigo("TSM");
        tsm.setValue(15);
        optList.add(tsm);
        map.put(tsm.getKigo(), tsm);

        RpgConfig conf = RpgConfig.getInstance();
        Map<String, RpgStatus> statusMap = new HashMap<>();
        Map<String, RpgStatus> optMap = new HashMap<>();
        Set<String> set = map.keySet();
        for (RpgData data  : list) {
            RpgStatus status = new RpgStatus();
            status.setKigo(data.getKigo());
            status.setValue(data.getValue());
            statusMap.put(data.getKigo(), status);
        }
        for (RpgData data  : optList) {
            RpgStatus status = new RpgStatus();
            status.setKigo(data.getKigo());
            status.setValue(data.getValue());
            optMap.put(data.getKigo(), status);
        }
        conf.setParamMap(map);
        conf.setStatusMap(statusMap);

        // 計算マップ
        createFormulaMap();
        PlayerCharactor player = null;
        try {
            player = new PlayerCharactor("test");
            player.setStatusMap(statusMap);
            player.setOptionalMap(optMap);
        } catch (RpgException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return player;
    }

    public static Map<String, RpgFormula> createFormulaMap() {
        Map<String, RpgFormula> map = new HashMap<>();

        RpgFormula atk = new RpgFormula("(POW + WEV) * (1 + (0.1 * JLV))");
        map.put("ATK", atk);
        RpgFormula def = new RpgFormula("(POW + AGI+DEX) / 3 + ARV");
        map.put("DEF", def);
        RpgFormula mat = new RpgFormula(" (INT + DEX+KSM) / 3  + MPW");
        map.put("MAT", mat);
        RpgFormula mde = new RpgFormula("(INT + DEX + AGI) / 3 + TSM ");
        map.put("MDE", mde);
        RpgFormula itm = new RpgFormula("(((INT + DEX) / 2 ) * 0.1) * ITV");
        map.put("ITM", itm);
        RpgFormula cst = new RpgFormula("( 1 + KSM * 0.1 )");
        map.put("CST", cst);
        RpgFormula evr = new RpgFormula("(AGI + INT + DEX) / 100");
        map.put("EVR", evr);
        RpgFormula itn = new RpgFormula("(POW + DEX) / 2 + BPK");
        map.put("ITN", itn);

        RpgConfig.getInstance().setFormulaMap(map);
        return map;
    }

    /**
     * MainWeponのインスタンスを作成する、
     * @param name 武器名
     * @param valueKigo 記号と値(WEV+1)
     * @return　MainWepon
     */
    public static MainWepon createWepon(String name, String valueKigo) {
        RpgItem item = new RpgItem();
        item.setName(name);
        item.setItemValueKigo(valueKigo);
        MainWepon wepon = null;
        try {
            wepon = new MainWepon(item);
        } catch(RpgException e) {
            e.printStackTrace();
            fail("失敗");
        }
        return wepon;
    }

    /**
     * Armorのインスタンスを作成する、
     * @param name 防具名
     * @param valueKigo 記号と値(WEV+1)
     * @return　Armor
     */
    public static Armor createArmor(String name, String valueKigo) {
        RpgItem item = new RpgItem();
        item.setName(name);
        item.setItemValueKigo(valueKigo);
        Armor armor = null;
        try {
            armor = new Armor(item);
        } catch(RpgException e) {
            e.printStackTrace();
            fail("失敗");
        }
        return armor;
    }
}
