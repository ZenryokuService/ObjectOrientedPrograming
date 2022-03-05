package jp.zenryoku.rpg.data.job;

import jp.zenryoku.rpg.data.RpgData;

import java.util.ArrayList;
import java.util.List;

public class RpgJob extends RpgData {
    /** 職業ID */
    private String jobId;
    /** コマンドリスト */
    private List<String> commandList;

    /**
     * コンストラクタ。
     * @param id 記号
     * @param name 名前
     * @param discription 説明
     * @param commandList 使用できるコマンドの記号(複数)
     */
    public RpgJob(String id, String name, String discription, String[] commandList) {
        this.commandList = new ArrayList<>();
        this.jobId = id;
        this.name = name;
        this.discription = discription;
        for (String s : commandList) {
            // 余計なスペースを削除
            this.commandList.add(s.trim());
        }
    }

}
