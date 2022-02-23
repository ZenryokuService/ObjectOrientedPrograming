package jp.zenryoku.rpg.calc;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.status.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.util.CheckerUtils;

import java.nio.CharBuffer;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * クラス同士の計算用オブジェクト。
 */
public class CalcObj {
    private static final boolean isDebug = false;
    /** 演算子指定の正規表現(剰余残を含む) */
    public static final String SISOKU_OPE_REG = "[+\\-*/%]";
    /** 足し算の演算子 */
    public static final String ADD_OPE = "+";
    /** 引き算の演算子 */
    public static final String SUBT_OPE = "-";
    /** 掛け算の演算子 */
    public static final String MULT_OPE = "*";
    /** 割り算の演算子 */
    public static final String DEVI_OPE = "/";
    /** 剰余算の演算子 */
    public static final String JOYO_OPE = "%";
    /** 左側の数字 */
    private List<String> leftList;
    /** 右側の数字 */
    private List<String> rightList;
    /** 保持する四則演算子 */
    private List<Character> opeList;
    /** 内部に保持する計算Obj */
    private List<CalcObj> children;
    /** 保持する項(RpgStatus) */
    private List<RpgData> koList;
    /** 計算可能状態 */
    private boolean canCalcurate;

    /** コンストラクタ。 */
    public CalcObj() {
        leftList = new ArrayList<String>();
        rightList =  new ArrayList<String>();
        koList = new ArrayList<RpgData>();
        opeList = new ArrayList<Character>();
        children = new ArrayList<CalcObj>();
    }

    /**
     * 四則演算子を設定する。
     * @param ope 四則演算子
     */
    public void setOpes(char ope) {
        opeList.add(ope);
    }

    public void setLeft(String left) {
        leftList.add(left);
    }

    public void setRight(String right) {
        rightList.add(right);
    }

    /**
     * 未使用
     * A + B, A - B, A * B, A / B, A % Bのように
     * 2つの数をセットされたオブジェクトの計算を行う。
     * @return 計算結果
     * @throws RpgException 想定外のエラー
     */
    @Deprecated
    public int calcurate() throws RpgException {
//        if (koList.get(0) == null || koList.get(1) == null
//                || ope4 == null || "".equals(ope4)) {
//            throw new RpgException(MessageConst.ERR_CALCOBJ.toString() + " in CaclObj#calcurate");
//        }
//        int result = 0;
//        boolean firstFlg = false;
//        // 必ず２回ループする
//        for (int i = 0; i < 2; i++) {
//            RpgData status = koList.get(i);
//            if (firstFlg == false) {
//                result = status.getValue();
//                firstFlg = true;
//                continue;
//            }
//            if (ADD_OPE.equals(ope4)) {
//                if (isDebug) System.out.println("Add: " + status.getValue());
//                result += status.getValue();
//            } else if (SUBT_OPE.equals(ope4)) {
//                if (isDebug) System.out.println("sub: " + status.getValue());
//                result -= status.getValue();
//            } else if (MULT_OPE.equals(ope4)) {
//                if (isDebug) System.out.println("mul: " + status.getValue());
//                result *= status.getValue();
//            } else if (DEVI_OPE.equals(ope4)) {
//                if (isDebug) System.out.println("div: " + status.getValue());
//                result /= status.getValue();
//            } else if (JOYO_OPE.equals(ope4)) {
//                if (isDebug) System.out.println("Joy: " + status.getValue());
//                result %= status.getValue();
//            }
//        }
        return 0;
    }

    public Map<Integer, String> toIndexCalc(String formula, Map<Integer, String> result) throws RpgException {
        int startKako = 0;
        int endKako = 0;
        char[] fArr = formula.toCharArray();
        // かっこの数を数える
        for (int i = 0; i < fArr.length; i ++) {
            if (isDebug) System.out.print(fArr[i]);
            if (fArr[i] == '(') {
                //System.out.println("is start");
                startKako++;
            } else if (fArr[i] == ')') {
                //System.out.println("is end");
                endKako++;
            }
        }
        // かっこ開始と、終了の数が等しくない場合はエラー
        if (startKako != endKako) {
            throw new RpgException(MessageConst.ERR_KAKO_NOT_SAME.toString());
        }
        // かっこの内部を切り出す
        String f = formula;
        int count = 0;
        for (int i = 0; i < startKako; i++) {
            if (isDebug) System.out.println("*** Testing ***");
            int start = f.indexOf("(");
            int end = f.indexOf(")");
            if (start < 0 || end < 0) {
                break;
            }
            String o = null;
            System.out.println("isOpe: " + f);
            if ((o = findOperator(f)) != null) {
                System.out.println("find: " + o);
                result.put(count, o);
                count++;
            }
            System.out.println("start: " + start + " end: " + end);
            String out = null;
            int lastIdx = 0;
//            if (f.endsWith(")")) {
//                end = f.lastIndexOf(")") + 1;
//            }
            out = f.substring(start, end + 1);
            System.out.println("Str: " + out);
            result.put(count, out.trim());
            if (f.length() < end + 1) {
                break;
            }
            f = f.substring(end + 1);
            System.out.println("f: " + f);
            count++;
            if (f.length() <= 0) {
                break;
            }
        }
        System.out.println("length: " + result.size());

        return result;
    }

    /**
     * 文字列fomulaに四則演算子が含まれているときはtrue
     * @param formula 計算式の文字列
     * @return 演算子が初めに来るときは対処の文字、それ以外はNullを返却する。
     */
    public String findOperator(String formula) {
        String res = null;
        String f = formula.trim();
        String ope = f.substring(0, 1);
        System.out.println("findOpe: " + ope);
        boolean add = ope.equals("+");
        boolean sub = ope.equals("-");
        boolean mul = ope.equals("*");
        boolean dev = ope.equals("/");
        boolean joy = ope.equals("%");
        if (add | sub | mul | dev | joy) {
             return ope;
        }
        return null;
    }

    public int[][] forwardIdx(String formula) {
        boolean[][] idxs = new boolean[2][formula.length()];
        int indexCount = 0;
        // 文字配列
        char[] ch = formula.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == '(') {
                if (isDebug) System.out.println("(: " + i);
                idxs[0][i] = true;
                idxs[1][i] = false;
                indexCount++;
            } else if (ch[i] == ')') {
                if (isDebug) System.out.println("): " + i);
                idxs[0][i] = false;
                idxs[1][i] = true;
            } else {
                idxs[0][i] = false;
                idxs[1][i] = false;
            }
        }
        // カウンター初期化
        int countStart = 0;
        int countEnd = 0;
        int[][] indexes = new int[2][indexCount];
        for (int i = 0; i < idxs[0].length; i++) {
            if (idxs[0][i]) {
                if (isDebug) System.out.println("start: " + i);
                indexes[0][countStart] = i;
                countStart++;
            }
            if (idxs[1][i]) {
                if (isDebug) System.out.println("end: " + i);
                indexes[1][countEnd] = i + 1;
                countEnd++;
            }
        }
        return indexes;
    }

    public int[][] reversIdx(String formula) {
        boolean[][] idxs = new boolean[2][formula.length()];
        // 文字配列
        char[] ch = formula.toCharArray();
        int indexCount = 0;
        int lastCount = 0;
        int last = ch.length;
        for (int i = last - 1; i >= 0; i--) {
            if (ch[i] == '(') {
                if (isDebug) System.out.println("(: " + i);
                idxs[0][i] = true;
                idxs[1][i] = false;
                indexCount++;
            } else if (ch[i] == ')') {
                if (isDebug) System.out.println("): " + i);
                idxs[0][i] = false;
                idxs[1][i] = true;
            } else {
                idxs[0][i] = false;
                idxs[1][i] = false;
            }
        }
        int[][] indexes = new int[2][indexCount];
        last = idxs[0].length;
        indexCount = 0;
        lastCount = 0;
        for (int i = last - 1; i >= 0 ; i--) {
            if (idxs[0][i]) {
                //System.out.println("rev0: " + i);
                indexes[0][indexCount] = i;
                indexCount++;
            }
            if (idxs[1][i]) {
                //System.out.println("rev1: " + i);
                indexes[1][lastCount] = i + 1;
                lastCount++;
            }
        }
        int endKako = this.countEndKako(formula);

        for (int i = 0; i < endKako; i++) {
            int tmp = indexes[0][i];
            indexes[0][i] = indexes[0][i + 1];
            indexes[0][i + 1] = tmp;
            int tmp1 = indexes[1][i];
            indexes[1][i] = indexes[1][i + 1];
            indexes[1][i + 1] = tmp1;
        }

        return indexes;
    }

    /**
     * かっこのインデクス配列を作成する。
     * [0]: '('のある場所にtrue
     * [1]: ')'のある場所にtrue
     * @param formula 計算式の文字列
     * @return booleanの2次元配列
     */
    public int[][] analizeFormula(String formula) {
        int[][] indexes = null;
        boolean isBackward = formulaType(formula);
        // 文字配列
        char[] ch = formula.toCharArray();
        if (isBackward) {
            indexes = reversIdx(formula);
        } else {
            indexes = forwardIdx(formula);
        }
        return indexes;
    }

    public String convertKigo(String formula) throws RpgException {
        Map<String, RpgData> map = RpgConfig.getInstance().getParamMap();

        Set<String> set = map.keySet();
        String result = null;
        result = formula;
        for (String key : set) {
            //System.out.println("Key: " +  key + " Val; " + map.get(key));
            String s = String.valueOf(map.get(key).getValue());

            result = result.replaceAll(key, s);
        }
        //System.out.println("result: " + result);
        return result;
    }

    public void cutFormulaStr(String formula) throws RpgException {
        List<String> list = this.splitFormula(formula);
        for (String st : list) {
            String res = formula.replace(st, "CONVERT");
            System.out.println(res);
        }
    }

    public boolean formulaType(String formula) {
        boolean isBackward = false;
        if (formula.endsWith("))")) {
            isBackward = true;
        }
        return isBackward;
    }

    /**
     * 閉じかっこの数を数える。
     * @param formula 文字列式
     * @return 閉じかっこの数
     */
    public int countEndKako(String formula) {
        char[] ch = formula.toCharArray();
        int count = 0;
        for (int i = 0; i < ch.length; i++) {
            if (')' == ch[i]) {
                count++;
            }
        }
        int pos = 0;
        int kakoCount = 0;
        for (int i = 0; i < count; i++) {
            pos = formula.indexOf("))", pos);
            if (pos < 0) {
                break;
            }
            pos += 1;
            kakoCount++;
        }
        return kakoCount;
    }

    public List<String> splitFormula(String formula) throws RpgException {
        List<String> formulaList = new ArrayList<>();
        Map<String, Integer> formMap = new LinkedHashMap<>();
        String res = convertKigo(formula);
        System.out.println("Formula: " + res);
        char[] ch = res.toCharArray();

        int startCount = 0;
        int endCount = 0;
        int[] kakoIdxes = new int[RpgConst.FORUMULA_MAX_KAKO];
        int result = 0;
        int maxLn = ch.length;
        String tmp = formula;
        String chStr = formula;
        try {
            for (int i = 0; i < maxLn; i++) {
                if (ch[i] == '(') {
                    if (isDebug) System.out.println("in start: " + startCount);
                    kakoIdxes[startCount] = i;
                    startCount++;
                }
                if (ch[i] == ')') {
                    startCount--;
                    String p = tmp.substring(kakoIdxes[startCount], i + 1);
                    if (formMap.size() > 0) {
                        Set<String> set = formMap.keySet();
                        for (String s : set) {
                            chStr = chStr.replace(s, String.valueOf(calcurate(s)));
                            System.out.println("chStr: " + chStr);
                        }
                    }
                    //System.out.println(i + ": " + p);
                    formMap.put(p, calcurate(p));

                    if (isDebug) System.out.println("out start: " + startCount + " index: " + kakoIdxes[startCount]);
                    formulaList.add(p);
                }
            }
        } catch (ArrayIndexOutOfBoundsException ae){
            ae.printStackTrace();
            throw new RpgException(MessageConst.ERR_FORMLA_KAKKO.toString());
        }

        Set<String> set = formMap.keySet();
        String conv = formula;
        for (String s : set) {
            System.out.println("s: " + s);
            conv = conv.replace(s, String.valueOf(formMap.get(s)));
            System.out.println("conv: " + conv);
        }
        return formulaList;
    }

    /**
     * かっこのない状態での計算を前提とする。数字と、演算子はスペースを間に入れること。
     * 演算の順番は左から右にそのまま進む
     * 例：(123 + 45), 4- 9,
     * 3桁までの計算。
     * @param formula 文字列の数式:
     * @return 計算の結果
     * @throws RpgException 想定外のエラー
     */
    public int calcurate(String formula) throws RpgException {
        int result = 0;
        // かっこ及びスペースを削除
        String form = formula.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ", "");
        // 式の長さ(終了判定に使用のため1減算)
        int len = form.length() - 1;

        if (isDebug) System.out.println("Formula: " + form + " len: " + len);
        // 文字配列に変換する
        char[] ch = form.toCharArray();
        // 文字バッファ
        StringBuilder buf = new StringBuilder();
        boolean isSetLeft = false;
        boolean isSetRight = false;
        boolean isSetOpe = false;
        for (int i = 0; i < ch.length; i++) {
            // 数字か判定する
            boolean isNum = CheckerUtils.isNumber(ch[i]);
            // 演算子か判定する
            boolean isOpe = CheckerUtils.isShisokuOperator(ch[i]);
            // 左と右のリスト長が同じ
            boolean sameListSize = leftList.size() == rightList.size();
            // 次ん文字が存在する
            char nextMoji = len - i > 0 ? ch[i + 1] : Character.MIN_VALUE;
            boolean nextNum = CheckerUtils.isNumber(nextMoji);
            // 文字列
            if (isNum && (isSetLeft == false | isSetRight == false)) {
                buf.append(String.valueOf(ch[i]));
                if (i != len) continue;
            }

            // 数字でリスト長が同じ場合は左に追加
            if (isNum == false && sameListSize) {
                isSetLeft = true;
                if (isDebug) System.out.println("Left: " + buf.toString());
                setLeft(buf.toString());
                buf = new StringBuilder();
            }
            // 演算子を追加
            if (isOpe) {
                if (isDebug) System.out.println("Opes Add: " + ch[i]);
                setOpes(ch[i]);
                isSetOpe = true;
                if (buf.length() == 0) {
                    continue;
                }
                isSetRight = true;
                if (isDebug) System.out.println("Opes=>Right: " + buf.toString());
                setRight(buf.toString());
                buf = new StringBuilder();
            }
            // 右にセット
            if (isNum && isSetRight == false) {
                isSetRight = true;
                if (isDebug) System.out.println("Right: " + buf.toString());
                setRight(buf.toString());
                buf = new StringBuilder();
            }
            int answer = 0;
            if (isDebug) System.out.println("isSetLeft: " + isSetLeft + " isSetRight: " + isSetRight);
            // 計算処理可能
            if (isSetLeft && isSetRight) {
                for (int j = 0; j < leftList.size(); j++) {
                    double left = Double.valueOf(leftList.get(j));
                    char ope = opeList.get(j);
                    double right = Double.valueOf(rightList.get(j));
                    if (isDebug) System.out.println("Left: " + left + " Right: " + right + " ope: " + ope);
                    answer = (int) cal(left, right, ope);
                    if (isDebug) System.out.println("Anser: " + answer);
                    result = answer;
                    if (i >= len) {
                        break;
                    } else {
                        isSetRight = false;
                        leftList.clear();
                        opeList.remove(j);
                        leftList.add(String.valueOf(result));
                        rightList.clear();
                    }
                }
            }
        }
        return result;
    }

    public double cal(double left, double right, char ope) {
        double result = 0;
        if ('+' == ope) {
            result = left + right;
        }
        if ('-' == ope) {
            result = left - right;
        }
        if ('*' == ope) {
            result = left * right;
        }
        if ('/' == ope) {
            result = left / right;
        }
        if ('%' == ope) {
            result = left % right;
        }
        return result;
    }

    public void reset() {
        this.leftList.clear();
        this.rightList.clear();
        this.children.clear();
        this.opeList.clear();
        this.koList.clear();
    }

    /**
     * メモリ開放
     */
    public void finalize() {
        this.leftList = null;
        this.rightList = null;
        this.children = null;
        this.opeList = null;
        this.koList = null;
    }


}
