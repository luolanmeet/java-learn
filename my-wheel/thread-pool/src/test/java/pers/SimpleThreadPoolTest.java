package pers;

import java.io.IOException;

import org.junit.Test;

public class SimpleThreadPoolTest {
    
    @Test
    public void testSubmit() throws IOException {
        
        SimpleThreadPool tp = new SimpleThreadPool(4);
        
        for (int i = 0; i < 150; i++) {
            
            String task = "task" + i;
            
            tp.submit(
                    new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(task);
                        }
                    }
                );
        }
        
        System.in.read();
    }

}
