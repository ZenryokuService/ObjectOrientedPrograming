package jp.zenryoku.rpg.data.status;

import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.RpgData;

/**
 * 職業を表す。
 * ストーリーテキストから生成される
 */
public class RpgJob extends RpgData {
    public RpgJob() {
        super.setType(RpgConst.DATA_TYPE_JOB);
    }

    @Override
    public RpgJob clone() {
        return clone();
    }
}
