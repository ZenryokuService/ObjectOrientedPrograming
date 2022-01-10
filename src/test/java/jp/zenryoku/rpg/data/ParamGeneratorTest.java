package jp.zenryoku.rpg.data;

import jp.zenryoku.rpg.exception.RpgException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParamGeneratorTest {
    private static boolean isDebug = false;
    private static ParamGenerator target;
    @BeforeAll
    public static void init() {
        target = ParamGenerator.getInstance();
    }

    @BeforeEach
    public void resetParam() {
        target.getConfig().getParamMap().clear();
    }
    @Test
    public void testSetDiceCode1() {
        try {
            target.setDiceCode("<1D6>");
            assertEquals(1, target.getConfig().getDiceTimes());
            assertEquals(6, target.getConfig().getDiceFaces());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSetDiceCode2() {
        try {
            target.setDiceCode("<3D20>");
            assertEquals(3, target.getConfig().getDiceTimes());
            assertEquals(20, target.getConfig().getDiceFaces());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateParam() {
        BufferedReader buf = null;
        try {
            buf = Files.newBufferedReader(Paths.get("src/test/resources", "testParamGenerator.txt"));
            assertEquals("CONFIG_PARAM", buf.readLine());
            // ２行目を飛ばす。
            //buf.readLine();
            target.createParam(buf);
            Map<String, RpgData> res = target.getConfig().getParamMap();
            //assertEquals(6, res.size());
            if (isDebug) res.forEach((key, val) -> {
                System.out.println("Key: " + key + " Value: " + val.getKigo() + " " + val.getDiscription());
            });
        } catch (Exception e) {
            fail(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testJob() {
        BufferedReader buf = null;
        try {
            buf = Files.newBufferedReader(Paths.get("src/test/resources", "testParamGeneratorJob.txt"));
            assertEquals("CONFIG_JOB", buf.readLine());
            target.createJobMap(buf);
            if (isDebug) target.getConfig().getJobMap().forEach((key, val) -> { System.out.println(key + " : "+ val);});
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateFormula() {
        BufferedReader buf = null;
        try {
            // 職業マップ
            BufferedReader job = Files.newBufferedReader(Paths.get("src/test/resources", "testParamGeneratorJob.txt"));
            job.readLine(); // 1行飛ばす
            target.createJobMap(job);
            // アイテムマップ
            BufferedReader item = Files.newBufferedReader(Paths.get("src/test/resources", "testParamGeneratorItem.txt"));
            item.readLine(); // 1行飛ばす
            target.createItemTypeMap(item);
            item.readLine();
            item.readLine();
            target.createItemMap(item);
            // ステータスマップ
            BufferedReader status = Files.newBufferedReader(Paths.get("src/test/resources", "testParamGeneratorStatus.txt"));
            status.readLine();
            target.createStatus(status);
            // パラメータマップ
            BufferedReader param = Files.newBufferedReader(Paths.get("src/test/resources", "testParamGenerator.txt"));
            param.readLine();
            target.createParam(param);

            buf = Files.newBufferedReader(Paths.get("src/test/resources", "testParamGeneratorFormula.txt"));
            assertEquals("CONFIG_FORMULA", buf.readLine());
            target.createFormulaMap(buf);
            Map<String, RpgData> list = target.getConfig().getFormulaMap();
            if (true) list.forEach((key, val) -> { System.out.println(key + " : "+ val);});

            // 計算式の生成を行う。
//            target.createFormula(list.get(0).getFormulaStr());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testItem() {
        BufferedReader buf = null;
        try {
            buf = Files.newBufferedReader(Paths.get("src/test/resources", "testParamGeneratorItem.txt"));
            assertEquals("CONFIG_ITEM", buf.readLine());
            String line = null;
            //buf.readLine();
            target.createItemTypeMap(buf);
            buf.readLine();
            assertEquals("ITEM_LIST", buf.readLine());
            target.createItemMap(buf);
            Map<String, RpgData> list = target.getConfig().getItemMap();
            if (isDebug) list.forEach((key, val) -> { System.out.println(key + " : "+ val.getName());});
            // 計算式の生成を行う。
            //target.createFormula(list.get(0).getFormulaStr());
        } catch (IOException | RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

//    @Test
//    public void testCreateItemType() {
//        try {
//            target.createItemTypeMap("アイテム種類: アイテムの種類を設定する。 ITM(薬草などの通常アイテム) WEP(武器の類) ARM(防具の類): アイテム(ITM) 武器(WEP) 防具(ARM)");
//            target.createItemTypeMap("アイテム効果: 各アイテムの持っている効果。薬草であれば回復量、武器であれば武器攻撃力を示す。防具の場合は防具防御力: アイテム効果(ITV) 武器攻撃力(WEV) 防具防御力(ARV)");
//            target.createItemTypeMap("値段: 各アイテムの値段、ここでは通貨の単位を記述する: ニギ(NIG)");
//            target.createItemTypeMap("アイテム副作用: アイテムの副作用、「もっていると魔法を受け付けなくなる」など: アイテム副作用(SIV)");
//        } catch (RpgException e) {
//            fail(e.getMessage());
//        }
//        Map<String, RpgItemType> map = target.getConfig().getItemTypeMap();
//        map.forEach((key, val) -> {
//            assertEquals(key, map.get(key).getName());
//        });
//    }
}
