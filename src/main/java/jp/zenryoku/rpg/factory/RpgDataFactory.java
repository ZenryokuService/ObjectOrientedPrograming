package jp.zenryoku.rpg.factory;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.Effects;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.categry.RpgMaster;
import jp.zenryoku.rpg.data.charactor.RpgLevel;
import jp.zenryoku.rpg.data.items.EvEffect;
import jp.zenryoku.rpg.data.items.RpgItem;
import jp.zenryoku.rpg.data.job.RpgJob;
import jp.zenryoku.rpg.data.status.RpgFormula;
import jp.zenryoku.rpg.data.status.RpgStatus;
import jp.zenryoku.rpg.data.status.StEffect;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.util.CalcUtils;
import jp.zenryoku.rpg.util.CheckerUtils;
import jp.zenryoku.rpg.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * RpgDataを継承する各種データオブジェクトを生成する。
 */
public class RpgDataFactory {
    private static boolean isDebug = false;
    /** 改行コード */
    private static final String SEP = System.lineSeparator();

    /**
     * CONFIG_PARAMの1行からRGPデータを作成する。
     * @param line ストーリーテキストの１行
     * @return RpgData　RPGデータ
     */
    public static RpgData createRpgDataFromConfig(String line, Map<String, RpgData> map) throws RpgException {
        String[] sep = line.split(":");

        if (sep.length != RpgConst.PARAM_SIZE) {
            throw new RpgException(MessageConst.ERR_CONFIG_PARAM.toString() + line);
        }
        if (isDebug) System.out.println("*** Debug: " + sep[2]);
        if (CheckerUtils.isMasterCatNum(sep[2].trim()) == false) {
            throw new RpgException(MessageConst.ERR_CONFIG_PARAM_MASTER.toString() + line);
        }
        // データの設定
        RpgData data = new RpgData();
        data.setName(sep[0].trim());
        data.setKigo(sep[1].trim());
        // パラメータ定義内のマスタカテゴリ設定
        String mast = sep[2].trim();
        if ("-".equals(mast) == false) {
            if (isDebug) System.out.println("*** Debug2: " + sep[2]);
            mast = mast.substring(0,3);
            String priority = sep[2].trim().substring(3);
            data.setPriority(Integer.parseInt(priority));
        }
        data.setMaster(mast);
        // 説明「式」が入っているときはFormulaを生成する。
        String disc = sep[3].trim();
//        if (disc.contains("式=")) {
//            int pos = disc.indexOf("式=");
//            String formula = disc.substring(pos + 2);
//            System.out.println("Formula: " + formula);
//            RpgFormula f = new RpgFormula(formula);
//            data.setFormula(f);
//        }
        data.setDiscription(disc);
        int val = sep[5].trim().equals("-") || sep[5].trim().equals("") ? 0 : Integer.parseInt(sep[5].trim());
        data.setValue(val);
        // 親の設定
        String parent = sep[4].trim();
        data.setParent(parent);
        if ("-".equals(parent) == false) {
            RpgData parentData = map.get(parent);
            if (parentData == null) {
                throw new RpgException(MessageConst.ERR_PARAM_KIGO.toString());
            }
            data.setParentCls(parentData);
        }
        return data;
    }

    /**
     * オプショナルステータスに追加するRpgStatusを生成する。
     * @param d RpgData
     * @return RpgStatus オプショナルステータス
     */
    public static RpgStatus createOptionalRpgStatus(RpgData d) {
        RpgStatus data = new RpgStatus();
        data.setName(d.getName());
        data.setKigo(d.getKigo());
        String disc = d.getDiscription();
        data.setDiscription(disc);
        if (disc.contains("式=")) {
            int pos = disc.indexOf("式=");
            String formula = disc.substring(pos + 2);
            if (isDebug) System.out.println(data.getKigo() + " : Formula: " + formula);
            RpgFormula f = new RpgFormula(formula);
            data.setFormula(f);
        }
        data.setValue(d.getValue());

        return data;
    }

    /**
     * CONFIG_STATUS以降に定義される、１行からステータスのデータを生成する。
     * @param line ストーリーテキストの１行
     * @return RpgStatus ステータス
     * @throws RpgException 想定外のエラー
     */
    public static RpgStatus createRpgStatus(String line) throws RpgException {
        RpgStatus data = new RpgStatus();
        String[] setting = line.split(":");
        if (setting.length != RpgConst.STATUS_SIZE) {
            throw new RpgException(MessageConst.PLAYER_STATUS_SEPARATE3.toString() + SEP + line);
        }
        // 0:ステータス名
        data.setName(setting[0].trim());
        // 1:説明
        data.setDiscription(setting[1].trim());
        // 2:記号(ATKなど)
        data.setKigo(setting[2].trim());
        return data;
    }

    /**
     * CONFIG_MASTERから開始される１行からマスターカテゴリのデータを生成する。
     * 一番初めに読み込む必要がある。
     * @param line ストーリーテキストの１行
     * @return RpgMaster マスタカテゴリ
     * @throws RpgException 想定外のエラー
     */
    public static RpgMaster createMasterData(String line) throws RpgException {
        RpgMaster data = new RpgMaster();
        String[] setting = line.split(":");
        if (setting.length != RpgConst.MASTER_SIZE) {
            throw new RpgException(MessageConst.MASTER_CATEGORY_SEPARATE4.toString() + SEP + line);
        }
        // 0:カテゴリID
        data.setCategoryId(setting[0].trim());
        // 1:カテゴリ名
        data.setName(setting[1].trim());
        // 2:用途説明
        data.setDiscription(setting[2].trim());
        // 3:フィールド名
        data.setFieldName(setting[3].trim());
        data.setValue(0);
        return data;
    }

    /**
     * Job.xmlから読み込むように変更
     * CONFIG_MASTERから開始される１行からマスターカテゴリのデータを生成する。
     * @param line ストーリーテキストの１行
     * @return RpgMaster マスタカテゴリ
     * @throws RpgException 想定外のエラー
     */
    @Deprecated
    public static RpgJob createJobData(String line) throws RpgException {
        RpgJob data = new RpgJob("", "", "", new ArrayList<>());
        String[] setting = line.split(":");
        if (setting.length != RpgConst.JOB_SIZE) {
            throw new RpgException(MessageConst.JOB_SEPARATE3.toString() + SEP + line);
        }
        // 0:ステータス名
        data.setName(setting[0].trim());
        // 1:説明
        data.setDiscription(setting[1].trim());
        // 2:記号(ATKなど)
        data.setKigo(setting[2].trim());
        return data;
    }

    /**
     * ITEM_LISTから開始される１行からマスターカテゴリのデータを生成する。
     *
     * @param line ストーリーテキストの１行
     * @return RpgItem アイテムリスト
     * @throws RpgException 想定外のエラー
     */
    public static RpgItem createRpgItem(String line) throws RpgException {
        RpgItem data = new RpgItem();
        String[] setting = line.split(":");
        if (setting.length != RpgConst.ITE_LIST_SIZE) {
            throw new RpgException(MessageConst.ITEM_SEPARATE5.toString() + SEP + line + " length: " + setting.length);
        }
        // 0:名前
        data.setName(setting[0].trim());
        // 1:種類の記号
        data.setItemType(setting[1].trim());
        // 2:効果記号と値
        data.setItemValueKigo(setting[2].trim());
        // 3. 金額
        data.setMoney(Integer.parseInt(setting[3].trim()));
        // 4. 副作用の数値
        data.setSideEffectValue(setting[4].trim());
        return data;
    }

    /**
     * CONFIG_FORMULAから開始される１行からマスターカテゴリのデータを生成する。
     *
     * @param line ストーリーテキストの１行
     * @return RpgMaster マスタカテゴリ
     * @throws RpgException 想定外のエラー
     */
    public static RpgFormula createRpgFormula(String line) throws RpgException {
        CalcUtils util = CalcUtils.getInstance();
        RpgFormula data = new RpgFormula();
        String[] setting = line.split(":");
        if (setting.length != RpgConst.FORMULA_SIZE) {
            throw new RpgException(MessageConst.FORMULA_SEPARATE3.toString() + SEP + line + " length: " + setting.length);
        }
        String discription = setting[1];
        // 0:ステータス名
        data.setName(setting[0].trim());
        // 1:説明
        data.setDiscription(discription.trim());
        // 2:記号(ATKなど)
        data.setKigo(setting[2].trim());
        // 3. 説明の中から式を取り出す
        int start = discription.indexOf('=') + 1;
        String siki = null;
        try {
            siki = util.sepTankoSiki(discription.substring(start).trim());
            data.setFormulaStr(siki);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RpgException(e.getMessage() + siki + "開始位置: " + start);
        }
        return data;
    }

    /**
     * CONFIG_ST_EFFECTから開始される１行からステータス変化オブジェクトを生成する。
     * @param line ストーリーテキストの１行
     * @return StEffect ステータス変化オブジェクト
     * @throws RpgException 想定外のエラー
     */
    public static Effects createEffects(String line) throws RpgException {
        Map<String, RpgData> paramMap = RpgConfig.getInstance().getParamMap();
        String[] sep = line.split(":");

        if (RpgConst.EFFECT_SIZE != sep.length) {
            throw new RpgException(MessageConst.ERR_EFFECT_TXT_SIZE.toString());
        }
        // 記号の取得
        String kigo = sep[0].trim();
        // 表示文字
        String disp = sep[1].trim();
        // 効果式
        String siki = sep[2].trim();
        // 表示メッセージ
        String message = sep[3].trim();

        if (isDebug) System.out.println("kigo: " + kigo + " disp: " + disp + " siki: " + siki + " mes: " + message);
        Effects eff = new Effects(kigo, disp, siki, message);
        List<Effects> effChilden = createEffectAppear(siki);
        eff.setEffList(effChilden);

        return eff;
    }

    /**
     * 効果式をオブジェクトに変換する。そして設定オブジェクトに各効果式オブジェクトを登録する。
     * @param effectAppear 効果式
     * @return String[] [0]記号 [1]演算子(+-) [2]効果値
     * @throws RpgException 効果式の設定のエラー
     */
    public static List<Effects> createEffectAppear(String effectAppear) throws RpgException {
        // 基本手には定義は１つ
        String[] teigi = new String[1];
        // 服す定義可能定、定義分割
        if (effectAppear.contains(",")) {
            teigi = effectAppear.split(",");
        } else {
            teigi[0] = effectAppear;
        }
        List<Effects> teigiList = new ArrayList<>();
        Map<String, RpgMaster> mstMap = RpgConfig.getInstance().getMasterMap();
        Map<String, RpgStatus> stMap = RpgConfig.getInstance().getStatusMap();
        Map<String, Effects> effMap = RpgConfig.getInstance().getEffectMap();

        // 定義を読み込む
        for (String tei : teigi) {
            // スペース消去
            tei = tei.trim();
            // TSを含む効果式
            if(CheckerUtils.isTS(tei)) {
                // TODO-[TSを含む効果式に対応するEffectsの生成を実装する]
                String[] pam = StringUtils.separateEffectAppear(tei);
                Effects eff = new Effects(pam[0], pam[1], pam[2], pam[3], pam[4]);
                teigiList.add(eff);
                continue;
            }
            // TSを含まない効果式
            if (tei == null || tei.matches(RpgConst.REG_EFFECT_TXT) == false) {
                throw new RpgException(MessageConst.ERR_EFFECT_APPEAR_SIZE.toString() + tei);
            }
            String[] res = new String[RpgConst.EFFECT_TXT_SIZE];
            // 例：ZHP-10%
            String kigo = tei.substring(0, 3);
            res[0] = kigo;
            // 演算子
            String ope = tei.substring(3, 4);
            res[1] = ope;
            // 値
            String val = tei.substring(4);
            res[2] = val;

            // マスタカテゴリまたは、ステータスマップに含まれる場合
            if (mstMap.containsKey(kigo) || stMap.containsKey(kigo)) {
                StEffect obj = new StEffect(kigo, ope, val);
                effMap.put(obj.getKigo(), obj);
                teigiList.add(obj);
            } else {
                // それ以外の場合
                EvEffect obj = new EvEffect(kigo, ope, val);
                effMap.put(obj.getKigo(), obj);
                teigiList.add(obj);
            }
            if(isDebug) System.out.println("kigo: " + kigo + " ope: " + ope + " val: " + val);
        }

        return teigiList;
    }

    public static void createLevelConfig(String line, Map<String, RpgLevel> map) throws RpgException {
        String[] sep = line.split(":");
        if (sep.length != RpgConst.LEVEL_SIZE) {
            throw new RpgException(MessageConst.LEVEL_SEPARATE3.toString() + line + ": " + sep.length);
        }
        // 職業記号
        String jobKigo = sep[0].trim();
        // レベルアップの値
        String[] levels = sep[1].split(",");
        // レベルアップの値
        int[] levelups = new int[levels.length];
        for (int i = 0; i < levels.length; i++) {
            String num = levels[i].trim();
            if (CheckerUtils.isNumber(num, "2")) {
                levelups[i] = Integer.parseInt(num);
            }
        }
        // レベルアップ設定の名前
        String confName = sep[2].trim();
        // オブジェクト生成
        RpgLevel data = new RpgLevel();
        data.setKigo(jobKigo);
        data.setLevelup(levelups);
        data.setConfName(confName);
        // マップに登録
        map.put(jobKigo, data);
        // 設定オブジェクトに登録
        RpgConfig.getInstance().setLevelMap(map);
    }
}
