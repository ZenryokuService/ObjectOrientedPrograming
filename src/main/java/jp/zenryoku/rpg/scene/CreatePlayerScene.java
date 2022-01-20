package jp.zenryoku.rpg.scene;

import jp.zenryoku.rpg.charactors.Player;
import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.constants.SelectConst;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.util.CalcUtils;
import jp.zenryoku.rpg.util.ConsoleUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CreatePlayerScene extends StoryScene {

    /** 設定クラス */
    private RpgConfig config;

    /**
     * コンストラクタ
     *
     * @param sceneIdx  シーンインデックス
     * @param sceneType シーンタイプ
     */
    public CreatePlayerScene(String sceneIdx, String sceneType) {
        super(sceneIdx, sceneType);
        config = RpgConfig.getInstance();
    }

    /**
     *  プレーヤー作成用のシーン。
     *
     * @return 処理継続のフラグ = false
     * @throws Exception
     */
    @Override
    public boolean playScene() throws Exception {
        if (config.isDiceCode() == false) {
            throw new RpgException("ダイスコードを設定してください。");
        }
        ConsoleUtils console = ConsoleUtils.getInstance();
        // テキスト表示
        super.printStory();
        System.out.println(MessageConst.CREATE_PLAYER.toString());
        String name = console.acceptInput(MessageConst.INPUT_NAME.toString());
        PlayerCharactor player = new PlayerCharactor(name);
        String birthDay = console.acceptInput(MessageConst.INPUT_BIRTH_DAY.toString());
        player.setBirthDay(birthDay);

        PlayerParty party = PlayerParty.getInstance();
        party.setPlayer(player);

        System.out.println(MessageConst.INPUT_STATUS);

        CalcUtils calc = CalcUtils.getInstance();
        int diceTimes = config.getDiceTimes();
        // ステータスマップ取得
        List<RpgStatus> status = player.getStatusList();
        if (isDebug) System.out.println("map.size: " + status.size());
        boolean isRepeat = true;
        while (true) {
            for (RpgStatus val : status) {
                int res = calc.throwDice(config.getDiceTimes(), config.getDiceFaces()
                        , val.getName() + MessageConst.CREATE_STATUS, val.getName());
                val.setValue(res);
            }
            console.printStatus(player);
            String select = console.acceptInput(MessageConst.CHECK_STATUS.toString()
                    + SelectConst.SELECT_YES.getSelectMessage() + " " + SelectConst.SELECT_NO.getSelectMessage()
                    ,true);
            if (SelectConst.SELECT_YES.getValue().equals(select)) {
                break;
            }
        }
        return false;
    }
}