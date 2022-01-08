package jp.zenryoku.rpg.data;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.exception.RpgException;
import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ステータスなどの設定情報を管理する。
 */
@Data
public class ParamGenerator {
    /** インスタンス */
    private static ParamGenerator instance;
    /** ストーリーテキストの設定部分 */
    private Map<String, Integer> textMap;
    /** ステータス情報 */
    private Map<String, RpgData> dataMap;
    /** 職業マップ */
    private List<RpgJob> jobList;
    /** 設定クラス */
    private RpgConfig config;
    /** 各種計算式管理クラス */
    private List<RpgFormula> formulaList;
    /** アイテム(武器なども含む)設定 */
    private List<RpgItem> itemList;

    /** プライベート・コンストラクタ */
    private ParamGenerator() {
        textMap = new HashMap<>();
        dataMap = new HashMap<>();
        config = new RpgConfig();
        jobList = new ArrayList<>();
        formulaList = new ArrayList<>();
        itemList = new ArrayList<>();
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
     * @throws RpgException
     */
    public void createDataMap() throws RpgException {
        if (jobList.size() != 0 && formulaList.size() != 0 && itemList.size() != 0) {
            for (RpgData data : jobList) {
                dataMap.put(data.getName(), data);
            }
            for (RpgData data : formulaList) {
                dataMap.put(data.getName(), data);
            }
            for (RpgData data : itemList) {
                dataMap.put(data.getName(), data);
            }

        } else {
            throw new RpgException("職業リスト、計算式リスト、アイテム設定リストをすべて作成してください。");
        }
    }
    /**
     * ステータス・パラメータの生成を行う。RpgConfigに設定する。
     * @param buf ストーリーテキスト
     * @throws RpgException
     */
    public void createStatusParam(BufferedReader buf) throws RpgException {
        String line = null;
        try {
            // ダイスコードの設定
            setDiceCode(buf.readLine());
            while ((line = buf.readLine()).equals("END_PARAM") == false) {
                RpgData data = new RpgData();
                String[] setting = line.split(":");
                if (setting.length != 3) {
                    throw new RpgException(MessageConst.PLAYER_STATUS_SEPARATE3.toString());
                }
                // 0:ステータス名
                data.setName(setting[0]);
                // 1:説明
                data.setDiscription(setting[1]);
                // 2:記号(ATKなど)
                data.setKigo(setting[2]);
                // データマップに登録
                dataMap.put(data.getName(), data);
            }
            config.setParamList(dataMap);
        } catch (IOException | RpgException e) {
            throw new RpgException(e.getMessage());
        } catch (Exception e) {
            throw new RpgException(MessageConst.UNEXPECTED_ERR.toString() + " " + line);
        }
    }

    /**
     * ジョブ(職業)リストの生成を行う。
     * @param buf ストーリーテキスト
     * @throws RpgException
     */
    public void createJobList(BufferedReader buf) throws RpgException {
        String line = null;
        try {

            while ((line = buf.readLine()).equals("END_JOB") == false) {
                RpgJob data = new RpgJob();
                String[] setting = line.split(":");
                if (setting.length != 3) {
                    throw new RpgException(MessageConst.JOB_SEPARATE3.toString());
                }
                // 0:ステータス名
                data.setName(setting[0]);
                // 1:説明
                data.setDiscription(setting[1]);
                // 2:記号(ATKなど)
                data.setKigo(setting[2]);
                // 職業リストに追加
                jobList.add(data);
            }
            config.setJobList(jobList);
        } catch (IOException | RpgException e) {
            throw new RpgException(e.getMessage());
        } catch (Exception e) {
            throw new RpgException(MessageConst.UNEXPECTED_ERR.toString() + " " + line);
        }
    }

    /**
     * 計算式リストの生成を行う。
     * @param buf ストーリーテキスト
     * @throws RpgException
     */
    public void createFormulaList(BufferedReader buf) throws RpgException {
        String line = null;
        try {

            while ((line = buf.readLine()).equals("END_FORMULA") == false) {
                RpgFormula data = new RpgFormula();
                String[] setting = line.split(":");
                if (setting.length != 3) {
                    throw new RpgException(MessageConst.FORMULA_SEPARATE3.toString() + " length: " + setting.length);
                }
                String form = setting[1];
                // 0:ステータス名
                data.setName(setting[0]);
                // 1:説明
                data.setDiscription(form);
                // 2:記号(ATKなど)
                data.setKigo(setting[2]);
                // 3. 説明の中から式を取り出す
                int start = form.indexOf('=') + 1;
                data.setFormulaStr(form.substring(start).trim());
                // 計算式リストに追加
                formulaList.add(data);
            }
            config.setFormulaList(formulaList);
        } catch (IOException | RpgException e) {
            throw new RpgException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.UNEXPECTED_ERR.toString() + " " + line);
        }
    }

    /**
     * アイテムの設定リストを作成する。
     * @param buf ストーリーテキスト
     * @throws RpgException
     */
    public void createItemList(BufferedReader buf) throws RpgException  {
        String line = null;
        try {

            while ((line = buf.readLine()).equals("END_ITEM") == false) {
                RpgItem data = new RpgItem();
                String[] setting = line.split(":");
                if (setting.length != 3) {
                    throw new RpgException(MessageConst.ITEM_SEPARATE3.toString() + " length: " + setting.length);
                }
                String form = setting[1];
                // 0:ステータス名
                data.setName(setting[0]);
                // 1:説明
                data.setDiscription(form);
                // 2:記号(ATKなど)
                data.setKigo(setting[2]);
                // アイテムリストに追加
                itemList.add(data);
            }
            config.setItemList(itemList);
        } catch (IOException | RpgException e) {
            throw new RpgException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.UNEXPECTED_ERR.toString() + " " + line);
        }
    }
    /**
     * ダイスコードから、以下を取得する
     * 1. 何面ダイスか？
     * 2. 何回ふるか？
     * @param diceCode
     */
    public void setDiceCode(String diceCode) throws Exception {
        if (diceCode.matches("\\<[1-9]D[0-9]{1,2}\\>") == false) {
            throw new RpgException(MessageConst.ERR_DICE_CODE.toString());
        }
        String dice = diceCode.replace("<", "").replace(">", "");
        String[] res = dice.split("D");
        config.setDiceCode(true);
        config.setDiceTimes(Integer.parseInt(res[0]));
        config.setDiceFaces(Integer.parseInt(res[1]));
    }

    /**
     * 文字列の計算式から、計算するためのロジックを生成する。
     * 実行前に、createDataMap()を実行し、データマップを生成しておく必要がある。
     * @param formula 文字列の計算式
     */
    public void createFormula(String formula) throws RpgException {
        if (dataMap.size() == 0) {
            throw new RpgException(MessageConst.ERR_DATAMAP_SIZE0.toString());
        }

    }
    /**
     *
     * @param line
     * @return
     */
    public RpgData createPlayerData(String line) {
        RpgData data = null;

        return data;
    }
}
