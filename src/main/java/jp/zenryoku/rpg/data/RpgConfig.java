package jp.zenryoku.rpg.data;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * ストーリーテキストのCONFIG_PARAM以降の
 * 行から読み取り、RPGゲームで使用する設定を生成する。
 */
@Data
public class RpgConfig {
    /** ダイスコードがセットされているか */
    private boolean isDiceCode;
    /** 何回ふるか？ */
    private int diceTimes;
    /** 何面ダイスかを示す数値 */
    private int diceFaces;
    /** パラメータリスト */
    private Map<String, RpgData> paramList;
    /** 計算式リスト */
    private List<RpgFormula> formulaList;
    /** 職業リスト */
    private List<RpgJob> jobList;
    /** 職業リスト */
    private List<RpgItem> itemList;
}
