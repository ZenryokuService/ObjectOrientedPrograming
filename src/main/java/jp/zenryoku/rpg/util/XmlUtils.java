package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.charactors.monsters.Monster;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgStm;
import jp.zenryoku.rpg.data.job.RpgCommand;
import jp.zenryoku.rpg.data.job.RpgJob;
import jp.zenryoku.rpg.data.job.RpgMonsterType;
import jp.zenryoku.rpg.data.status.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class XmlUtils {
    private static final boolean isDebug = false;

    private static NodeList getTagNode(Element e, String tagName) throws RpgException {
        Element ele = null;
        NodeList nodeList = e.getElementsByTagName(tagName);
        if (nodeList.getLength() == 0) {
            throw new RpgException("Node(" + e.getTagName() + ")に" + tagName + "は存在しません。");
        }
        return nodeList;
    }

    /**
     * XMLドキュメント(ファイル)を読み込む。
     * @param directory ファイルの配置しているディレクトリ
     * @param fileName ファイル名
     * @return Documentオブジェクト
     * @throws RpgException ファイルの読み込みエラー
     */
    private static Document loadDocumentBuilder(String directory, String fileName) throws RpgException {
        //creating a constructor of file class and parsing an XML files
        Path path = Paths.get(directory, fileName);
        File file = path.toFile();// ("/Monster.xml");
        //an instance of factory that gives a document builder
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //an instance of builder to parse the specified xml file
        DocumentBuilder db = null;
        Document doc = null;
        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(file);
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.ERR_XML_PERSE.toString() + ": " + e.getMessage());
        } catch (SAXException e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.ERR_XML_PERSE.toString() + ": " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.ERR_IOEXCEPTION.toString() + ": " + e.getMessage());
        }
        return doc;
    }

    /**
     * Monster.xmlを読み込む。
     * @return モンスターリスト
     * @throws RpgException XMLからクラス生成時のエラー
     */
    public static List<Monster> loadMonsters() throws RpgException {
        List<Monster> monsList = new ArrayList<>();
        Map<String, RpgMonsterType> types = loadMonterType();
        RpgConfig.getInstance().setMonsterTypeMap(types);
        try {
            Document doc = loadDocumentBuilder("src/main/resources", "Monsters.xml");
            if (isDebug) System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList list = doc.getElementsByTagName("monster");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    monsList.add(createMonster(node));
                }
            }
        } catch (RpgException e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.ERR_XML_PERSE.toString() + ": " + e.getMessage());
        }
        return monsList;
    }

    /**
     * Monster.xmlを読み込む。
     * @return モンスターリスト
     * @throws RpgException XMLからクラス生成時のエラー
     */
    public static List<Monster> loadMonsters(String directory) throws RpgException {
        List<Monster> monsList = new ArrayList<>();

        try {
            Document doc = loadDocumentBuilder(directory, "Monsters.xml");
            if (isDebug) System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList list = doc.getElementsByTagName("monster");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    monsList.add(createMonster(node));
                }
            }
        } catch (RpgException e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.ERR_XML_PERSE.toString() + ": " + e.getMessage());
        }
        return monsList;
    }
    /**
     * Node(monsterタグ)からクラスを生成する。
     * @param node monsterタグ
     * @return Monsterクラス
     * @throws RpgException XMLの設定エラー
     */
    private static Monster createMonster(Node node) throws RpgException {
        // ステータス定義のコピー
        Map<String, RpgStatus> stMap = RpgConfig.getInstance().getStatusMap();
        Map<String, RpgStatus> statusMap = new HashMap<>();
        statusMap.putAll(stMap);
        // モンスタータイプのセット
        Map<String, RpgMonsterType> typeMap = RpgConfig.getInstance().getMonsterTypeMap();

        Element e = (Element) node;
        // 固定値(マスタカテゴリ)
        String name = getTagNode(e, "name").item(0).getTextContent();
        String type = getTagNode(e, "type").item(0).getTextContent();
        String lv = getTagNode(e, "lv").item(0).getTextContent();
        String hp =  getTagNode(e, "hp").item(0).getTextContent();
        String mp =  getTagNode(e, "mp").item(0).getTextContent();

        // その他
//        NodeList nodeList = e.getChildNodes();
        NamedNodeMap nodeMap = ((Element) node).getAttributes();
        for (int i = 0; i < nodeMap.getLength(); i++) {
            Element ele = (Element) nodeMap.item(i);
            String tagName = ele.getTagName().toLowerCase();
            System.out.println("nodeName: " + tagName);
            if (statusMap.containsKey(tagName) == false) {
                continue;
            } else {

            }
            RpgStatus status = statusMap.get(tagName.toUpperCase());
            String val = ele.getTextContent();
            status.setValue(Integer.parseInt(val));

        }

//        String pow =  getTagNode(e, "pow").item(0).getTextContent();
//        String agi =  getTagNode(e, "agi").item(0).getTextContent();
//        String inta =  getTagNode(e, "int").item(0).getTextContent();
//        String dex =  getTagNode(e, "dex").item(0).getTextContent();
//        String ksm =  getTagNode(e, "ksm").item(0).getTextContent();
        String isTalk =  getTagNode(e, "isTalk").item(0).getTextContent();
        String message =  getTagNode(e, "message").item(0).getTextContent();
        String exp =  getTagNode(e, "exp").item(0).getTextContent();
        String money =  getTagNode(e, "money").item(0).getTextContent();

        Monster monster = null;
        try {
            int plv = Integer.parseInt(lv);
            int php = Integer.parseInt(hp);
            int pmp = Integer.parseInt(mp);
            boolean pisTalk = Boolean.parseBoolean(isTalk);
            String pmessage = message;

            monster = new Monster(name, plv, php, pmp, pisTalk, pmessage);
            monster.setExp(Integer.parseInt(exp));
            monster.setMoney(Integer.parseInt(money));
            monster.setStatusMap(statusMap);
            monster.setType(typeMap.get(type));

        } catch (NumberFormatException ne) {
            ne.printStackTrace();
            throw new RpgException(MessageConst.ERR_NUMBER_FORMAT.toString() + ": " + ne.getMessage());
        }
        return monster;
    }

    /**
     * Job.xmlを読み込む。必ず、Command.xmlを読み込んでから実行する。
     * @return Jobリスト
     * @throws RpgException XMLの設定エラー
     */
    public static Map<String, RpgJob> loadJobs() throws RpgException {
        Map<String, RpgJob> jobMap = new HashMap<>();
        Map<String, RpgCommand> cmdMap = RpgConfig.getInstance().getCommandMap();
        if (cmdMap.size() <= 0) {
            throw new RpgException(MessageConst.ERR_BEFORE_LOAD_CMD.toString());
        }
        try {
            Document doc = loadDocumentBuilder("src/main/resources", "Job.xml");
            if (isDebug) System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList list = doc.getElementsByTagName("job");

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    RpgJob j = createJob(node, cmdMap);
                    jobMap.put(j.getJobId(), j);
                }
            }
        } catch (RpgException e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.ERR_XML_PERSE.toString() + ": " + e.getMessage());
        }
        return jobMap;
    }

    /**
     * Job.xmlを読み込む。必ず、Command.xmlを読み込んでから実行する。
     * @return Jobリスト
     * @throws RpgException XMLの設定エラー
     */
    public static Map<String, RpgJob> loadJobs(String directory) throws RpgException {
        Map<String, RpgJob> jobMap = new HashMap<>();
        Map<String, RpgCommand> cmdMap = RpgConfig.getInstance().getCommandMap();
        if (cmdMap.size() <= 0) {
            throw new RpgException(MessageConst.ERR_BEFORE_LOAD_CMD.toString());
        }
        try {
            Document doc = loadDocumentBuilder(directory, "Job.xml");
            if (isDebug) System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList list = doc.getElementsByTagName("job");

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    RpgJob j = createJob(node, cmdMap);
                    jobMap.put(j.getJobId(), j);
                }
            }
        } catch (RpgException e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.ERR_XML_PERSE.toString() + ": " + e.getMessage());
        }
        return jobMap;
    }

    /**
     * RpgJobクラスを生成する。依存するCommandListをセット。
     * @param node jobタグ
     * @return RpgJob 職業クラス
     * @throws RpgException XML設定エラー
     */
    private static RpgJob createJob(Node node, Map<String, RpgCommand> map) throws RpgException {
        Element e = (Element) node;
        String id = getTagNode(e, "id").item(0).getTextContent();
        String name = getTagNode(e, "name").item(0).getTextContent();
        String disc =  getTagNode(e, "discription").item(0).getTextContent();
        String commandList =  getTagNode(e, "commandList").item(0).getTextContent();
        String[] list = commandList.split(",");

        List<RpgCommand> cmdList = new ArrayList<>();
        for (String cmd : list) {
            RpgCommand cmdCls = map.get(cmd.trim());
            cmdList.add(cmdCls);
        }

        // RogConfigの値を変えないように気を付ける
        Map<String, RpgStatus> templateMap = RpgConfig.getInstance().getStatusMap();
        Map<String, RpgStatus> statusMap = new HashMap<>();
        statusMap.putAll(templateMap);
        Set<String> set = statusMap.keySet();
        // ステータス上昇値の設定
        for (String key : set) {
            if (isDebug) System.out.println("Status: " + key);
            String statsUp = getTagNode(e, key.toLowerCase()).item(0).getTextContent();
            RpgStatus st = new RpgStatus();
            st.setKigo(key);
            st.setValue(Integer.parseInt(statsUp));
            statusMap.put(key, st);
        }
        RpgJob job = null;
        job = new RpgJob(id, name, disc, cmdList);
        // ステータス上昇値のマップを設定する。
        job.setStatusUpMap(statusMap);
        return job;
    }

    /**
     * MonseterType.xmlを読み込む。必ず、Command.xmlを読み込んでから実行する。
     * @return MonseterTypeリスト
     * @throws RpgException XMLの設定エラー
     */
    public static Map<String, RpgMonsterType> loadMonterType() throws RpgException {
        Map<String, RpgMonsterType> monsMap = new HashMap<>();
        Map<String, RpgCommand> cmdMap = RpgConfig.getInstance().getCommandMap();
        if (cmdMap.size() <= 0) {
            throw new RpgException(MessageConst.ERR_BEFORE_LOAD_CMD.toString());
        }
        try {
            Document doc = loadDocumentBuilder("src/main/resources", "MonseterType.xml");
            if (isDebug) System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList list = doc.getElementsByTagName("monsterType");

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    RpgMonsterType j = createMonsterType(node, cmdMap);
                    monsMap.put(j.getJobId(), j);
                }
            }
        } catch (RpgException e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.ERR_XML_PERSE.toString() + ": " + e.getMessage());
        }
        return monsMap;
    }

    /**
     * MonseterType.xmlを読み込む。必ず、Command.xmlを読み込んでから実行する。
     * @return MonseterTypeリスト
     * @throws RpgException XMLの設定エラー
     */
    public static Map<String, RpgMonsterType> loadMonterType(String directory) throws RpgException {
        Map<String, RpgMonsterType> monsMap = new HashMap<>();
        Map<String, RpgCommand> cmdMap = RpgConfig.getInstance().getCommandMap();
        if (cmdMap.size() <= 0) {
            throw new RpgException(MessageConst.ERR_BEFORE_LOAD_CMD.toString());
        }
        try {
            Document doc = loadDocumentBuilder(directory, "MonseterType.xml");
            if (isDebug) System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList list = doc.getElementsByTagName("monsterType");

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    RpgMonsterType j = createMonsterType(node, cmdMap);
                    monsMap.put(j.getJobId(), j);
                }
            }
        } catch (RpgException e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.ERR_XML_PERSE.toString() + ": " + e.getMessage());
        }
        return monsMap;
    }

    /**
     * RpgMonsterTypeクラスを生成する。
     * @param node monsterTypeタグ
     * @return RpgMonsterType モンスタータイプクラス
     * @throws RpgException XML設定エラー
     */
    private static RpgMonsterType createMonsterType(Node node, Map<String, RpgCommand> map) throws RpgException {
        Element e = (Element) node;
        String id = getTagNode(e, "id").item(0).getTextContent();
        String name = getTagNode(e, "name").item(0).getTextContent();
        String disc =  getTagNode(e, "discription").item(0).getTextContent();
        String commandList =  getTagNode(e, "commandList").item(0).getTextContent();
        String[] list = commandList.split(",");

        List<RpgCommand> cmdList = new ArrayList<>();
        for (String cmd : list) {
            RpgCommand cmdCls = map.get(cmd.trim());
            cmdList.add(cmdCls);
        }

        RpgMonsterType job = new RpgMonsterType(id, name, disc, cmdList);
        return job;
    }

    /**
     * Command.xmlを読み込む。
     * @return Commandリスト
     * @throws RpgException XMLの設定エラー
     */
    public static Map<String, RpgCommand> loadCommands() throws RpgException {
        Map<String, RpgCommand> commandMap = new HashMap<>();

        try {
            Document doc = loadDocumentBuilder("src/main/resources", "Commands.xml");
            if (isDebug) System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList list = doc.getElementsByTagName("command");

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    RpgCommand cmd = createCommand(node);
                    commandMap.put(cmd.getCommandId(), cmd);
                }
            }
        } catch (RpgException e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.ERR_XML_PERSE.toString() + ": " + e.getMessage());
        }
        return commandMap;
    }

    /**
     * Command.xmlを読み込む。依存するSTM.xmlはロード済みの前提。
     * @return Commandリスト
     * @throws RpgException XMLの設定エラー
     */
    public static Map<String, RpgCommand> loadCommands(String direcory) throws RpgException {
        Map<String, RpgCommand> commandMap = new HashMap<>();

        try {
            Document doc = loadDocumentBuilder(direcory, "Commands.xml");
            if (isDebug) System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList list = doc.getElementsByTagName("command");

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    RpgCommand cmd = createCommand(node);
                    commandMap.put(cmd.getCommandId(), cmd);
                }
            }
        } catch (RpgException e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.ERR_XML_PERSE.toString() + ": " + e.getMessage());
        }
        return commandMap;
    }

    /**
     * RpgCommandクラスを生成する。STMマップを生成している必要がある。
     * @param node jobタグ
     * @return RpgJob 職業クラス
     * @throws RpgException XML設定エラー
     */
    private static RpgCommand createCommand(Node node) throws RpgException {
        Element e = (Element) node;
        String id = getTagNode(e, "id").item(0).getTextContent();
        String name = getTagNode(e, "name").item(0).getTextContent();
        String formula =  getTagNode(e, "formula").item(0).getTextContent();
        String hasChild = getTagNode(e, "hasChild").item(0).getTextContent();
        String exeMessage =  getTagNode(e, "exeMessage").item(0).getTextContent();

        RpgCommand com = null;
        com = new RpgCommand(id, name, formula, exeMessage);
        com.setChildDir(Boolean.parseBoolean(hasChild));

        // 子ディレクトリありの場合
        if (com.isChildDir()) {
            // STMのマップ取得
            Map<String, List<RpgStm>> stmMap = RpgConfig.getInstance().getStmMap();

            if (isDebug) System.out.println("stmMap: " + stmMap.get(id));
            com.setChildList(stmMap.get(id));
        }
        return com;
    }

    /**
     * STM.xmlを読み込む。TODO-[XMLタグの読み込みを動的に修正する(手段の検討中)
     * @return Stmリスト
     * @throws RpgException XMLの設定エラー
     */
    public static void loadStm(String directory) throws RpgException {
        Map<String, List<RpgStm>> stmMap = new HashMap<>();

        try {
            Document doc = null;
            if ("".equals(directory)) {
                doc = loadDocumentBuilder("src/main/resources", "STM.xml");
            } else {
                doc = loadDocumentBuilder(directory, "STM.xml");
            }
            if (isDebug) System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            // 魔法
            NodeList magList = doc.getElementsByTagName("mag");

            for (int i = 0; i < magList.getLength(); i++) {
                Node node = magList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    List<RpgStm> stmList = createStm(node);
                    stmMap.put(RpgConst.MAG.getType(), stmList);
                }
            }
            // 技
            NodeList tecList = doc.getElementsByTagName("tec");
            for (int i = 0; i < tecList.getLength(); i++) {
                Node node = tecList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    List<RpgStm> stmList = createStm(node);
                    stmMap.put(RpgConst.TEC.toString(), stmList);
                }
            }
            if (isDebug) {
                stmMap.forEach((key, val) -> {
                    System.out.println("stmKey: " + key + " : stmValueSize: " + val.size());
                });
            }
            RpgConfig.getInstance().setStmMap(stmMap);
        } catch (RpgException e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.ERR_XML_PERSE.toString() + ": " + e.getMessage());
        }
    }

    /**
     * RpgStmクラスを生成する。
     * @param node jobタグ
     * @return RpgJob 職業クラス
     * @throws RpgException XML設定エラー
     */
    private static List<RpgStm> createStm(Node node) throws RpgException {
        List<RpgStm> stmList = new ArrayList<>();
        Element e = (Element) node;
        try {
            String mid = getTagNode(e, "id").item(0).getTextContent();
            String name = getTagNode(e, "name").item(0).getTextContent();
            String ori =  getTagNode(e, "ori").item(0).getTextContent();
            String cost =  getTagNode(e, "cost").item(0).getTextContent();
            int iCost = Integer.parseInt(cost);
            String mpw =  getTagNode(e, "force").item(0).getTextContent();
            // 最大桁数は3: 999まで
            int iMpw = 0;
            String formula = "";
            if (CheckerUtils.isNumber(mpw, "3")) {
                iMpw = Integer.parseInt(mpw);
            } else {
                formula = mpw;
            }
            String jobs = getTagNode(e, "jobs").item(0).getTextContent();
            // ループで職業IDと習得レベルを取得RpgStmを生成する
            String[] sep = jobs.split(",");
            for (String st : sep) {
                RpgStm stm = new RpgStm(mid, name, ori, iCost, iMpw);
                String[] jobData = StringUtils.cnvertStmJObsData(st);
                stm.setJobId(jobData[0]);
                stm.setLeanLv(Integer.parseInt(jobData[1]));
                stm.setFormula(formula);
                stmList.add(stm);
            }

        } catch (NumberFormatException ne) {
            ne.printStackTrace();
            throw new RpgException(MessageConst.ERR_XML_STM.toString());
        }
        return stmList;
    }
}
