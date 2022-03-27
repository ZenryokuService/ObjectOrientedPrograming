package jp.zenryoku.rpg.data.job;

import java.util.List;

/**
 * モンスタータイプ、Jobと同じように各モンスターのコマンドセットを保持する。
 */
public class RpgMonsterType extends RpgJob {

    /**
     * コンストラクタ。
     *
     * @param id          記号
     * @param name        名前
     * @param discription 説明
     * @param commandList 使用できるコマンドの記号(複数)
     */
    public RpgMonsterType(String id, String name, String discription, List<RpgCommand> commandList) {
        super(id, name, discription, commandList);
    }
}
