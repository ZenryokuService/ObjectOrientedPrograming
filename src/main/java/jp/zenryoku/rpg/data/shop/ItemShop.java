package jp.zenryoku.rpg.data.shop;

import jp.zenryoku.rpg.data.RpgItem;
import jp.zenryoku.rpg.data.RpgShop;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemShop implements RpgShop {
    /** アイテムショップの名前 */
    private String shopName;
    /** 半版しているアイテムのリスト */
    private List<RpgItem> itemList;

    /**
     * アイテムショップの名前、これをキーにする.
     * つまりはアルファベット指定？ TODO-[全角許容するか？]
     * @param name アイテムショップの名前
     */
    public ItemShop(String name) {
        shopName = name;
        itemList = new ArrayList<RpgItem>();
    }

    public void addItemList(RpgItem item) {
        itemList.add(item);
    }
}
