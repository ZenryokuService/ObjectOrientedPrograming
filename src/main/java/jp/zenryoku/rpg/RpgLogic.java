package jp.zenryoku.rpg;

import jp.zenryoku.rpg.charactors.monsters.Monster;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.*;
import jp.zenryoku.rpg.data.items.RpgItem;
import jp.zenryoku.rpg.data.job.RpgCommand;
import jp.zenryoku.rpg.data.job.RpgJob;
import jp.zenryoku.rpg.data.shop.ItemShop;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.exception.StoryTextException;
import jp.zenryoku.rpg.factory.RpgDataFactory;
import jp.zenryoku.rpg.scene.*;
import jp.zenryoku.rpg.util.CalcUtils;
import jp.zenryoku.rpg.util.CheckerUtils;
import jp.zenryoku.rpg.util.StringUtils;
import jp.zenryoku.rpg.util.XmlUtils;
import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * TextRpgLocigの親クラス、ストーリーテキストの読み込み、シーンオブジェクトの生成を行う。
 * @see jp.zenryoku.rpg.TextRpgLogic
 */
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
        // コマンドリストのロード
        loadCommands();
        // 職業リストのロード
        loadJobs();
        // パラメータマップ(設定情報)の読み込み
        try {
            loadParamMaps();
        } catch (RpgException | IOException | StoryTextException e) {
            System.out.println("cpmf.txtの読み込みに失敗しました。");
            e.printStackTrace();
            System.exit(-1);
        }
        // モンスターリストの読み込み
        loadMonsters();
        // ストーリー.txtの読み込み
        BufferedReader story = getBufferedReader("src/main/resources/story", "Sample_story.txt");
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
            loadScenes();
            //setStoryData(storyTxt);
            // 最初のシーンを設定する
            scene = sceneList.get(0);
        } catch (IOException ie) {
            ie.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("ストーリーテキストが不適切です。");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * ストーリーテキストを読み込み、シーンオブジェクトを生成する、ファクトリーメソッド。
     * <b>キーメソッド</b><br>
     * <ul>
     *     <li>ストーリーテキストの上部にのみコメントを記述できる。またコメントはthis.commentListにセットされる。</li>
     *     <li>改行コードのみの行はスキップする</li>
     *     <li>「\<シーン番号:シーンタイプ\>」と記述した行からストーリー内容として認識する。</li>
     *     <li>「END_SCENE」と記述した行でシーンの終わりを示す。「END_SCENE 1」のように次のシーンをしてすることができる。<br>また、「END_SCENE」のみの場合はストーリーの終了を示す。</li>
     *     <li>同様に、「END_SCENE オプション」O=ゲームオーバー, C=クリア、ゲームの進行状況は自動保存されます。</li>
     * </ul>
     * ※ resources/story/SampleRpg_story.txtにテキストの書き方を記載。
     *
     * @param storyTxt　ストーリーテキスト
     * @throws Exception 想定外のエラー
     * @see jp.zenryoku.rpg.data.ParamGenerator
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
        // パラメータ設定クラス
        ParamGenerator generator = ParamGenerator.getInstance();
        // 取得した1行分のデータ
        String line = null;
        try {
            // コメントフラグ:テキストのはじめのみに記載することができる
            boolean isComment = true;
            // 選択肢
            boolean isSelectLine = false;

            while ((line = storyTxt.readLine()) != null) {
                // コメントの取得
                if (CheckerUtils.isCommentLine(line) && isComment) {
                    loadCommentLine(storyTxt, commentList);
                    continue;
                }
                // コメント行が終了
                isComment = false;

                // コメント行を飛ばす 改行コードのみの場合
                if (CheckerUtils.isCommentLine(line) || CheckerUtils.isEmpptyOrSep(line)) {
                    continue;
                }
                // カテゴリマスターの生成
                if (line.equals("CONFIG_MASTER")) {
                    generator.createMasterCategory(storyTxt);
                    continue;
                }
                // 設定オブジェクトの生成
                if (line.equals("CONFIG_PARAM")) {
                    generator.createParam(storyTxt);
                    continue;
                }
                if (line.equals("CONFIG_STATUS")) {
                    generator.createStatus(storyTxt);
                    continue;
                }
                if (line.equals("CONFIG_ITEM")) {
                    generator.createItemTypeMap(storyTxt);
                    continue;
                }
                if (line.equals("CONFIG_FORMULA")) {
                    generator.createFormulaMap(storyTxt);
                    continue;
                }
                if (line.equals("ITEM_LIST")) {
                    generator.createItemMap(storyTxt);
                    continue;
                }
                if (line.equals("CONFIG_ST_EFFECT")) {
                    generator.createEffects(storyTxt);
                    continue;
                }

                //// 参照渡しを使用せず、値渡しで実装する ////
                // シーン開始行の判定
                if (CheckerUtils.isStartSceneLine(line)) {
                    sceneObj = loadScene(line, rpgSceneList);
                    isSelectLine = false;
                    continue;
                }
                // これ以降は、シーンオブジェクトの生成済みの必要がある
                throwNullPoiinter(sceneObj, "シーンオブジェクトの生成ができていません。" + line);
                // 選択肢ありの場合
                if (CheckerUtils.isStartSelectNextScene(line)) {
                    setSelections(line, sceneObj);
                    isSelectLine = true;
                    continue;
                }
                // アイテムショップの設定
                if (CheckerUtils.isStartShopScene(line)) {
                    setItemShop(line, storyTxt, sceneObj);
                    continue;
                }

                // エフェクトシーンの設定
                if (CheckerUtils.isStartEffectScene(line) || CheckerUtils.isStartWithTSEffectScene(line)) {
                    if (isDebug) System.out.println("*** " + line + " ***");
                    setEffectScene(line, storyTxt, sceneObj);
                    continue;
                }

                // バトルシーンの設定
                if (CheckerUtils.isStartBattleScene(line)) {
                    System.out.println("*** " + line + " ***");
                    setBattleScene(line, storyTxt, sceneObj);
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

    /**
     * コメント行の追加
     * @param storyTxt ストーリーテキスト
     * @param commentList コメントリスト
     * @throws IOException 想定外のエラー
     */
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
     * @throws RpgException 想定外のエラー
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
     * モンスターリストを読み込み、RpgConfigに設定する。
     */
    private void loadMonsters() {
        try {
            List<Monster> list = XmlUtils.loadMonsters();
            RpgConfig.getInstance().setMonsterList(list);
        } catch (RpgException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void loadJobs() {
        try {
            Map<String, RpgJob> map = XmlUtils.loadJobs();
            if (isDebug) System.out.println("mapSize: " + map.size());
            RpgConfig.getInstance().setJobMap(map);
        } catch (RpgException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void loadCommands() {
        try {
            Map<String, RpgCommand> map = XmlUtils.loadCommands();
            RpgConfig.getInstance().setCommandMap(map);
        } catch (RpgException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void loadParamMaps() throws RpgException, IOException, StoryTextException {
        // コメントの行リスト
        List<String> commentList = new ArrayList<>();
        // パラメータ設定クラス
        ParamGenerator generator = ParamGenerator.getInstance();
        BufferedReader storyTxt = getBufferedReader("src/main/resources", "conf.txt");
        String line = null;
        try {
            // コメントフラグ:テキストのはじめのみに記載することができる
            boolean isComment = true;
            // 選択肢
            boolean isSelectLine = false;

            while ((line = storyTxt.readLine()) != null) {
                // コメントの取得
                if (CheckerUtils.isCommentLine(line) && isComment) {
                    loadCommentLine(storyTxt, commentList);
                    continue;
                }
                // コメント行が終了
                isComment = false;

                // コメント行を飛ばす 改行コードのみの場合
                if (CheckerUtils.isCommentLine(line) || CheckerUtils.isEmpptyOrSep(line)) {
                    continue;
                }
                // カテゴリマスターの生成
                if (line.equals("CONFIG_MASTER")) {
                    generator.createMasterCategory(storyTxt);
                    continue;
                }
                // 設定オブジェクトの生成
                if (line.equals("CONFIG_PARAM")) {
                    generator.createParam(storyTxt);
                    continue;
                }
                if (line.equals("CONFIG_STATUS")) {
                    generator.createStatus(storyTxt);
                    continue;
                }
                if (line.equals("CONFIG_ITEM")) {
                    generator.createItemTypeMap(storyTxt);
                    continue;
                }
                if (line.equals("CONFIG_FORMULA")) {
                    generator.createFormulaMap(storyTxt);
                    continue;
                }
                if (line.equals("ITEM_LIST")) {
                    generator.createItemMap(storyTxt);
                    continue;
                }
                if (line.equals("CONFIG_ST_EFFECT")) {
                    generator.createEffects(storyTxt);
                    continue;
                }
            }
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

    public void loadScenes() throws RpgException, IOException, StoryTextException {
        // パラメータ設定クラス
        ParamGenerator generator = ParamGenerator.getInstance();
        BufferedReader storyTxt = getBufferedReader("src/main/resources/story", "Sample_story.txt");
        // シーンオブジェクトのリスト
        List<RpgScene> rpgSceneList = new ArrayList<>();
        // シーンオブジェクト
        RpgScene sceneObj = null;

        // 取得した1行分のデータ
        String line = null;
        // ストーリーテキストの行数
        int lineIdx = 0;
        // 読み込み行のカウント
        int lineCount = 0;

        try {
            // 選択肢
            boolean isSelectLine = false;

            while ((line = storyTxt.readLine()) != null) {
                //// 参照渡しを使用せず、値渡しで実装する ////
                // シーン開始行の判定
                if (CheckerUtils.isStartSceneLine(line)) {
                    sceneObj = loadScene(line, rpgSceneList);
                    isSelectLine = false;
                    continue;
                }
                // これ以降は、シーンオブジェクトの生成済みの必要がある
                throwNullPoiinter(sceneObj, "シーンオブジェクトの生成ができていません。" + line);
                // 選択肢ありの場合
                if (CheckerUtils.isStartSelectNextScene(line)) {
                    setSelections(line, sceneObj);
                    isSelectLine = true;
                    continue;
                }
                // アイテムショップの設定
                if (CheckerUtils.isStartShopScene(line)) {
                    setItemShop(line, storyTxt, sceneObj);
                    continue;
                }

                // エフェクトシーンの設定
                if (CheckerUtils.isStartEffectScene(line) || CheckerUtils.isStartWithTSEffectScene(line)) {
                    if (isDebug) System.out.println("*** " + line + " ***");
                    setEffectScene(line, storyTxt, sceneObj);
                    continue;
                }

                // バトルシーンの設定
                if (CheckerUtils.isStartBattleScene(line)) {
                    System.out.println("*** " + line + " ***");
                    setBattleScene(line, storyTxt, sceneObj);
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
     * @throws RpgException 想定外のエラー
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
     * @throws RpgException 想定外のエラー
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
     * 選択肢とその選択による、次のシーンインデックスをシーンオブジェクトに設定する。
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
            if (data.length != RpgConst.ITEM_SIZE) {
                throw new RpgException(MessageConst.ERR_ITEM_CSV.toString());
            }
            // アイテム名(アイテムリストのキーになっている)
            RpgData i = itemMap.get(data[0].trim());
            if ((i instanceof RpgItem) == false) {
                throw new RpgException(MessageConst.ERR_SETTING_OBJECT.toString()
                        + ": " + i.getClass().getName());
            }
            RpgItem itemData = (RpgItem) i;
            itemData.setMoney(Integer.parseInt(data[1].trim()));
            itemData.setDiscription(data[2].trim());
            shop.addItemList(itemData);
        }
        // 改めてsceneObjにセットする
        sceneObj = scene;
    }

    /**
     * エフェクトシーンの生成を行う。
     * @param line ストーリーテキストの１行
     * @param txt 続きのストーリーテキスト
     * @param sceneObj シーンオブジェクト
     * @throws IOException　ファイルの読み込みエラー
     * @throws RpgException　設定エラー
     */
    private void setEffectScene(String line, BufferedReader txt, RpgScene sceneObj) throws IOException, RpgException {
        if ((sceneObj instanceof EffectScene) == false) {
            throw new RpgException(MessageConst.SCENE_TYPE_ERR.toString());
        }
        // 1行目の読み込み<effect:XXX[+\\-]NUM>
        // 記号 + (プラス) or -(マイナス) なまえ 個数の指定
        String effect = line.split(":")[1];
        effect = effect.substring(0, effect.length() - 1);
        if (isDebug) System.out.println("** " + effect + " ** ");
        EffectScene scene = (EffectScene) sceneObj;

        //// ターン指定がある場合の「ZHP+10%TS3」のような効果式の場合 ////
        if (CheckerUtils.isTS(effect)) {
            // 効果式のリストをプレーヤーにセット
            List<Effects> eff = RpgDataFactory.createEffectAppear(effect);
            scene.setEffList(eff);
            String gyo = null;
            while ((gyo = txt.readLine()).equals("</effect>") == false) {
                // TODO-[エフェクトシーンの仕様を考える]
            }
            return;
        }

        //// 永続、一回のみの「ZHP+10」のような効果式の場合 ////
        // 記号
        String kigo = StringUtils.findKigo(effect);
        if (kigo == null) {
            throw new RpgException(MessageConst.ERR_EFFECT_SCENE_CONF.toString() + ": " + effect);
        }
        scene.setKigo(kigo);
        // 演算子(+ or -)
        String ope = StringUtils.findOperator(effect);
        scene.setOpe(ope);

        int res = CheckerUtils.indexOfNum(effect);
        // 個数の部分をセット
        scene.setKosu(Integer.parseInt(effect.substring(res)));

        String gyo = null;
        while ((gyo = txt.readLine()).equals("</effect>") == false) {
            // TODO-[エフェクトシーンの仕様を考える]
        }
        // 改めてsceneObjにセットする
        sceneObj = scene;
    }

    /**
     * バトルシーンの設定を行う。
     * @param line ストーリーテキストの１行
     * @param txt ストーリーテキスト
     * @param sceneObj シーンオブジェクト
     * @throws IOException ファイルの読み込みエラー
     * @throws RpgException 設定エラー
     */
    private void setBattleScene(String line, BufferedReader txt, RpgScene sceneObj) throws IOException, RpgException {
        String[] vals = StringUtils.findMonsterNo(line);
        BattleScene battleScene = (BattleScene) sceneObj;
        List<Monster> monsterList = RpgConfig.getInstance().getMonsterList();
        if (monsterList.size() <= 0) {
            throw new RpgException(MessageConst.NO_MONSTERS.toString());
        }
        System.out.println(monsterList);
        // モンスター指定
        if (vals != null && vals.length == 1) {
            int val = Integer.parseInt(vals[0]);
            battleScene.setMonster(monsterList.get(val));
        } else if (vals != null && vals.length == 2) {
            // 範囲指定でランダム取得
            int start = Integer.parseInt(vals[0]);
            int end = Integer.parseInt(vals[1]);
            // 通常の定義は配列としてみていないので、0から数えるためにー１する。
            int v = CalcUtils.getInstance().generateRandom(start, end);
            battleScene.setMonster(monsterList.get(v));
        } else {
            throw new RpgException(MessageConst.ERR_MONSTER_NO.toString() + ": " + line);
        }
        String text = null;
        try {
            while ((text = txt.readLine()).equals("</monster>") == false) {
                // TODO-[タグの中身を考える]
            }
        } catch (IOException e) {
            throw new RpgException(MessageConst.ERR_IOEXCEPTION.toString());
        }
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
     * @param message エラーメッセージ
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
     * @throws RpgException 想定外のエラー
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

    /**
     * 「次へ」のメッセージを表示しない
     * @return true: 非表示 false: 表示する
     */
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
     * @return 用途なし
     *
     */
    @Override
    @Deprecated
    public boolean updateData(String input) { return false; }

    /**
     *  各シーンを実行する<br>。
     *  各シーンの実行後、実行中にRpgConstのステータスを設定する必要がある。<br>
     *  <b>例:</b><br>
     *  this.status = RpgConst.SAVE; // 保存して終了する
     *      *  this.status = RpgConst.CLEAR; // ゲームクリアして終了する
     *  this.status = RpgConst.NEXT; // 自動的に次のシーンへ移動する
     * @return true: ゲームを終了する false: ゲームを続ける
     * @throws Exception 想定外のエラー
     */
    public abstract boolean executeScene() throws Exception;

}
