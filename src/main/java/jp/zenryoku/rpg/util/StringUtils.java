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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String findNo(String partOfSiki) {
        char[] ch = partOfSiki.toCharArray();
        boolean isStart = true;
        int start = 0;
        int end = 0;
        Pattern pat = Pattern.compile("[0-9|%]");
        for (int i = 0; i < ch.length; i++) {
            Matcher mat = pat.matcher(String.valueOf(ch[i]));
            if (mat.matches()) {
                if (isStart) {
                    start = i;
                    isStart = false;
                }
            } else {
                if (end == 0) {
                    end = i;
                    break;
                }
            }
        }
        if (isDebug) System.out.println("start: " + start + " end: " + end);
        return partOfSiki.substring(start, end);
    }

    public static String findNoBack(String partOfSiki) {
        char[] ch = partOfSiki.toCharArray();
        boolean isStart = true;
        int start = ch.length - 1;
        int end = -1;
        Pattern pat = Pattern.compile("[0-9]");
        for (int i = ch.length - 1; i >= 0; i--) {
            Matcher mat = pat.matcher(String.valueOf(ch[i]));
            if (mat.matches()) {
                if (isStart) {
                    start = i + 1;
                    isStart = false;
                }
            } else {
                if (end == -1) {
                    end = i + 1;
                    break;
                }
            }
        }
        if (isDebug) System.out.println("start: " + start + " end: " + end);
        return partOfSiki.substring(end, start);
    }

    public static String findTS(String partOfSiki) {
        char[] ch = partOfSiki.toCharArray();
        int start = 0;
        int end = 0;
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == 'T' || ch[i] == 'S') {
                if (start == 0) {
                    start = i;
                }
            } else if (start != 0) {
                if (end == 0) {
                    end = i;
                    break;
                }
            }
        }
        if (start == 0 && end == 0) {
            return null;
        }
        return partOfSiki.substring(start, end);
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

    /**
     * 効果式を分割する。デフォルトステータスのプレフィックスはつけたまま。
     * 前提として、文字列のチェック済み
     * @param kokaSiki　効果式：ZHP-10%, POI-10TS3
     * @return String[] {記号, 演算子, 数字, TS, ターン数}
     */
    public static String[] separateEffectAppear(String kokaSiki) {
        // TSあり
        if (CheckerUtils.isTS(kokaSiki)) {
            String[] res = new String[5];
            // 記号
            res[0] = kokaSiki.substring(0, 3);
            // 演算子
            res[1]  = kokaSiki.substring(3, 4);
            String after = kokaSiki.substring(4);
            // 数字
            res[2] = StringUtils.findNo(after);
            // TS
            res[3] = StringUtils.findTS(after);
            // ターン数
            res[4] = StringUtils.findNoBack(after);
            return res;
        }
        // TSなし
        String[] res = new String[3];
        res[0] = kokaSiki.substring(0, 3);
        res[1]  = kokaSiki.substring(3, 4);
        res[2] = kokaSiki.substring(4);
        return res;
    }

    public static String[] findMonsterNo(String line) {
        String[] res = null;
        // lineの変数に対象の文字列が入っているかチェック
        if (CheckerUtils.isStartBattleScene(line)) {
            // 「:」で区切る
            String[] sep = line.split(":");
            // 区切った後の右側を取得する
            String val = sep[1];
            // 「>」を削除
            val = val.replaceAll(">", "");
            // もし「-」を含んでいるのなら
            if (val.contains("-")) {
                // 「-」で分割
                String[] sep1 = val.split("-");
                res = new String[2];
                res[0] = sep1[0];
                res[1] = sep1[1];
            } else {
                res = new String[1];
                res[0] =val;
            }
        } else {
            return null;
        }
        return res;
    }
}
