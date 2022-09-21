package com.dom4j;

import org.dom4j.*;

import java.util.List;

/**
 * @auther ken.ck
 * @date 2022/9/14 20:00
 */
public class Dom4jUtil {

    public static String removeAttribute(String xml) throws DocumentException {
        Document document = DocumentHelper.parseText(xml);
        document.accept(new NameSpaceCleaner());
        return document.asXML();
    }

    public static void main(String[] args) throws DocumentException {
        String xmlStr = "";
        String result = "";
//        xmlStr = "<ns2:msg xmlns:ns2=\"http://vo.webservice.fotile.io.ems.sie.com\">已存在该:2来源明细的申请单!</ns2:msg>";
//        xmlStr = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body><_tns_:dcsResponse xmlns:_tns_=\"http://www.primeton.com/dcsService\"><ns1:out1 xmlns:ns1=\"http://www.primeton.com/dcsService\"><ns2:msg xmlns:ns2=\"http://vo.webservice.fotile.io.ems.sie.com\">已存在该:2来源明细的申请单!</ns2:msg><ns2:object xmlns:ns2=\"http://vo.webservice.fotile.io.ems.sie.com\" xmlns:ns3=\"http://www.w3.org/2001/XMLSchema-instance\" ns3:nil=\"true\" /><ns2:status xmlns:ns2=\"http://vo.webservice.fotile.io.ems.sie.com\">F</ns2:status></ns1:out1></_tns_:dcsResponse></soapenv:Body></soapenv:Envelope>";
        xmlStr = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body><_tns_:dcsResponse xmlns:_tns_=\"http://www.primeton.com/dcsService\"><ns1:out1 xmlns:ns1=\"http://www.primeton.com/dcsService\"><ns2:msg a=\"xx\" xmlns:ns2=\"http://vo.webservice.fotile.io.ems.sie.com\">已存在该:2来源明细的申请单!</ns2:msg><ns2:object xmlns:ns2=\"http://vo.webservice.fotile.io.ems.sie.com\" xmlns:ns3=\"http://www.w3.org/2001/XMLSchema-instance\" ns3:nil=\"true\" /><ns2:status xmlns:ns2=\"http://vo.webservice.fotile.io.ems.sie.com\">F</ns2:status></ns1:out1></_tns_:dcsResponse></soapenv:Body></soapenv:Envelope>";
        result = removeAttribute(xmlStr);
        System.out.println(result);
    }

}
