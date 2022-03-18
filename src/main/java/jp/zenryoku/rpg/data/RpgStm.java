package jp.zenryoku.rpg.data;

import lombok.Data;

/**
 * スキル・技・魔法を表現するクラス。
 */
@Data
public class RpgStm {
    /** MID */
    private String mid;
    /** 名前 */
    private String name;
    /** 指向性 */
    private String orient;
    /** 仕様MP */
    private int cost;
    /** 魔法威力 */
    private int mpw;

    public RpgStm() {
    }

    public RpgStm(String mid, String name, String ori, int cost, int mpw) {
        this.mid = mid;
        this.name = name;
        this.orient = ori;
        this.cost = cost;
        this.mpw = mpw;
    }

}
