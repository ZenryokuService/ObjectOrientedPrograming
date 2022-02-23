package jp.zenryoku.rpg.factory;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.categry.RpgMaster;
import jp.zenryoku.rpg.data.items.RpgItem;
import jp.zenryoku.rpg.data.status.RpgFormula;
import jp.zenryoku.rpg.data.status.RpgJob;
import jp.zenryoku.rpg.data.status.RpgStatus;
import jp.zenryoku.rpg.data.status.StEffect;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.util.CalcUtils;

import java.util.Map;

/**
 * RpgDataを継承する各種データオブジェクトを生成する。
 */
public class RpgDataFactory {
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
        // データの設定
        RpgData data = new RpgData();
        data.setName(sep[0].trim());
        data.setKigo(sep[1].trim());
        data.setMaster(sep[2].trim());
        data.setDiscription(sep[3].trim());
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
        data.setDiscription(d.getDiscription());
        // issue#23 アイテム装備時のValueがNullになる問題
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
        // 3:読み方
        data.setHowToRead(setting[3].trim());
        data.setValue(0);
        return data;
    }

    /**
     *
     * CONFIG_MASTERから開始される１行からマスターカテゴリのデータを生成する。
     * @param line ストーリーテキストの１行
     * @return RpgMaster マスタカテゴリ
     * @throws RpgException 想定外のエラー
     */
    public static RpgJob createJobData(String line) throws RpgException {
        RpgJob data = new RpgJob();
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
    public static StEffect createStEffect(String line) throws RpgException {
        Map<String, RpgData> paramMap = RpgConfig.getInstance().getParamMap();
        String[] sep = line.split(":");

        if (RpgConst.EFFECT_SIZE != sep.length) {
            throw new RpgException(MessageConst.ERR_EFFECT_TXT_SIZE.toString());
        }
        StEffect eff = null;
        // 記号の取得
        String kigo = sep[0];
        // 表示文字
        String disp = sep[1];
        // 効果式
        String siki = sep[2];
        // 表示メッセージ
        String message = sep[3];

        System.out.println("kigo: " + kigo + " disp: " + disp + " siki: " + siki + " mes: " + message);


        return eff;
    }
}
