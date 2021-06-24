package pers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.flow.FlowBuilder;

/**
 * @auther ken.ck
 * @date 2021/6/19 22:42
 */
@SpringBootTest(classes=pers.Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class FlowBuilderTest {

    @Autowired
    private FlowBuilder flowBuilder;

    @Test
    public void testGetJavaNode() {
        flowBuilder.buildFlow("");
    }

}
