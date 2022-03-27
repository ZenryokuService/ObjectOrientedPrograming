package jp.zenryoku.rpg.data.items;

import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.RpgData;
import lombok.Data;

@Data
public class RpgItem extends RpgData {
    /** アイテム名(super.nameを使用する) */
    /** アイテムタイプ */
    protected String itemType;
    /** アイテム効果記号 */
    protected String itemValueKigo;
    /** アイテム効果数値 */
    protected int itemValue;
    /** 金額 */
    protected int money;
    /** 副作用対象 */
    protected String targetSideEffect;
    /** 副作用効果(記号 値(「ITN-1」など) */
    protected String sideEffectValue;


    public RpgItem() {
        super(RpgConst.DATA_TYPE_ITEM);
    }

    @Override
    public RpgItem clone() {
        return clone();
    }
}
