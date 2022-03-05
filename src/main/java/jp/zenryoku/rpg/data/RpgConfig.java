package jp.zenryoku.rpg.data;

import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.charactors.monsters.Monster;
import jp.zenryoku.rpg.data.categry.RpgMaster;
import jp.zenryoku.rpg.data.items.EvEffect;
import jp.zenryoku.rpg.data.shop.RpgShop;
import jp.zenryoku.rpg.data.status.RpgFormula;
import jp.zenryoku.rpg.data.status.RpgStatus;
import jp.zenryoku.rpg.data.status.StEffect;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * ストーリーテキストのCONFIG_PARAM以降の
 * 行から読み取り、RPGゲームで使用する設定を生成する。
 * シングルトン実装
 */
@Data
public class RpgConfig {
    /** インスタンス */
    private static RpgConfig instance;
    /** ダイスコードがセットされているか */
    private boolean isDiceCode;
    /** 何回ふるか？ */
    private int diceTimes;
    /** 何面ダイスかを示す数値 */
    private int diceFaces;
    /** 表示する行数の指定 */
    private int printLine;
    /** 全ての設定を格納する */
    private Map<String, RpgData> dataMap;
    /** マスタカテゴリ */
    private Map<String, RpgMaster> masterMap;
    /** パラメータリスト */
    private Map<String, RpgData> paramMap;
    /** ステータスリスト */
    private Map<String, RpgStatus> statusMap;
    /** オプショナルステータスList */
    private Map<String, RpgStatus> optionStatusMap;
    /** 計算式リスト */
    private Map<String, RpgFormula> formulaMap;
    /** 職業リスト */
    private Map<String, RpgData> jobMap;
    /** アイテムリスト */
    private Map<String, RpgData> itemMap;
    /** アイテムタイプリスト */
    private Map<String, RpgData> itemTypeMap;
    /** ショップリスト */
    private Map<String, RpgShop> shopMap;
    /** ステータス効果リスト */
    private Map<String, Effects> effectMap;
    /** ステータス変化オブジェクト */
    private Map<String, StEffect> stEffectMap;
    /**イベント変化オブジェクト */
    private Map<String, EvEffect> evEffectMap;
    /** モンスターリスト */
    private List<Monster> monsterList;


    /** プレーヤーパーティ */
    private PlayerParty party;

    /** プライベートコンストラクタ */
    private RpgConfig() {
    }

    /** インスタンスはこのメソッドで取得する */
    public static RpgConfig getInstance() {
        if (instance == null) {
            instance = new RpgConfig();
        }
        return instance;
    }
}
