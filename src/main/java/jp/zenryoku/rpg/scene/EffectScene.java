package jp.zenryoku.rpg.scene;

import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.exception.RpgException;
import lombok.Data;

/**
 * アイテムの取得、ステータス変化などの何かしらの変化があるシーン。
 */
@Data
public class EffectScene extends StoryScene {
    /** 影響を与える項目の記号 */
    private String kigo;
    /** 対象の項目を増減する */
    private String ope;
    /** 対象物の名前 */
    private String name;
    /** 対象物の個数 */
    private int kosu;

    public EffectScene(String sceneIdx, String sceneType) {
        super(sceneIdx, sceneType);
    }

    /**
     * ストーリーテキスト内の次のような定義を読み込み、フィールドに対象になる「項目」「増減」「名前」「個数」を指定する。
     * <effect:MNY+100>
     * -> 金額を１００追加する
     *
     * <effect:POI+1>
     * -> 毒を1人が受ける
     *
     * <effect:ITM+やくそう1>
     * -> 薬草を一つ取得
     *
     * <effect:ITM-たんけん3>
     * -> たんけんを3つなくす
     * @return
     * @throws Exception
     */
    @Override
    public boolean playScene() throws Exception {
        super.playScene();
        PlayerParty party = RpgConfig.getInstance().getParty();
        // TODO-[エフェクトシーンの実装内容を設計する#]

        return false;
    }
}
