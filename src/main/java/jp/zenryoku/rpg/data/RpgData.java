package jp.zenryoku.rpg.data;

import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.status.RpgFormula;
import lombok.Data;

/**
 * RpgDataは基本的に、子クラスを生成するためのデータを保持することが多い。
 * @see jp.zenryoku.rpg.factory.RpgDataFactory
 */
@Data
public class RpgData {
    /** 順番(優先順序) */
    protected Integer priority;
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
    /** マスタカテゴリ */
    protected String master;
    /** 計算式 */
    protected RpgFormula formula;

    public RpgData() {
    }

    public RpgData(RpgConst type) {
        this.type = type;
    }

    @Override
    public RpgData clone() {
        return clone();
    }

}
