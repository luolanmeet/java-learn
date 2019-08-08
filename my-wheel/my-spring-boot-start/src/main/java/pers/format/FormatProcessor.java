package pers.format;

public interface FormatProcessor {
    
    // 格式化字符串
    <T> String format(T obj);
    
}
