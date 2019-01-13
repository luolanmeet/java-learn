package com.parsing.dom;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * 
 * @author cck
 */
public class XPathParser {
    
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        
        Document document = DomParser.createDocument();
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        
        Node node = (Node) xPath.evaluate("/configuration/environments/environment/dataSource", 
                document, XPathConstants.NODE);
        System.out.println(node.getNodeName());
        
        String val = xPath.evaluate("/configuration/environments/environment/dataSource/@type", document);
        System.out.println(val);
        // 可以从当前已经获得节点开始找
        val = xPath.evaluate("@type", node);
        System.out.println(val);
        
        // 注意！ 索引从1开始的
        val = xPath.evaluate("property[1]/@name", node);
        System.out.println(val);
        
        String evaluate = xPath.evaluate("/configuration/mappers/mapper[1]/@resource", document);
        System.out.println(evaluate);
    }
    
}
