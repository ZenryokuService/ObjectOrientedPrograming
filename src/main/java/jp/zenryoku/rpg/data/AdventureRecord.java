package jp.zenryoku.rpg.data;

import jp.zenryoku.rpg.charactors.Player;

import java.util.List;

/**
 * 冒険の記録を管理するクラス。
 */
public class AdventureRecord {
    /**
     * シーンインデックスリスト、通った筋道を補完する。
     */
    private List<String> sceneIndexList;
    /** ゲームのプレーヤーデータ */
    private Player player;

}
