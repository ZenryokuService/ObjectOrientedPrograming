package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.data.RpgEvFlg;
import jp.zenryoku.rpg.exception.RpgException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class StringUtilsTest {
    /**
     * デフォルトステータスを取得する処理のテスト。
     */
    @Test
    public void testFindDefaultStatus() {
        try {
            String tes = "MNY+100>";
            String res = StringUtils.findDefaultStatus(tes);
            assertEquals("MNY", res);
            String resT = StringUtils.findOperator(tes);
            assertEquals("+", resT);

            String res2 = StringUtils.findDefaultStatus("ZHP+100>");
            assertEquals("HP", res2);

            String res3 = StringUtils.findDefaultStatus("ZHP100>");
            assertEquals(null, res3);

            String res4 = StringUtils.findDefaultStatus("ZHP-100>");
            assertEquals("HP", res4);
            String res5 = StringUtils.findOperator("HP-100>");
            assertEquals("-", res5);

        } catch (RpgException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @Test
    public void testConvertFieldName() {
        assertEquals("HP", StringUtils.convertDefaultStatusKigo("ZHP"));
        assertEquals("MP", StringUtils.convertDefaultStatusKigo("ZMP"));
        assertEquals("ATK", StringUtils.convertDefaultStatusKigo("ATK"));
        assertEquals("POW", StringUtils.convertDefaultStatusKigo("POW"));
        assertEquals("LV", StringUtils.convertDefaultStatusKigo("ZLV"));
        assertEquals(null, StringUtils.convertDefaultStatusKigo("ZHPS"));
    }

    @Test
    public void testSeparateEffectAppear() {
        String[] res0 = StringUtils.separateEffectAppear("ZHP-10%");
        assertEquals("ZHP", res0[0]);
        assertEquals("-", res0[1]);
        assertEquals("10%", res0[2]);
        String[] res1 = StringUtils.separateEffectAppear("POI-10TS3");
        assertEquals("POI", res1[0]);
        assertEquals("-", res1[1]);
        assertEquals("10", res1[2]);
        assertEquals("TS", res1[3]);
        assertEquals("3", res1[4]);
        String[] res2 = StringUtils.separateEffectAppear("POI-10%S3");
        assertEquals("POI", res2[0]);
        assertEquals("-", res2[1]);
        assertEquals("10%", res2[2]);
        assertEquals("S", res2[3]);
        assertEquals("3", res2[4]);
    }

    @Test
    public void testFindTS() {
        assertEquals("TS", StringUtils.findTS("10TS2"));
        assertEquals("T", StringUtils.findTS("10T2"));
        assertEquals("S", StringUtils.findTS("10S2"));
        assertEquals(null, StringUtils.findTS("10"));
    }

    @Test
    public void testFindNo() {
        assertEquals("10", StringUtils.findNo("10TS2"));
        assertEquals("100", StringUtils.findNo("100T2"));
        assertEquals("1", StringUtils.findNo("1TS2"));
    }

    @Test
    public void testFindNoBack() {
        assertEquals("1", StringUtils.findNoBack("10TS1"));
        assertEquals("2", StringUtils.findNoBack("100T2"));
        assertEquals("3", StringUtils.findNoBack("100T3"));
        assertEquals("321", StringUtils.findNoBack("123TS321"));
        assertEquals("21", StringUtils.findNoBack("12TS21"));
    }

    @Test
    public void testFindMonsterNo() {
        assertEquals("0", StringUtils.findMonsterNo("<monster:0>")[0]);
        assertEquals("0", StringUtils.findMonsterNo("<monster:0-4>")[0]);
        assertEquals("4", StringUtils.findMonsterNo("<monster:0-4>")[1]);
    }

    @Test
    public void testConvertProperty() {
        try  {
            assertEquals("これはてすとです。"
                    , StringUtils.convertProperty("これは{Player.name}です。", "Player.name", "てすと"));
            assertEquals("これは通貨です。"
                    , StringUtils.convertProperty("これは{MNY}です。", "MNY", "通貨"));
            assertEquals("これは{Player.aaa}です。"
                    , StringUtils.convertProperty("これは{Player.aaa}です。", "Player.name", "てすと"));
            assertEquals("これは{MNY}です。"
                    , StringUtils.convertProperty("これは{MNY}です。", "MNY＠＠", "通貨"));
        } catch (RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testReadEventFlg() {
        try {
            RpgEvFlg evFlg = StringUtils.readEventFlg("<evflg:1:null>");
            assertEquals("1", evFlg);
            assertEquals("0", StringUtils.readEventFlg("<evflg: 2: flg=1>"));
            assertEquals("4", StringUtils.readEventFlg("<evflg: 2: flg=1>"));
        } catch (RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
