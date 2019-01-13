package com.parsing.dom;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.parsing.dom.entityResolver.MyEntityResolver;
import com.parsing.dom.errorhandler.MyErrorHandler;

/**
 * DOM解析器
 * @author cck
 */
public class DomParser {
    
    public static Document createDocument() throws ParserConfigurationException, SAXException, IOException {
        
        // 读入一个XML文件，需要DocumentBuilder
        DocumentBuilderFactory factory 
            = DocumentBuilderFactory.newInstance();
        // 开启校验
        factory.setValidating(true);
        // 忽略空白字符
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        builder.setEntityResolver(new MyEntityResolver());
        builder.setErrorHandler(new MyErrorHandler());
        
        InputStream stream 
            = Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis-config.xml");
        Document document = builder.parse(stream);
        
        /*File file = new File("src/main/resources/mybatis-config.xml");
        Document document = builder.parse(file);*/
        
        return document;
    }
    
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        
        Document document = createDocument();
        
        // 获取根节点
        Element root = document.getDocumentElement();
        System.out.println("root name :" + root.getTagName());
        
        // 获取孩子节点
        NodeList childNodes = root.getChildNodes();
        System.out.println(childNodes.getLength());
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node instanceof Element) {
                System.out.println(node.getNodeName() + " " + node.getTextContent());
            }
        }
        
        // 遍历形式2
        for (Node node = root.getFirstChild(); node != null; node = node.getNextSibling()) {
        }
        
    }
    
}
