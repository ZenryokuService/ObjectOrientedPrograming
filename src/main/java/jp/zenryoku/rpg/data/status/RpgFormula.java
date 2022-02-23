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
     * @param player
     * @return
     */
    public int formula(PlayerCharactor player) {
        int result = 0;
        Map<String, RpgStatus> statusMap = player.getStatusMap();
        Map<String, RpgStatus> optionalMap = player.getOptionalMap();
        Set<String> set = statusMap.keySet();
        String convStr = formulaStr;

        System.out.print("Key: ");
        for (String key : set) {
            RpgData data = statusMap.get(key);
            System.out.print(data.getKigo() + ", ");
            convStr = convStr.replaceAll(data.getKigo(), String.valueOf(data.getValue()));
        }
        Set<String> setOpt = optionalMap.keySet();
        for (String key : setOpt) {
            RpgData data = optionalMap.get(key);
            if (data == null) {
                System.out.println("Ket is null: " + key);
            } else if (data.getValue() == null) {
                System.out.println("Value is null: " + key);
            }
            System.out.print(data.getKigo() + " : " + data.getValue() + ", ");
            convStr = convStr.replaceAll(data.getKigo(), String.valueOf(data.getValue()));
        }
        System.out.println();
        System.out.print("conv: " + convStr);
        double res = new ExpressionBuilder(convStr).build().evaluate();
        System.out.println(" = " + res);
        result = (int) Math.round(res);
        return result;
    }

    /**
     * 計算結果の小数点以下は四捨五入する。
     * @param player
     * @return
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
