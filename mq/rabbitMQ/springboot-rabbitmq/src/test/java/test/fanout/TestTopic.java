package test.fanout;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fanout.publisher.MyPublisher;

/**
 * 
 * @author cck
 */
@SpringBootTest(classes=com.fanout.App.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestTopic {
    
    @Autowired
    MyPublisher publisher;
    
    @Test
    public void testSend() throws IOException {
        
        publisher.sendA();
        publisher.sendB();
        System.in.read();
    }
}
