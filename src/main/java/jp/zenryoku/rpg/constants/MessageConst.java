package jp.zenryoku.rpg.constants;

public enum MessageConst {
    //// 通常メッセージ ///
    /** ルート */
    ON_ROOT("<Command input>"),
    /** シーン */
    ON_SCENE("<Scene>"),
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
    /** ステータスマップにオブジェクトがありません。 */
    NO_STATUS_OBJECT("ステータスマップにオブジェクトがありません。"),
    /** 先にCommand.xmlを読み込んでください。 */
    ERR_BEFORE_LOAD_CMD("先にCommand.xmlを読み込んでください。"),
    /** 職業を選択してください。 */
    SLECT_JOB("職業を選択してください。"),
    /** メニュー */
    MENU("*** メニュー ***" + RpgConst.SEP
            + "* 1. そうび    *" + RpgConst.SEP
            + "* 2. アイテム  *" + RpgConst.SEP
            + "* 3. まほう    *" + RpgConst.SEP
            + "***************" + RpgConst.SEP),
    /** メニュー中 */
    IS_MENU("<メニュー>"),
    /** メニューを選択してください。exitでメニューを終了します。 */
    MENU_DO_SELECT("選択してください。exitでメニューを終了します。"),
    /** 選択してください */
    DO_SELECT("選択してください。"),
    /** すでに装備しています。 */
    ALREADY_SOBIED("すでにそうびしています。"),
    /** 装備の変更を行います。 */
    EQUIP_SELECT("装備の変更を行います。装備するアイテム番号を入力してください。"),
    /** 装備を終了します。 */
    FINISH_SOBI("そうびをしゅうりょうします。"),
    /** アイテムがありません。 */
    THERES_NO_ITEM("アイテムがありません。"),
    /** ありがとうございます。 */
    THANKS("ありがとうございます。"),
    /** ありがとうございました。 */
    THANKS_BYE("ありがとうございました。"),
    /** 荷物がいっぱいです。 */
    CANNOT_HOLD("にもつがいっぱいです。"),
    /** でよろしいですか？ */
    YOU_BUY_THIS("でよろしいですか?(1: yes, 2: no)"),
    /** ほかにようじはありますか？ */
    DO_YOU_WANT_MORE("ほかにようじはありますか？(1: yes, n: no)"),
    /** アイテムデータの設定ミス */
    //// エラーメッセージ ///
    /** 想定外のエラー */
    UNEXPECTED_ERR("想定外のエラーです。"),
    /** シーンタイプの設定エラー */
    SCENE_TYPE_ERR("シーンタイプの指定が不適切です。"),
    /** シーン終了時の設定エラー */
    END_SCENE_ERR("END_SCENEの後にオプションを指定してください。"),
    /** リフレクションでのインスタンス生成エラー */
    REFRECT_INSTANCE_ERR("リフレクションでのインスタンス生成エラーです。"),
    /** プレーヤーステータス設定エラー：コロン区切り */
    PLAYER_STATUS_SEPARATE3("プレーヤーステータスは「名前:説明:記号」のように設定してください。"),
    /** マスタカテゴリ設定エラー：コロン区切り */
    MASTER_CATEGORY_SEPARATE4("マスタカテゴリは「カテゴリID:カテゴリ名:用途・説明:読み方」のように設定してください。"),
    /** 計算式設定エラー：コロン区切り */
    FORMULA_SEPARATE3("計算式は「名前:説明:記号」のように設定してください。"),
    /** 職業設定エラー：コロン区切り */
    JOB_SEPARATE3("職業は「名前:説明:記号」のように設定してください。"),
    /** アイテム設定エラー：コロン区切り */
    ITEM_SEPARATE5("アイテムは「名前:記号:効果値:金額:副作用」のように設定してください。"),
    /** レベル設定は「職業記号:レベルアップのレベル数値:レベル設定名」 */
    LEVEL_SEPARATE3("レベル設定は「職業記号:レベルアップのレベル数値:レベル設定名」"),
    /** 選択肢は「<1:[0-9]{1,2}>」のように背呈してください。 */
    ERR_SELECT_SCENE_SEP2("選択肢は「<1:[0-9]{1,2}>」のように指定してください。"),
    /** 未入力です。 */
    ERR_NO_INPUT("未入力です。"),
    /** 指定の範囲で入力してください。 */
    ERR_OUT_OF_INPUT("指定の範囲で入力してください。。"),
    /** 入力指定エラーメッセージ */
    ERR_INPUT("次の範囲で入力してください。: "),
    /** プレーヤーステータス設定エラー */
    PLAYER_STATUS_SETTING_ERR("プレーヤーステータス設定の書式が不適切です。"),
    /** デフォルトステータス指定が不適切です。 */
    ERR_DEFAULT_STATUS_KIGO("デフォルトステータス指定が不適切です。"),
    /** ダイスコード生成時のエラーメッセージ */
    ERR_DICE_CODE("ダイスコードが不適切です"),
    /** ダイスコード生成時のエラーメッセージ */
    ERR_PRINT_LINE("表示する行数の指定が不適切です"),
    /** effect:XXXの設定が不適切です。 */
    ERR_EFFECT_SCENE_CONF("<effect:XXX></effect>の設定が不適切です"),
    /** CONFIG_PARAM, CONFIG_MASTERの設定にない項目です。 */
    ERR_NO_CONFIGS("CONFIG_PARAM, CONFIG_MASTERの設定にない項目です。"),
    /** CONFIG_PARAMの設定が不適切です。 */
    ERR_CONFIG_PARAM("CONFIG_PARAMの設定が不適切です。"),
    /** かっこの数が不適切です。 */
    ERR_KAKO_NOT_SAME("かっこの数が不適切です。"),
    /** 計算式のかっこの数が不適切です。 */
    ERR_FORMLA_KAKKO("計算式のかっこの数が不適切です。"),
    /** データマップを未生成によるエラー */
    ERR_ITEMTYPEMAP_SIZE0("アイテム設定を生成(ParamGenerator#createItemTypeMap())してから実行してください。"),
    /** ファイルの読み込みに失敗 */
    ERR_IOEXCEPTION("ファイルの読み込みに失敗しました。"),
    /** パラメータ生成時のマップキーがありません */
    ERR_NO_FIELD_GENPARA("パラメータマップキーに対応するフィールドがありません"),
    /** パラメータ生成時のマップキーがありません */
    ERR_NO_KEY_GENPARA("パラメータ生成時のマップキーがありません"),
    /** 計算式生成時のマップキーがありません */
    ERR_NO_KEY_GENFORMLA("計算式生成時のマップキーがありません"),
    /** 設定オブジェクトが違います。 */
    ERR_SETTING_OBJECT("設定オブジェクトが違います。"),
    /** ショップのアイテムはカンマ区切りで指定してください。 */
    ERR_ITEM_CSV("ショップのアイテムはカンマ区切りで指定してください。"),
    /** 登録済みのパラメータ記号がありません。 */
    ERR_PARAM_KIGO("登録済みのパラメータ記号がありません。"),
    /** 計算オブジェクトのパラメータ設定エラー */
    ERR_CALCOBJ("計算オブジェクトのパラメータ設定エラー"),
    /** 計算オブジェクトの演算子がありません。 */
    ERR_OPE4_EMPTY("計算オブジェクトの演算子がありません。"),
    /** 計算オブジェクトの演算子がありません。 */
    ERR_EMPTY("対象のオブジェクトがNULL、または空です。"),
    /** ステータス変化オブジェクトは「記号:表示文字:効果式:表示メッセージ」のように定義してください。 */
    ERR_EFFECT_TXT_SIZE("ステータス変化オブジェクトは「記号:表示文字:効果式:表示メッセージ」のように定義してください。"),
    /** ステータス変化オブジェクトは「記号:表示文字:効果式:表示メッセージ」のように定義してください。 */
    ERR_EFFECT_APPEAR_SIZE("効果式は「[A-Z]{3}[+\\-][0-9]{1,3}%*」のように定義してください。"),
    /** 効果式内に対象のカテゴリがありません */
    ERR_NO_APPEAR_SIKI("効果式内に対象のカテゴリがありません"),
    /** 効果オブジェクトがありません */
    ERR_NO_EFFECT_OBJ("効果オブジェクトがありません"),
    /** 効果値の演算子が不適切です */
    ERR_OPE("効果値の演算子が不適切です"),
    /** パラメータ定義内でのマスタカテゴリ設定は最後に数字を付けます。 */
    ERR_CONFIG_PARAM_MASTER("パラメータ定義内でのマスタカテゴリ設定は最後に数字を付けます。"),
    /** 対象のフィールド変数がありません */
    NO_FIELD("対象のフィールド変数がありません"),
    /** 取得するパラメータはInt型である必要があります。 */
    ERR_MISS_EFFECT_PARAM("取得するパラメータはInt型である必要があります。"),
    /** STM.xml定義が不適切です。 */
    ERR_XML_STM("STM.xml定義が不適切です。"),
    /** 数字ではない値が数字に変換されました。 */
    ERR_NUMBER_FORMAT("数字ではない値が数字に変換されました。"),
    /** XMLの返還に失敗しました。 */
    ERR_XML_PERSE("XMLの返還に失敗しました。"),
    /** モンスターのID設定が間違っています。 */
    ERR_MONSTER_NO("モンスターのID設定が間違っています。"),
    /** モンスターリストが読み込まれていません。 */
    NO_MONSTERS("モンスターリストが読み込まれていません。"),
    /** STMリストに登録されていないコマンドIDです。 */
    ERR_NO_STM_COMMAND_ID("STMリストに登録されていないコマンドIDです。"),
    /** イベントフラグを分割した後の配列要素数 */
    ERR_EV_FLG_SIZE("イベントフラグを分割した後の配列要素数は３です。"),
    /** イベントフラグの設定が不適切です。 */
    ERR_EV_FLG("イベントフラグの設定が不適切です。"),
    /** ストーリーテキストの読み込みエラーです。 */
    ERR_FILE_READ("ストーリーテキストの読み込みエラーです。"),
    ;

    /** メッセージ */
    private String message;

    private MessageConst(String message) {
        this.message = message;
    }

    public String toString() {
        return message;
    }
}
