package jp.zenryoku.rpg.scene;

public class SelectNextScene extends StoryScene {

    /**
     * コンストラクタ
     *
     * @param sceneIdx  シーンインデックス
     * @param sceneType シーンタイプ
     */
    public SelectNextScene(String sceneIdx, String sceneType) {
        super(sceneIdx, sceneType);
    }

    @Override
    public boolean playScene() throws Exception {
        // TODO-[実装方法検討中]
        System.out.println("実装方法検討中");
        return false;
    }
}
