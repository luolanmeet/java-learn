package pers;

import org.junit.Test;

import java.io.IOException;

public class SimpleThreadPoolTest {
    
    @Test
    public void testSubmit() throws IOException {
        
        SimpleThreadPool tp = new SimpleThreadPool(4);
        
        for (int i = 0; i < 150; i++) {
            
            String task = "task" + i;
            
            tp.submit(
                    () -> System.out.println(task)
            );
        }
        
        System.in.read();
    }

}
