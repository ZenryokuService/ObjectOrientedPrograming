package jp.zenryoku.rpg.data;

import jp.zenryoku.rpg.constants.RpgConst;
import lombok.Data;

@Data
public class RpgData {
    /** データタイプ */
    protected RpgConst type;
    /** パラメータ名　*/
    protected String name;
    /** 説明 */
    protected String discription;
    /** 記号 */
    protected String kigo;
    /** パラメータ値 */
    protected Integer value;
    /** 親カテゴリ */
    protected String parent;
    /** 親カテゴリクラス */
    protected RpgData parentCls;

    public RpgData() {
    }

    public RpgData(RpgConst type) {
        this.type = type;
    }

}
