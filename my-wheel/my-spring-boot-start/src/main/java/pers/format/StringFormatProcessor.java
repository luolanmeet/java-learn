package pers.format;
/**
 * 
 * @author cck
 */
public class StringFormatProcessor implements FormatProcessor {
    
    @Override
    public <T> String format(T obj) {
        return "StringFormatProcessor: " + obj.toString();
    }
}
