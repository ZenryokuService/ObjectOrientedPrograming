package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.calc.CalcObj;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.*;
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
     * @throws RpgException
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
     * RpgDataを親に持つクラスの計算を行う。
     * CONFIG_FORMULA以下にある計算式を計算する。
     *
     * @param line CONFIG_FORMULAの計算式
     */
    public void calcRpgData(String line) {
        String[] texts = line.split(":");
        Map<String, RpgData> map = RpgConfig.getInstance().getItemTypeMap();
        String tex = texts[1];
        int start = tex.indexOf("=");
        String siki = tex.substring(start);
        System.out.println(siki);


    }

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
        //System.out.println(siki);
        return siki;
    }

    /**
     * 「POW + AGI」のような文字列を計算する。
     * TODO-[仕様：式は必ず半角スペースで区切られていること]
     * @param tankoshiki 「POW + AGI」のような文字列
     */
    public void calcStr(String tankoshiki, Map<String, RpgStatus> statusMap) {
        String[] sep = tankoshiki.split(" ");
        int koCount = sep.length;
        for (String key : sep) {
            if (CALC_ADD.equals(key)) {

            }
            RpgStatus status = statusMap.get(key);
            if (status == null) {

            }
        }
    }
    /**
     * (POW + WEV) * (1 + (0.1 * JLV))
     * @param shiki 上記のような文字列
     * @throws RpgException
     */
    public List<String> tankoShiki(String shiki) throws RpgException {
        Map<String, RpgData> statusMap = RpgConfig.getInstance().getParamMap();
        // TODO-[記号は半角大文字の3文字で作る決まり->仕様に追加]
        // かっこ開始フラグ
        boolean startKako = false;
        // かっこ終了フラグ
        boolean endKako = false;
        // A-Zの１文字に該当する
        Pattern charPat = Pattern.compile("[A-Z]");
        // 演算子の文字に該当する
        Pattern opePat = Pattern.compile("[+\\-*/]");
        // 文字列作成
        StringBuilder buf = new StringBuilder();
        // 記号のリスト
        List<String> kigoList = new ArrayList<>();
        // 計算オブジェクトのリスト
        List<CalcObj> calcList = new ArrayList<>();
        // データオブジェクトのりすと
        List<RpgData> dataList = new ArrayList<>();
        // 演算子の順番
        int opeCount = 0;

        char[] ch = shiki.toCharArray();
        CalcObj calObj = null;
        for (char c : ch) {
            // ||

            // 四則演算の記号に該当するとき
            String moji = String.valueOf(c);
            Matcher opeMat = opePat.matcher(moji);
            if (opeMat.matches()) {
                calObj = new CalcObj();
                //calObj.setOpe4(moji);
            }
            // A-Zの文字列に該当するとき
            Matcher mat = charPat.matcher(moji);
            if (mat.matches()) {
                buf.append(moji);
                if (buf.length() >= 3) {
                    // 3文字になったら追加
                    RpgData data = statusMap.get(buf.toString());
                    dataList.add(data);
                    buf = null;
                    buf = new StringBuilder();
                }
            }
        }
        return kigoList;
    }

    /** 文字切り出し用のCharGuffer */
    private CharBuffer createBuffer(int capacity) {
        CharBuffer buf = CharBuffer.allocate(RpgConst.BUF_SIZE);
        buf.limit(capacity);
        return buf;
    }
}
