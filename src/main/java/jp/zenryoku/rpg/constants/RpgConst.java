package jp.zenryoku.rpg.constants;

/**
 * RPGゲームのステータスを示す定数クラスです。
 * ステータス-シーンタイプで一意にする。
 * (EX) 0-C = 「クリアしてゲームを終了する。」
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
    GAME_OVER(1, "E"),
    /** シーンタイプ(バトルシーン) */
    SENE_TYPE_BATTLE(1, "F"),
    /** プレーヤー生成 */
    SCENE_TYPE_CREATE(1,"G"),
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
    ;

    /** ゲームステータス(1:終了時に保存、2:クリア、3:次のシーン...) */
    private int status;
    /** シーンタイプ */
    private String sceneType;

    /**
     * シーンイベントのみを指定する場合
     * @param status
     */
    private RpgConst(int status) {
        this.status = status;
    }

    /**
     * シーンイベントとシーンタイプを指定する場合
     * @param status　
     * @param sceneType
     */
    private RpgConst(int status, String sceneType) {
        this.status = status;
        this.sceneType = sceneType;
    }

    public int getStatus() {
        return status;
    }

    public String getSceneType() {
        return sceneType;
    }

}