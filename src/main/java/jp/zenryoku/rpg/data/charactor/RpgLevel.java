package jp.zenryoku.rpg.data.charactor;

import jp.zenryoku.rpg.data.RpgData;
import lombok.Data;

@Data
public class RpgLevel extends RpgData {
    /** 職業記号 */
    private String jobKigo;
    /** レベルアップの値リスト */
    private int[] levelup;
    /** レベルアップ設定の名前 */
    private String confName;
}
