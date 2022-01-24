package jp.zenryoku.rpg.data;

import jp.zenryoku.rpg.exception.RpgException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParamGeneratorTest {
    private static boolean isDebug = true;
    private static ParamGenerator target;
    @BeforeAll
    public static void init() {
        target = ParamGenerator.getInstance();
    }

    @BeforeEach
    public void resetParam() {
        target.getConfig().getParamMap().clear();
    }

    /**
     * プライベートメソッドの取得、アクセスを許可して返却する。
     * @param methodName 取得するメソッド名
     * @return 対象のメソッド
     * @throws RpgException
     */
    private Method getTargetMethod(String methodName, Class<?>... cls) throws RpgException {
        Method mes = null;
        try {
            mes = target.getClass().getDeclaredMethod(methodName, cls);
            mes.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mes;
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
            Map<String, RpgData> tList = target.getConfig().getItemTypeMap();
            if (isDebug) tList.forEach((key, val) -> { System.out.println(key + " : "+ val.getKigo() + " : " + val.getDiscription());});
            Map<String, RpgData> list = target.getConfig().getItemMap();
            if (isDebug) list.forEach((key, val) -> { System.out.println(key + " : "+ val.getName());});
            // 計算式の生成を行う。
            //target.createFormula(list.get(0).getFormulaStr());
        } catch (IOException | RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testSepNameKigo() {
        try {
            Method mes = this.getTargetMethod("sepNameAndKigo", String.class);
            String[] res1 = (String[]) mes.invoke(target, "アイテム(ITM) 武器(WEP) 防具(ARM)");
            assertEquals("アイテム", res1[0]);
            assertEquals("ITM", res1[1]);
            assertEquals("武器", res1[2]);
            assertEquals("WEP", res1[3]);
            assertEquals("防具", res1[4]);
            assertEquals("ARM", res1[5]);
            String[] res2 = (String[]) mes.invoke(target,"アイテム効果(ITV) 武器攻撃力(WEV) 防具防御力(ARV)");
            assertEquals("アイテム効果", res2[0]);
            assertEquals("ITV", res2[1]);
            assertEquals("武器攻撃力", res2[2]);
            assertEquals("WEV", res2[3]);
            assertEquals("防具防御力", res2[4]);
            assertEquals("ARV", res2[5]);
            String[] res3 = (String[]) mes.invoke(target,"ニギ(NIG)");
            assertEquals("ニギ", res3[0]);
            assertEquals("NIG", res3[1]);
            String[] res4 = (String[]) mes.invoke(target,"アイテム副作用(SIV)");
            assertEquals("アイテム副作用", res4[0]);
            assertEquals("SIV", res4[1]);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        Map<String, RpgData> map = target.getConfig().getItemTypeMap();
        map.forEach((key, val) -> {
            assertEquals(key, map.get(key).getName());
        });
    }

    /**
     * アイテムの記号から武器、防具、通常アイテムの分類をしたうえで
     * 各アイテムリストのアイテム・オブジェクトのインスタンスをマップに登録する。
     */
    public void createItemInstance() {

    }
}
