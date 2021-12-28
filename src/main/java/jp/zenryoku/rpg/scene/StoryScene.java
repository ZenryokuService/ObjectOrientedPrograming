package jp.zenryoku.rpg.scene;

import jp.zenryoku.rpg.RpgScene;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.util.ConsoleUtils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * テキストファイルを読み込み、"END_SCENE"の次に来るコマンドで、次のアクションを設定する。
 */
public class StoryScene extends RpgScene {

    public StoryScene(String sceneIdx, String sceneType) {
        super(sceneIdx, sceneType);
    }

    @Override
    public void initScene() {

    }
    @Override
    public boolean playScene() {
        if (isDebug) System.out.println("SceneIdx: " + super.sceneIndex + "  SceneType: " + super.sceneType);

        // シーンタイプごとに処理を分ける
        if ("A".equals(sceneType)) {
            textList.forEach(value -> {
                System.out.println(value + SEP);
            });
        } else {

        }
        return false;
    }
}
