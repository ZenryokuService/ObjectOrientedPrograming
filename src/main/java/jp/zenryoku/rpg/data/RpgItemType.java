package jp.zenryoku.rpg.data;

import jp.zenryoku.rpg.constants.RpgConst;
import lombok.Data;

@Data
public class RpgItemType extends RpgData {
    /** アイテム名(super.nameを使用する) */
    /** アイテム記号 */
    protected String itemKigo;
    /** 説明 */
    protected String itemDiscription;

    public RpgItemType() {
        super(RpgConst.DATA_TYPE_ITEM_TYPE);
    }

}
