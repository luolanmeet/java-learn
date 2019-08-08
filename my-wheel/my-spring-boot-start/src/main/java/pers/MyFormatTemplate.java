package pers;

import pers.autoconfiguration.MyProperties;
import pers.format.FormatProcessor;

/**
 * 
 * @author cck
 */
public class MyFormatTemplate {
    
    private MyProperties myProperties;
    
    private FormatProcessor formatProcessor;
    
    public MyFormatTemplate(MyProperties myProperties, FormatProcessor formatProcessor) {
        this.myProperties = myProperties;
        this.formatProcessor = formatProcessor;
    }
    
    public <T> String doFormat(T obj) {
    
        return new StringBuilder()
                .append("properties: ")
                .append(myProperties.getInfo())
                .append(" result: ")
                .append(formatProcessor.format(obj))
                .toString();
    }
    
}
