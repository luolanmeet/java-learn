package test.direct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.direct.publisher.MyPublisher;

/**
 * 
 * @author cck
 */
@SpringBootTest(classes=com.direct.App.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDirect {
    
    @Autowired
    MyPublisher publisher;
    
    @Test
    public void testSend() {
        publisher.send();
    }
}
