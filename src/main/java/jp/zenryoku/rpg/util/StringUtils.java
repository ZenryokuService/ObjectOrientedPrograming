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
     * 効果式の中から、記号を取得する。。
     *
     * @param kigo ストーリーテキストで定義する記号(HP, MP, LVなど)のプレフィックス
     * @return 四則演算子を返却、見つからないときはNULLを返却
     * @throws RpgException 想定外のエラー
     */
    public static String findKigo(String kigo) throws RpgException {
        String res = null;
        // 四則演算子の位置を取得
        int idx = CheckerUtils.indexOfOpe(kigo);
        if (idx < 0) {
            return null;
        }
        return kigo.substring(0, idx);
    }

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
     * 効果式の中から演算子を取得する。
     * @param siki　効果式
     * @return デフォルトステータス それ以外はNullを返却する。
     * @throws RpgException 想定外のエラー
     */
    public static String findOperator(String siki) throws RpgException {
        String res = null;
        // 四則演算しの位置を取得
        int idx = CheckerUtils.indexOfOpe(siki);
        if (idx < 0) {
            return null;
        }
        return siki.substring(idx, idx + 1);
    }

    /**
     * デフォルトステータスのときは、２文字分、通常の記号の場合はそのまま返却する。
     * @param kigo 検証する文字列
     * @return デフォルトステータス or 記号 それ以外はNULL
     */
    public static String convertDefaultStatusKigo(String kigo) {
        String res = null;
        if (kigo != null && kigo.length() == 3 && kigo.startsWith(RpgConst.DEFAULT_STATUS_PREFIX)) {
            res = kigo.substring(1);
        } else if (kigo != null && kigo.matches(RpgConst.REG_KIGO)) {
            res = kigo;
        }
        return res;
    }

    /**
     * ストーリーテキストの分割を行う。「:」で区切る
     * @param line ストーリーテキストの1行
     * @param con 対象になる項目(PARAM_CONFIG, PARAM_STATUSなど)
     * @return 分割した後の文字列は配列
     */
    @Deprecated
    public static String[] separateParam(String line, RpgConst con) {
        String[] res = null;
        return res;
    }

}
