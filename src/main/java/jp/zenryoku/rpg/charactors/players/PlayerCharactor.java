package jp.zenryoku.rpg.charactors.players;

import jp.zenryoku.rpg.charactors.Player;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.Effects;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.RpgStm;
import jp.zenryoku.rpg.data.charactor.RpgLevel;
import jp.zenryoku.rpg.data.job.RpgJob;
import jp.zenryoku.rpg.data.status.RpgFormula;
import jp.zenryoku.rpg.data.status.RpgStatus;
import jp.zenryoku.rpg.data.status.StEffect;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.item.equip.Armor;
import jp.zenryoku.rpg.item.equip.MainWepon;
import jp.zenryoku.rpg.util.CalcUtils;
import jp.zenryoku.rpg.util.ConsoleUtils;
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
    /** ステータス異常(変化)リスト */
    protected List<Effects> effectList;
    /** 動けるフラグ */
    protected boolean canMove;
    /** 職業 */
    protected RpgJob job;
    /** 経験値 */
    protected int exp;
    /** STMマップ */
    protected List<RpgStm> stmList;


    /**
     * ストーリーテキストの設定情報を取得して、ステータスを生成する。
     * @param name 名前
     * @throws RpgException 想定外のエラー
     */
    public PlayerCharactor(String name) throws RpgException {
        super(name);
        statusMap = new LinkedHashMap<String, RpgStatus>();
        optionalMap = new LinkedHashMap<String, RpgStatus>();
        effectList = new ArrayList<Effects>();
        canMove = true;
        // ステータス設定を取得する。
        Map<String, RpgStatus> stMap = RpgConfig.getInstance().getStatusMap();
//        System.out.println("*** PlayerCharactor ***");
//        System.out.println(stMap);
        statusMap.putAll(stMap);
        // オプショナルステータスマップ
        Map<String, RpgStatus> optMap = RpgConfig.getInstance().getOptionStatusMap();
        optionalMap.putAll(optMap);
    }

    /**
     * 攻撃力を取得する
     * @return 攻撃力
     * @throws RpgException ステータスオブジェクトがない
     */
    public int getAtk() throws RpgException {
        RpgStatus atk = optionalMap.get(RpgConst.ATK);
        if (atk == null) {
            throw new RpgException(MessageConst.NO_STATUS_OBJECT.toString() + " ATK: " + atk);
        }
        return atk.getValue();
    }

    /**
     * 秒魚力を取得する
     * @return 防御力
     * @throws RpgException ステータスオブジェクトがない
     */
    public int getDef() throws RpgException {
        RpgStatus def = optionalMap.get(RpgConst.DEF);
        if (def == null) {
            throw new RpgException(MessageConst.NO_STATUS_OBJECT.toString() + " DEF: " + def);
        }
        return def.getValue();
    }

    /**
     * 武器を装備する。
     * @param wepon 武器オブジェクト
     * @throws RpgException 想定外のエラー
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
     * @throws RpgException 想定外のエラー
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

    public void getEffect(StEffect effect) {
        /// 対象になる項目
        String kigo = effect.getKigo();
        // 増減
        //String ope = effect.get();

    }

    /**
     * 設定オブジェクトから、レベルアップマップを取得して対象の経験値を取得したらレベルアップする。
     */
    public void levelup() {
        Map<String, RpgLevel> levelMap = RpgConfig.getInstance().getLevelMap();
        String jobKigo = job.getJobId();
        if (isDebug) {
            System.out.println("jobKigo; " + jobKigo);
            System.out.println(levelMap);
        }
        RpgLevel data = levelMap.get(jobKigo);
        if (isDebug) System.out.println(data);
        int lv = data.getLevelup()[level - 1];
        if (exp >= lv) {
            level = level + 1;
            System.out.println(name + "のレベルが上がりました。");
            // ステータスアップ

            Map<String, RpgStatus> upMap = job.getStatusUpMap();
            Set<String> set = upMap.keySet();
            for (String key : set) {
                RpgStatus upStatus = upMap.get(key);
                RpgStatus pStatus = statusMap.get(upStatus.getKigo());
                pStatus.setValue(pStatus.getValue() + upStatus.getValue());
                System.out.println(getName() + "は" + pStatus.getName() + "が" + upStatus.getValue() + "あがった");
                // 入力チェックなし
                ConsoleUtils.getInstance().acceptInput("", false);
            }
            System.out.println(name + "のHPとMPが回復した。");
            HP = getMaxHP();
            MP = getMaxMP();
        }
    }

    public void getDamage(int damage) {
        HP = HP - damage;
    }

    public void useMp(int useMp) {
        MP = MP - useMp;
    }
}
