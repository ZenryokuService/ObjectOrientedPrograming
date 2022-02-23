package jp.zenryoku.rpg.data.status;

import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.util.StringUtils;

/**
 * 一時的なステータス変化を表す。ステータス異常、強化など。
 * ストーリーテキストから生成される。
 */
public class StEffect {
    /** 記号 */
    private String kigo;
    /** 表示文字 */
    private String disp;
    /** 効果式 */
    private RpgFormula apper;

    /**
     * ストーリーテキストの１行からプロパティ(属性)をセットする。
     * @param line ストーリーテキストの１行
     */
    public StEffect(String line, RpgConst cons) {
        String[] seps = StringUtils.separateParam(line, cons);
    }
}
