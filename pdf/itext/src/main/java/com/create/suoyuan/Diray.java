package com.create.suoyuan;

/**
 * 毕设的时候的例子
 * 将用户的日记导出为pdf
 * @author cck
 */
public class Diray {
    
    // 日记内容
    private String content; 
    // 日记时间
    private String time;
    
    public Diray(String content, String time) {
        super();
        this.content = content;
        this.time = time;
    }
    
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }      
}
