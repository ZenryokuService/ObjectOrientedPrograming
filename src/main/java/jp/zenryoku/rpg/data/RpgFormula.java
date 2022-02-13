package jp.zenryoku.rpg.data;

import jp.zenryoku.rpg.constants.RpgConst;
import lombok.Data;

/**
 * ストーリーテキストから生成する、各種計算式クラス。
 */
@Data
public class RpgFormula extends RpgData {
    /**　計算式(文字列) */
    private String formulaStr;

    public RpgFormula() {
        super.setType(RpgConst.DATA_TYPE_FORMULA);
    }

    @Override
    public RpgFormula clone() {
        return clone();
    }
}
