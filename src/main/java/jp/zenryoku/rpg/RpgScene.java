package jp.zenryoku.rpg;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Rogのシーンを作成するときのルートクラス
 */
@Data
public abstract class RpgScene {
    public final boolean isDebug = true;
    /** 改行文字 */
    protected final String SEP = System.lineSeparator();
    /** コメントテキスト */
    protected List<String> commentList;
    /** ストーリーテキスト */
    protected List<String> textList;
    /** シーンインデックス */
    protected String sceneIndex;
    /** 次のシーンインデックス */
    protected String nextIndex;
    /** シーンタイプ */
    protected String sceneType;

    public RpgScene() {
        textList = new ArrayList<String>();
        commentList = new ArrayList<String>();
    }

    public RpgScene(String sceneIndex, String sceneType) {
        this.sceneIndex = sceneIndex;
        this.sceneType = sceneType;
        this.textList = new ArrayList<>();
        this.commentList = new ArrayList<>();
    }

    /** シーンの初期化処理 */
    public abstract void initScene();
    /** シーンの実装 */
    public abstract boolean playScene();
}
