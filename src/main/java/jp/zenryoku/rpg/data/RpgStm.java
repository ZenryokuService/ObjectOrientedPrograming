package jp.zenryoku.rpg.data;

import lombok.Data;

/**
 * スキル・技・魔法を表現するクラス。
 */
@Data
public class RpgStm {
    /** ID */
    private String id;
    /** 名前 */
    private String name;
    /** 指向性 */
    private String orient;
    /** 仕様MP */
    private int cost;
    /** 魔法威力 */
    private int mpw;
    /** 職業ID */
    private String jobId;
    /** 習得レベル */
    private int leanLv;
    /** 計算式 */
    private String formula;

    public RpgStm() {
    }

    public RpgStm(String mid, String name, String ori, int cost, int mpw) {
        this.id = mid;
        this.name = name;
        this.orient = ori;
        this.cost = cost;
        this.mpw = mpw;
    }

}
