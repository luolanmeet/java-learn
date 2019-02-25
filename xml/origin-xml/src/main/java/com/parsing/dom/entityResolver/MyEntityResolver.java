package com.parsing.dom.entityResolver;

import com.parsing.dom.DomParser;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

/**
 * 当要解析一个流时，需要实体解析器来支持DTD
 * @author cck
 */
public class MyEntityResolver implements EntityResolver {
    
    @Override
    public InputSource resolveEntity(
            String publicId, 
            String systemId) throws SAXException, IOException {
        
        System.out.println(publicId);

        System.out.println(systemId);
        
        InputStream stream 
            = DomParser.class.getClassLoader().getResourceAsStream("mybatis-config.dtd");
        
        return new InputSource(stream);
    }
    
}
