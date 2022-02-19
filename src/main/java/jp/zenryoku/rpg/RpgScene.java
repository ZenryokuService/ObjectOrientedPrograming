package jp.zenryoku.rpg;

import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.exception.RpgException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Rogのシーンを作成するときのルートクラス
 */
@Data
public abstract class RpgScene {
    public final boolean isDebug = false;
    /** 改行文字 */
    protected final String SEP = System.lineSeparator();
    /** 終了ステータス */
    protected RpgConst status;
    /** コメントテキスト */
    protected List<String> commentList;
    /** ストーリーテキスト */
    protected List<String> textList;
    /** シーンインデックス(シーンを一意に判別するインデックス) */
    protected String sceneIndex;
    /** 次のシーンインデックス */
    protected String nextIndex;
    /** シーンタイプ */
    protected String sceneType;
    /** 次のシーン選択フラグ */
    protected boolean isSelectNextScene;
    /** 終了オプション */
    protected RpgConst finishOpt;
    /** 選択肢の数 **/
    protected int selectCount;
    /** 選択肢に対応する次のシーン番号 */
    protected String[] nextIndexes;
    /** 「次へ」を表示しないフラグ */
    protected boolean skipNextMessage;


    /**
     * デフォルトコンストラクタ。
     */
    public RpgScene() {
        textList = new ArrayList<String>();
        commentList = new ArrayList<String>();
    }

    /**
     * シーンオブジェクトの生成時には、シーンインデックスとシーンタイプを指定する。
     *
     * @param sceneIndex シーンインデックス
     * @param sceneType シーンタイプ
     */
    public RpgScene(String sceneIndex, String sceneType) {
        this.sceneIndex = sceneIndex;
        this.sceneType = sceneType;
        this.textList = new ArrayList<>();
        this.commentList = new ArrayList<>();
        this.isSelectNextScene = false;
    }

    /**
     * 選択肢の数だけ要素数を持つ、次のシーンへのインデックス用配列を作成する。
     * @param max
     */
    public void createSelectSceneArray(int max) {
        nextIndexes = new String[max];
    }

    /**
     * 項目番号と次のシーンインデックスをセットにして設定する。
     * @param komokuNo 項目版尾久
     * @param idx 次のシーンインデックス
     */
    public void setNextIndexes(String komokuNo, String idx) {
        if (isDebug) System.out.println("選択肢配列の要素数 = " + nextIndexes.length);
        nextIndexes[Integer.parseInt(komokuNo)] = idx;
    }

    /**
     * このゲーム固有の例外を投げる。
     * @param message 表示するメッセージ
     * @throws RpgException
     */
    public void throwRpgException(String message) throws RpgException {
        throw new RpgException(message + "(info) SceneIdx: " + sceneIndex + "  SceneType: " + sceneType);
    }

    /**
     * 以下の終了オプションを指定する。
     * O=ゲームオーバー, C=クリア.
     * @param opt オプション
     */
    public void setFinishOption(String opt) {
        if ("C".equals(opt)) {
            finishOpt = RpgConst.CLEAR;
        } else if ("O".equals(opt)) {
            finishOpt = RpgConst.GAME_OVER;
        }
    }

    /** 「次へ」のメッセージを表示しない */
    public boolean getSkipNextMessage() { return skipNextMessage; }
    /** シーンの初期化処理 */
    public abstract void initScene() throws RpgException;
    /** シーンの実装 */
    public abstract boolean playScene() throws Exception;
}
