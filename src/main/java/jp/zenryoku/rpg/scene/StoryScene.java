package jp.zenryoku.rpg.scene;

import jp.zenryoku.rpg.RpgScene;
import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.ParamGenerator;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgEvFlg;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.util.CheckerUtils;
import jp.zenryoku.rpg.util.ConsoleUtils;
import jp.zenryoku.rpg.util.StringUtils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
                if (isDebug) {
                    int count = 0;
                    for (String s : nextIndexes) {
                        System.out.print(count + "=next: " + s + " ");
                        count++;
                    }
                    System.out.println("select = " + selected);
                }
                nextIndex = nextIndexes[Integer.parseInt(selected) - 1];
                if (isDebug) System.out.println("nextIdx: " + nextIndex);
                int nextIdx = Integer.parseInt(nextIndex);
                if (nextIdx < 0) {
                    nextIndex = String.valueOf(nextIdx);
                }
                if (isDebug) System.out.println(sceneSize);
                if (isDebug) System.out.println(nextIndex);
                skipNextMessage = true;
            }
        } else if ("B".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("C".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("D".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("E".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("F".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("G".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("H".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("I".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("J".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("K".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("L".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("M".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("N".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("O".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("P".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("Q".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("R".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("S".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("T".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("U".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("V".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("W".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("X".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("Y".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else if ("Z".equals(sceneType)) {
            // ストーリーを表示する
            printStory();
        } else {
            throwRpgException("ストーリーテキストの設定が不適切です。");
        }
        return false;
    }

    /**
     * 表示する行数をRpgConst#PRINT_LINEに設定する
      * @return true: イベントフラグがあり、子クラスのplayScene()を実行しない false: 子クラスのplayScene()も実行する。
     * @throws RpgException 想定外のエラー
     */
    protected boolean printStory() throws RpgException {
        PlayerParty party = PlayerParty.getInstance();
        int count = 0;
        int printLineNo = RpgConfig.getInstance().getPrintLine();
        if (isDebug) System.out.println("EvFLg: " + evFlg);
        if (evFlg != null) {
            Map<String, String> playerKey = party.getEvflgKeyMap();
            Map<String, List<String>> evStoryMap = evFlg.getEvStoryMap();
            Map<String, String> evNextSceneMap = evFlg.getNextSceneMap();
            Set<String> keys = playerKey.keySet();
            List<String> story = null;
            String evNextScene = null;
            for (String key : keys) {
                if (evStoryMap.containsKey(key)) {
                    System.out.println("Contains Key: " + key);
                    story = evStoryMap.get(key);
                    evNextScene = evNextSceneMap.get(key);
                    break;
                }
            }
            if (story != null) {
                if (isDebug) System.out.println("story is");
                textList = story;
                setNextIndex(evNextScene);
            } else {
                if (isDebug) System.out.println("no story");
                System.out.println("evStoryMap" + evStoryMap);
                textList = evStoryMap.get(RpgConst.EV_FLG_NULL);
                setNextIndex(evNextSceneMap.get(RpgConst.EV_FLG_NULL));
            }
            // RpgLogic#readEvFlg()にてセットしたイベントフラグキー
            if (CheckerUtils.isEmpty(evFlg.getEvFlgId()) == false
                    && CheckerUtils.isEmpty(evFlg.getEvFlgKey()) == false) {
                // イベントフラグキーをプレーヤーのマップに登録
                party.getEvflgKeyMap().put(evFlg.getEvFlgId(), evFlg.getEvFlgKey());
            }
            party.getEvflgKeyMap().put(evFlg.getEvFlgId(), evFlg.getEvFlgKey());
        }
        // ストーリーを表示する
        for (String text : textList) {
            String printLine = convertText(text);
            System.out.println(printLine);
            if (printLineNo != 0 && count > printLineNo) {
                ConsoleUtils.getInstance().acceptInput("<次へ>", false);
                count = 0;
            }
            count++;
        }
        return false;
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
