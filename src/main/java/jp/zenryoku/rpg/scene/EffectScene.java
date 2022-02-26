package jp.zenryoku.rpg.scene;

import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.Effects;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.categry.RpgMaster;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.util.CheckerUtils;
import lombok.Data;

import java.util.Map;

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
     * \<effect:NIG+100\>
     * -> 金額を１００追加する
     *
     * \<effect:POI+1\>
     * -> 毒を1人が受ける
     *
     * \<effect:ITM+やくそう1\>
     * -> 薬草を一つ取得
     *
     * \<effect:ITM-たんけん3\>
     * -> たんけんを3つなくす
     * @return false　固定値
     * @throws Exception
     */
    @Override
    public boolean playScene() throws Exception {
        super.playScene();

        // TODO-[エフェクトシーンの実装内容を設計する#27]
        if (isDebug) System.out.println("kigo: " + kigo + " ope: " + ope + " kosu: " + kosu);

        Map<String, RpgData> dataMap = RpgConfig.getInstance().getParamMap();
        Map<String, RpgMaster> mstMap = RpgConfig.getInstance().getMasterMap();

        // CONFIG_PARAMにないときはマスタカテゴリを検索
        RpgData data = dataMap.get(kigo);
        RpgMaster mst = mstMap.get(kigo);
        if (mst == null && CheckerUtils.isDefaultStatusKigo(kigo)) {
            mst = mstMap.get(RpgConst.DEFAULT_STATUS_PREFIX + kigo);
        }
        if (data == null && mst == null) {
            throw new RpgException((MessageConst.ERR_NO_CONFIGS.toString() + ": " + kigo));
        }
        String name = data == null ? mst.getName() : data.getName();
        System.out.println("Debug: " + name + ope + kosu);

        // 効果を及ぼす

        return false;
    }
}
