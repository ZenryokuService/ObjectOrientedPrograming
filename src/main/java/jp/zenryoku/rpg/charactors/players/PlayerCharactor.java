package jp.zenryoku.rpg.charactors.players;

import jp.zenryoku.rpg.charactors.Player;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class PlayerCharactor extends Player {
    /** 生年月日 */
    private String birthDay;
    /** パラメータリスト */
    protected List<RpgStatus> statusList;

    /**
     * ストーリーテキストの設定情報を取得して、ステータスを生成する。
     * @param name
     * @throws RpgException
     */
    public PlayerCharactor(String name) throws RpgException {
        super(name);
        statusList = new ArrayList<>();
        // ステータス設定を取得する。
        Map<String, RpgData> map = RpgConfig.getInstance().getStatusMap();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            RpgData data = map.get(key);
            if ((data instanceof RpgStatus) == false) {
                throw new RpgException(MessageConst.NO_MUCH_STATUS.toString() + ": " + data.getType());
            }
            // ダウンキャストしてリストに設定
            statusList.add((RpgStatus) data);
        }
    }
}
