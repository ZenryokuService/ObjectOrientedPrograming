package jp.zenryoku.rpg.constants;

/**
 * RPGゲームのステータスを示す定数クラスです。
 * A 何もなく次のシーンに移動する、移動の場合も単純に別の場所のシーンを設定。
 *   "END_SCENE 1"と指定したときは、自動でシーン１に移動する。呼び出したシーンによりA～Eのイベントが起動する。
 * B 店舗(道具屋、武器屋など)に入って買い物ができるイベントに移動
 * C パーティのステータスが変化(全回復、ステータス異常など)
 * D アイテム、武器・防具の取得
 * E ゲーム終了、次の終了パターンがある。セーブするとき(S)と、ゲームオーバー(O)、ゲームクリア(C)
 * F バトルシーンに移動します。
 */
public enum RpgConst {
    /** 保存して終了する */
    SAVE(0),
    /** ゲームクリアして終了する */
    CLEAR(1),
    /** 自動的に次のシーンへ移動する */
    NEXT(2),
    /** シーンタイプ(次のシーンへ) */
    SENE_TYPE_A(2, "A"),
    /** シーンタイプ(店舗(道具屋、武器屋など)) */
    SENE_TYPE_B(2, "B"),
    /** シーンタイプ(パーティのステータスが変化) */
    SENE_TYPE_C(2, "C"),
    /** シーンタイプ(アイテム、武器・防具の取得) */
    SENE_TYPE_D(2, "D"),
    /** シーンタイプ(ゲーム終了) */
    SENE_TYPE_E(2, "E"),
    /** シーンタイプ(バトルシーン) */
    SENE_TYPE_F(2, "E");

    /** ゲームステータス(終了時に保存、クリア、次のシーン) */
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

}