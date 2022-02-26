package jp.zenryoku.rpg.factory;

import jp.zenryoku.rpg.TestUtils;
import jp.zenryoku.rpg.data.Effects;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.status.StEffect;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.util.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RpgDataFactoryTest {

    @Test
    public void testCreateRpgDataFromConfig() {
        TestUtils.initRpgConfig();
        Map<String, RpgData> map = RpgConfig.getInstance().getParamMap();
        try {
            RpgData data = RpgDataFactory.createRpgDataFromConfig("アイテム:ITM:-:薬草など使用することでその効果を発揮するもの:-:-", map);
            RpgData data1 = RpgDataFactory.createRpgDataFromConfig("アイテム効果:ITV:PLY0:アイテムを使用したときの効果値:-:0", map);
            RpgData data2 = RpgDataFactory.createRpgDataFromConfig("ぶき:WEP:PLY1:装備することで攻撃力(ATK)を上げることができる:-:0", map);
        } catch (RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    @Test
    public void testCreateStEffecr() {

        try {
            Effects res = RpgDataFactory.createEffects("POI:どく:ZHP-10%:どくをうけた");
            assertEquals("POI", res.getKigo());
            assertEquals("どく", res.getDisp());
            assertEquals("ZHP-10%", res.getSiki());
            assertEquals("どくをうけた", res.getMessage());
        } catch (RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateEffectApper() {
        try {
            TestUtils.initRpgConfig();
            List<Effects> res01 = RpgDataFactory.createEffectApeer("ZHP-10%");
            StEffect res0 = (StEffect) res01.get(0);
            assertEquals("ZHP", res0.getKigo());
            assertEquals("-", res0.getOpe());
            assertEquals("10%", res0.getKokaValue());
            List<Effects> res10 = RpgDataFactory.createEffectApeer("ZMP+10");
            StEffect res1 = (StEffect) res10.get(0);
            assertEquals("ZMP", res1.getKigo());
            assertEquals("+", res1.getOpe());
            assertEquals("10", res1.getKokaValue());
            List<Effects> res20 = RpgDataFactory.createEffectApeer("DNM+1");
            StEffect res2 = (StEffect) res20.get(0);
            assertEquals("DNM", res2.getKigo());
            assertEquals("+", res2.getOpe());
            assertEquals("1", res2.getKokaValue());
            List<Effects> res30 = RpgDataFactory.createEffectApeer("DNM+1, POI-1");
            StEffect res3 = (StEffect) res30.get(0);
            assertEquals("DNM", res3.getKigo());
            assertEquals("+", res3.getOpe());
            assertEquals("1", res3.getKokaValue());
            StEffect res4 = (StEffect) res30.get(1);
            assertEquals("POI", res4.getKigo());
            assertEquals("-", res4.getOpe());
            assertEquals("1", res4.getKokaValue());
        } catch (RpgException e) {
            e.printStackTrace();
            fail(e);
        }
    }
}
