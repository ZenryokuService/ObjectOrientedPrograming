package jp.zenryoku.rpg.data.status;

import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.RpgData;

/**
 * ステータスを表す。
 * ストーリーテキストから生成される
 */
public class RpgStatus extends RpgData {
    public RpgStatus() {
        super.setType(RpgConst.DATA_TYPE_STATUS);
    }
}
