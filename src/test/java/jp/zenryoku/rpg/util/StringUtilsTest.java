package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.TestUtils;
import jp.zenryoku.rpg.data.Effects;
import jp.zenryoku.rpg.data.status.StEffect;
import jp.zenryoku.rpg.exception.RpgException;
import org.junit.jupiter.api.Test;

import java.util.List;

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
}
