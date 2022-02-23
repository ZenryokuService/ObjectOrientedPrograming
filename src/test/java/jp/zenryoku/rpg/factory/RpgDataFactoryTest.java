package jp.zenryoku.rpg.factory;

import jp.zenryoku.rpg.data.status.StEffect;
import jp.zenryoku.rpg.exception.RpgException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class RpgDataFactoryTest {

    @Test
    public void testCreateStEffecr() {

        try {
            StEffect res = RpgDataFactory.createStEffect("POI:どく:HP-10%:どくをうけた");
        } catch (RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
