package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgMaster;
import jp.zenryoku.rpg.exception.RpgException;

import java.util.Map;

/**
 * 文字列操作のユーティリティ
 */
public class StringUtils {

    /**
     * デフォルトステータスに関しては、プレフィックスに「Z」がつく。
     *
     * @param kigo ストーリーテキストで定義する記号(HP, MP, LVなど)のプレフィックス
     * @return 四則演算子を返却、見つからないときはNULLを返却
     */
    public static String findDefaultStatus(String kigo) throws RpgException {
        String res = null;
        // 四則演算しの位置を取得
        int idx = CheckerUtils.indexOfOpe(kigo);
        if (idx < 0) {
            return null;
        }
        return kigo.substring(0, idx);
    }

    public static String findDefaultStatusOperator(String kigo) throws RpgException {
        String res = null;
        // 四則演算しの位置を取得
        int idx = CheckerUtils.indexOfOpe(kigo);
        if (idx < 0) {
            return null;
        }
        return kigo.substring(idx, idx + 1);
    }

}
