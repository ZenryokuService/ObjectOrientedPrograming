package jp.zenryoku.rpg;

import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.charactors.monsters.Monster;
import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.data.*;
import jp.zenryoku.rpg.data.categry.RpgMaster;
import jp.zenryoku.rpg.data.items.RpgItem;
import jp.zenryoku.rpg.data.job.RpgCommand;
import jp.zenryoku.rpg.data.job.RpgJob;
import jp.zenryoku.rpg.data.job.RpgMonsterType;
import jp.zenryoku.rpg.data.status.RpgFormula;
import jp.zenryoku.rpg.data.status.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.exception.StoryTextException;
import jp.zenryoku.rpg.item.equip.Armor;
import jp.zenryoku.rpg.item.equip.MainWepon;
import jp.zenryoku.rpg.scene.CreatePlayerScene;
import jp.zenryoku.rpg.util.CheckerUtils;
import jp.zenryoku.rpg.util.XmlUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestUtils {
    private static final boolean isDebug = false;
    /** 改行コード */
    private static final String SEP = System.lineSeparator();

    public static PlayerCharactor initRpgConfig() {
        ParamGenerator param = ParamGenerator.getInstance();
        Map<String, RpgMaster> masterMap = new HashMap<>();
        // パラメータマップ
        Map<String, RpgData> map = new HashMap<>();
        // ステータスマップ
        List<RpgData> list = new ArrayList<>();
        // オプショナルステータスマップ
        List<RpgData> optList = new ArrayList<>();

        RpgMaster mny = new RpgMaster();
        List<String> lis = new ArrayList<>();
        lis.add("NIG");
        mny.setKigo("MNY");
        mny.setFieldName("money");
        mny.setChildList(lis);
        // NIG通貨
        RpgData nig = new RpgData();
        nig.setPriority(0);
        nig.setMaster("MNY");
        nig.setKigo("NIG");
        map.put(nig.getKigo(), nig);
        masterMap.put(mny.getKigo(), mny);


        // HP
        RpgMaster zhp = new RpgMaster();
        zhp.setKigo("ZHP");
        zhp.setValue(10);
        zhp.setFieldName("HP");
        //list.add(zhp);
        masterMap.put(zhp.getKigo(), zhp);
        // MP
        RpgMaster zmp = new RpgMaster();
        zmp.setKigo("ZMP");
        zmp.setValue(11);
        zmp.setFieldName("MP");
        //list.add(zmp);
        masterMap.put(zmp.getKigo(), zmp);
        // ちから
        RpgData pow = new RpgData();
        pow.setKigo("POW");
        pow.setValue(1);
        list.add(pow);
        //map.put(pow.getKigo(), pow);

        // 武器攻撃力
        RpgData wev = new RpgData();
        wev.setKigo("WEV");
        wev.setValue(2);
        optList.add(wev);
        map.put(wev.getKigo(), wev);

        // 防具防御撃力
        RpgData arv = new RpgData();
        arv.setKigo("ARV");
        arv.setValue(3);
        optList.add(arv);
        map.put(arv.getKigo(), arv);

        // すばやさ
        RpgData agi = new RpgData();
        agi.setKigo("AGI");
        agi.setValue(4);
        list.add(agi);
        //map.put(agi.getKigo(), agi);

        // 攻撃力
        RpgData atk = new RpgData();
        atk.setKigo("ATK");
        atk.setValue(5);
        optList.add(atk);
        map.put(atk.getKigo(), atk);

        // 防御力
        RpgData def = new RpgData();
        def.setKigo("DEF");
        def.setValue(6);
        optList.add(def);
        map.put(def.getKigo(), def);

        RpgData itm = new RpgData();
        itm.setKigo("ITM");
        itm.setValue(7);
        optList.add(itm);
        map.put(itm.getKigo(), itm);

        RpgData ksm = new RpgData();
        ksm.setKigo("KSM");
        ksm.setValue(8);
        list.add(ksm);
        //map.put(ksm.getKigo(), ksm);

        RpgData dex = new RpgData();
        dex.setKigo("DEX");
        dex.setValue(9);
        list.add(dex);
        map.put(dex.getKigo(), dex);

        RpgData bpk = new RpgData();
        bpk.setKigo("BPK");
        bpk.setValue(10);
        optList.add(bpk);
        map.put(bpk.getKigo(), bpk);

        RpgData inta = new RpgData();
        inta.setKigo("INT");
        inta.setValue(11);
        list.add(inta);
        //map.put(inta.getKigo(), inta);

        RpgData jvl = new RpgData();
        jvl.setKigo("JLV");
        jvl.setValue(12);
        optList.add(jvl);
        map.put(jvl.getKigo(), jvl);

        RpgData itv = new RpgData();
        itv.setKigo("ITV");
        itv.setValue(13);
        optList.add(itv);
        map.put(itv.getKigo(), itv);

        RpgData poi = new RpgData();
        poi.setKigo("POI");
        poi.setValue(14);
        optList.add(poi);
        map.put(poi.getKigo(), poi);

        RpgData mpw = new RpgData();
        mpw.setKigo("MPW");
        mpw.setValue(14);
        optList.add(mpw);
        map.put(mpw.getKigo(), mpw);

        RpgData tsm = new RpgData();
        tsm.setKigo("TSM");
        tsm.setValue(15);
        optList.add(tsm);
        map.put(tsm.getKigo(), tsm);

        RpgData dnm = new RpgData();
        dnm.setKigo("DNM");
        dnm.setValue(16);
        optList.add(dnm);
        map.put(dnm.getKigo(), dnm);

        RpgConfig conf = RpgConfig.getInstance();
        conf.setMasterMap(masterMap);
        Map<String, RpgStatus> statusMap = new HashMap<>();
        Map<String, RpgStatus> optMap = new HashMap<>();
        Set<String> set = map.keySet();
        for (RpgData data  : list) {
            RpgStatus status = new RpgStatus();
            status.setKigo(data.getKigo());
            status.setValue(data.getValue());
            statusMap.put(data.getKigo(), status);
        }
        for (RpgData data  : optList) {
            RpgStatus status = new RpgStatus();
            status.setKigo(data.getKigo());
            status.setValue(data.getValue());
            optMap.put(data.getKigo(), status);
        }
        conf.setParamMap(map);
        conf.setStatusMap(statusMap);
        conf.setParty(PlayerParty.getInstance());

        // 計算マップ
        createFormulaMap();
        PlayerCharactor player = null;
        try {

            player = new PlayerCharactor("test");
            player.setStatusMap(statusMap);
            player.setOptionalMap(optMap);
        } catch (RpgException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return player;
    }

    public static void initRpgConfig(boolean is) {
        RpgConfig conf = RpgConfig.getInstance();
        PlayerParty party = PlayerParty.getInstance();
        try {
            loadParamMaps();
            loadCommands();
            loadJobs();
            loadMonsterType();
            loadMonsters();
            PlayerCharactor player = new PlayerCharactor("test");
            Map<String, RpgStatus> map =  new HashMap<>();
            Map<String, RpgStatus> statusMap = conf.getStatusMap();
            map.putAll(statusMap);
            Set<String> set = map.keySet();
            int count = 1;
            for (String key : set) {
                RpgStatus st = map.get(key);
                st.setValue(count);
                //System.out.println("Status: " + st.getKigo() + " : " + st.getValue());
                count++;
            }
            Map<String, RpgStatus> optMap = conf.getOptionStatusMap();
            Set<String> optSet = optMap.keySet();
            count = 1;
            for (String key : optSet) {
                RpgStatus st = new RpgStatus();
                st.setKigo(key);
                st.setValue(count);
                map.put(key, st);
                //System.out.println("Status: " + st.getKigo() + " : " + st.getValue());
                count++;
            }
            player.setStatusMap(map);

            party.setPlayer(player);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * コメント行の追加
     * @param storyTxt ストーリーテキスト
     * @param commentList コメントリスト
     * @throws IOException 想定外のエラー
     */
    private static void loadCommentLine(BufferedReader storyTxt, List<String> commentList) throws IOException {
        String line = null;
        while((line = storyTxt.readLine()).startsWith("# ")) {
            commentList.add(line);
        }
    }

    /**
     * モンスターリストを読み込み、RpgConfigに設定する。
     */
    private static void loadMonsters() {
        try {
            List<Monster> list = XmlUtils.loadMonsters();
            RpgConfig.getInstance().setMonsterList(list);
        } catch (RpgException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Job.xmlを読み込んで職業マップを作成する。
     */
    private static void loadJobs() {
        try {
            Map<String, RpgJob> map = XmlUtils.loadJobs();
            if (isDebug) System.out.println("mapSize: " + map.size());
            RpgConfig.getInstance().setJobMap(map);
        } catch (RpgException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    /**
     * MonsterType.xmlを読み込んで職業マップを作成する。
     */
    private static void loadMonsterType() {
        try {
            Map<String, RpgMonsterType> map = XmlUtils.loadMonterType();
            if (isDebug) System.out.println("mapSize: " + map.size());
            RpgConfig.getInstance().setMonsterTypeMap(map);
        } catch (RpgException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Command.xmlを読み込んで職業マップを作成する。
     */
    private static void loadCommands() {
        try {
            Map<String, RpgCommand> map = XmlUtils.loadCommands();
            RpgConfig.getInstance().setCommandMap(map);
        } catch (RpgException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * conf.txtを読み込んで、パラメータマップを作成する。
     * パラメータマップは以下のマップを総称している。
     * <ol>
     *     <li>マスタカテゴリマップ(固定)：CONFIG_MASTER</li>
     *     <li>パラメータマップ(ユーザー定義)：CONFIG_PARAM</li>
     *     <li>ステータスマップ(ユーザー定義)：CONFIG_STATUS </li>
     *     <li>アイテムリスト(ユーザー定義)：ITEM_LIST</li>
     *     <li>計算式マップ(ユーザー定義)：CONFIG_FORMULA</li>
     *     <li>ステータス効果マップ：CONFIG_ST_EFFECT</li>
     * </ol>
     * @throws RpgException
     * @throws IOException
     * @throws StoryTextException
     */
    public static void loadParamMaps() throws RpgException, IOException, StoryTextException {
        // コメントの行リスト
        List<String> commentList = new ArrayList<>();
        // パラメータ設定クラス
        ParamGenerator generator = ParamGenerator.getInstance();
        BufferedReader storyTxt = getBufferedReader("src/main/resources", "conf.txt");
        String line = null;
        try {
            // コメントフラグ:テキストのはじめのみに記載することができる
            boolean isComment = true;
            // 選択肢
            boolean isSelectLine = false;

            while ((line = storyTxt.readLine()) != null) {
                // コメントの取得
                if (CheckerUtils.isCommentLine(line) && isComment) {
                    loadCommentLine(storyTxt, commentList);
                    continue;
                }
                // コメント行が終了
                isComment = false;

                // コメント行を飛ばす 改行コードのみの場合
                if (CheckerUtils.isCommentLine(line) || CheckerUtils.isEmpptyOrSep(line)) {
                    continue;
                }
                // カテゴリマスターの生成
                if (line.equals("CONFIG_MASTER")) {
                    generator.createMasterCategory(storyTxt);
                    continue;
                }
                // 設定オブジェクトの生成
                if (line.equals("CONFIG_PARAM")) {
                    generator.createParam(storyTxt);
                    continue;
                }
                if (line.equals("CONFIG_STATUS")) {
                    generator.createStatus(storyTxt);
                    continue;
                }
                if (line.equals("CONFIG_ITEM")) {
                    generator.createItemTypeMap(storyTxt);
                    continue;
                }
                if (line.equals("CONFIG_FORMULA")) {
                    generator.createFormulaMap(storyTxt);
                    continue;
                }
                if (line.equals("ITEM_LIST")) {
                    generator.createItemMap(storyTxt);
                    continue;
                }
                if (line.equals("CONFIG_ST_EFFECT")) {
                    generator.createEffects(storyTxt);
                    continue;
                }
            }
        } catch (RpgException re) {
            System.out.println("ストーリーテキストの読み込み中にエラーがありました。: " + re.getMessage() + SEP + line);
            re.printStackTrace();
        } catch (IOException ie) {
            System.out.println("ストーリーテキストの読み込みに失敗しました。。: " + ie.getMessage() + SEP + line);
            ie.printStackTrace();
            throw ie;
        } catch (Exception e) {
            e.printStackTrace();
            throw new StoryTextException("ストーリーテキスト読み込み中の想定外のエラーがありました。" + e.getMessage() + SEP + line);
        }
    }


    public static Map<String, RpgFormula> createFormulaMap() {
        Map<String, RpgFormula> map = new HashMap<>();

        RpgFormula atk = new RpgFormula("(POW + WEV) * (1 + (0.1 * JLV))");
        map.put("ATK", atk);
        RpgFormula def = new RpgFormula("(POW + AGI+DEX) / 3 + ARV");
        map.put("DEF", def);
        RpgFormula mat = new RpgFormula(" (INT + DEX+KSM) / 3  + MPW");
        map.put("MAT", mat);
        RpgFormula mde = new RpgFormula("(INT + DEX + AGI) / 3 + TSM ");
        map.put("MDE", mde);
        RpgFormula itm = new RpgFormula("(((INT + DEX) / 2 ) * 0.1) * ITV");
        map.put("ITM", itm);
        RpgFormula cst = new RpgFormula("( 1 + KSM * 0.1 )");
        map.put("CST", cst);
        RpgFormula evr = new RpgFormula("(AGI + INT + DEX) / 100");
        map.put("EVR", evr);
        RpgFormula itn = new RpgFormula("(POW + DEX) / 2 + BPK");
        map.put("ITN", itn);

        RpgConfig.getInstance().setFormulaMap(map);
        return map;
    }

    /**
     * MainWeponのインスタンスを作成する、
     * @param name 武器名
     * @param valueKigo 記号と値(WEV+1)
     * @return　MainWepon
     */
    public static MainWepon createWepon(String name, String valueKigo) {
        RpgItem item = new RpgItem();
        item.setName(name);
        item.setItemValueKigo(valueKigo);
        MainWepon wepon = null;
        try {
            wepon = new MainWepon(item);
        } catch(RpgException e) {
            e.printStackTrace();
            fail("失敗");
        }
        return wepon;
    }

    /**
     * Armorのインスタンスを作成する、
     * @param name 防具名
     * @param valueKigo 記号と値(WEV+1)
     * @return　Armor
     */
    public static Armor createArmor(String name, String valueKigo) {
        RpgItem item = new RpgItem();
        item.setName(name);
        item.setItemValueKigo(valueKigo);
        Armor armor = null;
        try {
            armor = new Armor(item);
        } catch(RpgException e) {
            e.printStackTrace();
            fail("失敗");
        }
        return armor;
    }

    /**
     * 指定したファイルを読み込む、BufferedReaderを取得する。
     *
     * @param path ディレクトリのパス
     * @param fileName ファイル名
     * @return 対象のファイルリーダー
     */
    private static BufferedReader getBufferedReader(String path, String fileName) {
        BufferedReader buf = null;
        try {
            buf = Files.newBufferedReader(Paths.get(path, fileName));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return buf;
    }
}
