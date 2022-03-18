package jp.zenryoku.rpg.data.job;

import jp.zenryoku.rpg.charactors.Command;
import jp.zenryoku.rpg.data.RpgData;
import lombok.Data;

import java.util.List;

@Data
public class RpgCommand extends RpgData {
    /** コマンドID */
    private String commandId;
    /** コマンド名は親クラス */
    /** 実行式(実行時の値を算出するための式) */
    private String formulaStr;
    /** 実行時のセリフ */
    private String exeMessage;
    /** 個ディレクトリあり */
    private boolean childDir;
    /** 子供ディレクトリコマンド */
    private List<RpgCommand> childList;

    /**
     * コンストラクタ
     * @param commandId 記号
     * @param name 名前
     * @param formula 実行式
     */
    public RpgCommand(String commandId, String name, String formula) {
        this.commandId = commandId;
        this.name = name;
        this.formulaStr = formula;
    }

    public RpgCommand(String commandId, String name, String formula, String mess) {
        this.commandId = commandId;
        this.name = name;
        this.formulaStr = formula;
        this.exeMessage = mess;
    }
}
