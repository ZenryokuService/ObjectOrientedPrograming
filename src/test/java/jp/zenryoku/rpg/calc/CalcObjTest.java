package jp.zenryoku.rpg.calc;

import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CalcObjTest {
    /** テスト対象 */
    private CalcObj target;
    /** データリスト */
    private List<RpgData> list;


    public static void initClass() {
    }
    /**
     * 0: pow=1
     * 1: wev=2
     * 2: arv=3
     */
    @BeforeEach
    public void init() {
        target = new CalcObj();
        Map<String, RpgData> map = new HashMap<>();
        list = new ArrayList<>();
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
        inta.setValue(10);
        list.add(inta);
        map.put(inta.getKigo(), inta);

        RpgData jvl = new RpgData();
        jvl.setKigo("JLV");
        jvl.setValue(10);
        list.add(jvl);
        map.put(jvl.getKigo(), jvl);

        RpgData itv = new RpgData();
        itv.setKigo("ITV");
        itv.setValue(10);
        list.add(itv);
        map.put(itv.getKigo(), itv);

        RpgConfig.getInstance().setParamMap(map);
    }

    /** 四則演算を行うテスト */
    //@Test
    public void testCalcShisoku() {
        List<RpgData> list = new ArrayList<>();
        // ちから
        RpgData pow = new RpgData();
        pow.setKigo("POW");
        pow.setValue(1);

        // 武器攻撃力
        RpgData wev = new RpgData();
        wev.setKigo("WEB");
        wev.setValue(2);

        String[] opes = new String[] {"+", "-", "*", "/", "%"};
        int[] answers = new int[] {3, -1, 2, 0, 1};
        for (int i = 0; i < opes.length; i++) {
            String ope = opes[i];
            try {
//                target.setOpes(ope);
//                target.setLeft(pow.getValue().toString());
//                target.setRight(wev.getValue().toString());
                //System.out.println("ope: " + ope);
                assertEquals(answers[i], target.calcurate());
            } catch (Exception e) {
                e.printStackTrace();
                fail(e.getMessage());
            }
            target.finalize();
            target = new CalcObj();
        }
    }

    //@Test
    public void testToIndexCalc() {
        Map<Integer, String> res = null;
        try {
            Map<Integer, String> result = new HashMap<>();
            res = target.toIndexCalc("(ATK + POW) + (1 + (0.1 * JLV))", result);
        } catch (RpgException r) {
            r.printStackTrace();
            fail(r.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        res.forEach((key, val) -> {
            System.out.println("Key: " + key + " Value: " + val);
        });
    }

    //@Test
    public void testForwardIdx() {
        String formula = "(((INT + DEX) / 2 ) * 0.1) * ITV";
        int[][] res = target.forwardIdx(formula);
        assertEquals(3, res[0].length);
        assertEquals(3, res[1].length);

        for (int i = 0; i < res[0].length;i++ ) {
            System.out.print(i + ": " + res[0][i] + ", ");
        }
        System.out.println();
        for (int i = 0; i < res[1].length;i++ ) {
            System.out.print(i + ": " + res[1][i] + ", ");
        }
    }

    //@Test
    public void testRreversIdx() {
        String formula = "(ATK + POW) + (1 + (0.1 * JLV))";
        int[][] res = target.reversIdx(formula);
        assertEquals(3, res[0].length);
        assertEquals(3, res[1].length);

        int[] ans0 = new int[] {14, 19, 0};
        for (int i = 0; i < res[0].length;i++ ) {
            assertEquals(ans0[i], res[0][i]);
        }
        int[] ans1 = new int[] {31, 30, 11};
        for (int i = 0; i < res[1].length;i++ ) {
            assertEquals(ans1[i], res[1][i]);
        }
    }

    //@Test
    public void testAnalizeFormula0_1() {
        int[][] res = target.analizeFormula("(a + (b - ((c * 0.1) - (2 * d + (e + 1 / (f / 1))))))");
        for (int i : res[0]) {
            System.out.print(i + ", ");
        }
        System.out.println();
        for (int i : res[1]) {
            System.out.print(i + ", ");
        }
        assertEquals(7, res[0].length);
        assertEquals(7, res[1].length);
        int[] ans0 = new int[] {32, 23, 11, 10, 5, 41, 0};
        for (int i = 0; i < res[0].length; i++) {
            assertEquals(ans0[i], res[0][i]);
        }
        int[] ans1 = new int[] {53, 52, 51, 50, 49, 48, 20};
        for (int i = 0; i < res[1].length; i++) {
            assertEquals(ans1[i], res[1][i]);
        }
    }

    //@Test
    public void testAnalizeFormula0() {
        int[][] res = target.analizeFormula("(ATK + POW) + (1 + (0.1 * JLV))");
        for (int i : res[0]) {
            System.out.print(i + ", ");
        }
        System.out.println();
        for (int i : res[1]) {
            System.out.print(i + ", ");
        }
        assertEquals(3, res[0].length);
        assertEquals(3, res[1].length);
        int[] ans0 = new int[] {14, 19, 0};
        for (int i = 0; i < res[0].length; i++) {
                assertEquals(ans0[i], res[0][i]);
        }
        int[] ans1 = new int[] {31, 30, 11};
        for (int i = 0; i < res[1].length; i++) {
            assertEquals(ans1[i], res[1][i]);
        }
    }

    // @Test
    public void testAnalizeFormula1() {
        int[][] res = target.analizeFormula("(POW + AGI +DEX) / 3 + ARV");
        int[] ans0 = new int[] {0};
        for (int i = 0; i < res[0].length; i++) {
            // 0, 15, 20
            assertEquals(res[0][i], ans0[i]);
        }
        int[] ans1 = new int[] {16};
        for (int i = 0; i < res[1].length; i++) {
            assertEquals(res[1][i], ans1[i]);
        }
    }

    //@Test
    public void testAnalizeFormula2() {
        int[][] res = target.analizeFormula("(((INT + DEX) / 2 ) * 0.1) * ITV");
        int[] ans0 = new int[] {0, 1, 2};
        int count = 0;
        for (int i = 0; i < res[0].length; i++) {
            assertEquals(res[0][i], ans0[i]);
        }
        int[] ans1 = new int[] {13, 19, 26};
        for (int i = 0; i < res[1].length; i++) {
            assertEquals(res[1][i], ans1[i]);
        }
    }

    //@Test
    public void testAnalizeFormula3() {
        int[][] res = target.analizeFormula("1 + カリスマ * 0.1");
        assertEquals(res[0].length, 0);
        assertEquals(res[1].length, 0);
    }

    @Test
    public void testConvertKigo() throws RpgException {
        initClass();
        target.convertKigo("(POW + DEX) / 2 + BPK");
        target.convertKigo("(AGI + (POW - ((ATK * 0.1) - (2 * DEF + (ITM + 1 / (KSM / 1))))))");
        target.convertKigo("(ATK + POW) + (1 + (0.1 * JLV))");
        target.convertKigo("(((INT + DEX) / 2 ) * 0.1) * ITV");
    }

    //@Test
    public void testCutFormulaStr() throws RpgException {
        target.cutFormulaStr("(POW + DEX) / 2 + BPK");
        target.cutFormulaStr("ITV * (((INT + DEX) / 2 ) * 0.1)");
        target.cutFormulaStr("(ATK + POW) + (1 + (0.1 * JLV))");
        target.cutFormulaStr("(POW + AGI +DEX) / 3 + ARV");
        target.cutFormulaStr("(((INT + DEX) / 2 ) * 0.1) * ITV");
        target.cutFormulaStr("1 + KSM * 0.1");
        target.cutFormulaStr("(INT + DEX + AGI) / 3 + TSM");
        target.cutFormulaStr("(AGI + (POW - ((ATK * 0.1) - (2 * DEF + (ITM + 1 / (KSM / 1))))))");
    }

    @Test
    public void testSplitFormula() {
        try {
            String formula = target.convertKigo("(AGI + (POW - ((ATK * 0.1) - (2 * DEF + (ITM + 1 / (KSM / 1))))))");
            List<String> list = target.splitFormula(formula);
            list.forEach(System.out::println);
        } catch (RpgException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCalucrate() {
        try {
            int result = target.calcurate("(10 * 0.1)");
            assertEquals(1, result);
            target.reset();

            int result2 = target.calcurate("(7 +1)");
            assertEquals(8, result2);
            target.reset();

            int result3 = target.calcurate("10 - 5");
            assertEquals(5, result3);
            target.reset();

            int result4 = target.calcurate("(7.5 + 1.5)");
            assertEquals(9, result4);
            target.reset();

            int result5 = target.calcurate("6/3");
            assertEquals(2, result5);
            target.reset();

            int result6 = target.calcurate("3 % 2");
            assertEquals(1, result6);
            target.reset();

            int result7 = target.calcurate("1 + 2 - 3");
            assertEquals(0, result7);
            target.reset();

            int result8 = target.calcurate("1.0 + 4 - 3.0");
            assertEquals(2, result8);
            target.reset();

            int result9 = target.calcurate("(3 / 3 - 1.0)");
            assertEquals(0, result9);
            target.reset();

            int result10 = target.calcurate("4 / 4 - 1.0 + 1");
            assertEquals(1, result10);
            target.reset();

        } catch (RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
