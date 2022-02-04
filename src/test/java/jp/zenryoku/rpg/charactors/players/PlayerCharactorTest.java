package jp.zenryoku.rpg.charactors.players;

import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PlayerCharactorTest {
    /** テスト対象 */
    private static PlayerCharactor target;

    /** テストに使用するプレーヤーデータ作成 */
    private static List<RpgStatus> createStatus() {
        List<RpgStatus> statusList = new ArrayList<>();
        statusList.add(createData("ちから", "攻撃力、武器・防具の持てる合計重量を示す。", "POW"));
        statusList.add(createData("すばやさ", "行動の速さ、相手と５以上の差があるとき２回攻撃。", "AGI"));
        statusList.add(createData("かしこさ", "魔法・術などの効果量を示す。", "INT"));
        statusList.add(createData("きようさ", "使える武器、防具、魔法・術などの種類が増える。", "DEX"));
        statusList.add(createData("カリスマ", "人やモンスターに好かれる度合、統率力を示す。", "KSM"));

        final RpgConfig conf = RpgConfig.getInstance();
        Map<String, RpgData> map = new HashMap<>();
        statusList.forEach(data -> {
            map.put(data.getKigo(), data);
        });
        conf.setStatusMap(map);


        return statusList;
    }

    private static RpgStatus createData(String name, String discription, String kigo) {
        RpgStatus data = new RpgStatus();
        data.setName(name);
        data.setDiscription(discription);
        data.setKigo(kigo);
        return data;
    }
    @BeforeAll
    public static void init() {
        try {
            // createStatus()を先に実行する必要あり
            List<RpgStatus> list = createStatus();
            target = new PlayerCharactor("test");
            target.setStatusList(list);

        } catch (RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testNew() {
        assertEquals("test", target.getName());
        List<RpgStatus> statuses = target.getStatusList();

        assertEquals("ちから", statuses.get(0).getName());
        assertEquals("POW", statuses.get(0).getKigo());
        assertEquals("すばやさ", statuses.get(1).getName());
        assertEquals("AGI", statuses.get(1).getKigo());
        assertEquals("かしこさ", statuses.get(2).getName());
        assertEquals("INT", statuses.get(2).getKigo());
        assertEquals("きようさ", statuses.get(3).getName());
        assertEquals("DEX", statuses.get(3).getKigo());
        assertEquals("カリスマ", statuses.get(4).getName());
        assertEquals("KSM", statuses.get(4).getKigo());
    }
}