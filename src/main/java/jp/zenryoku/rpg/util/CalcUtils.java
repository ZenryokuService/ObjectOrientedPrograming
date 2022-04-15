package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.charactors.Player;
import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.*;
import jp.zenryoku.rpg.data.categry.RpgMaster;
import jp.zenryoku.rpg.data.status.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.CharBuffer;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
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

    public int generateRandom(int start, int end) {
        int res = -1;
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        res = rnd.nextInt(start, end);
        return res;
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
            statusMap.forEach((key ,val) -> {
                System.out.println("statusKey: " + key + " : " + val);
            });
            optMap.forEach((key ,val) -> {
                System.out.println("optKey: " + key + " : " + val);
            });
        }

        Pattern pat = Pattern.compile(RpgConst.REG_KIGO_SINGLE);
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

    public void calcEffect(String kigo, String ope, int kosu) throws RpgException {
        PlayerParty party = PlayerParty.getInstance();
        PlayerCharactor player = party.getPlayer();
        RpgStatus status = player.getStatusMap().get(kigo);

        // ステータスの有無からチェック
        if (status != null) {
            status.setValue(status.getValue() + kosu);
            return;
        }
        // ステータスでなければ、その他
        RpgConfig conf = RpgConfig.getInstance();
        RpgData dd = conf.getParamMap().get(kigo);
        RpgMaster mst = conf.getMasterMap().get(kigo);

        String master = dd != null ? dd.getMaster() : null;
        if (mst == null) {
            mst = conf.getMasterMap().get(master);
        }
        if (dd == null && mst == null && status == null) {
            throw new RpgException(MessageConst.ERR_NO_KEY_GENPARA.toString() + ": " + kigo);
        }
        List<Class<?>> inte = new ArrayList<>();
        if (isDebug) System.out.println(mst);
        Field field = findTargetField(mst.getFieldName(), inte);

        Object targetObj = null;
        String target = inte.get(0).getSimpleName();
        if ("PlayerParty".equals(target)) {
            targetObj = party;
        } else if ("Player".equals(target) || "PlayerCharactor".equals(target)) {
            targetObj = party.getPlayer();
        } else {
            throw new RpgException(MessageConst.ERR_NO_FIELD_GENPARA.toString() + ": " + target + " = " + kigo);
        }

        try {
            if ("+".equals(ope)) {
                Integer i = (Integer) field.get(targetObj);
                field.set(targetObj, i + kosu);
            } else if ("-".equals(ope)) {
                Integer i = (Integer) field.get(targetObj);
                field.set(targetObj, i - kosu);
            }
        } catch (IllegalAccessException ee) {
            throw new RpgException(MessageConst.ERR_MISS_EFFECT_PARAM.toString());
        }
    }

    /**
     *
     * @param fieldName 検索するフィールド名
     * @param res 検索対象フラグ
     * @return 取得したフィールド
     * @throws RpgException フィールドが見つからない場合
     */
    public Field findTargetField(String fieldName, List<Class<?>> res) throws RpgException {
        PlayerParty party = PlayerParty.getInstance();
        Class<? extends PlayerCharactor> plCls = PlayerCharactor.class;
        Class<? extends Player> ply = Player.class;
        Field f = null;
        try {
            f = party.getClass().getDeclaredField(fieldName);
            res.add(party.getClass());
        } catch (NoSuchFieldException e) {
            try {
                f = plCls.getDeclaredField(fieldName);
                res.add(plCls);
            } catch (NoSuchFieldException ne) {
                try {
                    f = ply.getDeclaredField(fieldName);
                    res.add(ply);
                } catch (NoSuchFieldException ee) {
                    throw new RpgException(MessageConst.NO_FIELD.toString() + ": " + fieldName);
                }
            }
        }
        // フィールドにアクセス可能をセット
        f.setAccessible(true);
        return f;
    }

    /**
     * 1. <1D6>: ダイスコード
     * 2. 1:A: シーン定義
     * 3. END_SCENE C: シーンの終了
     * 4. <1:13>: 選択定義
     * 5. <monster:1>：モンスター定義
     * 6. <item:店舗名>: アイテムショップ
     * 7. <effect:ZHP+20TS4>: イベント効果定義
     * 8. <evget:1: a1>: イベントフラグの取得
     *
     * @param testStr 検証元文字列
     * @param target 検証文字列
     * @return 合致率
     */
    public int testMutch(String testStr, String target) {
        // 固定文字列「END_SCENE」の場合
        /* シンプルに一致率を算出する */
        double mutch = 0.0;
        // 1. 文字列の長さ取得
        int len = testStr.length();
        // 2. 文字列長と同じBoolean配列
        boolean[] bArr = new boolean[len];

        // 3. それぞれの文字列が等しいかチェック
        char[] testChs = testStr.toCharArray();
        char[] targetChs = target.toCharArray();

        /* 検証する文字列のタイプを判別 */
        // コロンの有無
        boolean isColon = testStr.indexOf(":") > 0;

        // . マッチする数を計算
        for (char c : targetChs) {


        }

        return (int) mutch;
    }

    /**
     * END_SCENEの文字列、区切り文字、パラメータのブロック(prbablity)11個に分ける。
     * boolean配列(probability)を以下のように定義する。
     * probability[0]-probability[9] => END_SCENEまでの固定文字が等しい
     * probability[10]: パラメータの有無(スペースがあるか)の判定
     * probability[11]: パラメータが[0-9]{1,3}または[A-Z]
     *
     * 合致率 = 各ブロックの判定 / ブロック数(11) * 100
     *
     * @param testStr 検証文字列
     * @return 合致率
     */
    public double calcEndSceneProbability(String testStr) {
        final String endScene = "END_SCENE";
        final int endLen = endScene.length();
        final char[] endChs = endScene.toCharArray();
        final boolean[] probability = new boolean[endLen + 2];

        // 検証文字列が正しいか
        int testLen = testStr.length();
        char[] testChs = testStr.toCharArray();
        for (int i = 0; i < endLen; i++) {
            if (i >= testLen) {
                probability[i] = false;
            }
            if (testChs[i] == endChs[i]) {
                probability[i] = true;
            } else {
                probability[i] = false;
            }
        }
        // パラメータの有無
        if (testStr.contains(" ")) {
            String[] sep = testStr.split(" ");
            String param = sep[1];
            probability[endLen + 1] = param.matches("[0-9]{1,3}|[A-Z]");
            probability[endLen] = true;
        } else {
            probability[endLen + 1] = false;
            probability[endLen] = false;
        }

        int trueCount = 0;
        for (boolean isTrue : probability) {
            if (isTrue) {
                trueCount++;
            }
        }
        // 合致率を計算する
        BigDecimal mutch = percent(trueCount, endLen + 2);
        return Double.parseDouble(mutch.toString());
    }

    /**
     * シーン定義として正しい割合を算出する。計算方法は以下の通り。
     * シーン定義(シーン番号:シーンタイプ)を次のブロックに分ける。
     * ブロック１：シーン番号
     * ブロック２：区切り文字「:(コロン)」
     * ブロック３：シーンタイプ
     *
     * 合致率 = 各ブロックの判定 / ブロック数(3) * 100
     *
     * @param testStr 検査対象文字列
     * @return 合致率：シーン定義として正しい割合を算出する。
     */
    public double calcSceneDefProbability(String testStr) {
        double mutch = 0.0;
        boolean[] probability = new boolean[3];
        // ブロック２： 区切り文字が正しいか
        boolean isColon = testStr.contains(":");
        probability[1] = isColon;
        if (isColon) {
            String[] sep = testStr.split(":");
            // ブロック１：シーン番号
            probability[0] = sep[0].matches("[0-9]{1,3}");
            // ブロック３：シーンタイプ
            probability[2] = sep[1].matches("[A-Z]");
        } else {
            // 区切り位置がない場合は、全て不正とする
            probability[0] = false;
            probability[2] = false;
        }
        // trueの数を算出
        int trueCount = 0;
        for (boolean b : probability) {
            if (b) {
                trueCount++;
            }
        }
        //　パーセンテージの算出
        System.out.println("true; " + trueCount);
        mutch = Double.parseDouble(percent(trueCount, 3).toString());
        return mutch;
    }

    /** TODO-[選択肢の定義行も必要になるのでちょっと保留]
     * シーン定義として正しい割合を算出する。計算方法は以下の通り。
     * シーン定義(シーン番号:シーンタイプ)を次のブロックに分ける。
     * ブロック１：１であるか
     * ブロック２：区切り文字「:(コロン)」
     * ブロック３：数字であるか
     *
     * 合致率 = 各ブロックの判定 / ブロック数(3) * 100
     *
     * @param testStr 検査対象文字列
     * @return 合致率：選択肢開始行として正しい割合を算出する。
     */
    public double calcSelectNextProbability(String testStr) {
        double mutch = 0.0;
        boolean[] probability = new boolean[3];
        return 0.0;
    }

    /**
     * アイテムショップシーン定義として正しい割合を算出する。
     * シーン定義(シーン番号:シーンタイプ)を次のブロックに分ける。
     * ブロック１：「<>」で囲まれているか
     * ブロック２：区切り文字「:(コロン)」
     * ブロック３：「item」になっているか
     * ブロック４：メタ文字が使われていない,かつ３文字以上、１０文字以内
     *
     * 合致率 = 各ブロックの判定 / ブロック数(4) * 100
     *
     * @param testStr 検証文字列
     * @return 合致率：アイテムショップシーン開始行として正しい割合を算出する。
     */
    public double calcItemProbability(String testStr) {
        double mutch = 0.0;
        boolean[] probability = new boolean[4];
        // 1. 「<>」で囲まれているか
        String first = testStr.substring(0,1);
        String last = testStr.substring(testStr.length() - 1, testStr.length());
        System.out.println("last : " + last);
        probability[0] = "<".equals(first) && ">".equals(last);

        // 2. 区切り文字「:(コロン)」
        probability[1] = testStr.contains(":");

        String target = testStr.substring(1, testStr.length() - 1);
        String[] sep = target.split(":");
        // 3. 「item」になっているか
        probability[2] = "item".equals(sep[0]);
        // 4. メタ文字が使われていない,かつ３文字以上、１０文字以内
        probability[3] = sep[1].matches("[^-~｡ﾟ ]{3,10}"); // && sep[1].matches("[^!#$%&'()=\\-+*/~@;:?<>]");

        // 計算する。
        int trueCount = 0;
        for (boolean b : probability) {
            if (b) {
                trueCount++;
            }
        }
        mutch = Double.parseDouble(percent(trueCount, 4).toString());
        return mutch;
    }

    /**
     * 分子 / 分母 * 100の計算結果をBigDecimalで返却する。
     * @param bunsi 分子(double型)
     * @param bunbo 分母(double型)
     * @return 算出したパーセンテージ
     */
    public BigDecimal percent(double bunsi, double bunbo) {
        BigDecimal mutch = new BigDecimal(bunsi)
                .divide(new BigDecimal(bunbo), 2, BigDecimal.ROUND_DOWN)
                .multiply(new BigDecimal(100));
        return mutch;
    }

}
