package jp.zenryoku.tutorial;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlSample {
	public static void main(String[] args) {
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
				System.out.println("NodeName: " + node.getNodeName());

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					System.out.println("name: " + e.getElementsByTagName("name").item(0).getTextContent());
					System.out.println("name: " + e.getElementsByTagName("lv").item(0).getTextContent());
					System.out.println("name: " + e.getElementsByTagName("hp").item(0).getTextContent());
					System.out.println("name: " + e.getElementsByTagName("mp").item(0).getTextContent());
					System.out.println("name: " + e.getElementsByTagName("pow").item(0).getTextContent());
					System.out.println("name: " + e.getElementsByTagName("agi").item(0).getTextContent());
					System.out.println("name: " + e.getElementsByTagName("int").item(0).getTextContent());
					System.out.println("name: " + e.getElementsByTagName("dex").item(0).getTextContent());
					System.out.println("name: " + e.getElementsByTagName("ksm").item(0).getTextContent());
				}
			}
		} catch (ParserConfigurationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
