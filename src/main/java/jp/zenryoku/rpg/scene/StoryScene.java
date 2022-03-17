package jp.zenryoku.rpg.scene;

import jp.zenryoku.rpg.RpgScene;
import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.ParamGenerator;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.util.ConsoleUtils;
import jp.zenryoku.rpg.util.StringUtils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * テキストファイルを読み込み、"END_SCENE"の次に来るコマンドで、次のアクションを設定する。
 */
public class StoryScene extends RpgScene {


    public StoryScene() {
        super();
    }
    /**
     * コンストラクタ
     * @param sceneIdx シーンインデックス
     * @param sceneType シーンタイプ
     */
    public StoryScene(String sceneIdx, String sceneType) {
        super(sceneIdx, sceneType);
    }

    @Override
    public void initScene() {
        isSelectNextScene = false;
        skipNextMessage = false;
    }

    @Override
    public boolean playScene() throws Exception {

        if (isDebug) System.out.println("SceneIdx: " + super.sceneIndex + "  SceneType: " + super.sceneType);
        // TODO-[シーンタイプごとに処理を分ける必要なし削除予定]

        // シーンタイプごとに処理を分ける
        if ("A".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
            //textList.forEach(value -> { System.out.println(value); });
            // 選択肢ありフラグが立っているとき
            if (isSelectNextScene) {
                // 選択により次のシーンを設定する
                boolean isOK = false;
                String selected = null;

                // 入力チェックを行う
                selected = ConsoleUtils.getInstance().acceptInput("選択肢を選んでください。", selectCount);
                nextIndex = nextIndexes[Integer.parseInt(selected) - 1];
                int nextIdx = Integer.parseInt(nextIndex);
                if (nextIdx < 0) {
                    nextIndex = String.valueOf(nextIdx);
                }
                System.out.println(sceneSize);
                System.out.println(nextIndex);
                skipNextMessage = true;
            }
        } else if ("B".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("C".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("D".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("E".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("F".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("G".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("H".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("I".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("J".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("K".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("L".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("M".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("N".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("O".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("P".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("Q".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("R".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("S".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("T".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("U".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("V".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("W".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("X".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("Y".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
        } else if ("Z".equals(sceneType)) {
            // ストーリーを表示する
            textList.forEach(value -> { System.out.println(value); });
            //ParamGenerator
        } else {
            throwRpgException("ストーリーテキストの設定が不適切です。");
        }
        return false;
    }

    /** 表示する行数をRpgConst#PRINT_LINEに設定する */
    protected void printStory() throws RpgException {
        int count = 0;
        int printLineNo = RpgConfig.getInstance().getPrintLine();
        // ストーリーを表示する
        for (String text : textList) {
            String printLine = convertText(text);
            System.out.println(printLine);
            if (printLineNo != 0 && count > printLineNo) {
                ConsoleUtils.getInstance().acceptInput("<次へ>", false);
                count = 0;
            }
            count++;
        };
    }

    /**
     * 変換するプロパティの書き方を固定する。
     * ・プレーヤー名: Player.name
     * ・MNY: MNY
     * @param text 表示する文字列(ストーリーテキスト1行分)
     * @return 返還後の文字列(1行分)
     */
    private String convertText(String text) throws RpgException {
        String res = null;
        PlayerCharactor player = PlayerParty.getInstance().getPlayer();
        if (player != null) {
            //System.out.println("*** Testing ***");
            // プレーヤー名の変換
            res = StringUtils.convertProperty(text, "Player.name", player.getName());
        } else {
            res = text;
        }
        // 通貨
        res = StringUtils.convertProperty(res, "MNY", RpgConst.getMnyName());
        return res;
    }
}
