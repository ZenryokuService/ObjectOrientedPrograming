package jp.zenryoku.rpg.data.categry;

import jp.zenryoku.rpg.data.RpgData;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * マスタカテゴリデータを格納する
 * カテゴリ名はsuper.nameを使用する
 * 用途説明はsuper.discriptionを使用する
 */
@Data
public class RpgMaster extends RpgData {
    /** カテゴリID */
    private String categoryId;
    /** 読み方 */
    private String fieldName;
    /** 子供リスト */
    private List<String> childList;

    public RpgMaster() {
        childList = new ArrayList<>();
    }
}
