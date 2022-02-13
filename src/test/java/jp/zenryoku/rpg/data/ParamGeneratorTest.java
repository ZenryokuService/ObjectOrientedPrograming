package jp.zenryoku.rpg.data;

import jp.zenryoku.rpg.exception.RpgException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
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
            // アイテムマップ
            BufferedReader item = Files.newBufferedReader(Paths.get("src/test/resources", "testParamGeneratorItem.txt"));
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

            // 計算テキスト読み込み
            buf = Files.newBufferedReader(Paths.get("src/test/resources", "testParamGeneratorFormula.txt"));
            assertEquals("CONFIG_FORMULA", buf.readLine());
            target.createFormulaMap(buf);
            Map<String, RpgFormula> map = target.getConfig().getFormulaMap();
            if (true) map.forEach((key, val) -> { System.out.println(key + " : "+ val);});

            // 計算式の生成を行う。
            RpgFormula f = map.get("ATK");
            target.createFormula("ATK", f.getFormulaStr(), f);
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

    public void testCreateFormulaObject() {
        RpgFormula f = new RpgFormula();
        f.setKigo("ATK");
        f.setFormulaStr("(POW + WEV) * (1 + (0.1 * JLV))");
        try {
            target.createFormula(f.getKigo(), f.getFormulaStr(), f);

        } catch (Exception e) {
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
     * カテゴリ設定処理のテスト
     */
    @Test
    public void testCreateConfigParams() {
        BufferedReader buf = null;
        try {
            buf = Files.newBufferedReader(Paths.get("src/test/resources", "testParamGenerator.txt"));
            Method mes = this.getTargetMethod("createConfigParams", BufferedReader.class);
            buf.readLine();
            buf.readLine();

            mes.invoke(target, buf);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        try {
            buf = Files.newBufferedReader(Paths.get("src/test/resources", "testParamGeneratorItem.txt"));
            Method mes1 = this.getTargetMethod("createItemMap", BufferedReader.class);
            System.out.println(buf.readLine());
            mes1.invoke(target, buf);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        Map<String, RpgData> map = target.getConfig().getParamMap();
        map.forEach((key, val) -> {
            System.out.println("Key: " + key + " : " + map.get(key).getName());
        });
        Map<String, RpgData> map1 = target.getConfig().getItemMap();
        map1.forEach((key, val) -> {
            System.out.println("Key: " + key + " : " + map1.get(key).getName());
        });

    }

    @Test
    public void testCreateRpgDataFromConfig() {
        Map<String, RpgData> map = new HashMap<>();
        RpgData parentData = new RpgData();
        parentData.setKigo("STS");
        map.put("STS", parentData);
        try {
            Method mes = this.getTargetMethod("createRpgDataFromConfig", String.class, Map.class);
            RpgData res = (RpgData) mes.invoke(target, "アイテム:ITM:薬草など使用することでその効果を発揮するもの:-", map);
            assertEquals("アイテム", res.getName());
            assertEquals("ITM", res.getKigo());
            assertEquals("薬草など使用することでその効果を発揮するもの", res.getDiscription());
            assertEquals("-", res.getParent());

            RpgData res1 = (RpgData) mes.invoke(target, "どく:POI:ステータス異常を示す、1ターンごとに体力の10%程のダメージを受ける、戦闘後元に戻る:STS", map);
            assertEquals("どく", res1.getName());
            assertEquals("POI", res1.getKigo());
            assertEquals("ステータス異常を示す、1ターンごとに体力の10%程のダメージを受ける、戦闘後元に戻る", res1.getDiscription());
            assertEquals("STS", res1.getParent());
            assertEquals("STS", res1.getParentCls().getKigo());

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testCreateStatus() {
        String test = "ちから: 攻撃力、武器・防具の持てる合計重量を示す。: POW";
        Map<String, RpgData> map = new HashMap<>();
        RpgData parentData = new RpgData();
        parentData.setKigo("STS");
        map.put("STS", parentData);
        try {
            Method mes = this.getTargetMethod("createRpgDataFromConfig", String.class, Map.class);
            RpgData res = (RpgData) mes.invoke(target, "アイテム:ITM:薬草など使用することでその効果を発揮するもの:-", map);
            assertEquals("アイテム", res.getName());
            assertEquals("ITM", res.getKigo());
            assertEquals("薬草など使用することでその効果を発揮するもの", res.getDiscription());
            assertEquals("-", res.getParent());

            RpgData res1 = (RpgData) mes.invoke(target, "どく:POI:ステータス異常を示す、1ターンごとに体力の10%程のダメージを受ける、戦闘後元に戻る:STS", map);
            assertEquals("どく", res1.getName());
            assertEquals("POI", res1.getKigo());
            assertEquals("ステータス異常を示す、1ターンごとに体力の10%程のダメージを受ける、戦闘後元に戻る", res1.getDiscription());
            assertEquals("STS", res1.getParent());
            assertEquals("STS", res1.getParentCls().getKigo());

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
