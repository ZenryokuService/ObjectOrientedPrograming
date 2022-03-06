package jp.zenryoku.rpg.data.job;

import jp.zenryoku.rpg.data.RpgData;
import lombok.Data;

@Data
public class RpgCommand extends RpgData {
    /** コマンドID */
    private String commandId;
    /** コマンド名は親クラス */
    /** 実行式(実行時の値を算出するための式) */
    private String formula;

    /**
     * コンストラクタ
     * @param commandId 記号
     * @param name 名前
     * @param formula 実行式
     */
    public RpgCommand(String commandId, String name, String formula) {
        this.commandId = commandId;
        this.name = name;
        this.formula = formula;
    }
}
