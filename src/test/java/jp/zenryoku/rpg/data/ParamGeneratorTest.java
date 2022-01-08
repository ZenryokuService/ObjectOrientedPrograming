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
        target.getDataMap().clear();
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
            target.createStatusParam(buf);
            Map<String, RpgData> res = target.getDataMap();
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
            target.createJobList(buf);
            if (isDebug) target.getConfig().getJobList().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testFormula() {
        BufferedReader buf = null;
        try {
            BufferedReader job = Files.newBufferedReader(Paths.get("src/test/resources", "testParamGeneratorJob.txt"));
            job.readLine(); // 1行飛ばす
            target.createJobList(job);
            BufferedReader item = Files.newBufferedReader(Paths.get("src/test/resources", "testParamGeneratorItem.txt"));
            item.readLine(); // 1行飛ばす
            target.createItemList(item);
            buf = Files.newBufferedReader(Paths.get("src/test/resources", "testParamGeneratorFormula.txt"));
            assertEquals("CONFIG_FORMULA", buf.readLine());
            target.createFormulaList(buf);
            List<RpgFormula> list = target.getConfig().getFormulaList();
            if (isDebug) list.forEach(System.out::println);
            // データマップを生成する
            target.createDataMap();
            // 計算式の生成を行う。
            target.createFormula(list.get(0).getFormulaStr());
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
            target.createItemList(buf);
            List<RpgItem> list = target.getConfig().getItemList();
            if (isDebug) list.forEach(System.out::println);
            // 計算式の生成を行う。
            //target.createFormula(list.get(0).getFormulaStr());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    //@Test
    public void testCreateFormula() {
        try {
            target.createFormula("物理攻撃力= (ちから + 武器攻撃力) * (1 + (0.1 * 熟練度))");
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
