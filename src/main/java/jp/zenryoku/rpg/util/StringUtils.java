package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.exception.RpgException;

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
        // 四則演算しの位置を取得
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

    /**
     *
     * @param effectAppear 効果式
     * @return String[] [0]記号 [1]演算子(+-) [2]効果値
     * @throws RpgException 効果式の設定のエラー
     */
    public static String[] createEffectApeer(String effectAppear) throws RpgException {
        if (effectAppear == null || effectAppear.matches(RpgConst.REG_EFFECT_TXT) == false) {
            throw new RpgException(MessageConst.ERR_EFFECT_TXT_SIZE.toString());
        }
        String[] res = new String[RpgConst.EFFECT_TXT_SIZE];
        // 例：ZHP-10%
        String kigo = effectAppear.substring(0, 3);
        res[0] = kigo;
        // 演算子
        String ope = effectAppear.substring(3, 4);
        res[1] = ope;
        // 値
        String val = effectAppear.substring(4);
        res[2] = val;

        if (isDebug) System.out.println("kigo: " + kigo + " ope: " + ope + " val: " + val);
        return res;
    }
}
