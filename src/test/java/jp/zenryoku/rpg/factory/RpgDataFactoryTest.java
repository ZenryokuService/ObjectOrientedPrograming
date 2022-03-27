package jp.zenryoku.rpg.factory;

import jp.zenryoku.rpg.TestUtils;
import jp.zenryoku.rpg.data.Effects;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.items.EvEffect;
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
    public void testCreateEffects() {
        try {
            TestUtils.initRpgConfig();
            Effects res = RpgDataFactory.createEffects("POI:どく:ZHP-10%:どくをうけた");
            assertEquals("POI", res.getKigo());
            assertEquals("どく", res.getDisp());
            assertEquals("ZHP-10%", res.getSiki());
            assertEquals("どくをうけた", res.getMessage());
            List<Effects> effList = res.getEffList();
            assertEquals(1, effList.size());
            Effects koka1 = effList.get(0);
            StEffect k1 = (StEffect) koka1;
            assertEquals("ZHP", k1.getKigo());
            assertEquals("-", k1.getOpe());
            assertEquals("10%", k1.getKokaValue());
        } catch (RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateEffectApper() {
        try {
            TestUtils.initRpgConfig();
            List<Effects> res01 = RpgDataFactory.createEffectAppear("ZHP-10%");
            StEffect res0 = (StEffect) res01.get(0);
            assertEquals("ZHP", res0.getKigo());
            assertEquals("-", res0.getOpe());
            assertEquals("10%", res0.getKokaValue());
            List<Effects> res10 = RpgDataFactory.createEffectAppear("ZMP+10");
            StEffect res1 = (StEffect) res10.get(0);
            assertEquals("ZMP", res1.getKigo());
            assertEquals("+", res1.getOpe());
            assertEquals("10", res1.getKokaValue());
            List<Effects> res20 = RpgDataFactory.createEffectAppear("DNM+1");
            EvEffect res2 = (EvEffect) res20.get(0);
            assertEquals("DNM", res2.getKigo());
            assertEquals("+", res2.getOpe());
            assertEquals("1", res2.getKokaValue());
            List<Effects> res30 = RpgDataFactory.createEffectAppear("DNM+1, POI-1");
            EvEffect res3 = (EvEffect) res30.get(0);
            assertEquals("DNM", res3.getKigo());
            assertEquals("+", res3.getOpe());
            assertEquals("1", res3.getKokaValue());
            EvEffect res4 = (EvEffect) res30.get(1);
            assertEquals("POI", res4.getKigo());
            assertEquals("-", res4.getOpe());
            assertEquals("1", res4.getKokaValue());

            EvEffect res5 = (EvEffect) res20.get(0);
            assertEquals("DNM", res2.getKigo());
            assertEquals("+", res2.getOpe());
            assertEquals("1", res2.getKokaValue());
            List<Effects> res50 = RpgDataFactory.createEffectAppear("POW+1, POI-10%");
            StEffect res51 = (StEffect) res50.get(0);
            assertEquals("POW", res51.getKigo());
            assertEquals("+", res51.getOpe());
            assertEquals("1", res51.getKokaValue());
            EvEffect res52 = (EvEffect) res50.get(1);
            assertEquals("POI", res52.getKigo());
            assertEquals("-", res52.getOpe());
            assertEquals("10%", res52.getKokaValue());

            List<Effects> res6 = RpgDataFactory.createEffectAppear("POI-10%TS3");
            Effects res60 = res6.get(0);


        } catch (RpgException e) {
            e.printStackTrace();
            fail(e);
        }
    }
}
