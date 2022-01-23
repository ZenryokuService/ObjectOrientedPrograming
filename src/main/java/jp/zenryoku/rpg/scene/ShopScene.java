package jp.zenryoku.rpg.scene;

import jp.zenryoku.rpg.RpgScene;

public class ShopScene extends StoryScene {
    public ShopScene(String sceneIdx, String sceneType) {
        super(sceneIdx, sceneType);
    }

    @Override
    public boolean playScene() throws Exception {
        // ストーリーテキストの内容を表示
        super.playScene();

        return false;
    }
}
