package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.create.suoyuan.Diray;
import com.create.suoyuan.PDFCreater;
import com.create.suoyuan.User;

public class TestPDFCreater {
    
    User user;
    List<Diray> dirays;
    
    @Before
    public void before() {
        
        dirays = new ArrayList<Diray>();
        user = new User("cck", "越来越开心~~~~~~~~~~~~~", "c.jpg");
        int i = 10;
        while(i-- > 0) {
            dirays.add(new Diray("我越是逃离，却越是靠近你；我越是背过脸，却越是看见你。我是一座孤岛，处在相思之水中。四面八方，隔绝我通向你。一千零一面镜子，转映着你的容颜，我从你开始，我在你结束。 " + i, "2014-6-18"));
        }
    }
    
    @Test
    public void test() {
        PDFCreater.create(dirays, user);
    }
    
}
