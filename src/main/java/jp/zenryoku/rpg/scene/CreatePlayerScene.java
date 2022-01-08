package jp.zenryoku.rpg.scene;

import jp.zenryoku.rpg.charactors.Player;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.util.CalcUtils;
import jp.zenryoku.rpg.util.ConsoleUtils;

public class CreatePlayerScene extends StoryScene {
    /** ダイスコードを設定したフラグ */
    private boolean isSetDiceCode;
    /** ダイスコード(3D6など) */
    private String diceCode;
    /**
     * コンストラクタ
     *
     * @param sceneIdx  シーンインデックス
     * @param sceneType シーンタイプ
     */
    public CreatePlayerScene(String sceneIdx, String sceneType) {
        super(sceneIdx, sceneType);
        isSetDiceCode = false;
    }

    /**
     * ダイスコードを設定する。
     * @param diceCode
     */
    public void setDiceCode(String diceCode) {
        this.diceCode = diceCode;
        isSetDiceCode = true;
    }
    /**
     *  プレーヤー作成用のシーン。
     *
     * @return 処理継続のフラグ = false
     * @throws Exception
     */
    @Override
    public boolean playScene() throws Exception {
        if (isSetDiceCode == false) {
            throw new RpgException("ダイスコードを設定してください。");
        }
        // テキスト表示
        super.printStory();
        System.out.println(MessageConst.CREATE_PLAYER.toString());
        String name = ConsoleUtils.getInstance().acceptInput(MessageConst.INPUT_NAME.toString());
        Player player = new Player(name);

        CalcUtils calc = CalcUtils.getInstance();
        calc.throwDice("");

        return false;
    }
}
