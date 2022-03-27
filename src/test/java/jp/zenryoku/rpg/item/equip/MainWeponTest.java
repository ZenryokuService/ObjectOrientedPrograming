package jp.zenryoku.rpg.item.equip;

import jp.zenryoku.rpg.data.items.RpgItem;
import jp.zenryoku.rpg.exception.RpgException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MainWeponTest {
    @Test
    public void testNew() {
        RpgItem item = new RpgItem();
        item.setName("ひのきのぼう");
        item.setItemValueKigo("WEV+1");
        MainWepon wepon = null;
        try {
            wepon = new MainWepon(item);
            assertEquals(1, wepon.getOffence());
        } catch(RpgException e) {
            e.printStackTrace();
            fail("失敗");
        }
    }
}
