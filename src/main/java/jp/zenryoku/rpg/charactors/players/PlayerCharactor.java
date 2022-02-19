package jp.zenryoku.rpg.charactors.players;

import jp.zenryoku.rpg.charactors.Player;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.RpgFormula;
import jp.zenryoku.rpg.data.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.item.equip.Armor;
import jp.zenryoku.rpg.item.equip.MainWepon;
import jp.zenryoku.rpg.util.CalcUtils;
import lombok.Data;

import java.util.*;

@Data
public class PlayerCharactor extends Player {
    private static final boolean isDebug = false;
    /** 生年月日 */
    private String birthDay;
    /** ステータスリスト */
    protected Map<String, RpgStatus> statusMap;
    /** オプショナルステータスリスト */
    protected Map<String, RpgStatus> optionalMap;

    /**
     * ストーリーテキストの設定情報を取得して、ステータスを生成する。
     * @param name
     * @throws RpgException
     */
    public PlayerCharactor(String name) throws RpgException {
        super(name);
        statusMap = new LinkedHashMap<String, RpgStatus>();
        optionalMap = new LinkedHashMap<String, RpgStatus>();
        // ステータス設定を取得する。
        Map<String, RpgStatus> map = RpgConfig.getInstance().getStatusMap();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            RpgStatus data = map.get(key);
            if ((data instanceof RpgStatus) == false) {
                throw new RpgException(MessageConst.NO_MUCH_STATUS.toString() + ": " + data.getType());
            }
            //RpgStatus new1 = data.clone();
            // ダウンキャストしてリストに設定
            // TODO-[オブジェクトが一つになっているので注意が必要]
            statusMap.put(data.getKigo(), data);
        }
    }

    public int getAtk() throws RpgException {
        RpgStatus atk = optionalMap.get(RpgConst.ATK);
        if (atk == null) {
            throw new RpgException("ステータスマップにオブジェクトがありません。AKT" + atk);
        }
        return atk.getValue();
    }

    public int getDef() throws RpgException {
        RpgStatus def = optionalMap.get(RpgConst.DEF);
        if (def == null) {
            throw new RpgException("ステータスマップにオブジェクトがありません。AKT" + def);
        }
        return def.getValue();
    }

    /**
     * 武器を装備する。
     * @param wepon 武器オブジェクト
     */
    @Override
    public void setMainWepon(MainWepon wepon) throws RpgException {
        mainWepon = wepon;
        RpgStatus atk = optionalMap.get(RpgConst.ATK);
        //RpgStatus atk = statusMap.get(RpgConst.ATK);

        Map<String, RpgFormula> map = RpgConfig.getInstance().getFormulaMap();
        RpgFormula formula = map.get(atk.getKigo());
        String formulaStr = formula.getFormulaStr();
        CalcUtils utils = CalcUtils.getInstance();
        if (isDebug) System.out.println("formula: " + formulaStr);
        RpgStatus opt = optionalMap.get(RpgConst.WEV);
        opt.setValue(wepon.getOffence());
        atk.setValue(formula.formula(this));
    }

    /**
     * 防具を装備する。
     * @param arm 防具オブジェクト
     */
    @Override
    public void setArmor(Armor arm) {
        armor = arm;
        RpgStatus def = optionalMap.get(RpgConst.DEF);

        Map<String, RpgFormula> map = RpgConfig.getInstance().getFormulaMap();
        if (isDebug) System.out.println("Map key: " + def.getKigo() + " Name: " + def.getName());
        RpgFormula formula = map.get(def.getKigo());
        //formula.
        if (isDebug) System.out.println("formula: " + formula.getFormulaStr());
        RpgStatus opt = optionalMap.get(RpgConst.ARV);
        opt.setValue(arm.getDeffence());
        def.setValue(formula.formula(this));
    }
}
