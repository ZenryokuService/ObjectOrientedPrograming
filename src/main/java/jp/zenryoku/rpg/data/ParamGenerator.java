package jp.zenryoku.rpg.data;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.categry.RpgMaster;
import jp.zenryoku.rpg.data.charactor.RpgLevel;
import jp.zenryoku.rpg.data.items.EvEffect;
import jp.zenryoku.rpg.data.items.RpgItem;
import jp.zenryoku.rpg.data.items.RpgItemType;
import jp.zenryoku.rpg.data.job.RpgJob;
import jp.zenryoku.rpg.data.shop.RpgShop;
import jp.zenryoku.rpg.data.status.RpgFormula;
import jp.zenryoku.rpg.data.status.RpgStatus;
import jp.zenryoku.rpg.data.status.StEffect;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.factory.RpgDataFactory;
import jp.zenryoku.rpg.util.CalcUtils;
import jp.zenryoku.rpg.util.CheckerUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * ステータスなどの設定情報を管理する。シングルトン実装。
 */
public class ParamGenerator {
    private static final boolean isDebug = false;
    /** 改行コード */
    private static final String SEP = System.lineSeparator();
    /** インスタンス */
    private static ParamGenerator instance;
    /** マスタカテゴリ設定情報 */
    private Map<String, RpgMaster> masterMap;
    /** パラメータ設定情報 */
    private Map<String, RpgData> paramMap;
    /** ステータス設定情報(順番を保持する) */
    private Map<String, RpgStatus> statusMap;
    /** ステータスオプション設定情報(順番を保持する) */
    private Map<String, RpgStatus> optionStatusMap;
    /** 職業マップ */
    private Map<String, RpgJob> jobMap;
    /** 設定クラス */
    private RpgConfig config;
    /** 各種計算式管理クラス */
    private Map<String, RpgFormula> formulaMap;
    /** アイテム(武器なども含む)設定 */
    private Map<String, RpgData> itemMap;
    /** アイテムタイプ、アイテムの設定 */
    private Map<String, RpgData> itemTypeMap;
    /** 全てのパラメータを格納するマップ */
    private Map<String, RpgData> dataMap;
    /** 天のマップ */
    private Map<String, RpgShop> shopMap;
    /** 効果オブジェクトリスト */
    private Map<String, Effects> effectMap;
    /** ステータス変化オブジェクト */
    private Map<String, StEffect> stEffectMap;
    /**イベント変化オブジェクト */
    private Map<String, EvEffect> evEffectMap;
    /** レベルマップ */
    private Map<String, RpgLevel> levelMap;
    /** シーンオブジェクトの数 */
    private int sceneSize;

    /** プライベート・コンストラクタ */
    private ParamGenerator() {
        config = RpgConfig.getInstance();
        config.setMasterMap(new HashMap<>());
        config.setParamMap(new HashMap<>());
        config.setStatusMap(new LinkedHashMap<>());
        config.setOptionStatusMap(new LinkedHashMap<>());
        config.setFormulaMap(new HashMap<>());
        config.setItemMap(new HashMap<>());
        config.setItemTypeMap(new HashMap<>());
        //config.setJobMap(new HashMap<>());
        config.setDataMap(new HashMap<>());
        config.setEffectMap(new HashMap<>());
        config.setShopMap(new HashMap<>());
        config.setEffectMap(new HashMap<>());
        config.setStEffectMap(new HashMap<>());
        config.setEvEffectMap(new HashMap<>());
        config.setLevelMap(new HashMap<>());
        // このクラス内で使用するための変数
        masterMap = config.getMasterMap();
        paramMap = config.getParamMap();
       // jobMap = config.getJobMap();
        formulaMap = config.getFormulaMap();
        itemMap = config.getItemMap();
        itemTypeMap = config.getItemTypeMap();
        statusMap = config.getStatusMap();
        optionStatusMap = config.getOptionStatusMap();
        dataMap = config.getDataMap();
        // TODO-[オブジェクトが重複しているのでのちに見直し]
        effectMap = config.getEffectMap();
        stEffectMap = config.getStEffectMap();
        evEffectMap = config.getEvEffectMap();
        shopMap = config.getShopMap();
        levelMap = config.getLevelMap();

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
     * @throws RpgException 設定のエラー
     */
    @Deprecated
    public void createDataMap() throws RpgException {
        if (/* jobMap.size() != 0 && */ formulaMap.size() != 0 && itemMap.size() != 0) {
//            Set<String> jobKey = jobMap.keySet();
//            for (String key : jobKey) {
//                dataMap.put(key, jobMap.get(key));
//            }
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
     * CONFIG_MASTERから始まる設定を読み込む。
     *
     * @param buf ストーリーテキスト
     * @throws RpgException 設定のエラー
     */
    public void createMasterCategory(BufferedReader buf) throws RpgException {
        String line = null;
        try {
            while ((line = buf.readLine()).equals("END_MASTER") == false) {
                if (CheckerUtils.isComment(line)) {
                    continue;
                }
                // RpgMasterの生成
                RpgMaster data = RpgDataFactory.createMasterData(line);
                // データマップに登録
                masterMap.put(data.getCategoryId(), data);
            }
            config.setMasterMap(masterMap);
        } catch (IOException | RpgException e) {
            e.printStackTrace();
            throw new RpgException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.UNEXPECTED_ERR.toString() + " " + line);
        }
    }

    /**
     * ステータスの生成を行う。CONFIG_STATUS以下の設定を、RpgConfigに設定する。
     * @param buf ストーリーテキスト
     * @throws RpgException 設定のエラー
     */
    public void createStatus(BufferedReader buf) throws RpgException {
        String line = null;
        try {
            while ((line = buf.readLine()).equals("END_STATUS") == false) {
                if (CheckerUtils.isComment(line)) {
                    continue;
                }
                // ステータスデータを生成する。
                RpgStatus data = RpgDataFactory.createRpgStatus(line);
                // データマップに登録
                statusMap.put(data.getKigo().trim(), data);
            }
            //System.out.println("*** Testing ***");
            config.setStatusMap(statusMap);
            //System.out.println(config.getStatusMap());
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
     * @throws RpgException 設定のエラー
     */
    public void createParam(BufferedReader buf) throws RpgException {
        String line = null;
        try {
            // ダイスコードの設定(１行目)
            setDiceCode(buf.readLine());
            // 表示行数の指定(2行目)
            String sepLine = buf.readLine();
            setPrintLine(sepLine.startsWith("#") ? "" : sepLine);
            createConfigParams(buf);
        } catch (IOException | RpgException e) {
            e.printStackTrace();
            throw new RpgException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.UNEXPECTED_ERR.toString() + " " + line);
        }
    }
    /**
     * ジョブ(職業)リストの生成を行う。CONFIG_JOB以下の設定を、RpgConfigに設定する。
     * @param buf ストーリーテキスト
     * @throws RpgException 設定のエラー
     */
    @Deprecated
    public void createJobMap(BufferedReader buf) throws RpgException {
        String line = null;
//        try {
//            Map<String, RpgJob> jobMap = config.getJobMap();
//            while ((line = buf.readLine()).equals("END_JOB") == false) {
//                if (CheckerUtils.isComment(line)) {
//                    continue;
//                }
//                // 職業データの生成
//                RpgJob data = RpgDataFactory.createJobData(line);
//                // 職業リストに追加
//                jobMap.put(data.getName(), data);
//            }
//            config.setJobMap(jobMap);
//        } catch (IOException | RpgException e) {
//            throw new RpgException(e.getMessage());
//        } catch (Exception e) {
//            throw new RpgException(MessageConst.UNEXPECTED_ERR.toString() + " " + line);
//        }
    }

    /**
     * 計算式リストの生成を行う。CONFIG_FORMULA以下の設定を、RpgConfigに設定する。
     * @param buf ストーリーテキスト
     * @throws RpgException 設定のエラー
     */
    public void createFormulaMap(BufferedReader buf) throws RpgException {
        String line = null;
        try {
            CalcUtils util = CalcUtils.getInstance();
            while ((line = buf.readLine()).equals("END_FORMULA") == false) {
                if (CheckerUtils.isComment(line)) {
                    continue;
                }
                RpgFormula data = RpgDataFactory.createRpgFormula(line);
                // MEMO-[関連ステータスオブジェクト]
                util.relatedSymbols(data.getFormulaStr(), statusMap, optionStatusMap);
                // 計算式リストに追加
                if (isDebug) System.out.println("FormulaKigo: " + data.getKigo());
                formulaMap.put(data.getKigo().trim(), data);
            }
            config.setFormulaMap(formulaMap);
        } catch (IOException | RpgException e) {
            e.printStackTrace();
            throw new RpgException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.UNEXPECTED_ERR.toString() + " " + line);
        }
    }

    /**
     * アイテムの設定リストを作成する。ITEM_LIST以降の行の設定を、RpgConfigに設定する。
     * @param buf ストーリーテキスト
     * @throws RpgException 設定のエラー
     */
    public void createItemMap(BufferedReader buf) throws RpgException  {
        String line = null;
        RpgConfig conf = RpgConfig.getInstance();
        try {
            while ((line = buf.readLine()).equals("END_ITEM_LIST") == false) {
                if (CheckerUtils.isComment(line)) {
                    continue;
                }
                // RpgItemを生成する。
                RpgItem data = RpgDataFactory.createRpgItem(line);
                // アイテムリストに追加
                itemMap.put(data.getName(), data);
            }
            config.setItemMap(itemMap);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RpgException(e.getMessage() + " : " + line);
        } catch (IOException | RpgException e) {
            e.printStackTrace();
            throw new RpgException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.UNEXPECTED_ERR.toString() + " " + line);
        }
    }

    /**
     * 効果オブジェクトマップを作成する。CONFIG_ST_EFFECT以降の行の設定を、RpgConfigに設定する。
     * @param buf ストーリーテキスト
     * @throws RpgException 設定のエラー
     */
    public void createEffects(BufferedReader buf) throws RpgException  {
        String line = null;
        RpgConfig conf = RpgConfig.getInstance();
        try {
            while ((line = buf.readLine()).equals("END_EFFECT") == false) {
                if (CheckerUtils.isComment(line)) {
                    continue;
                }
                // Effectsを生成する。
                Effects data = RpgDataFactory.createEffects(line);
                // 効果式リストに追加
                effectMap.put(data.getKigo(), data);
            }
            config.setEffectMap(effectMap);
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
     * 「CONFIG_ITEM」はストーリーテキストで定義していないので注意。
     * @param buf CONFIG_ITEM内の各行
     * @throws RpgException 設定のエラー
     */
    public void createItemTypeMap(BufferedReader buf) throws RpgException{
        String line = null;
        try {
            while ((line = buf.readLine()).equals("END_ITEM") == false) {
                if (CheckerUtils.isComment(line)) {
                    continue;
                }
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
        while((line = buf.readLine()).equals("END_PARAM") == false) {
            if (CheckerUtils.isComment(line)) {
                continue;
            }
            // RpgDataを生成する。ここから子クラスを生成するものとそうでないものがある。。
            RpgData data = RpgDataFactory.createRpgDataFromConfig(line, paramMap);
            if (isDebug) System.out.println(data);
            // オプショナルステータスの設定
            if (RpgConst.PLY.equals(data.getMaster())) {
                optionStatusMap.put(data.getKigo(), RpgDataFactory.createOptionalRpgStatus(data));
            }
            paramMap.put(data.getKigo(), data);
            // マスタカテゴリ設定とのリンク
            String mstKey = data.getMaster();
            if (masterMap.containsKey(mstKey)) {
                RpgMaster mst = masterMap.get(mstKey);
                // パラメータ定義のキーリストに追加
                mst.getChildList().add(data.getKigo());
            }
        }
    }

    /**
     *
     * @param buf 設定ファイル
     * @throws IOException ファイルの読み込みエラー
     * @throws RpgException 想定外のエラー
     */
    public void createLevelMap(BufferedReader buf) throws IOException, RpgException {
        String line = null;
        while((line = buf.readLine()).equals("END_LEVEL") == false) {
            if (CheckerUtils.isComment(line)) {
                continue;
            }
            // RpgLeveを生成。マップに登録。
            RpgDataFactory.createLevelConfig(line, levelMap);
        }
    }

    /**
     * ダイスコードから、以下を取得する
     * 1. 何面ダイスか？
     * 2. 何回ふるか？
     * @param diceCode ダイスコード、振るダイス(さいころ)の形を定義する。
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
     * 実行前に、createParamMap()を実行し、設定オブジェクトを生成しておく必要がある。
     * @param key 計算の結果算出される値のキー　例: ATK, DEF
     * @param formula 文字列の計算式　
     *                例: 物理攻撃力 => (ちから + 武器攻撃力) * (1 + (0.1 * じゅくれんど))
     *                         ATK = (POW + WEV) + (1 + (0.1 + JLV)
     */
    @Deprecated
    public void createFormula(String key, String formula, RpgFormula f) throws RpgException {
        List<String> formulas = new ArrayList<>();
        int first = formula.indexOf("(");
        int second = formula.indexOf(")");
        if (first < 0  | second < 0) {

        }
        String kou = formula.substring(first + 1, second);
        //formulas.add();

        char[] ch = formula.toCharArray();
        for (int i = 0; i < ch.length; i++) {

        }
    }

    /**
     * ストーリーテキストから表示する行数を指定する
     * \<printLine: 数字\>のように指定する。CONFIG_PARAMから2行目に書く必要がある。
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
     * @return RPGデータのマップ
     */
    public Map<String, RpgData> getAllMap() {
        Map<String, RpgData> res = new HashMap<>();
        Map<String, RpgData> itemType = config.getItemTypeMap();
        Map<String, RpgData> item = config.getItemMap();
        Map<String, RpgData> param = config.getParamMap();
        Map<String, RpgStatus> status = config.getStatusMap();
        //Map<String, RpgJob> job = config.getJobMap();
        res.putAll(itemType);
        res.putAll(item);
        res.putAll(param);
        res.putAll(status);
        //res.putAll(job);
        return res;
    }

    /**
     * RpgConfigを取得する。
     * @return
     */
    public RpgConfig getConfig() {
        return config;
    }

    /**
     * 未使用
     * @param config　設定オブジェクト
     */
    @Deprecated
    public void setConfig(RpgConfig config) {
        this.config = config;
    }

    public void setSceneSize(int size) {
        this.sceneSize = size;
    }

    public int getSceneSize() {
        return sceneSize;
    }
}
