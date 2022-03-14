package jp.zenryoku.rpg.data.job;

import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.status.RpgStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class RpgJob extends RpgData {
    /** 職業ID */
    private String jobId;
    /** コマンドリスト */
    private List<RpgCommand> commandList;
    /** ステータスアップマップ */
    private Map<String, RpgStatus> statusUpMap;

    /**
     * コンストラクタ。
     * @param id 記号
     * @param name 名前
     * @param discription 説明
     * @param commandList 使用できるコマンドの記号(複数)
     */
    public RpgJob(String id, String name, String discription, List<RpgCommand> commandList) {
        this.jobId = id;
        this.name = name;
        this.discription = discription;
        this.commandList = commandList;
    }

}
