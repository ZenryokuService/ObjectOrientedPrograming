package jp.zenryoku.rpg.data;

import lombok.Data;

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
    private String howToRead;
}
