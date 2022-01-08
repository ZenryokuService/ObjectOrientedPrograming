package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.exception.RpgException;

import java.util.Random;

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
        System.out.println("times: " + times + " dice: " + dice);
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
}
