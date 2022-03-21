package jp.zenryoku.rpg.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ストーリーテキスト内のイベントフラグを示す。
 * このフラグが立っていれば、ストーリーA、そうでなければストーリーBのようにハンドルできる。
 */
@Data
public class RpgEvFlg {
    /** イベントフラグ */
    private String evFlg;
    /** イベント別ストーリー */
    private List<String> evStory;

    /**
     * コンストラクタ。
     */
    public RpgEvFlg() {
        //evStory = new HashMap<>();
    }

}
