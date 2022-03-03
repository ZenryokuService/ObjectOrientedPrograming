package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.charactors.monsters.Monster;
import jp.zenryoku.rpg.constants.MessageConst;
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

    public static List<Monster> loadMonsters() throws RpgException {
        List<Monster> monsList = new ArrayList<>();
        //creating a constructor of file class and parsing an XML files
        Path path = Paths.get("src/main/resources", "Monsters.xml");
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
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList list = doc.getElementsByTagName("monster");

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    monsList.add(createMonster(node));
                }
            }
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
        return monsList;
    }

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
}
