package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.constants.SelectConst;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;

import jp.zenryoku.rpg.data.items.RpgItem;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.item.equip.Armor;
import jp.zenryoku.rpg.item.equip.MainWepon;

import java.util.List;
import java.util.Map;

public class MenuUtils {
    private static final boolean isDebug = false;
    private static MenuUtils instance;
    /** 表示ユーティリティ */
    private ConsoleUtils console = ConsoleUtils.getInstance();

    private MenuUtils() {
    }

    public static MenuUtils getInstance() {
        if (instance == null) {
            instance = new MenuUtils();
        }
        return instance;
    }

    /**
     * メニュー表示、操作(処理)を行う。
     * @throws RpgException 想定外のエラー
     */
    public void printMenu() throws RpgException {
        RpgConfig conf = RpgConfig.getInstance();
        PlayerParty party = conf.getParty();
        PlayerCharactor player = party.getPlayer();
        ConsoleUtils consUtil = ConsoleUtils.getInstance();
        List<RpgItem> itemList = player.getItemBag();
        // メニュー画面の表示フラグ、起動中はtrueになる
        boolean isMenu = true;
        while (isMenu) {
            // メニューの表示
            System.out.println(MessageConst.MENU);
            if (isDebug) System.out.println("アイテムサイズ: " + itemList.size());
            String input = consUtil.acceptInput(MessageConst.MENU_DO_SELECT.toString(), SelectConst.MENU_SELECT_REGREX);
            if (SelectConst.SELECT_SOBI.getValue().equals(input)) {
                // アイテムのリストの表示
                console.printItemList(player);
                // 所持アイテムなしのときはメッセージを表示して戻る
                if (console.isZeroSizeItemList(itemList)) {
                    continue;
                }
                // 装備のセット
                selectEquipMent(player, itemList);
            } else if (SelectConst.SELECT_ITEM.getValue().equals(input)) {
                // アイテムの使用をおこなう
                useItems(player, itemList);
            } else if (SelectConst.SELECT_MAGIC.getValue().equals(input)) {
                // まほうの使用をおこなう
            } else if (SelectConst.SELECT_EXIT.getPrintValue().equals(input)) {
                // 装備メニューを終了する。
                console.printMessage(MessageConst.FINISH_SOBI.toString());
                break;
            }
            //console.printStatus(player);
        }
    }

    /**
     * そうびの変更を行う。
     * @throws RpgException 想定外のエラー
     */
    private void selectEquipMent(PlayerCharactor player, List<RpgItem> itemList) throws RpgException {

        RpgConfig conf = RpgConfig.getInstance();
        ConsoleUtils consUtil = ConsoleUtils.getInstance();
        String max = String.valueOf(itemList.size());
        // 装備の変更をおこなう
        String selectSobi = consUtil.acceptInput(MessageConst.EQUIP_SELECT.toString(), "[1-" + max + "]");
        RpgItem sobi = itemList.get(Integer.parseInt(selectSobi) - 1);
        if (CheckerUtils.alreadySobied(sobi, player)) {
            return;
        }
        System.out.println(sobi.getName() + " : " + sobi.getItemType() + " : " + sobi.getItemValueKigo());
        if (true) {
            Map<String, RpgData> testMap = conf.getItemMap();
            testMap.forEach((key, val) -> {
                RpgItem item = (RpgItem) val;
                System.out.println("Key: " + key + " : " + "Val: " + item.getItemType());
            });
        }
        RpgItem type = (RpgItem) conf.getItemMap().get(sobi.getName());
        if (CheckerUtils.isWepOrArm(type) == false) {
            throw new RpgException(MessageConst.ERR_SETTING_OBJECT.toString() + " : " + type.getName());
        }
        // 武器の場合
        //System.out.println("Type: " + type.getItemType() + " Value: " + type.getValue());
        if (CheckerUtils.isWep(type)){
            player.setMainWepon(new MainWepon(type));
            console.printStatus(player);
        }
        // 防具の場合
        if (CheckerUtils.isArm(type)) {
            player.setArmor(new Armor(type));
            console.printStatus(player);
        }
    }

    /**
     * 未実装
     * @param player　プレーヤー
     * @param itemList アイテムリスト
     */
    private void useItems(PlayerCharactor player, List<RpgItem> itemList) {

    }
}
