package jp.zenryoku;

import jp.zenryoku.rpg.Games;
import jp.zenryoku.rpg.RpgScene;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.exception.StoryTextException;
import jp.zenryoku.rpg.scene.BattleScene;
import jp.zenryoku.rpg.scene.StoryScene;
import jp.zenryoku.rpg.util.CheckerUtils;
import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Data
public abstract class RpgLogic implements Games {
    /** でバックフラグ */
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
    List<RpgScene> sceneList;
    /** コメントリスト */
    List<String> commentList;

    public RpgLogic() {
        // 改行コード
        SEP = System.lineSeparator();
        scan = new Scanner(System.in);
        status = RpgConst.CLEAR;

        // title.txtの読み込み
        reader = getBufferedReader("src/main/resources", "title.txt");
        // ストーリー.txtの読み込み
        BufferedReader story = getBufferedReader("resources/story", "SampleRpg_story.txt");
        // シーンオブジェクトの生成
        createSceneObject(story);
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
     * ストーリーオブジェクトを生成する、ファクトリーメソッド。
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
        // ストーリーの行リスト
        List<String> storyList = new ArrayList<>();
        try {
            // 取得した1行分のデータ
            String line = null;
            // コメントフラグ:テキストのはじめのみに記載することができる
            boolean isComment = true;
            // シーンインデックス
            String sceneIndex = null;
            // シーンタイプ
            String sceneType = null;
            while ((line = storyTxt.readLine()) != null) {
                // コメントの取得
                if (line.startsWith("# ") && isComment) {
                    commentList.add(line);
                    continue;
                }
                // コメント行が終了
                isComment = false;

                // 改行コードのみの場合
                if (line.equals("") || line.equals(SEP)) {
                    continue;
                }
                // シーン開始行の判定
                if (line.matches("[0-9]{0,1000}:[A-Z]")) {
                    // プレフィックス「シーンIndex:シーンタイプ」
                    String[] preArr = line.split(":");
                    // シーンインデックス
                    sceneIndex = preArr[0];
                    // シーンタイプ
                    sceneType = preArr[1];

                    if (isDebug) System.out.println("sceneIndex: " + sceneIndex + " sceneType: " + sceneType);

                    // シーンオブジェクト
                    sceneObj = createRpgScene(sceneIndex, sceneType);
                    rpgSceneList.add(sceneObj);
                    setCommentList(commentList);
                    continue;
                }

                throwNullPoiinter(sceneObj, "シーンオブジェクトの生成ができていません。" + sceneIndex + ":" + sceneType);
                if (line.startsWith("END_SCENE ")) {
                    String[] last = line.split(" ");
                    sceneObj.setNextIndex(last[1]);
                    continue;
                }
                sceneObj.getTextList().add(line);
                lineCount++;
            }
            sceneList = rpgSceneList;
        } catch (IOException ie) {
            ie.printStackTrace();
            throw ie;
        } catch (Exception e) {
            e.printStackTrace();
            throw new StoryTextException("ストーリーテキストの読み込み中にエラーがありました。: " + e.getMessage());
        }
    }

    /**
     * RPGシーンオブジェクトを生成する。
     * プレフィックスから生成するので、ストーリーの中身は取得できていない。
     *
     * @param sceneIndex シーンインデックス
     * @param sceneType シーンタイプ
     * @return シーンオブジェクト
     */
    private RpgScene createRpgScene(String sceneIndex, String sceneType) {

        RpgScene sceneObj = null;
        // シーンタイプにより生成するシーンクラスを変更
        if ("A".equals(sceneType)) {
            // シーンオブジェクトの生成
            sceneObj = new StoryScene(sceneIndex, sceneType);
        } else if ("B".equals(sceneType)) {

        } else if ("C".equals(sceneType)) {

        } else if ("D".equals(sceneType)) {

        } else if ("E".equals(sceneType)) {

        } else if ("F".equals(sceneType)) {
            sceneObj = new BattleScene(sceneIndex, sceneType);
        }
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
    /**
     * 入力受付処理。
     * ※JavaAPIを呼び出すだけなので、テスト不要。
     */
    @Override
    public String acceptInput() {
        // 入力受付を返却する(一行分)
        return scan.nextLine();
    }

    /** TODO-[不要なら削除する]
     * データの更新処理。
     * @input String 選択したコマンド番号
     * @return true: 次の処理 false: もう一度
     */
    @Override
    public boolean updateData(String input) {
        boolean isNext = false;
        // 入力チェック
        if (CheckerUtils.isCommandInput(input, "[0-2]")) {
            //isNext = executeScene();
        }
        return isNext;
    }

    /**
     * 名前が不適当なので、別のメソッドを使用する
     * @return
     */
    @Override
    @Deprecated
    public boolean render() {
        return true;
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
     *  各シーンを実行する<br/>。
     *  各シーンの実行後、実行中にRpgConstのステータスを設定する必要がある。<br/>
     *  <b>例:</b><br/>
     *  this.status = RpgConst.SAVE; // 保存して終了する
     *      *  this.status = RpgConst.CLEAR; // ゲームクリアして終了する
     *  this.status = RpgConst.NEXT; // 自動的に次のシーンへ移動する
     */
    public abstract boolean executeScene() throws Exception;

}
