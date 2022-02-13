package jp.zenryoku.rpg.charactors.players;

import jp.zenryoku.rpg.charactors.Player;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.item.equip.MainWepon;
import lombok.Data;

import java.util.*;

@Data
public class PlayerCharactor extends Player {
    /** 生年月日 */
    private String birthDay;
    /** パラメータリスト */
    protected Map<String, RpgStatus> statusMap;

    /**
     * ストーリーテキストの設定情報を取得して、ステータスを生成する。
     * @param name
     * @throws RpgException
     */
    public PlayerCharactor(String name) throws RpgException {
        super(name);
        statusMap = new HashMap<String, RpgStatus>();
        // ステータス設定を取得する。
        Map<String, RpgStatus> map = RpgConfig.getInstance().getStatusMap();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            RpgStatus data = map.get(key);
            if ((data instanceof RpgStatus) == false) {
                throw new RpgException(MessageConst.NO_MUCH_STATUS.toString() + ": " + data.getType());
            }
            // ダウンキャストしてリストに設定
            statusMap.put(data.getKigo(), data.clone());
        }
    }

    /**
     * 武器を装備する。
     * @param wepon 武器オブジェクト
     */
    @Override
    public void setMainWepon(MainWepon wepon) {
        mainWepon = wepon;
        RpgStatus atk = statusMap.get(RpgConst.ATK);
        // TODO-[Formulaで値を設定する]
        atk.setValue(10);
    }

}
