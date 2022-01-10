package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.data.ParamGenerator;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.RpgItemType;
import jp.zenryoku.rpg.exception.RpgException;

import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 各種計算、数値取得を行う。
 */
public class CalcUtils {
    private static final boolean isDebug = false;
    private static CalcUtils instance;

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
}
