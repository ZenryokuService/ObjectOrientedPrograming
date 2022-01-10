package jp.zenryoku.rpg.constants;

public enum MessageConst {
    /** ゲームオーバー */
    END_GAMEOVER(""),
    /** 終了時メッセージ(保存) */
    END_SAVE("お疲れ様でした。次の冒険で会いましょう。"),
    /** 終了時メッセージ(クリア) */
    END_FIN("== Fin =="),
    /** プレーヤー作成 */
    CREATE_PLAYER("プレーヤー作成を行います。"),
    /** ステータス作成 */
    CREATE_STATUS("を設定します。"),
    /** 名前入力 */
    INPUT_NAME("名前を入力してください。"),
    /** 年齢入力 */
    INPUT_BIRTH_DAY("生年月日(西暦)を入力してください。"),
    /** ステータスの入力をしてください。 */
    INPUT_STATUS("ステータスの入力をしてください。"),
    /** ステータスをこれで決定してよいですか？ */
    CHECK_STATUS("ステータスをこれで決定してよいですか？"),
    /** ステータスオブジェクトではありません。 */
    NO_MUCH_STATUS("ステータスオブジェクトではありません。"),
    /** 想定外のエラー */
    UNEXPECTED_ERR("想定外のエラーです。"),
    /** シーンタイプの設定エラー */
    SCENE_TYPE_ERR("シーンタイプの指定が不適切です。"),
    /** シーン終了時の設定エラー */
    END_SCENE_ERR("END_SCENEの後にオプションを指定してください。"),
    /** リフレクションでのインスタンス生成エラー */
    REFRECT_INSTANCE_ERR("リフレクションでのインスタンス生成エラーです。"),
    /** プレーヤーステータス設定エラー */
    PLAYER_STATUS_SETTING_ERR("プレーヤーステータス設定の書式が不適切です。"),
    /** プレーヤーステータス設定エラー：コロン区切り */
    PLAYER_STATUS_SEPARATE3("プレーヤーステータスは「名前:説明:記号」のように設定してください。"),
    /** 計算式設定エラー：コロン区切り */
    FORMULA_SEPARATE3("計算式は「名前:説明:記号」のように設定してください。"),
    /** 職業設定エラー：コロン区切り */
    JOB_SEPARATE3("職業は「名前:説明:記号」のように設定してください。"),
    /** アイテム設定エラー：コロン区切り */
    ITEM_SEPARATE3("アイテムは「名前:説明:記号」のように設定してください。"),
    /** 未入力エラー */
    ERR_NO_INPUT("未入力です。"),
    /** 指定外入力エラー */
    ERR_OUT_OF_INPUT("指定の範囲で入力してください。。"),
    /** 入力指定エラーメッセージ */
    ERR_INPUT("次の範囲で入力してください。: "),
    /** ダイスコード生成時のエラーメッセージ */
    ERR_DICE_CODE("ダイスコードが不適切です"),
    /** ダイスコード生成時のエラーメッセージ */
    ERR_PRINT_LINE("表示する行数の指定が不適切です"),
    /** データマップを未生成によるエラー */
    ERR_ITEMTYPEMAP_SIZE0("アイテム設定を生成(ParamGenerator#createItemTypeMap())してから実行してください。"),
    /** ファイルの読み込みに失敗 */
    ERR_IOEXCEPTION("ファイルの読み込みに失敗しました。"),
    /** パラメータ生成時のマップキーがありません */
    ERR_NO_KEY_GENPARA("パラメータ生成時のマップキーがありません"),
    /** 設定オブジェクトが違います。 */
    ERR_SETTING_OBJECT("設定オブジェクトが違います。"),
    ;

    private String message;

    private MessageConst(String message) {
        this.message = message;
    }

    public String toString() {
        return message;
    }
}
