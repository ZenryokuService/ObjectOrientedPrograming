package jp.zenryoku.rpg.scene;

import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.constants.SelectConst;
import jp.zenryoku.rpg.data.*;
import jp.zenryoku.rpg.data.categry.RpgMaster;
import jp.zenryoku.rpg.data.job.RpgCommand;
import jp.zenryoku.rpg.data.job.RpgJob;
import jp.zenryoku.rpg.data.status.RpgFormula;
import jp.zenryoku.rpg.data.status.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.util.CalcUtils;
import jp.zenryoku.rpg.util.ConsoleUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CreatePlayerScene extends StoryScene {

    /**
     * コンストラクタ
     *
     * @param sceneIdx  シーンインデックス
     * @param sceneType シーンタイプ
     */
    public CreatePlayerScene(String sceneIdx, String sceneType) {
        super(sceneIdx, sceneType);

    }

    /**
     * ダイスコードから何面ダイスで何回振るか表示する。
     */
    private void printDiceInfo() {
        RpgConfig config = RpgConfig.getInstance();
        System.out.println(config.getDiceFaces() + "面ダイスを" + config.getDiceTimes() + "回振ります。");
    }
    /**
     *  プレーヤー作成用のシーン。
     *
     * @return 処理継続のフラグ: false
     * @throws Exception
     */
    @Override
    public boolean playScene() throws Exception {
        RpgConfig config = RpgConfig.getInstance();
        if (config.isDiceCode() == false) {
            throw new RpgException("ダイスコードを設定してください。");
        }
        ConsoleUtils console = ConsoleUtils.getInstance();
        // テキスト表示
        super.printStory();
        System.out.println(MessageConst.CREATE_PLAYER.toString());
        String name = console.acceptInput(MessageConst.INPUT_NAME.toString());
        PlayerCharactor player = new PlayerCharactor(name);
        String birthDay = console.acceptInput(MessageConst.INPUT_BIRTH_DAY.toString());
        player.setBirthDay(birthDay);

        PlayerParty party = PlayerParty.getInstance();
        party.setPlayer(player);

        System.out.println(MessageConst.INPUT_STATUS);

        CalcUtils calc = CalcUtils.getInstance();
        Map<String, RpgStatus> statusMap = player.getStatusMap();
        // ステータスマップ取得
        if (isDebug) System.out.println(config.getStatusMap());

        printDiceInfo();
        Set<String> keySet = statusMap.keySet();
        while (true) {
            int hp = 0;
            int mp = 0;
            for (String st : keySet) {
                RpgStatus val = statusMap.get(st);
                int res = calc.throwDice(config.getDiceTimes(), config.getDiceFaces()
                        , val.getName() + MessageConst.CREATE_STATUS, val.getName());
                val.setValue(res);
                hp += res;
                if ("POW".equals(st) == false && "AGI".equals(st) == false) {
                    mp += res;
                }
            }
            player.setMaxHP(hp);
            player.setHP(hp);
            player.setMaxMP(mp);
            player.setMP(mp);
            if (isDebug) System.out.println(player.getStatusMap());
            // ステータス表示
            console.printStatus(player);
            // オプショナルステータス
            Map<String, RpgStatus> optMap = player.getOptionalMap();
            Map<String, RpgData> data = config.getParamMap();
            Set<String> keys = data.keySet();
            for (String key : keys) {
                RpgData d = data.get(key);
                if (RpgConst.PLY.equals(d.getMaster())) {
                    optMap.put(key, createRpgStatus(d));
                }
            }
            // 計算式の設定
            CalcUtils calUtils = CalcUtils.getInstance();
            Map<String, RpgFormula> fMap = config.getFormulaMap();
            RpgFormula atkForm = fMap.get(RpgConst.ATK);
            RpgFormula defForm = fMap.get(RpgConst.DEF);
            //calUtils.relatedSymbols(atkForm.getFormulaStr(), statusMap, optMap);
            // 職業リストと職業選択
            createJob(console, player);
            // ステータス確定確認
            String select = console.acceptInput(MessageConst.CHECK_STATUS.toString()
                    + SelectConst.SELECT_YES.getSelectMessage() + " " + SelectConst.SELECT_NO.getSelectMessage()
                    ,SelectConst.YES_NO_REGREX);
            if (SelectConst.SELECT_YES.getValue().equals(select)) {
                party.setPlayer(player);
                break;
            }
        }
        config.setParty(party);
        return false;
    }

    /**
     * オプショナルステータスに追加するRpgStatusを生成する。
     * @param d 取得したPARAM_CONFIGの１行
     * @return RpgStatus
     */
    private RpgStatus createRpgStatus(RpgData d) {
        RpgStatus data = new RpgStatus();
        data.setName(d.getName());
        data.setKigo(d.getKigo());
        data.setDiscription(d.getDiscription());
        // issue#23 アイテム装備時のValueがNullになる問題
        data.setValue(d.getValue());

        return data;
    }

    private RpgStatus createRpgStatus(RpgMaster master) {
        RpgStatus data = new RpgStatus();
        data.setName(master.getName());
        data.setKigo(master.getKigo());
        data.setDiscription(master.getDiscription());

        return data;
    }

    private RpgStatus createOptionalRpgStatus() {
        RpgStatus st = new RpgStatus();
        return st;
    }

    /**
     * RpgJobのMAP生成を行う、Command.xml, STM.xmlのロードをしておく必要がある。
     * @return Map<String, RpgJob> JOBマップ
     */
    private Map<String, RpgJob> printJobs() {
        Map<String, RpgJob> resMap = new HashMap<>();
        Map<String, RpgJob> jobMap = RpgConfig.getInstance().getJobMap();
        resMap.putAll(jobMap);
        if (isDebug) System.out.println("jobMap: " + resMap.size());
        Set<String> set = resMap.keySet();
        int count = 1;
        for (String key : set) {
            RpgJob job = resMap.get(key);
            System.out.println(count + ". " + job.getName() + " : " + job.getDiscription());
            resMap.put(String.valueOf(count), job);
            count++;
        }
        return resMap;
    }

    private void createJob(ConsoleUtils console, PlayerCharactor player) {
        Map<String, RpgJob> jobMap = printJobs();
        int jobCount = jobMap.size();
        String jobSelect = console.acceptInput(SelectConst.JOB_SELECT.toString(), "[1-" + jobCount + "]");
        RpgJob job = jobMap.get(jobSelect);
        // Commandに依存するSTMをセットする
        job.setCommandList(job.getCommandList());
        player.setJob(job);

    }
}
