package jp.zenryoku.rpg.data;

import jp.zenryoku.rpg.constants.RpgConst;
import lombok.Data;

@Data
public class RpgData {
    /** データタイプ */
    private RpgConst type;
    /** パラメータ名　*/
    private String name;
    /** 説明 */
    private String discription;
    /** 記号 */
    private String kigo;
    /** パラメータ値 */
    private Integer value;
}
