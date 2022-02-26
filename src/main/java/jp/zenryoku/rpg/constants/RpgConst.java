package jp.zenryoku.rpg.constants;

/**
 * RPGゲームのステータスを示す定数クラスです。
 * ステータス-シーンタイプで一意にする。
 * (EX) 0-C = 「クリアしてゲームを終了する。」
 *
 * 同様に、項目インデクスと項目値を保持するクラス。
 * status = index, sceneType = value
 *
 * ＜ステータス＞
 * 0: ゲーム開始・終了時に使用するステータス
 * 1: シーン選択時に使用するステータス
 * 2: プレーヤー生成に使用するステータス
 * ＜シーンタイプ＞
 * 1-A 何もなく次のシーンに移動する、移動の場合も単純に別の場所のシーンを設定。
 *   "END_SCENE 1"と指定したときは、自動でシーン１に移動する。呼び出したシーンによりA～Eのイベントが起動する。
 * 1-B 店舗(道具屋、武器屋など)に入って買い物ができるイベントに移動
 * 1-C パーティのステータスが変化(全回復、ステータス異常など)
 * 1-D アイテム、武器・防具の取得
 * 1-E ゲーム終了、次の終了パターンがある。セーブするとき(S)と、ゲームオーバー(O)、ゲームクリア(C)
 * 1-F バトルシーンに移動します。
 * 1-G プレーヤー生成を行います。
 * 1-H ゲームクリアして終了する。
 * 1-I 保存して終了する。
 */
public enum RpgConst {
    ////////////////////////////////
    // ゲーム終了ステータス用の定数(0) //
    ////////////////////////////////
    /** ゲームクリアして終了する */
    CLEAR(0, "C"),
    /** 保存して終了する */
    SAVE(0, "S"),
    /** 初期表示画面のstartを選択 */
    INIT_START(0, "Z"),
    /** 初期表示画面のcontinueを選択 */
    INIT_CONTINUE(0, "Z"),
    /** ゲームオーバー */
    GAME_OVER(0, "E"),

    /////////////////////////////////
    // シーン・コントロール用の定数(1) //
    /////////////////////////////////
    /** シーンタイプ(次のシーンへ) */
    SENE_TYPE_NEXT(1, "A"),
    /** シーンタイプ(店舗(道具屋、武器屋など)) */
    SENE_TYPE_SHOP(1, "B"),
    /** シーンタイプ(パーティのステータスが変化) */
    SENE_TYPE_STATUS(1, "C"),
    /** シーンタイプ(アイテム、武器・防具の取得) */
    SENE_TYPE_GET(1, "D"),
    /** シーンタイプ(ゲーム終了) */
    SENE_TYPE_END_GAME(1, "E"),
    /** シーンタイプ(バトルシーン) */
    SENE_TYPE_BATTLE(1, "F"),
    /** プレーヤー生成 */
    SCENE_TYPE_CREATE(1,"G"),

    ////////////////////////////////
    // RPGで使用するデータのタイプ(2) //
    ////////////////////////////////
    /** そのほかに使用するパラメータ */
    DATA_TYPE_PARAM(2,"param"),
    /** ステータス・パラメータ */
    DATA_TYPE_STATUS(2,"status"),
    /** 各種計算式 */
    DATA_TYPE_FORMULA(2,"formula"),
    /** アイテム(武器なども含む)の設定 */
    DATA_TYPE_ITEM(2,"item"),
    /** アイテム(武器なども含む)の種類の設定 */
    DATA_TYPE_ITEM_TYPE(2,"itemtype"),
    /** 職業の設定 */
    DATA_TYPE_JOB(2,"job"),
    /** 装備品の設定 */
    DATA_TYPE_EQUIP(2,"equip"),

    ////////////////////////////////
    // RPGで使用するデータのタイプ(3以上) //
    ////////////////////////////////
    /** 武器を示す */
    WEPONS(3, "wepon"),
    /** 防具を示す */
    ARMORS(4, "armor"),
    /** 通常アイテムを示す */
    ITEMS(5, "item"),
    /** 魔法アイテムを示す */
    MAG_ITEMS(6, "magItem"),
    ;

    /** 改行コード */
    public static final String SEP = System.lineSeparator();
    /** プラス(+) */
    public static final String PLUS = "+";
    /** マイナス(-) */
    public static final String MINUS = "-";
    /** マスタカテゴリのプレーヤーステータスオプション */
    public static final String PLY = "PLY";
    /** デフォルトステータスに関しては、プレフィックスに「Z」がつく */
    public static final String DEFAULT_STATUS_PREFIX = "Z";
    /** アイテム保持可能数(デフォルト) */
    public static final int ITEM_HOLD_NUM = 5;
    /** マスタカテゴリのパラメータ定義内でのフォーマット */
    public static final String REG_MASTERT_DEF_FOR_PARAM = "[A-Z]{3}[0-9]{1,2}";
    /** マスタカテゴリ: ZHP */
    public static final String ZHP = "ZHP";
    /** マスタカテゴリ: ZMP */
    public static final String ZMP = "ZMP";
    /** マスタカテゴリ: ZLV */
    public static final String ZLV = "ZLV";
    /** マスタカテゴリ: MNY */
    public static final String MNY = "MNY";
    /** 武器を表示する */
    public static final String BUKI = "ぶき";
    /** 武器を示す記号 */
    public static final String WEP = "WEP";
    /** 武器攻撃力を示す記号 */
    public static final String WEV = "WEV";
    /** 防具を表示する */
    public static final String BOG = "ぼうぐ";
    /** 防具を示す記号 */
    public static final String ARM = "ARM";
    /** 防具防御力を示す */
    public static final String ARV = "ARV";
    /** 攻撃力 */
    public static final String ATK = "ATK";
    /** 防御力 */
    public static final String DEF = "DEF";
    /** アイテムを表示する */
    public static final String ITEM = "アイテム";
    /** 通常のアイテムを示す記号 */
    public static final String ITM = "ITM";
    /** データクラスのパッケージ名 */
    public static final String DATA_PACKAGE = "jp.zenryoku.rpg.data";
    /** パラメータを取得するためのCharBufferのサイズ */
    public static final int BUF_SIZE = 10;
    /** 各パラメータの記号は半角大文字の３文字(リミット4) */
    public static final int KIGO_BUFFER = 4;
    /** 計算式で使用できる文字の数 */
    public static final int FORMULA_STR_LEN = 120;
    /** 計算式のかっこの最大数 */
    public static final int FORUMULA_MAX_KAKO = 7;
    /** ヘルプコマンド */
    public static final String HELP_COMMAND = "help";
    /** メニューコマンド */
    public static final String MENU_COMMAND = "menu";
    /** 終了コマンド */
    public static final String EXIR_COMMAND = "exit";
    /** ストーリーテキストで定義する記号判別用の正規表現 */
    public static final String REG_KIGO = "[A-Z]";
    /** ストーリーテキストで定義するデフォルトステータス記号判別用の正規表現 */
    public static final String REG_DEFAULT_KIGO = "[A-Z]{2}";
    /** ステータス変化オブジェクト定義の書式正規表現 */
    public static final String REG_EFFECT_TXT = "[A-Z]{3}[+\\-][0-9]{1,3}%*";
    /** 記号の文字数(配列の添え字番号) */
    public static final int KIGO_SIZE = 2;
    /** アイテム定義のカンマ区切り数 */
    public static final int ITEM_SIZE = 3;
    /** PARAM_CONFIGの分割サイズ */
    public static final int PARAM_SIZE = 6;
    /** CONFIG_MASTERの分割サイズ */
    public static final int MASTER_SIZE = 4;
    /** PARAM_STATUSの分割サイズ */
    public static final int STATUS_SIZE = 3;
    /** PARAM_JOBの分割サイズ */
    public static final int JOB_SIZE = 3;
    /** ITEM_LISTの分割サイズ */
    public static final int ITE_LIST_SIZE = 5;
    /** PARAM_FORMULAの分割サイズ */
    public static final int FORMULA_SIZE = 3;
    /** ST_EFFECT_SIZEの分割サイズ */
    public static final int EFFECT_SIZE = 4;
    /** ST_EFFECT_TXT_SIZEの分割サイズ */
    public static final int EFFECT_TXT_SIZE = 3;

    /** 計算オブジェクトの保持できる項の数 */
    public static final int CALC_KO_COUNT = 2;

    /** ゲームステータス(1:終了時に保存、2:クリア、3:次のシーン...)<br>項目インデックス */
    private int status;
    /** シーンタイプ<br>項目値 */
    private String sceneType;

    /**
     * シーンイベントのみを指定する場合
     * @param status
     */
    private RpgConst(int status) {
        this.status = status;
    }

    /**
     * 項目のインデックス値と対象の文字列を保持する。
     * @param index　項目インデックス
     * @param value 項目値
     */
    private RpgConst(int index, String value) {
        this.status = index;
        this.sceneType = value;
    }

    public int getStatus() {
        return status;
    }

    public String getSceneType() {
        return sceneType;
    }

}