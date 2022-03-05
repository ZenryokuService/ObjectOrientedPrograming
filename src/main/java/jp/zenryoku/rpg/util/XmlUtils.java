package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.charactors.monsters.Monster;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.data.job.RpgCommand;
import jp.zenryoku.rpg.data.job.RpgJob;
import jp.zenryoku.rpg.exception.RpgException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XmlUtils {
    private static final boolean isDebug = false;

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
     * Node(monsterタグ)からクラスを生成する。
     * @param node monsterタグ
     * @return Monsterクラス
     * @throws RpgException XMLの設定エラー
     */
    private static Monster createMonster(Node node) throws RpgException {
        Element e = (Element) node;
        String name = e.getElementsByTagName("name").item(0).getTextContent();
        String lv = e.getElementsByTagName("lv").item(0).getTextContent();
        String hp =  e.getElementsByTagName("hp").item(0).getTextContent();
        String mp =  e.getElementsByTagName("mp").item(0).getTextContent();
        String atk =  e.getElementsByTagName("atk").item(0).getTextContent();
        String def =  e.getElementsByTagName("def").item(0).getTextContent();
        String isTalk =  e.getElementsByTagName("isTalk").item(0).getTextContent();
        String message =  e.getElementsByTagName("message").item(0).getTextContent();

        Monster monster = null;
        try {
            int plv = Integer.parseInt(lv);
            int php = Integer.parseInt(hp);
            int pmp = Integer.parseInt(mp);
            int patk = Integer.parseInt(atk);
            int pdef = Integer.parseInt(def);
            boolean pisTalk = Boolean.parseBoolean(isTalk);
            String pmessage = message;

            monster = new Monster(name, plv, php, pmp, patk, pdef, pisTalk, pmessage);
        } catch (NumberFormatException ne) {
            ne.printStackTrace();
            throw new RpgException(MessageConst.ERR_NUMBER_FORMAT.toString() + ": " + ne.getMessage());
        }
        return monster;
    }

    /**
     * Job.xmlを読み込む。
     * @return Jobリスト
     * @throws RpgException XMLの設定エラー
     */
    public static List<RpgJob> loadJobs() throws RpgException {
        List<RpgJob> jobList = new ArrayList<>();

        try {
            Document doc = loadDocumentBuilder("src/main/resources", "Job.xml");
            if (isDebug) System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList list = doc.getElementsByTagName("job");

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    jobList.add(createJob(node));
                }
            }
        } catch (RpgException e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.ERR_XML_PERSE.toString() + ": " + e.getMessage());
        }
        return jobList;
    }

    /**
     * RpgCommandクラスを生成する。
     * @param node commandタグ
     * @return RpgCommand コマンドクラス
     * @throws RpgException XML設定エラー
     */
    private static RpgJob createJob(Node node) throws RpgException {
        Element e = (Element) node;
        String id = e.getElementsByTagName("id").item(0).getTextContent();
        String name = e.getElementsByTagName("name").item(0).getTextContent();
        String disc =  e.getElementsByTagName("discription").item(0).getTextContent();
        String commandList =  e.getElementsByTagName("commandList").item(0).getTextContent();
        String[] list = commandList.split(",");

        RpgJob job = null;
        job = new RpgJob(id, name, disc, list);
        return job;
    }

    /**
     * Command.xmlを読み込む。
     * @return Commandリスト
     * @throws RpgException XMLの設定エラー
     */
    public static List<RpgCommand> loadCommands() throws RpgException {
        List<RpgCommand> commandList = new ArrayList<>();

        try {
            Document doc = loadDocumentBuilder("src/main/resources", "Commands.xml");
            if (isDebug) System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList list = doc.getElementsByTagName("command");

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    commandList.add(createCommand(node));
                }
            }
        } catch (RpgException e) {
            e.printStackTrace();
            throw new RpgException(MessageConst.ERR_XML_PERSE.toString() + ": " + e.getMessage());
        }
        return commandList;
    }

    /**
     * RpgJobクラスを生成する。
     * @param node jobタグ
     * @return RpgJob 職業クラス
     * @throws RpgException XML設定エラー
     */
    private static RpgCommand createCommand(Node node) throws RpgException {
        Element e = (Element) node;
        String id = e.getElementsByTagName("id").item(0).getTextContent();
        String name = e.getElementsByTagName("name").item(0).getTextContent();
        String formula =  e.getElementsByTagName("discription").item(0).getTextContent();

        RpgCommand com = null;
        com = new RpgCommand(id, name, formula);
        return com;
    }
}
