package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.exception.RpgException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class StringUtilsTest {
    @Test
    public void testFindDefaultStatus() {
        try {
            String tes = "MNY+100>";
            String res = StringUtils.findDefaultStatus(tes);
            assertEquals("MNY", res);
            String resT = StringUtils.findDefaultStatusOperator(tes);
            assertEquals("+", resT);

            String res2 = StringUtils.findDefaultStatus("HP+100>");
            assertEquals("HP", res2);

            String res3 = StringUtils.findDefaultStatus("HP100>");
            assertEquals(null, res3);

            String res4 = StringUtils.findDefaultStatus("HP-100>");
            assertEquals("HP", res4);
            String res5 = StringUtils.findDefaultStatusOperator("HP-100>");
            assertEquals("-", res5);

        } catch (RpgException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @Test
    public void testCreateEffectApper() {
        try {
            String[] res0 = StringUtils.createEffectApeer("ZHP-10%");
            assertEquals("ZHP", res0[0]);
            assertEquals("-", res0[1]);
            assertEquals("10%", res0[2]);
            String[] res1 = StringUtils.createEffectApeer("ZMP+10");
            assertEquals("ZMP", res1[0]);
            assertEquals("+", res1[1]);
            assertEquals("10", res1[2]);
        } catch (RpgException e) {
            e.printStackTrace();
            fail(e);
        }
    }
}
