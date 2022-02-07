package jp.zenryoku.rpg.scene;

import jp.zenryoku.rpg.RpgScene;
import jp.zenryoku.rpg.charactors.Player;
import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.SelectConst;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgItem;
import jp.zenryoku.rpg.data.RpgShop;
import jp.zenryoku.rpg.data.shop.ItemShop;
import jp.zenryoku.rpg.util.ConsoleUtils;
import lombok.Data;

import java.util.List;

@Data
public class ShopScene extends StoryScene {
    /** ショップのインスタンス */
    private ItemShop shop;


    /** コンストラクタ */
    public ShopScene(String sceneIdx, String sceneType) {
        super(sceneIdx, sceneType);
    }

    @Override
    public boolean playScene() throws Exception {
        // ストーリーテキストの内容を表示
        super.playScene();

        ConsoleUtils console = ConsoleUtils.getInstance();
        List<RpgItem> list = shop.getItemList();
        int size = list.size();
        String max = String.valueOf(size);

        Player player = RpgConfig.getInstance().getParty().getPlayer();

        while (true) {
            int count = 0;
            for (RpgItem item : list) {
                System.out.println(count + ": " + item.getName()
                        + " 金額: " + item.getMoney()
                        + " 説明: " + item.getDiscription());
                count++;
            }
            System.out.println("[1-" + max + "]");
            String select = console.acceptInput(MessageConst.DO_SELECT.toString(), "[1-" + max + "]");
            RpgItem it = list.get(Integer.parseInt(select));
            String res = console.acceptInput(it.getName() + MessageConst.YOU_BUY_THIS.toString(), SelectConst.YES_NO_REGREX);
            if (SelectConst.SELECT_YES.getValue().equals(res)) {
                System.out.println(MessageConst.THANKS);
                player.addItem(it);
            }
            String res1 = console.acceptInput(MessageConst.DO_YOU_WANT_MORE.toString(), SelectConst.YES_NO_REGREX);
            if (SelectConst.SELECT_NO.getValue().equals(res1)) {
                System.out.println(MessageConst.THANKS_BYE.toString());
                break;
            }
        }


        return false;
    }
}
