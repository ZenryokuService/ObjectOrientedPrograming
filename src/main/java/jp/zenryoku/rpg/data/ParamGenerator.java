package jp.zenryoku.rpg.data;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.util.CalcUtils;
import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * ステータスなどの設定情報を管理する。シングルトン実装。
 */
public class ParamGenerator {
    /** 改行コード */
    private static final String SEP = System.lineSeparator();
    /** インスタンス */
    private static ParamGenerator instance;
    /** パラメータ設定情報 */
    private Map<String, RpgData> paramMap;
    /** ステータス設定情報(順番を保持する) */
    private Map<String, RpgData> statusMap;
    /** 職業マップ */
    private Map<String, RpgData> jobMap;
    /** 設定クラス */
    private RpgConfig config;
    /** 各種計算式管理クラス */
    private Map<String, RpgData> formulaMap;
    /** アイテム(武器なども含む)設定 */
    private Map<String, RpgData> itemMap;
    /** アイテムタイプ、アイテムの設定 */
    private Map<String, RpgData> itemTypeMap;
    /** 全てのパラメータを格納するマップ */
    private Map<String, RpgData> dataMap;

    /** プライベート・コンストラクタ */
    private ParamGenerator() {
        config = RpgConfig.getInstance();
        config.setParamMap(new HashMap<>());
        config.setStatusMap(new LinkedHashMap<>());
        config.setFormulaMap(new HashMap<>());
        config.setItemMap(new HashMap<>());
        config.setItemTypeMap(new HashMap<>());
        config.setJobMap(new HashMap<>());
        config.setDataMap(new HashMap<>());
        // このクラス内で使用するための変数
        paramMap = config.getParamMap();
        jobMap = config.getJobMap();
        formulaMap = config.getFormulaMap();
        itemMap = config.getItemMap();
        itemTypeMap = config.getItemTypeMap();
        statusMap = config.getStatusMap();
        dataMap = config.getDataMap();
    }

    /**
     * インスタンスを取得する。
     * @return インスタンス
     */
    public static ParamGenerator getInstance() {
        if (instance == null) {
            instance = new ParamGenerator();
        }
        return instance;
    }

    /**
     * java.util.MapにKey=名前, Value=RpgDataとして設定情報を登録する。
     * ストーリーテキストからそのほかのデータオブジェクトを生成する。
     * @throws RpgException
     */
    @Deprecated
    public void createDataMap() throws RpgException {
        if (jobMap.size() != 0 && formulaMap.size() != 0 && itemMap.size() != 0) {
            Set<String> jobKey = jobMap.keySet();
            for (String key : jobKey) {
                dataMap.put(key, jobMap.get(key));
            }
            Set<String> formulaKey = formulaMap.keySet();
            for (String key : formulaKey) {
                dataMap.put(key, formulaMap.get(key));
            }
            Set<String> itemKey = itemMap.keySet();
            for (String key : itemKey) {
                dataMap.put(key, itemMap.get(key));
            }
        } else {
            throw new RpgException("職業リスト、計算式リスト、アイテム設定リストをすべて作成してください。");
        }
    }
    /**
     * ステータスの生成を行う。CONFIG_STATUS以下の設定を、RpgConfigに設定する。
     * @param buf ストーリーテキスト
     * @throws RpgException
     */
    public void createStatus(BufferedReader buf) throws RpgException {
        String line = null;
        try {
            while ((line = buf.readLine()).equals("END_STATUS") == false) {
                RpgStatus data = new RpgStatus();
                String[] setting = line.split(":");
                if (setting.length != 3) {
                    throw new RpgException(MessageConst.PLAYER_STATUS_SEPARATE3.toString() + SEP + line);
                }
                // 0:ステータス名
                data.setName(setting[0].trim());
                // 1:説明
                data.setDiscription(setting[1].trim());
                // 2:記号(ATKなど)
                data.setKigo(setting[2].trim());
                // データマップに登録
                statusMap.put(data.getName().trim(), data);
            }
            config.setStatusMap(statusMap);
        } catch (IOException | RpgException e) {
            e.printStackTrace();
            throw new RpgException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.UNEXPECTED_ERR.toString() + " " + line);
        }
    }
    /**
     * パラメータの生成を行う。CONFIG_PARAM以下の設定を、RpgConfigに設定する。
     * @param buf ストーリーテキスト
     * @throws RpgException
     */
    public void createParam(BufferedReader buf) throws RpgException {
        String line = null;
        try {
            // ダイスコードの設定(１行目)
            setDiceCode(buf.readLine());
            // 表示行数の指定(2行目)
            String sepLine = buf.readLine();
            setPrintLine(sepLine.startsWith("#") ? "" : sepLine);
            this.createConfigParams(buf);
        } catch (IOException | RpgException e) {
            throw new RpgException(e.getMessage());
        } catch (Exception e) {
            throw new RpgException(MessageConst.UNEXPECTED_ERR.toString() + " " + line);
        }
    }
    /**
     * ジョブ(職業)リストの生成を行う。CONFIG_JOB以下の設定を、RpgConfigに設定する。
     * @param buf ストーリーテキスト
     * @throws RpgException
     */
    public void createJobMap(BufferedReader buf) throws RpgException {
        String line = null;
        try {
            Map<String, RpgData> jobMap = config.getJobMap();
            while ((line = buf.readLine()).equals("END_JOB") == false) {
                RpgJob data = new RpgJob();
                String[] setting = line.split(":");
                if (setting.length != 3) {
                    throw new RpgException(MessageConst.JOB_SEPARATE3.toString() + SEP + line);
                }
                // 0:ステータス名
                data.setName(setting[0].trim());
                // 1:説明
                data.setDiscription(setting[1].trim());
                // 2:記号(ATKなど)
                data.setKigo(setting[2].trim());
                // 職業リストに追加
                jobMap.put(setting[0].trim(), data);
            }
            config.setJobMap(jobMap);
        } catch (IOException | RpgException e) {
            throw new RpgException(e.getMessage());
        } catch (Exception e) {
            throw new RpgException(MessageConst.UNEXPECTED_ERR.toString() + " " + line);
        }
    }

    /**
     * 計算式リストの生成を行う。CONFIG_FORMULA以下の設定を、RpgConfigに設定する。
     * @param buf ストーリーテキスト
     * @throws RpgException
     */
    public void createFormulaMap(BufferedReader buf) throws RpgException {
        String line = null;
        try {
            CalcUtils util = CalcUtils.getInstance();
            while ((line = buf.readLine()).equals("END_FORMULA") == false) {
                RpgFormula data = new RpgFormula();
                String[] setting = line.split(":");
                if (setting.length != 3) {
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
                data.setFormulaStr(util.sepTankoSiki(discription.substring(start).trim()));
                // 計算式リストに追加
                formulaMap.put(data.getName().trim(), data);
            }
            config.setFormulaMap(formulaMap);
        } catch (IOException | RpgException e) {
            throw new RpgException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.UNEXPECTED_ERR.toString() + " " + line);
        }
    }

    /**
     * アイテムの設定リストを作成する。ITEM_LIST以降の行の設定を、RpgConfigに設定する。
     * @param buf ストーリーテキスト
     * @throws RpgException
     */
    public void createItemMap(BufferedReader buf) throws RpgException  {
        String line = null;
        RpgConfig conf = RpgConfig.getInstance();
        try {
            while ((line = buf.readLine()).equals("END_ITEM_LIST") == false) {
                RpgItem data = new RpgItem();
                String[] setting = line.split(":");
                if (setting.length != 5) {
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
                // アイテムリストに追加
                itemMap.put(setting[0].trim(), data);
            }
            config.setItemMap(itemMap);
        } catch (NumberFormatException e) {
            throw new RpgException(e.getMessage() + " : " + line);
        } catch (IOException | RpgException e) {
            throw new RpgException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.UNEXPECTED_ERR.toString() + " " + line);
        }
    }

    /**
     * ストーリーテキストの「CONFIG_ITEM」の部分を読み込み、RpgConfigに設定する。
     * @param buf CONFIG_ITEM内の各行
     * @throws RpgException
     */
    public void createItemTypeMap(BufferedReader buf) throws RpgException{
        String line = null;
        try {
            while ((line = buf.readLine()).equals("END_ITEM") == false) {
                String[] data = line.split(":");

                if (data.length != 3) {
                    throw new RpgException(MessageConst.ITEM_SEPARATE5.toString() + SEP + line +  " length: " + data.length);
                }
                String discription = data[1].trim();
                String[] value = data[2].trim().split(" ");

                Map<String, RpgData> itemTypeMap = config.getItemTypeMap();
                for (String type : value) {
                    RpgItemType itemType = new RpgItemType();
                    String[] hako = sepNameAndKigo(type);
                    itemType.setName(hako[0].trim());
                    itemType.setKigo(hako[1].trim());
                    itemType.setDiscription(discription);
                    itemTypeMap.put(hako[0].trim(), itemType);
                }
                config.setItemTypeMap(itemTypeMap);
            }
        } catch (IOException e) {
            new RpgException(MessageConst.ERR_IOEXCEPTION.toString());
        }
    }

    /**
     * ストーリーテキストのアイテムタイプの「名前(記号)」を読み込み、配列にして返却する。
     * @param third　「名前(記号)」が可変長で設定されている(ストーリーテキスト)
     * @return　[0]=名前 [1]=記号
     */
    private String[] sepNameAndKigo(String third) {
        String[] types = third.split(" ");
        String[] hako = new String[types.length * 2];

        for (int i = 0; i < types.length; i++) {

            String type = types[i];
            String conv0 = type.replaceAll("[\\(]", " ");
            String conv1 = conv0.replaceAll("[\\)]", "");
            String conv2 = conv1.substring(0,conv1.length());
            String[] tmp = conv2.split(" ");
            hako[i * 2] = tmp[0];
            hako[(i * 2) + 1] = tmp[1];
        }

        return hako;
    }

    /**
     * カテゴリ設定処理　CONFIG_PARAM
     * パラメータMap(Key=記号, Value=RpgData)
     */
    public void createConfigParams(BufferedReader buf) throws IOException, RpgException {
        String line = null;
        while(!(line = buf.readLine()).equals("END_PARAM")) {
            RpgData data = createRpgDataFromConfig(line, paramMap);
            paramMap.put(data.getKigo(), data);
        }
    }

    /**
     * CONFIG_PARAMの1行からRGPデータを作成する。
     * @param line ストーリーテキストの１行
     * @return RpgData
     */
    private RpgData createRpgDataFromConfig(String line, Map<String, RpgData> map) throws RpgException {
        String[] sep = line.split(":");

        if (sep.length != 4) {
            throw new RpgException(MessageConst.ERR_CONFIG_PARAM.toString() + line);
        }
        // データの設定
        RpgData data = new RpgData();
        data.setName(sep[0]);
        data.setKigo(sep[1]);
        data.setDiscription(sep[2]);
        // 親の設定
        String parent = sep[3];
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
     * ダイスコードから、以下を取得する
     * 1. 何面ダイスか？
     * 2. 何回ふるか？
     * @param diceCode
     */
    public void setDiceCode(String diceCode) throws Exception {
        if (diceCode.matches("\\<[1-9]D[0-9]{1,2}\\>") == false) {
            throw new RpgException(MessageConst.ERR_DICE_CODE.toString() + diceCode);
        }
        String dice = diceCode.replace("<", "").replace(">", "");
        String[] res = dice.split("D");
        config.setDiceCode(true);
        config.setDiceTimes(Integer.parseInt(res[0].trim()));
        config.setDiceFaces(Integer.parseInt(res[1].trim()));
        config.setDiceFaces(Integer.parseInt(res[1].trim()));
    }

    /**
     * 文字列の計算式から、計算するためのロジックを生成する。
     * 実行前に、createItemTypeMap()を実行し、アイテム設定を生成しておく必要がある。
     * @param formula 文字列の計算式
     */
    public void createFormula(String formula) throws RpgException {
        if (config.getItemTypeMap().size() == 0) {
            throw new RpgException(MessageConst.ERR_ITEMTYPEMAP_SIZE0.toString() + ": " + formula);
        }

    }
    /**
     *
     * @param buf
     * @return
     */
    public RpgData createPlayerData(BufferedReader buf) {
        RpgData data = null;

        return data;
    }

    /**
     * ストーリーテキストから表示する行数を指定する
     * <printLine: 数字>のように指定する。CONFIG_PARAMから2行目に書く必要がある。
     */
    private void setPrintLine(String line) throws Exception {
        if ("".equals(line)) {
            return;
        }
        if (line.matches("\\<printLine: [0-9]{1,2}\\>") == false) {
            throw new RpgException(MessageConst.ERR_PRINT_LINE.toString() + line);
        }
        // 「<」「>」を削除する
        String plane = line.replace("<", "").replace(">", "");
        String[] setting = plane.split(":");
        int printLine = Integer.parseInt(setting[1].trim());
        config.setPrintLine(printLine);
    }

    /**
     * 各種マップのを合成して返却する
     */
    public Map<String, RpgData> getAllMap() {
        Map<String, RpgData> res = new HashMap<>();
        Map<String, RpgData> itemType = config.getItemTypeMap();
        Map<String, RpgData> item = config.getItemMap();
        Map<String, RpgData> param = config.getParamMap();
        Map<String, RpgData> status = config.getStatusMap();
        Map<String, RpgData> job = config.getJobMap();
        res.putAll(itemType);
        res.putAll(item);
        res.putAll(param);
        res.putAll(status);
        res.putAll(job);
        return res;
    }
    public RpgConfig getConfig() {
        return config;
    }

    public void setConfig(RpgConfig config) {
        this.config = config;
    }
}
