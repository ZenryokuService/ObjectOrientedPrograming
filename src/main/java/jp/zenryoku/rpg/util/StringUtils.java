package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.Effects;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.items.EvEffect;
import jp.zenryoku.rpg.data.status.RpgStatus;
import jp.zenryoku.rpg.data.status.StEffect;
import jp.zenryoku.rpg.exception.RpgException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文字列操作のユーティリティ
 */
public class StringUtils {
    private static final boolean isDebug = false;
    /**
     * デフォルトステータスに関しては、プレフィックスに「Z」がつく。
     *
     * @param kigo ストーリーテキストで定義する記号(HP, MP, LVなど)のプレフィックス
     * @return 四則演算子を返却、見つからないときはNULLを返却
     * @throws RpgException 想定外のエラー
     */
    public static String findDefaultStatus(String kigo) throws RpgException {
        String res = null;
        if (kigo.startsWith(RpgConst.DEFAULT_STATUS_PREFIX)) {
            // 頭の「Z」を削除
            kigo = kigo.substring(1);
        }
        // 四則演算子の位置を取得
        int idx = CheckerUtils.indexOfOpe(kigo);
        if (idx < 0) {
            return null;
        }
        return kigo.substring(0, idx);
    }

    /**
     * デフォルトステータスを取得する。
     * @param kigo
     * @return デフォルトステータス それ以外はNullを返却する。
     * @throws RpgException 想定外のエラー
     */
    public static String findDefaultStatusOperator(String kigo) throws RpgException {
        String res = null;
        // 四則演算しの位置を取得
        int idx = CheckerUtils.indexOfOpe(kigo);
        if (idx < 0) {
            return null;
        }
        return kigo.substring(idx, idx + 1);
    }

    /**
     * ストーリーテキストの分割を行う。「:」で区切る
     * @param line ストーリーテキストの1行
     * @param con 対象になる項目(PARAM_CONFIG, PARAM_STATUSなど)
     * @return 分割した後の文字列は配列
     */
    public static String[] separateParam(String line, RpgConst con) {
        String[] res = null;
        return res;
    }

}
