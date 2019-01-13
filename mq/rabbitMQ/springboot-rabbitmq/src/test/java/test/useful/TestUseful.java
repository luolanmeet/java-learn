package test.useful;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.useful.publisher.MyPublisher;

/**
 * 
 * @author cck
 */
@SpringBootTest(classes=com.useful.App.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUseful {
    
    @Autowired
    MyPublisher publisher;
    
    @Test
    public void testSend() throws IOException {
        
        publisher.send();
        System.in.read();
    }
}
