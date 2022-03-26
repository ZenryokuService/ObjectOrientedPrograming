package jp.zenryoku.rpg.charactors;

import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.data.RpgEvFlg;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * プレーヤーのパーティを管理するクラス。シングルトン実装。
 */
@Data
public class PlayerParty {
    /** インスタンス */
    private static PlayerParty instance;
    /** プレーヤー */
    private PlayerCharactor player;
    /** 所持金 */
    private static int money;
    /** イベントフラグキーのマップ */
    private Map<String, String> evflgKeyMap;

    /** プライベートコンストラクタ */
    private PlayerParty() {
    }

    /**
     * インスタンスを取得する。
     * @return 自インスタンス
     */
    public static PlayerParty getInstance() {
        if (instance == null) {
            instance = new PlayerParty();
        }
        return instance;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
