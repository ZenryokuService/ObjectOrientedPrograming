package jp.zenryoku.rpg.charactors;

import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import lombok.Data;

/**
 * プレーヤーのパーティを管理するクラス。シングルトン実装。
 */
@Data
public class PlayerParty {
    /** インスタンス */
    private static PlayerParty instance;
    /** プレーヤー */
    private PlayerCharactor player;

    /** プライベートコンストラクタ */
    private PlayerParty() {
    }

    /**
     * インスタンスを取得する。
     * @return
     */
    public static PlayerParty getInstance() {
        if (instance == null) {
            instance = new PlayerParty();
        }
        return instance;
    }
}
