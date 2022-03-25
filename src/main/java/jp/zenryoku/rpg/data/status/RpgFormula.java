package jp.zenryoku.rpg.data.status;

import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import lombok.Data;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ストーリーテキストから生成する、各種計算式クラス。
 */
@Data
public class RpgFormula extends RpgData {
    public boolean isDebug = false;
    /**　計算式(文字列) */
    private String formulaStr;
    /** 関連項目 */
    private List<RpgStatus> relatedList;

    public RpgFormula() {
        super.setType(RpgConst.DATA_TYPE_FORMULA);
        relatedList = new ArrayList<>();
    }

    /**
     * 数式をセットする。
     * @param formula
     */
    public RpgFormula(String formula) {
        super.setType(RpgConst.DATA_TYPE_FORMULA);
        this.formulaStr = formula;
    }

    /**
     * 計算結果の小数点以下は四捨五入する。
     * <ol>
     *     <li>プレーヤーのステータス、オプショナルステータス、設定オブジェクトから計算岸マップを取得する</li>
     *     <li>計算式 → オプショナルステータス → ステータスの順で記号を数字(プレーヤーの状態値)に変換する</li>
     *     <li>変換した数字をexp4jで計算、欠課をdouble型で返却する。</li>
     * </ol>
     * @param player
     * @return 計算結果
     */
    public int formula(PlayerCharactor player) {
        int result = 0;
        Map<String, RpgStatus> statusMap = player.getStatusMap();
        Map<String, RpgStatus> optionalMap = player.getOptionalMap();
        Map<String, RpgFormula> formulaMap = RpgConfig.getInstance().getFormulaMap();
        if (isDebug) {
            formulaMap.forEach((key, val) -> {
                System.out.println("key: " + key + "  val: " + val.getKigo());
            });
        }
        String convStr = formulaStr;
        if (isDebug) System.out.println("formulaStr: " + formulaStr);

        Set<String> setForm = formulaMap.keySet();
        for (String key : setForm) {
            RpgFormula f = formulaMap.get(key);
            RpgData d = (RpgData)f;
            if (isDebug) System.out.println("Key: " + f.getKigo());
            convStr = convStr.replaceAll(key, f.getFormulaStr());
        }
        if (isDebug) System.out.println("Form: " + convStr);
        Set<String> setOpt = optionalMap.keySet();
        if (isDebug) System.out.println("*** Opotional ***");
        for (String key : setOpt) {
            RpgData data = optionalMap.get(key);
            if (data == null) {
                System.out.println("Ket is null: " + key);
            } else if (data.getValue() == null) {
                System.out.println("Value is null: " + key);
            }
            RpgFormula form = data.getFormula();
            if (isDebug) System.out.println(data.getKigo() + " : " + form);
            if (form != null) {
                if (isDebug) System.out.print(data.getKigo() + " : " + form.getFormulaStr() + ", ");
                convStr = convStr.replaceAll(data.getKigo(), form.getFormulaStr());
            } else {
                if (isDebug) System.out.print(data.getKigo() + " : " + data.getValue() + ", ");
                convStr = convStr.replaceAll(data.getKigo(), String.valueOf(data.getValue()));
            }
            if (isDebug) System.out.println("Convert: " + convStr);
        }
        if (isDebug) System.out.println();

        Set<String> set = statusMap.keySet();
        if (isDebug) System.out.println("式：" + formulaStr);
        if (isDebug) System.out.print("Key: ");
        for (String key : set) {
            RpgData data = statusMap.get(key);
            if (isDebug) System.out.print(data.getKigo() + ", ");
            convStr = convStr.replaceAll(data.getKigo(), String.valueOf(data.getValue()));
        }
        if (isDebug) System.out.print("conv: " + convStr);

        double res = new ExpressionBuilder(convStr).build().evaluate();
        if (isDebug) System.out.println(" = " + res);
        result = (int) Math.round(res);
        return result;
    }

    /**
     * 計算結果の小数点以下は四捨五入する。
     * @param player
     * @return 計算結果
     */
    public double formula(PlayerCharactor player, boolean isDouble) {
        double result = 0.0;
        Map<String, RpgData> params = RpgConfig.getInstance().getParamMap();
        Map<String, RpgStatus> statusMap = player.getStatusMap();
        Set<String> set = params.keySet();
        String convStr = formulaStr;
        for (String key : set) {
            RpgData data = params.get(key);
            convStr = convStr.replaceAll(data.getKigo(), String.valueOf(data.getValue()));
        }
        Set<String> stSet = statusMap.keySet();
        for (String key : stSet) {
            RpgStatus st = statusMap.get(key);
            convStr = convStr.replaceAll(st.getKigo(), String.valueOf(st.getValue()));
        }
        System.out.print("conv: " + convStr);
        double res = new ExpressionBuilder(convStr).build().evaluate();
        System.out.println(" = " + res);

        if (isDouble) {
            // 少数(小数点第3位を四捨五入)
            result = new BigDecimal(res).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else {
            // 整数(小数点以下四捨五入)
            result = Math.round(res);
        }
        return result;
    }
    @Override
    public RpgFormula clone() {
        return clone();
    }
}
