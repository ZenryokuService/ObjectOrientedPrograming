package jp.zenryoku.rpg;

import jp.zenryoku.rpg.Games;
import jp.zenryoku.rpg.RpgScene;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.*;
import jp.zenryoku.rpg.data.shop.ItemShop;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.exception.StoryTextException;
import jp.zenryoku.rpg.scene.*;
import jp.zenryoku.rpg.util.CheckerUtils;
import lombok.Data;
import org.nd4j.shade.jackson.databind.deser.impl.CreatorCandidate;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Data
public abstract class RpgLogic implements Games {
    /** デバックフラグ */
    public final boolean isDebug = false;
    /** 改行コード */
    protected String SEP;
    /** 標準入力 */
    protected Scanner scan;
    /** タイトル画面デザイン */
    protected BufferedReader reader;
    /** 終了ステータス */
    protected RpgConst status;
    /** 現在のシーン */
    protected RpgScene scene;
    /** 使用するシーンのリスト */
    protected List<RpgScene> sceneList;
    /** コメントリスト */
    protected List<String> commentList;
    /** ゲームの進行度保存リスト */
    protected AdventureRecord record;
    /** シーン継続中フラグ */
    protected boolean isSceneStarted;
    /** 「次へ」のメッセージを表示しない */
    protected boolean skipNextMessage;

    public RpgLogic() {
        // 改行コード
        SEP = System.lineSeparator();
        // 標準入力
        scan = new Scanner(System.in);
        // 冒険の記録
        record = new AdventureRecord();
        // シーンリスト
        sceneList = new ArrayList<>();

        // title.txtの読み込み
        reader = getBufferedReader("src/main/resources", "title.txt");
        // ストーリー.txtの読み込み
        BufferedReader story = getBufferedReader("resources/story", "SampleRpg_story.txt");
        // シーンオブジェクトの生成
        createSceneObject(story);
        // シーン開始フラグの初期化
        isSceneStarted = false;
    }

    /**
     * テキストファイルを読み込み、RpgSceneオブジェクトを生成する。
     *
     * @param storyTxt 対象のストーリーテキストファイル
     */
    private void createSceneObject(BufferedReader storyTxt) {
        try {
            setStoryData(storyTxt);
            // 最初のシーンを設定する
            scene = sceneList.get(0);
        } catch (IOException ie) {
            ie.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("ストーリーテキストが不適切です。");
            e.printStackTrace();
        }
    }

    /**
     * ストーリーテキストを読み込み、シーンオブジェクトを生成する、ファクトリーメソッド。
     * <b>キーメソッド</b><br/>
     * <ul>
     *     <ol>ストーリーテキストの上部にのみコメントを記述できる。またコメントはthis.commentListにセットされる。</ol>
     *     <ol>改行コードのみの行はスキップする</ol>
     *     <ol>「\<シーン番号:シーンタイプ\>」と記述した行からストーリー内容として認識する。</ol>
     *     <ol>「END_SCENE」と記述した行でシーンの終わりを示す。「END_SCENE 1」のように次のシーンをしてすることができる。<br/>また、「END_SCENE」のみの場合はストーリーの終了を示す。</ol>
     *     <ol>同様に、「END_SCENE オプション」O=ゲームオーバー, C=クリア、ゲームの進行状況は自動保存されます。</ol>
     * </ul>
     * ※ resources/story/SampleRpg_story.txtにテキストの書き方を記載。
     *
     * @param storyTxt　ストーリーテキスト
     * @return 生成したシーンオブジェクト
     */
    private void setStoryData(BufferedReader storyTxt) throws Exception {
        // シーンオブジェクトのリスト
        List<RpgScene> rpgSceneList = new ArrayList<>();
        // シーンオブジェクト
        RpgScene sceneObj = null;
        // ストーリーテキストの行数
        int lineIdx = 0;
        // 読み込み行のカウント
        int lineCount = 0;

        // コメントの行リスト
        List<String> commentList = new ArrayList<>();
        // 取得した1行分のデータ
        String line = null;
        try {
            // コメントフラグ:テキストのはじめのみに記載することができる
            boolean isComment = true;
            // 選択肢
            boolean isSelectLine = false;

            while ((line = storyTxt.readLine()) != null) {
                // コメントの取得
                if (line.startsWith("# ") && isComment) {
                    loadCommentLine(storyTxt, commentList);
                    continue;
                }
                // コメント行が終了
                isComment = false;

                // 改行コードのみの場合
                if (line.equals("") || line.equals(SEP)) {
                    continue;
                }
                // 設定オブジェクトの生成
                if (line.equals("CONFIG_PARAM")) {
                    ParamGenerator.getInstance().createParam(storyTxt);
                    continue;
                }
                if (line.equals("CONFIG_STATUS")) {
                    ParamGenerator.getInstance().createStatus(storyTxt);
                    continue;
                }
                if (line.equals("CONFIG_ITEM")) {
                    ParamGenerator.getInstance().createItemTypeMap(storyTxt);
                    continue;
                }
                if (line.equals("CONFIG_FORMULA")) {
                    ParamGenerator.getInstance().createFormulaMap(storyTxt);
                    continue;
                }
                if (line.equals("CONFIG_JOB")) {
                    ParamGenerator.getInstance().createJobMap(storyTxt);
                    continue;
                }
                if (line.equals("ITEM_LIST")) {
                    ParamGenerator.getInstance().createItemMap(storyTxt);
                    continue;
                }

                //// ファイル読み込み処理は、参照渡しを使用せず、値渡しで実装する ////
                // シーン開始行の判定
                if (line.matches("[0-9]{0,1000}:[A-Z]")) {
                    sceneObj = loadScene(line, rpgSceneList);
                    isSelectLine = false;
                    continue;
                }
                // これ以降は、シーンオブジェクトの生成済みの必要がある
                throwNullPoiinter(sceneObj, "シーンオブジェクトの生成ができていません。" + line);
                // 選択肢ありの場合
                if (line.matches("\\<[1-9]\\:[1-9]\\>")) {
                    setSelections(line, sceneObj);
                    isSelectLine = true;
                    continue;
                }
                // アイテムショップの設定
                if (line.matches("\\<item\\:[a-zA-Z]{3,10}\\>")) {
                    setItemShop(line, storyTxt, sceneObj);
                    continue;
                }

                // ストーリーテキストのシーン終了部分
                if (line.startsWith("END_SCENE ")) {
                    sceneObj = finishSceneSetting(line, sceneObj);
                    isSelectLine = false;
                    continue;
                }
                // 選択肢の行
                if (isSelectLine) {
                    setKomokuAndNextIndex(line, sceneObj);
                }
                // ストーリーテキストの追加
                sceneObj.getTextList().add(line);
                lineCount++;
            }
            sceneList = rpgSceneList;
        } catch (RpgException re) {
            System.out.println("ストーリーテキストの読み込み中にエラーがありました。: " + re.getMessage() + SEP + line);
            re.printStackTrace();
        } catch (IOException ie) {
            System.out.println("ストーリーテキストの読み込みに失敗しました。。: " + ie.getMessage() + SEP + line);
            ie.printStackTrace();
            throw ie;
        } catch (Exception e) {
            e.printStackTrace();
            throw new StoryTextException("ストーリーテキスト読み込み中の想定外のエラーがありました。" + e.getMessage() + SEP + line);
        }
    }

    /** コメント行の追加 */
    private void loadCommentLine(BufferedReader storyTxt, List<String> commentList) throws IOException {
        String line = null;
        while((line = storyTxt.readLine()).startsWith("# ")) {
            commentList.add(line);
        }
    }

    /**
     * シーンオブジェクトを生成処理を呼び出し、シーンリストに追加する。以下の手順で行う。
     * 1. ストーリーテキストのシーン開始行から「シーンIndex:シーンタイプ」を取得したとき
     * 2. シーンオブジェクトを生成処理を呼び出す
     * 3. シーンリストに追加
     * @param line ストーリーテキストの「シーンIndex:シーンタイプ」の行
     * @param rpgSceneList シーンリスト
     * @return シーンオジェクト
     * @throws RpgException
     */
    private RpgScene loadScene(String line, List<RpgScene> rpgSceneList) throws RpgException {
        // プレフィックス「シーンIndex:シーンタイプ」
        String[] preArr = line.split(":");
        // シーンインデックス
        String sceneIndex = preArr[0];
        // シーンタイプ
        String sceneType = preArr[1];

        if (isDebug) System.out.println("sceneIndex: " + sceneIndex + " sceneType: " + sceneType);

        // シーンオブジェクト
        RpgScene sceneObj = createRpgScene(sceneIndex, sceneType);
        rpgSceneList.add(sceneObj);
        return sceneObj;
    }

    /**
     * 選択肢をシーンオブジェクトに設定する。
     * @param line ストーリーテキストの選択肢を指定する行
     * @param sceneObj シーンオブジェクト
     * @return シーンオブジェクト
     */
    private RpgScene setSelections(String line, RpgScene sceneObj) {
        int selectCount = Integer.parseInt(String.valueOf(line.charAt(3)));
        if (isDebug) System.out.println("*** " + selectCount + " ***");
        sceneObj.setSelectNextScene(true);
        sceneObj.setSelectCount(selectCount);
        sceneObj.createSelectSceneArray(selectCount);
        return scene;
    }
    /**
     * RPGシーンオブジェクトを生成する。
     * プレフィックスから生成するので、ストーリーの中身は取得できていない。
     *
     * @param sceneIndex シーンインデックス
     * @param sceneType シーンタイプ
     * @return シーンオブジェクト
     */
    private RpgScene createRpgScene(String sceneIndex, String sceneType) throws RpgException {

        RpgScene sceneObj = null;
        // シーンタイプにより生成するシーンクラスを変更
        if (RpgConst.SENE_TYPE_NEXT.getSceneType().equals(sceneType)) {
            // ストーリーシーンオブジェクトの生成
            sceneObj = new StoryScene(sceneIndex, sceneType);
        } else if (RpgConst.SENE_TYPE_SHOP.getSceneType().equals(sceneType)) {
            // ショップシーンオブジェクトの生成
            sceneObj = new ShopScene(sceneIndex, sceneType);
        } else if (RpgConst.SENE_TYPE_STATUS.getSceneType().equals(sceneType)) {
            // パーティーステータスシーンオブジェクトの生成
            sceneObj = new EffectScene(sceneIndex, sceneType);
        } else if (RpgConst.SENE_TYPE_GET.getSceneType().equals(sceneType)) {
            // アイテムの取得シーンオブジェクトの生成
            sceneObj = new EffectScene(sceneIndex, sceneType);
        } else if (RpgConst.SENE_TYPE_END_GAME.getSceneType().equals(sceneType)) {
            // 終了シーンオブジェクトの生成。シーンタイプをセットする。
            sceneObj = new StoryScene(sceneIndex, sceneType);
            sceneObj.setSceneType(RpgConst.SENE_TYPE_END_GAME.getSceneType());
        } else if (RpgConst.SENE_TYPE_BATTLE.getSceneType().equals(sceneType)) {
            // バトルシーンオブジェクトの生成
            sceneObj = new BattleScene(sceneIndex, sceneType);
        } else if (RpgConst.SCENE_TYPE_CREATE.getSceneType().equals(sceneType)) {
            // プレーヤー生成シーンオブジェクトの生成
            sceneObj = new CreatePlayerScene(sceneIndex, sceneType);
        } else {
            throw new RpgException(MessageConst.SCENE_TYPE_ERR.toString() + sceneType);
        }
        return sceneObj;
    }

    /**
     * 「END_SCENE」の行を取得したときの処理。
     * 次のシーンインデックスをシーンオブジェクトに設定する。
     * @param line　ストーリーテキストの1行
     * @param sceneObj シーンオブジェクト
     * @return シーンオブジェクト
     * @throws RpgException
     */
    private RpgScene finishSceneSetting(String line, RpgScene sceneObj) throws RpgException {
        if (line.contains(" ") == false) {
            throw new RpgException(MessageConst.END_SCENE_ERR.toString());
        }
        String[] last = line.split(" ");
        sceneObj.setNextIndex(last[1]);
        return sceneObj;
    }

    /**
     * 選択肢と次のシーンインデックスをシーンオブジェクトに設定する。
     * @param line 選択肢行
     * @param sceneObj シーンオブジェクト
     * @return シーンオブジェクト
     */
    private RpgScene setKomokuAndNextIndex(String line, RpgScene sceneObj) {
        String komokuNo = String.valueOf(line.charAt(0));
        String selectedNextIndex = line.split(" ")[2];
        if (isDebug) System.out.println("項目番号: " + komokuNo + " 選択肢: " + selectedNextIndex);
        sceneObj.setNextIndexes(komokuNo, selectedNextIndex);
        return sceneObj;
    }
    /**
     * 指定したファイルを読み込む、BufferedReaderを取得する。
     *
     * @param path ディレクトリのパス
     * @param fileName ファイル名
     * @return 対象のファイルリーダー
     */
    private BufferedReader getBufferedReader(String path, String fileName) {
        BufferedReader buf = null;
        try {
            buf = Files.newBufferedReader(Paths.get(path, fileName));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return buf;
    }

    private void setItemShop(String line, BufferedReader txt, RpgScene sceneObj) throws IOException, RpgException {
        if ((sceneObj instanceof ShopScene) == false) {
            throw new RpgException(MessageConst.SCENE_TYPE_ERR.toString());
        }
        String shopName = line.split(":")[1];
        String items = null;
        RpgConfig conf = RpgConfig.getInstance();
        ShopScene scene = (ShopScene) sceneObj;
        Map<String, RpgData> itemMap = conf.getItemMap();
        // アイテムショップインスタンス
        ItemShop shop = new ItemShop(shopName);
        scene.setShop(shop);
        // データはカンマ区切りになっている
        while ((items = txt.readLine()).equals("</item>") == false) {
            if (isDebug) System.out.println(items);
            // 設定オブジェクトよりデータ取得
            String[] data = items.split(",");
            if (data.length != 2) {
                throw new RpgException(MessageConst.ERR_ITEM_CSV.toString());
            }
            // アイテム名(アイテムリストのキーになっている)
            RpgData i = itemMap.get(data[0]);
            if ((i instanceof RpgItem) == false) {
                throw new RpgException(MessageConst.ERR_SETTING_OBJECT.toString()
                        + ": " + i.getClass().getName());
            }
            RpgItem itemData = (RpgItem) i;
            shop.addItemList(itemData);
        }
        // 改めてsceneObjにセットする
        sceneObj = scene;
    }
    /**
     * 入力受付処理。
     * ※JavaAPIを呼び出すだけなので、テスト不要。
     */
    @Override
    public String acceptInput() {
        // 入力受付を返却する(一行分)
        return scan.nextLine();
    }

    /**
     * オブジェクトがNullの場合は例外を投げる。
     * @param obj 検査対象のオブジェクト
     * @paran message エラーメッセージ
     * @throws StoryTextException
     */
    private void throwNullPoiinter(Object obj, String message) throws StoryTextException {
        if (obj == null) {
            throw new StoryTextException(message);
        }
    }

    /**
     * 次のシーンインデックスをシーンリストから取得
     * 取得したシーンオブジェクトに次のシーンインデックスを設定する。
     * @param next 次のシーンインデックス
     * @throws RpgException
     */
    protected void setNextScene(String next) throws RpgException {
        RpgScene nextScene = getSceneList().get(Integer.parseInt(next));
        if (nextScene == null) {
            if (isDebug) System.out.println("シーンインデックスが不適切です。: " + next);
            throw new RpgException("シーンインデックスが不適切です。: " + next);
        }
        // シーンを次のものに切り替える
        scene = nextScene;
    }
    /** 「次へ」のメッセージを表示しない */
    public boolean getSkipNextMessage() { return skipNextMessage; }

    /**
     * Gameインターフェースのメソッドだが使用しないので
     * このクラスでオーバーライドする
     * @return
     */
    @Override
    @Deprecated
    public boolean render() { return false; }

    /**
     * Gameインターフェースのメソッドだが使用しないので
     * このクラスでオーバーライドする
     * @return
     */
    @Override
    @Deprecated
    public boolean updateData(String input) { return false; }

    /**
     *  各シーンを実行する<br/>。
     *  各シーンの実行後、実行中にRpgConstのステータスを設定する必要がある。<br/>
     *  <b>例:</b><br/>
     *  this.status = RpgConst.SAVE; // 保存して終了する
     *      *  this.status = RpgConst.CLEAR; // ゲームクリアして終了する
     *  this.status = RpgConst.NEXT; // 自動的に次のシーンへ移動する
     */
        public abstract boolean executeScene() throws Exception;

}
