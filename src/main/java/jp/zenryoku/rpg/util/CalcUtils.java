package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.calc.CalcObj;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.*;
import jp.zenryoku.rpg.data.status.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;

import java.nio.CharBuffer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 各種計算、数値取得を行う。
 */
public class CalcUtils {
    private static final boolean isDebug = false;
    /** 自身のインスタンス */
    private static CalcUtils instance;
    /** 足し算を示すオペレータ */
    public static String CALC_ADD = "+";
    /** 引き算を示すオペレータ */
    public static String CALC_SUBTRACTION = "-";
    /** 掛け算を示すオペレータ */
    public static String CALC_MULTIPLY = "*";
    /** 割り算を示すオペレータ */
    public static String CALC_DEVIDE = "/";

    private CalcUtils() {
    }

    public static CalcUtils getInstance() {
        if (instance == null) {
            instance = new CalcUtils();
        }
        return instance;
    }
    /**
     * 「3D6」と書いてある場合は、６面ダイスを３回降る。
     * 「1D6」と書いてある場合は、６面ダイスを１回降る。
     * @param diceCode 3D6, 1D10のようなコード
     * @return 各ダイスの合計値
     * @throws RpgException 想定外のエラー
     */
    public int throwDice(String diceCode) throws RpgException {
        if (CheckerUtils.isCommandInput(diceCode, "[0-9]D[0-9]{1,2}") == false) {
            if (isDebug) System.out.println("*** Test: " + diceCode);
            throw new RpgException("ダイスの指定が間違っています。: 1D18などのように「回数:何面ダイス」と指定してください。");
        }
        int result = 0;
        String[] param = diceCode.split("D");
        int times = Integer.parseInt(String.valueOf(param[0]));
        int dice = Integer.parseInt(String.valueOf(param[1]));
        if (isDebug) System.out.println("times: " + times + " dice: " + dice);
        Random rdn = new Random();
        ConsoleUtils console = ConsoleUtils.getInstance();
        for (int i = 0; i < times; i++) {
           int diceRes = rdn.nextInt(dice) + 1;
           console.printMessage((i + 1) + "回目: " + diceRes);
           //console.acceptInput("<次へ>");
           result += diceRes;
        }
        console.printMessage("合計: " + result);
        return result;
    }

    /**
     * 設定済みのダイスコードがある場合はこちらのメソッドを使用する。
     * 複数回表示すると気にも使用可能。
     * @param diceTime ダイスを振る回数
     * @param dice 何面ダイスかを示す
     * @param message 表示メッセージ
     * @return 取得した値
     * @throws RpgException 想定外のエラー
     */
    public int throwDice(int diceTime, int dice, String message, String komoku) throws RpgException {
        int result = 0;
        Random rdn = new Random();
        ConsoleUtils console = ConsoleUtils.getInstance();
        console.acceptInput(message, false);
        for (int i = 0; i < diceTime; i++) {
            int diceRes = rdn.nextInt(dice) + 1;
            if (diceTime != 1) {
                console.printMessage((i + 1) + "回目: " + diceRes);
            } else {
                console.printMessage(komoku + ": " + diceRes);
            }
            result += diceRes;
        }
        if (diceTime != 1) {
            console.printMessage("合計: " + result);
        }
        return result;
    }

    /**
     * 日本語の計算式を記号の計算式に変換する。
     * @param siki 記号のある計算式の文字列
     * @return 変換した数字のみの数式文字列
     * @throws Exception 想定外のエラー
     */
    public String sepTankoSiki(String siki) throws Exception {
        Map<String, RpgData> dataMap = ParamGenerator.getInstance().getAllMap();
        Set<String> set = dataMap.keySet();
        for (String val : set) {

            RpgData data = dataMap.get(val);
            if (data == null) {
                throw new RpgException(MessageConst.ERR_NO_KEY_GENPARA.toString() + ": " + val);
            }
            siki = siki.replaceAll(data.getName(), data.getKigo());
        }
        if (isDebug) System.out.println(siki);
        return siki;
    }

    /**
     * 記号のある計算式内の、記号部分をRpgStatusで取得、リストにして返却。
     * @param siki ATK = (POW + WEV) * (1 + (0.1 * JLV))
     * @return POW, WEV, JLVのRpgStatusリスト
     * @throws Exception  想定外のエラー
     */
    public List<RpgStatus> relatedSymbols(String siki, Map<String, RpgStatus> statusMap, Map<String, RpgStatus> optMap) throws RpgException {
        List<RpgStatus> result = new ArrayList<>();

        if (isDebug) {
            System.out.println("*** Testing ***");
            statusMap.forEach((key ,val) -> {
                System.out.println("statusKey: " + key + " : " + val);
            });
            optMap.forEach((key ,val) -> {
                System.out.println("optKey: " + key + " : " + val);
            });
        }

        Pattern pat = Pattern.compile(RpgConst.REG_KIGO);
        char[] ch = siki.toCharArray();
        StringBuilder build = new StringBuilder();
        for  (int i = 0; i < ch.length; i++) {
            String moji = String.valueOf(ch[i]);
            Matcher mat = pat.matcher(moji);
            if (mat.matches()) {
                build.append(moji);
                if (build.length() > RpgConst.KIGO_SIZE) {
                    String key = build.toString();
                    RpgStatus st = statusMap.get(key);
                    if (st == null) {
                        st = optMap.get(key);
                    }
                    if (st == null) {
                        throw new RpgException(MessageConst.ERR_PARAM_KIGO.toString() + ": " + key);
                    }
                    result.add(st);
                    build = new StringBuilder();
                }
            }
        }
        if (result.size() == 0) {
            throw new RpgException(MessageConst.ERR_NO_KEY_GENFORMLA.toString() + " : " + siki);
        }
        return result;
    }
}
